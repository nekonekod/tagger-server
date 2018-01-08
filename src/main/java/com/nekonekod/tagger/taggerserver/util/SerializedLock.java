package com.nekonekod.tagger.taggerserver.util;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duwenjun
 * @date 2018/1/8
 */
@Log4j2
public class SerializedLock {

    private static ReentrantLock lock = new ReentrantLock();

    public static void run(Callable r) {
        try {
            lock.lock();
            r.call();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }


}
