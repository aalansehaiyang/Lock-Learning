### 锁汇总

---

#### :point_right: 乐观锁

分为三个阶段：数据读取、写入校验、数据写入。

假设数据一般情况下不会造成冲突，只有在数据进行提交更新时，才会正式对数据的冲突与否进行检测，如果发现冲突了，则返回错误信息，让用户决定如何去做。fail-fast机制。

https://github.com/aalansehaiyang/technology-talk/blob/master/system-architecture/%E9%94%81%E6%9C%BA%E5%88%B6.md


#### :point_right: 悲观锁

正如其名，它指对数据被外界（可能是本机的其他事务，也可能是来自其它服务器的事务处理）的修改持保守态度。在整个数据处理过程中，将数据处于锁定状态。悲观锁大多数情况下**依靠数据库的锁机制实现，以保证操作最大程度的独占性**。如果加锁的时间过长，其他用户长时间无法访问，影响程序的并发访问性，同时这样对数据库性能开销影响也很大，特别是长事务而言，这样的开销往往无法承受。

#### :point_right: 分布式锁

分布式集群中，对锁接口QPS性能要求很高，单台服务器满足不了要求，可以考虑将锁服务部署在独立的分布式系统中，比如借助分布式缓存来实现。

* [基于 redis分布式锁](https://github.com/aalansehaiyang/technology-talk/blob/master/system-architecture/%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81.md)
* [基于 zookeeper实现的分布式锁](https://github.com/aalansehaiyang/technology-talk/blob/master/system-architecture/lock-zk.md)


#### :point_right: 可重入锁

可重入锁，也叫做递归锁，是指在同一个线程在调外层方法获取锁的时候，再进入内层方法会自动获取锁。ReentrantLock 和synchronized 都是 可重入锁。可重入锁的一个好处是可一定程度避免死锁。

http://ifeve.com/java_lock_see4/

代码示例：$Link {com.lock.reentrant.ReentrantTest}

#### :point_right: 自旋锁

自旋锁是采用让当前线程不停地在循环体内执行，当循环的条件被其他线程改变时才能进入临界区。

自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，所以响应速度更快。但当线程数不断增加时，性能下降明显，因为每个线程都需要执行，会占用CPU时间片。如果线程竞争不激烈，并且保持锁的时间段。适合使用自旋锁。

http://ifeve.com/java_lock_see1/

代码示例：$Link {com.lock.spin.SpinLockTest}

#### :point_right: 独享锁

独享锁是指该锁一次只能被一个线程所持有。

ReentrantLock 、Synchronized 都是独享锁。

http://blog.csdn.net/ns_code/article/details/17487337

#### :point_right: 共享锁

共享锁是指该锁可被多个线程所持有。ReentrantReadWriteLock，其读锁是共享锁，其写锁是独享锁。读锁的共享锁可保证并发读是非常高效的，读写、写读、写写的过程是互斥的。独享锁与共享锁也是通过AQS（AbstractQueuedSynchronizer）来实现的，通过实现不同的方法，来实现独享或者共享。

#### :point_right: 互斥锁

独享锁/共享锁就是一种广义的说法，互斥锁/读写锁指具体的实现。

互斥锁在Java中的具体实现就是ReentrantLock

#### :point_right: 读写锁

读写锁在Java中的具体实现就是ReentrantReadWriteLock

http://blog.csdn.net/yanyan19880509/article/details/52435135

http://blog.csdn.net/eson_15/article/details/51553614

代码示例：$Link {com.lock.readwrite.ReentrantReadWriteLockTest}

#### :point_right: 阻塞锁

阻塞锁，可以说是让线程进入阻塞状态进行等待，当获得相应的信号（唤醒，时间） 时，才可以进入线程的准备就绪状态，准备就绪状态的所有线程，通过竞争，进入运行状态。

JAVA中，能够进入\退出、阻塞状态或包含阻塞锁的方法有 ，synchronized 关键字（其中的重量锁），ReentrantLock，Object.wait()/notify()，LockSupport.park()/unpark()

http://ifeve.com/java_lock_see3/

#### :point_right: 公平锁

公平锁是指多个线程按照申请锁的顺序来获取锁

#### :point_right: 非公平锁

非公平锁是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁。

可能造成优先级反转或者饥饿现象。对于Java ReentrantLock而言，通过构造函数 ReentrantLock(boolean fair) 指定该锁是否是公平锁，默认是非公平锁。

非公平锁的优点在于吞吐量比公平锁大。对于Synchronized而言，也是一种非公平锁。

#### :point_right: 分段锁

分段锁其实是一种锁的设计，目的是细化锁的粒度，并不是具体的一种锁，对于ConcurrentHashMap而言，其并发的实现就是通过分段锁的形式来实现高效的并发操作。

ConcurrentHashMap中的分段锁称为Segment，它即类似于HashMap（JDK7 中HashMap的实现）的结构，即内部拥有一个Entry数组，数组中的每个元素又是一个链表；同时又是一个ReentrantLock（Segment继承了ReentrantLock)。

当需要put元素的时候，并不是对整个HashMap加锁，而是先通过hashcode知道要放在哪一个分段中，然后对这个分段加锁，所以当多线程put时，只要不是放在同一个分段中，可支持并行插入。


#### :point_right: 对象锁

一个线程可以多次对同一个对象上锁。对于每一个对象，java虚拟机维护一个加锁计数器，线程每获得一次该对象，计数器就加1，每释放一次，计数器就减 1，当计数器值为0时，锁就被完全释放了。

在java程序中，只需要使用synchronized块或者synchronized方法就可以标志一个监视区域。当每次进入一个监视区域时，java 虚拟机都会自动锁上对象或者类。

synchronized修饰非静态方法、同步代码块的synchronized (this)、synchronized (非this对象)，锁的是对象，线程想要执行对应同步代码，需要获得对象锁。

http://blog.csdn.net/u013142781/article/details/51697672

代码示例：$Link {com.lock.object.SynchronizedMethod}

代码示例：$Link {com.lock.object.SynchronizedThis}

代码示例：$Link {com.lock.object.SynchronizedObject}

#### :point_right: 类锁

synchronized修饰静态方法或者同步代码块的synchronized (类.class)，线程想要执行对应同步代码，需要获得类锁。

代码示例：$Link {com.lock.class1.SynchronizedStaticMethod}

代码示例：$Link {com.lock.class1.SynchronizedClass}

#### :point_right: 信号量

Semaphore是用来保护一个或者多个共享资源的访问，Semaphore内部维护了一个计数器，其值为可以访问的共享资源的个数。一个线程要访问共享资源，先获得信号量，如果信号量的计数器值大于1，意味着有共享资源可以访问，则使其计数器值减去1，再访问共享资源。

如果计数器值为0,线程进入休眠。当某个线程使用完共享资源后，释放信号量，并将信号量内部的计数器加1，之前进入休眠的线程将被唤醒并再次试图获得信号量。

代码示例：$Link {com.lock.semaphore.SemaphoreTest}

#### :point_right: 条件变量Condition

条件变量很大一个程度上是为了解决Object.wait/notify/notifyAll难以使用的问题。

有人会问，如果一个线程lock() 后被挂起还没有执行unlock()，那么另外一个线程就拿不到锁，那么就无法唤醒前一个线程signal()，这样岂不是“死锁”了？

```
解释：
进入lock.lock()后唯一可能释放锁的操作就是await()。也就是说await()操作实际上就是释放锁，然后挂起线程，一旦条件满足就被唤醒，再次获取锁！
```

http://blog.csdn.net/vernonzheng/article/details/8288251

代码示例：$Link {com.lock.condition.ConditionDemo}

#### :point_right: 行级锁


行级锁是数据库引擎中对记录更新的时候引擎本身上的锁，是数据库引擎的一部分，在数据库引擎更新一条数据的时候，本身就会对记录上锁，这时候即使有多个请求更新，也不会产生脏数据，行级锁的粒度非常细，上锁的时间窗口也最少，只有更新数据记录的那一刻，才会对记录上锁，因此，能大大减少数据库操作的冲突，发生锁冲突的概率最低，并发度也最高。

通常在扣减库存的场景下使用行级锁，这样可以通过数据库引擎本身对记录加锁的控制，保证数据库的更新的安全性，并且通过where语句的条件，保证库存不会被减到0以下，也就是能够有效的控制超卖的场景。

```
update ... set amount = amount - 1 where id = $id and amount - 1 >=0 
```

另外一种场景是在状态转换的时候使用行级锁，例如交易引擎中，状态只能从init流转到doing状态，任何重复的从init到doing的流转，或者从init到finished等其他状态的流转都会失败。

```
boolean result = executeSql("update ... set status = 'doing' where id = $id and status = 'init'"); 
if (result) 
	{   // process sucessful logic } 
else 
	{   // process failure logic }
```

行级锁的并发性较高，性能是最好的，适用于高并发下扣减库存和控制状态流转的方向的场景。

但是，有人说这种方法是不能保证幂等的，比如说，在扣减余额场景，多次提交可能会扣减多次，这确实是实际存在的，但是，我们是有应对方案的，我们可以记录扣减的操作日志，如果有非幂等的场景出现，通过比较操作记录。

```
boolean result = executeSql("update ... set amount = amount - 1 where id = $id and amount > 1"); 
if (result) 
	{    executeSql("insert into hist (pre_amout, post_amout) values ($amount, $amount -1)");    
	// process successful logic 
	} else {
	    // process failure logic 
}
```

`在支付平台架构设计中，通常对交易和支付系统的流水表的状态流转的控制、对账户系统的状态控制，分账和退款余额的更新等，都推荐使用行级锁，而单独使用乐观锁和悲观锁是不推荐的。`



#### :point_right: 其它

```
Synchronized：非公平，悲观，独享，互斥，可重入的重量级锁
ReentrantLock：默认非公平但可实现公平的(构造器传true)，悲观，独享，互斥，可重入，重量级锁。
ReentrantReadWriteLocK：默认非公平但可实现公平的(构造器传true)，悲观，写独享，读共享，读写，可重入，重量级锁。
```

线程A和B都要获取对象o的锁定，假设A获取了对象o锁，B将等待A释放对o的锁定

* synchronized ，如果A不释放，B将一直等下去，不能被中断
* ReentrantLock，如果A不释放，可以使B在等待了足够长的时间以后，中断等待，而干别的事情

ReentrantLock获取锁定有三种方式：

* lock()， 如果获取了锁立即返回，如果别的线程持有锁，当前线程则一直处于休眠状态，直到获取锁
 
* tryLock()， 如果获取了锁立即返回true，如果别的线程正持有锁，立即返回false

* tryLock(long timeout,TimeUnit unit)， 如果获取了锁定立即返回true，如果别的线程正持有锁，会等待参数给定的时间，在等待的过程中，如果获取了锁定，就返回true，如果等待超时，返回false；

* lockInterruptibly()，如果获取了锁定立即返回，如果没有获取锁，当前线程处于休眠状态，直到获取锁定，或者当前线程被别的线程中断

11

### 其它

* [Java 各种锁的小结](https://mp.weixin.qq.com/s/_xazrXa8MBYaz2WaX6BNew)
