package com.lock.object;

/**
 * 同步代码块的synchronized (this)
 * 
 * @author onlyone
 */
public class SynchronizedThis {

    public void m1() {
        synchronized (this) {
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
        synchronized (this) {
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

        private SynchronizedThis synchronizedThis;

        public Task1(SynchronizedThis synchronizedThis){
            this.synchronizedThis = synchronizedThis;
        }

        @Override
        public void run() {
            synchronizedThis.m1();
        }
    }

    static class Task2 implements Runnable {

        private SynchronizedThis synchronizedThis;

        public Task2(SynchronizedThis synchronizedThis){
            this.synchronizedThis = synchronizedThis;
        }

        @Override
        public void run() {
            synchronizedThis.m2();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedThis syn = new SynchronizedThis();
        new Thread(new Task1(syn)).start();
        new Thread(new Task2(syn)).start();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }

}
