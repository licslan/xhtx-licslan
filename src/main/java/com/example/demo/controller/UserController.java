package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**   *
 * @author licslan
 * */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

        return userService.findAllUser(pageNum,pageSize);
    }


    /** spring 事务理解
     *
     * package org.springframework.transaction.annotation;
     *
     * public enum Isolation {
     *     DEFAULT(-1),
     *     READ_UNCOMMITTED(1),
     *     READ_COMMITTED(2),
     *     REPEATABLE_READ(4),
     *     SERIALIZABLE(8);
     *
     *     private final int value;
     *
     *     private Isolation(int value) {
     *         this.value = value;
     *     }
     *
     *     public int value() {
     *         return this.value;
     *     }
     * }
     *
     *
     *
     * DEFAULT：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是：READ_COMMITTED。
     * READ_UNCOMMITTED：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
     * READ_COMMITTED：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
     * REPEATABLE_READ：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
     * SERIALIZABLE：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。
     * 指定方法：通过使用isolation属性设置，例如：
     *
     * @Transactional(isolation = Isolation.DEFAULT)

     * 传播行为
     * 所谓事务的传播行为是指，如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为。
     *
     * 我们可以看org.springframework.transaction.annotation.Propagation枚举类中定义了6个表示传播行为的枚举值：
     *
     * public enum Propagation {
     *     REQUIRED(0),
     *     SUPPORTS(1),
     *     MANDATORY(2),
     *     REQUIRES_NEW(3),
     *     NOT_SUPPORTED(4),
     *     NEVER(5),
     *     NESTED(6);
     * }

     * REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
     * SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
     * MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
     * REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
     * NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
     * NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。
     * NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于REQUIRED。
     * 指定方法：通过使用propagation属性设置，例如：
     *
     * @Transactional(propagation = Propagation.REQUIRED)
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * 事务管理是应用系统开发中必不可少的一部分。Spring 为事务管理提供了丰富的功能支持。Spring 事务管理分为编程式和声明式的两种方式。编程式事务指的是通过编码方式实现事务；声明式事务基于 AOP,将具体业务逻辑与事务处理解耦。声明式事务管理使业务代码逻辑不受污染, 因此在实际使用中声明式事务用的比较多。声明式事务有两种方式，一种是在配置文件（xml）中做相关的事务规则声明，另一种是基于 @Transactional 注解的方式。本文将着重介绍基于 @Transactional 注解的事务管理。
     *
     * 需要明确几点：
     *
     * 默认配置下 Spring 只会回滚运行时、未检查异常（继承自 RuntimeException 的异常）或者 Error。参考这里
     * @Transactional 注解只能应用到 public 方法才有效。参考这里 Method visibility and @Transactional
     * 以下的示例使用的是 mybatis，所以 spring boot 会自动配置一个 DataSourceTransactionManager，我们只需在方法（或者类）加上 @Transactional 注解，就自动纳入 Spring 的事务管理了。
     *
     * 简单的使用方法
     * 只需在方法加上 @Transactional 注解就可以了。
     *
     * 如下有一个保存用户的方法，加入 @Transactional 注解，使用默认配置，抛出异常之后，事务会自动回滚，数据不会插入到数据库。
     *
     * @Transactional
     * @Override
     * public void save() {
     *     User user = new User("服部半藏");
     *     userMapper.insertSelective(user);
     *
     *     if (true) {
     *         throw new RuntimeException("save 抛异常了");
     *     }
     * }
     *
     * plz see the bolg
     * https://blog.csdn.net/nextyu/article/details/78669997
     *
     * 注入方式的几种 学习
     *
     *TODO  。。。。。
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * mybatis 与  spring-boot整合  两种方式
     * 1.一种是xml（代码格式的配置方式注入）
     * 代码里面会写sqlsessionfactory  相关代码
     *
     * 2.mybatis-spring-boot-starter  此种方式  mybatis团队提供的
     * 没有
     *
     *
     *mybatis开发团队为Spring Boot 提供了 MyBatis-Spring-Boot-Starter 。
     * 首先，MyBatis-Spring-Boot-Starter will:
     *
     * Autodetect an existing DataSource.
     * Will create and register an instance of a SqlSessionFactoryBean passing that DataSource as an input.
     * Will create and register an instance of a SqlSessionTemplate got out of the SqlSessionFactoryBean.
     * Autoscan your mappers, link them to the SqlSessionTemplate and register them to Spring context so they can be injected into your beans.
     * 来源： http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html
     *
     * 就是说，使用了该Starter之后，只需要定义一个DataSource即可，它会自动创建使用该DataSource的SqlSessionFactoryBean以及SqlSessionTemplate。
     * 会自动扫描你的Mappers，连接到SqlSessionTemplate，并注册到Spring上下文中。
     *
     *
     *
     *
     *
     *
     * MyBatis-Spring 使用总结
     * 说明：Java-based Config。
     *
     * 不是通过 mybatis 的 SqlSessionFactoryBuilder 来创建 SqlSessionFactory ，而是通过 mybatis-spring 的 SqlSessionFactoryBean 来获取。
     *
     * 1、首先要有一个DataSource 。
     * 需要注意，事务管理器也在这里注册。（mybatis-spring插件会自动调用该事务管理器）
     * 复制代码
     *     @Bean(name = "transactionManager")
     *     public DataSourceTransactionManager dataSourceTransactionManager() {
     *         DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
     *         dataSourceTransactionManager.setDataSource(this.dataSource());
     *         return dataSourceTransactionManager;
     *     }
     * 复制代码
     *
     * 2、然后，注册SqlSessionFactoryBean（或者SqlSessionFactory，二选一，内容一致）。如下：
     * 复制代码
     *     @Bean(name = "sqlSessionFactory")
     *     public SqlSessionFactory sqlSessionFactory() throws Exception {
     *         SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
     *         sqlSessionFactoryBean.setDataSource(this.dataSource());
     *         // sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml")); // 这里可以通过mybatis-config.xml 来设置 typeAliasPackage和mapper。
     *         // Resource[] mapperLocations = new Resource[] { new ClassPathResource("com.expert.dao") }; // 这个和@MapperScan冲突吗？这个设置有问题。
     *         // sqlSessionFactoryBean.setMapperLocations(mapperLocations);//<mappers>
     *         sqlSessionFactoryBean.setTypeAliasesPackage(PojoBasePackage);
     *         // sqlSessionFactoryBean.setCache(cache);
     *         SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
     *         // It can be specified a Configuration instance directly without MyBatis XML configuration file.
     *         sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);// 开启驼峰映射
     *         sqlSessionFactory.getConfiguration().setCacheEnabled(true);
     *         sqlSessionFactory.getConfiguration().setLazyLoadingEnabled(true);
     *         sqlSessionFactory.getConfiguration().setAggressiveLazyLoading(false);
     *         // Class<Object> logImpl = sqlSessionFactory.getConfiguration().getTypeAliasRegistry().resolveAlias("SLF4J");
     *         sqlSessionFactory.getConfiguration().setLogImpl(Slf4jImpl.class);// logImpl
     *         sqlSessionFactory.getConfiguration().setLogPrefix("###SPRING_BOOT###MYBATIS###");
     *         sqlSessionFactory.getConfiguration().setDefaultExecutorType(ExecutorType.REUSE);
     *         sqlSessionFactory.getConfiguration().setUseGeneratedKeys(true);
     *         return sqlSessionFactory;
     *     }
     * 复制代码
     * 这里还设置了一堆参数。需要注意的是，
     * ①设置了 TypeAliasesPackage 。
     * ②设置了 Configuration 。
     * ③mybatis-spring会自动创建 Configuration 对象，所以通过 sqlSessionFactory.getConfiguration() 即可获取并进行设置。
     *
     * 3、再注册一个 SqlSessionTemplate，这是 mybatis-spring 的核心。
     *     @Bean
     *     @Scope(BeanDefinition.SCOPE_PROTOTYPE) // 多例？
     *     public SqlSessionTemplate sqlSessionTemplate() throws Exception {
     *         return new SqlSessionTemplate(this.sqlSessionFactory());
     *     }
     *
     * 4、设置mapper的位置，有两种方法。
     * ①推荐这种，简单。
     * @Configuration
     * @MapperScan(basePackages = { "com.expert.dao" })
     * public class DruidDataSourceConfig{
     *     // ...
     * }
     * ②
     * 复制代码
     *     @Bean
     *     public MapperScannerConfigurer mapperScannerConfigurer() {
     *         MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
     *         mapperScannerConfigurer.setBasePackage(DaoBasePackage);
     *         mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
     *         return mapperScannerConfigurer;
     *     }
     * 复制代码
     *
     *
     * 至此，已可以成功运行。
     *
     * 但是，还有更简单的方法，那就是 MyBatis-Spring-Boot-Starter 。使用该Starter时，会自动查找DataSource，
     * 并自动创建SqlSessionFactoryBean 和 SqlSessionTemplate。所以，只需要设置mapper所在的位置和别名所在的包即可。
     *  */


}