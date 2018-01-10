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
    private ConcurrentHashMap<Path, Set<Path>> dirs; //TODO key use string

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

    public List<Path> search(String fileName) {
        if (StringUtil.isNullOrEmpty(fileName)) return Collections.emptyList();
        return Collections.list(dirs.keys())
                .parallelStream()
                .map(key -> fileSet(key).map(set -> set.stream().filter(path -> path.getFileName().toString().contains(fileName)).collect(Collectors.toList())).orElse(Collections.emptyList()))
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Path> search(List<String> fileNames) {
        return searchThenMap(fileNames, Function.identity(), (string, path) -> path);
    }

    public <T, R> List<R> searchThenMap(List<T> src, Function<T, String> keyGen, BiFunction<T, Path, R> mapper) {
        if (CollectionUtil.isEmpty(src)) return Collections.emptyList();
        return Collections.list(dirs.keys())
                .parallelStream()
                .map(key -> fileSet(key).map(set -> {
                    //dir并行查找其fileSet
                    return set.stream().map(path -> {
                        // match any of fileNames
                        return src.stream().filter(t -> {
                            String fName = keyGen.apply(t);
                            return path.getFileName().toString().contains(fName);
                        }).findAny().map(t -> mapper.apply(t, path)).orElse(null);
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                }).orElse(Collections.emptyList()))
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void registerDir(File dir) {
        if (Objects.nonNull(dir) && dir.exists() && dir.isDirectory()) {
            ConcurrentSkipListSet<Path> files = new ConcurrentSkipListSet<>();
            Path path = dir.toPath();
            try {
                path.register(watcher, eventKinds, modifier);
                dirs.put(path, files);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                Arrays.stream(listFiles).forEach(fileOrDir -> {
                    if (fileOrDir.exists() && fileOrDir.isFile()) {
                        addFile(path, fileOrDir); //文件
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
            File[] files = dir.listFiles();
            if (files != null) Arrays.stream(files).forEach(this::removeDir);
            removeDir(dir);
            print();
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
        return Collections.list(dirs.keys()).stream().map(Path::toString).collect(Collectors.toList());
    }

    /**
     * dirs中移除key，不遍历子文件夹
     */
    private void removeDir(File dir) {
        if (Objects.nonNull(dir) && dir.exists() && dir.isDirectory()) {
            Collections.list(dirs.keys())
                    .stream()
                    .filter(p -> p.toString().equals(dir.toPath().toString()))
                    .findAny()
                    .ifPresent(key -> {
                        log.info("findAny key:{}", key);
                        dirs.remove(key);
                    });
        }
    }

    /**
     * 如果监听dir，则向dir中加入file
     *
     * @param dir
     * @param file
     */
    private void addFile(Path dir, File file) {
        if (Objects.nonNull(file) && file.exists() && file.isFile())
            fileSet(dir).ifPresent(set -> set.add(file.toPath()));
    }

    /**
     * 如果监听dir，则向dir中移除file
     *
     * @param dir
     * @param file
     */
    private void removeFile(Path dir, File file) {
        if (Objects.nonNull(file) && file.exists() && file.isFile())
            fileSet(dir).ifPresent(set -> set.remove(file.toPath()));
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
                        addFile(parent, file);
                    } else if (StandardWatchEventKinds.ENTRY_DELETE == kind) {
                        //删除
                        unregisterDir(file);
                        removeFile(parent, file);
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
     * 是否监听此文件夹
     *
     * @param dir
     * @return
     */
    private Optional<Path> isWatched(Path dir) {
        if (Objects.isNull(dir)) return Optional.empty();
        return Collections.list(dirs.keys()).stream().filter(p -> p.toString().equals(dir.toString())).findFirst();
    }

    /**
     * 如果监听此文件夹，则返回文件夹内的文件集合
     *
     * @param dir
     * @return
     */
    private Optional<Set<Path>> fileSet(Path dir) {
        return isWatched(dir).map(dirs::get);
    }

    private void print() {
        StringBuilder builder = new StringBuilder();
        dirs.forEach((dir, files) -> {
            String d = dir.toString() + "\n";
            String f = "\t|-" + files.stream().map(Path::toString).collect(Collectors.joining("\n\t|-"));
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
