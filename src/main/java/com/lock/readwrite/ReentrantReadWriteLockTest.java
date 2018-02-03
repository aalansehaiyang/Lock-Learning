package com.lock.readwrite;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * 
 * @author onlyone
 */
public class ReentrantReadWriteLockTest {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        // 启动20个读任务
        for (int i = 1; i <= 20; i++) {
            new Thread(new ReadTask(countDownLatch, lock)).start();
        }

        // 启动20个写任务
        for (int i = 1; i <= 15; i++) {
            new Thread(new WriteTask(countDownLatch, lock)).start();
        }

        countDownLatch.countDown();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }

}
