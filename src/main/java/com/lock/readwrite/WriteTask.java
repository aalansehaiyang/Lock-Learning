package com.lock.readwrite;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 写任务
 * 
 * @author onlyone
 */
public class WriteTask implements Runnable {

    private CountDownLatch         countDownLatch;

    private ReentrantReadWriteLock lock;

    public WriteTask(CountDownLatch countDownLatch, ReentrantReadWriteLock lock){
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

        System.out.println(name + " 尝试请求write锁,,,,,,,");
        WriteLock writeLock = lock.writeLock();
        writeLock.lock();

        System.out.println(name + " 已拿到write锁，开始处理业务");

        // 模拟业务处理
        try {
            Thread.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " 释放write锁！！！！！！！！！！！！");
        writeLock.unlock();

    }

}
