package com.lock.condition;

public class TestClass {

    // 写任务
    static class WriteTask implements Runnable {

        private ConditionDemo condition;

        public WriteTask(ConditionDemo condition){
            this.condition = condition;
        }

        @Override
        public void run() {
            for (int k = 1; k <= 20; k++) {
                condition.put();
            }
        }
    }

    // 读任务
    static class ReadTask implements Runnable {

        private ConditionDemo condition;

        public ReadTask(ConditionDemo condition){
            this.condition = condition;
        }

        public void run() {
            for (int k = 1; k <= 20; k++) {
                condition.take();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo condition = new ConditionDemo();

        new Thread(new WriteTask(condition)).start();
        new Thread(new ReadTask(condition)).start();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);
    }

}
