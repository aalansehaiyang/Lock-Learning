package com.lock.object;

/**
 * synchronized修饰非静态方法
 * 
 * @author onlyone
 */
public class SynchronizedMethod {

    public synchronized void m1() {
        System.out.println("m1方法获得锁");
        try {
            Thread.sleep(1200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m1方法释放锁");
    }

    public synchronized void m2() {
        System.out.println("m2方法获得锁");
        try {
            Thread.sleep(1200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m2方法释放锁");
    }

    static class Task1 implements Runnable {

        private SynchronizedMethod synchronizedMethod;

        public Task1(SynchronizedMethod synchronizedMethod){
            this.synchronizedMethod = synchronizedMethod;
        }

        @Override
        public void run() {
            synchronizedMethod.m1();
        }
    }

    static class Task2 implements Runnable {

        private SynchronizedMethod synchronizedMethod;

        public Task2(SynchronizedMethod synchronizedMethod){
            this.synchronizedMethod = synchronizedMethod;
        }

        @Override
        public void run() {
            synchronizedMethod.m2();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedMethod syn = new SynchronizedMethod();
        new Thread(new Task1(syn)).start();
        new Thread(new Task2(syn)).start();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }
}
