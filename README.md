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

示例：$Link {com.lock.reentrant.ReentrantTest}

http://ifeve.com/java_lock_see4/

#### :point_right: 互斥锁

#### :point_right: 读写锁

#### :point_right: 自旋锁

自旋锁是采用让当前线程不停地在循环体内执行，当循环的条件被其他线程改变时才能进入临界区。

自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，所以响应速度更快。但当线程数不断增加时，性能下降明显，因为每个线程都需要执行，会占用CPU时间片。如果线程竞争不激烈，并且保持锁的时间段。适合使用自旋锁。

http://ifeve.com/java_lock_see1/

#### :point_right: 独享锁

https://www.cnblogs.com/lxmyhappy/p/7380073.html

#### :point_right: 共享锁

#### :point_right: 阻塞锁

阻塞锁，可以说是让线程进入阻塞状态进行等待，当获得相应的信号（唤醒，时间） 时，才可以进入线程的准备就绪状态，准备就绪状态的所有线程，通过竞争，进入运行状态。

JAVA中，能够进入\退出、阻塞状态或包含阻塞锁的方法有 ，synchronized 关键字（其中的重量锁），ReentrantLock，Object.wait()/notify()，LockSupport.park()/unpark()

http://ifeve.com/java_lock_see3/

#### :point_right: 公平锁
#### :point_right: 非公平锁
#### :point_right: 偏向锁
#### :point_right: 对象锁
#### :point_right: 线程锁
#### :point_right: 信号量

