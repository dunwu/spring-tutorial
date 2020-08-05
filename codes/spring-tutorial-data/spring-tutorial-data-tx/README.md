# Spring 事务使用示例

## Spring 事务最佳实践

### Spring 事务未生效

- @Transactional 方法必须是 public
- 必须通过 Spring 注入的 Bean 进行调用

### 事务虽然生效但未回滚

@Transactional 方法只有出现 RuntimeException 和 Error 的时候回滚。

解决方案：

- 手动回滚：TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
- 回滚所有异常：​@Transactional(rollbackFor = Exception.class)

### 细化事务传播方式

@Transactional 注解的 Propagation 属性
