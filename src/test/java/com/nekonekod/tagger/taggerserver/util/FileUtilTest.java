package com.nekonekod.tagger.taggerserver.util;

import com.sun.nio.file.SensitivityWatchEventModifier;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

/**
 * @author duwenjun
 * @date 2017/12/28
 */
@Ignore
public class FileUtilTest {

    public void mockFile() throws IOException {
        // file/1.txt
        // file/2.txt
        // file/dir1/
        // file/dir2/
        // file/dir1/3.txt
        FileUtil.touch(Paths.get("file", "1.txt").toFile());
        File txt2 = Paths.get("file", "2.txt").toFile();
        try (FileOutputStream out = new FileOutputStream(txt2)) {
            out.write("Hello".getBytes());
        }
        FileUtil.touch(txt2);

        Paths.get("file", "dir1").toFile().mkdirs();
        Paths.get("file", "dir2").toFile().mkdirs();
        FileUtil.touch(Paths.get("file", "dir1", "3.txt").toFile());
    }

    public void testMove() throws IOException {
        //move file
        FileUtil.moveToDirectory(
                Paths.get("file", "1.txt").toFile(),
                Paths.get("file", "dir3").toFile(),
                true);
        //move dir
        FileUtil.moveToDirectory(
                Paths.get("file", "dir1").toFile(),
                Paths.get("file", "dir3").toFile(),
                true);
    }

    public void testCopy() throws IOException {
        FileUtil.copyFile(
                Paths.get("file", "2.txt").toFile(),
                Paths.get("file", "2_copy.txt").toFile());
        FileUtil.copyDirectory(
                Paths.get("file", "dir3").toFile(),
                Paths.get("file", "dir3_copy").toFile());
    }

    public void testRead() throws IOException {
        String content = FileUtil.readFileToString(Paths.get("file", "2_copy.txt").toFile(), Charset.defaultCharset());
        assert "Hello".equals(content);
    }


    public void testDeleteFile() throws IOException {
        FileUtil.forceDelete(Paths.get("file", "2_copy.txt").toFile());
    }

    public void testCleanDir() throws IOException {
        FileUtil.cleanDirectory(Paths.get("file").toFile());
    }

    public void testDeleteDir() throws IOException {
        FileUtil.forceDelete(Paths.get("file").toFile());
    }

    @Test
    public void test() throws IOException, InterruptedException {
        //watch
        File rootDir = Paths.get("file").toFile();
        FileUtil.forceMkdir(rootDir);
        WatchService watcher = FileSystems.getDefault().newWatchService();

        rootDir.toPath().register(watcher,
                new WatchEvent.Kind[]{
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                },
                SensitivityWatchEventModifier.HIGH);

        new Thread(() -> {
            try {
//                Thread.sleep(5000);
                int wait = 2000;
                mockFile();
                Thread.sleep(wait);
                testMove();
                Thread.sleep(wait);
                testCopy();
                Thread.sleep(wait);
                testRead();
                Thread.sleep(wait);
                testDeleteFile();
                Thread.sleep(wait);
                testCleanDir();
                Thread.sleep(wait);
//                testDeleteDir();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();

        final int[] cnt = {0};
        while (true) {
//            WatchKey watchKey = watcher.poll(1000, TimeUnit.MILLISECONDS);
            WatchKey watchKey = watcher.take();
            List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
            watchEvents.forEach(e -> {
                cnt[0] += 1;
                System.out.println(cnt[0] + ":context:" + e.context().toString() + ",count:" + e.count() + ",kind:" + e.kind());
            });
            if (!watchKey.reset()) {
                System.out.println("exit watch server");
                break;
            }
        }
    }

}