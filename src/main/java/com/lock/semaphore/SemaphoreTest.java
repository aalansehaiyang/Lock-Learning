package com.lock.semaphore;

import java.util.concurrent.CountDownLatch;

/**
 * 信号量锁
 * 
 * @author onlyone
 */
public class SemaphoreTest implements Runnable {

    private ResourceManage resourceManage;
    private int            userId;
    private CountDownLatch countDownLatch;

    public SemaphoreTest(ResourceManage resourceManage, int userId, CountDownLatch countDownLatch){
        this.resourceManage = resourceManage;
        this.userId = userId;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("userId:" + userId + "准备使用资源...\n");
        resourceManage.useResource(userId);
        System.out.print("userId:" + userId + "使用资源完毕...\n");
    }

    public static void main(String[] args) throws InterruptedException {
        ResourceManage resourceManage = new ResourceManage();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 1; i <= 30; i++) {
            new Thread(new SemaphoreTest(resourceManage, i, countDownLatch)).start(); // 创建多个资源使用者
        }

        countDownLatch.countDown();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);

    }
}
