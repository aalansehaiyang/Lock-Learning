package com.lock.readwrite;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

/**
 * 读任务
 * 
 * @author onlyone
 */

public class ReadTask implements Runnable {

    private CountDownLatch         countDownLatch;

    private ReentrantReadWriteLock lock;

    public ReadTask(CountDownLatch countDownLatch, ReentrantReadWriteLock lock){
        this.countDownLatch = countDownLatch;
        this.lock = lock;
    }

    @Override
    public void run() {

        try {
            countDownLatch.await();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String name = Thread.currentThread().getName();

        System.out.println(name + " 尝试请求read锁,,,,,,,");
        ReadLock readLock = lock.readLock();
        readLock.lock();

        System.out.println(name + " 已拿到read锁，开始处理业务");

        // 模拟业务处理
        try {
            Thread.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " 释放read锁！！！！！！！！！！！！");
        readLock.unlock();

    }
}
