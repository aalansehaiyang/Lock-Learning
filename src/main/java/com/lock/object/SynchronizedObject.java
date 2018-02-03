package com.lock.object;

/**
 * synchronized (非this对象)
 * 
 * @author onlyone
 */
public class SynchronizedObject {

    // private Object lock = new Object();

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void m1() {
        synchronized (lock1) {
            System.out.println("m1方法获得锁");
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("m1方法释放锁");
        }
    }

    public void m2() {
        synchronized (lock2) {
            System.out.println("m2方法获得锁");
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("m2方法释放锁");

        }
    }

    static class Task1 implements Runnable {

        private SynchronizedObject synchronizedObject;

        public Task1(SynchronizedObject synchronizedObject){
            this.synchronizedObject = synchronizedObject;
        }

        @Override
        public void run() {
            synchronizedObject.m1();
        }
    }

    static class Task2 implements Runnable {

        private SynchronizedObject synchronizedObject;

        public Task2(SynchronizedObject synchronizedObject){
            this.synchronizedObject = synchronizedObject;
        }

        @Override
        public void run() {
            synchronizedObject.m2();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedObject syn = new SynchronizedObject();
        new Thread(new Task1(syn)).start();
        new Thread(new Task2(syn)).start();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }

}
