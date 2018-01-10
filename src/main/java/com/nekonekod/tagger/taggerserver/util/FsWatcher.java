package com.nekonekod.tagger.taggerserver.util;

import com.sun.nio.file.SensitivityWatchEventModifier;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author duwenjun
 * @date 2017/12/28
 */
@Log4j2
public class FsWatcher {

    private volatile boolean finished = false;

    private WatchEvent.Kind[] eventKinds = new WatchEvent.Kind[]{
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY,
    };
    private WatchEvent.Modifier modifier = SensitivityWatchEventModifier.HIGH;

    private WatchService watcher;
    private ConcurrentHashMap<String, Set<String>> dirs;

    private FsWatcher() {
        dirs = new ConcurrentHashMap<>(100);
        try {
            watcher = FileSystems.getDefault().newWatchService();
            Thread thread = new Thread(this::watch, "FsWatcher.start");
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * Singleton
     *
     * @return
     */
    public static FsWatcher getInstance() {
        return SingletonHolder.INSTANCE.fsWatcher;
    }

    public List<String> search(String fileName) {
        if (StringUtil.isNullOrEmpty(fileName)) return Collections.emptyList();
        return Collections.list(dirs.keys())
                .parallelStream()
                .map(key -> fileSet(key).map(set -> set.stream().filter(path -> path.contains(fileName)).collect(Collectors.toList())).orElse(Collections.emptyList()))
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<String> search(List<String> fileNames) {
        return searchThenMap(fileNames, Function.identity(), (string, fPath) -> fPath);
    }

    public <T, R> List<R> searchThenMap(List<T> src, Function<T, String> keyGen, BiFunction<T, String, R> mapper) {
        if (CollectionUtil.isEmpty(src)) return Collections.emptyList();
        return Collections.list(dirs.keys())
                .parallelStream()
                .map(key -> fileSet(key).map(set -> {
                    //dir并行查找其fileSet
                    return set.stream().map(fPath -> {
                        // match any of fileNames
                        return src.stream().filter(t -> {
                            String fName = keyGen.apply(t);
                            return fPath.contains(fName);
                        }).findAny().map(t -> mapper.apply(t, fPath)).orElse(null);
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                }).orElse(Collections.emptyList()))
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void registerDir(File dir) {
        if (Objects.nonNull(dir) && dir.exists() && dir.isDirectory()) {
            ConcurrentSkipListSet<String> files = new ConcurrentSkipListSet<>();
            try {
                dir.toPath().register(watcher, eventKinds, modifier);
                dirs.put(dir.getAbsolutePath(), files);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                Arrays.stream(listFiles).forEach(fileOrDir -> {
                    if (fileOrDir.exists() && fileOrDir.isFile()) {
                        addFile(dir.getAbsolutePath(), fileOrDir); //文件
                    } else {
                        registerDir(fileOrDir); //文件夹
                    }
                });
            }
            print();
        } else {
            log.warn("{} 不是文件夹", dir);
        }
    }

    public void unregisterDir(File dir) {
        if (Objects.nonNull(dir) && dir.exists() && dir.isDirectory()) {
            String path = dir.getAbsolutePath();
            Collections.list(dirs.keys()).stream().filter(key -> key.startsWith(path)).forEach(dirs::remove);
        } else {
            log.warn("{} 不是文件夹", dir);
        }
    }


    /**
     * 监听着的文件夹
     *
     * @return
     */
    public List<String> watchedDir() {
        return new ArrayList<>(Collections.list(dirs.keys()));
    }

    /**
     * 如果监听dir，则向dir中加入file
     *
     * @param dir
     * @param file
     */
    private void addFile(String dir, File file) {
        if (Objects.nonNull(file) && file.exists() && file.isFile())
            fileSet(dir).ifPresent(set -> set.add(file.getAbsolutePath()));
    }

    /**
     * 如果监听dir，则向dir中移除file
     *
     * @param dir
     * @param file
     */
    private void removeFile(String dir, File file) {
        if (Objects.nonNull(file) && file.exists() && file.isFile()) {
            if (StringUtil.isNullOrEmpty(dir)) {
                dir = file.getParentFile().getAbsolutePath();
            }
            fileSet(dir).ifPresent(set -> set.remove(file.getAbsolutePath()));
        }
    }

    private void watch() {
        while (!finished) {
            try {
                WatchKey key = watcher.take();
                Path parent = (Path) key.watchable();
                key.pollEvents().forEach(e -> {
                    WatchEvent.Kind<?> kind = e.kind();
                    if (StandardWatchEventKinds.OVERFLOW == kind) {
                        return;
                    }
                    Path context = parent.resolve((Path) e.context());
                    File file = context.toFile();
                    log.info("context:{},kind:{}", context, e.kind());
                    if (StandardWatchEventKinds.ENTRY_CREATE == kind) {
                        //创建
                        registerDir(file);
                        addFile(parent.toString(), file);
                    } else if (StandardWatchEventKinds.ENTRY_DELETE == kind) {
                        //删除
                        unregisterDir(file);
                        removeFile(parent.toString(), file);
                    }
                });
                boolean valid = key.reset();
                if (!valid) {
                    dirs.remove(parent);
                }
                print();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                finished = true;
            }
        }
    }

    /**
     * 如果监听此文件夹，则返回文件夹内的文件集合
     *
     * @param dir
     * @return
     */
    private Optional<Set<String>> fileSet(String dir) {
        return Optional.ofNullable(dirs.get(dir));
    }

    private void print() {
        StringBuilder builder = new StringBuilder();
        dirs.forEach((dir, files) -> {
            String d = dir + "\n";
            //String f = "\t|-" + files.stream().collect(Collectors.joining("\n\t|-"));
            builder.append(d);//.append(f);
        });
        log.info("\n============================\n" +
                "{}" +
                "\n============================\n", builder.toString());
    }

    private enum SingletonHolder {
        INSTANCE;
        private FsWatcher fsWatcher = new FsWatcher();
    }

}
