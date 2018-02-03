package com.lock.spin;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自旋锁
 * 
 * @author onlyone
 */
public class SpinLock {

    private AtomicBoolean ab = new AtomicBoolean(false);

    /**
     * <pre>
     * getAndSet原子操作来判断锁状态并尝试获取锁 
     * 缺点：getAndSet底层使用CAS来实现，一直在修改共享变量的值，会引发缓存一致性流量风暴
     * </pre>
     */
    public void lock() {
        while (ab.getAndSet(true)) {
        }
    }

    /**
     * 释放锁
     */
    public void unlock() {
        ab.set(false);
    }
}
