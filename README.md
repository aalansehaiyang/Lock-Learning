### 常见锁

---


#### :snail: 乐观锁

分为三个阶段：数据读取、写入校验、数据写入。

假设数据一般情况下不会造成冲突，只有在数据进行提交更新时，才会正式对数据的冲突与否进行检测，如果发现冲突了，则返回错误信息，让用户决定如何去做。fail-fast机制。

https://github.com/aalansehaiyang/technology-talk/blob/master/system-architecture/%E9%94%81%E6%9C%BA%E5%88%B6.md


#### :snail: 悲观锁

#### :snail: 读写锁

#### :snail: 可重入锁

#### :snail: 分布式锁

