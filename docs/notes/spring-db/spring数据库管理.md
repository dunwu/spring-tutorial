# Spring的数据访问策略

## 异常

- Spring JDBC不同于JDBC，提供了比较丰富的数据异常类型。
- Spring的数据异常并没有与特定的持久化方式相关联，所以异常对于不同的持久化方式都是一致的
- Spring的数据异常都继承自DataAccessException，这种异常是非检查型异常。即，不一定非要捕获Spring所抛出的数据访问异常。




## 数据访问模板化

Spring在数据访问过程中，采用了模板方法设计模式。
它将数据访问过程分为两块：模板和回调。模板管理过程中固定的部分，而回调处理自定义的数据访问代码。
下图中，左边属于模板固定部分，右边属于模板回调部分。
![spring数据访问流程](http://upload-images.jianshu.io/upload_images/3101171-d4b685e27d934b52.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**对于不同的持久化平台，Spring提供了多个可选的模板。**
![模板类表](http://upload-images.jianshu.io/upload_images/3101171-e98a1fe9635b3f1b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 使用DAO支持类

基于模板-回调设计，Spring提供了DAO支持类，而将业务本身的DAO类作为它的子类。

下图展示了模板类、DAO支持类以及自定义DAO实现之间的关系。
![DAO关系图](http://upload-images.jianshu.io/upload_images/3101171-4a9bf6f7ce95428a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Spring不仅提供了多个数据模板实现类，还为每种模板提供了对应的DAO支持类。
![DAO支持类表](http://upload-images.jianshu.io/upload_images/3101171-373f10dd0d377aed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 配置数据源

## 使用JNDI数据源

如果Spring应用部署在支持JNDI的WEB服务器上（如WebSphere、JBoss、Tomcat等），就可以使用JNDI获取数据源。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:jee="http://www.springframework.org/schema/jee"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
http://www.springframework.org/schema/jee
http://www.springframework.org/schema/jee/spring-jee-3.2.xsd">
 
  <!-- 1.使用bean配置jndi数据源 -->
  <bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
    <property name="jndiName" value="java:comp/env/jdbc/orclight" />
  </bean>
 
  <!-- 2.使用jee标签配置jndi数据源，与1等价，但是需要引入命名空间 -->
  <jee:jndi-lookup id="dataSource" jndi-name=" java:comp/env/jdbc/orclight" />
</beans>
```



## 使用数据源连接池

```xml
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource"destroy-method="close">
  <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver" />
  <property name="url" value="jdbc:oracle:thin:@iphost:port:project"/>
  <property name="username" value="******" />
  <property name="password" value="******" />
</bean>
```



## 基于JDBC驱动的数据源

```xml
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
  <property name="driverClassName" value="com.mysql.jdbc.Driver" />
  <property name="url" value="jdbc:mysql://127.0.0.1:3306/testdb"/>
  <property name="username" value="root" />
  <property name="password" value="root" />
</bean>
```



## 完整实例

**ContextHelper.java**

```java
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public final class ContextHelper {
   private static ClassPathXmlApplicationContext _ctx;
 
   static {
      _ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
   }
 
   private ContextHelper() {
   }
 
   public static ClassPathXmlApplicationContext getContext() {
      return _ctx;
   }
}
```



**DBUtil.java**

```java
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public final class DBUtil {
   private static Logger log = LoggerFactory.getLogger(DBUtil.class);
 
   /**
    * 获取系统的数据源
    *
    * @return DataSource
    */
   public static DataSource getDataSource() {
      DataSource dataSource = null;
      try {
        dataSource = (DataSource) ContextHelper.getContext()
              .getBean("dataSource");
      } catch (Exception e) {
        log.error("获取数据源出错，请检查Spring数据源配置！");
      }
      return dataSource;
   }
 
   /**
    * 获取数据库连接
    *
    * @return Connection
    */
   public static Connection makeConnection() {
      Connection conn = null;
      try {
        conn = getDataSource().getConnection();
      } catch (SQLException e) {
        log.error("通过数据源获取数据库连接发生异常！");
        e.printStackTrace();
      }
      return conn;
   }
 
   /**
    * 执行没有参数的SQL过程
    *
    * @param procedureName
    *存储过程名字
    * @return boolean 返回存储过程执行的结果,true表示执行成功,false表示执行失败.
    */
   public static boolean executeBSDProcedure(String procedureName) {
      boolean flag = false;
      String sqlStr = "{call " + procedureName + "()}";
      CallableStatement cs;
      Connection conn = makeConnection();
      try {
        cs = (CallableStatement) conn.prepareStatement(sqlStr);
        cs.executeUpdate(sqlStr);
        flag = true;
      } catch (SQLException e) {
        log.error("调用存储过程" + sqlStr + "失败！");
        e.printStackTrace();
      }
      return flag;
   }
}
```



**Test.java**

```java
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * 14:41:49 Spring 数据源应用测试
 */
public class Test {
   private static Logger log = LoggerFactory.getLogger(Test.class);
 
   public static void main(String args[]) {
      Test.test();
   }
 
   public static void test() {
      String testSql = "select * from t_user";
      Connection conn = DBUtil.makeConnection();
      Statement stmt = null;
      try {
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
              ResultSet.CONCUR_READ_ONLY,
              ResultSet.CLOSE_CURSORS_AT_COMMIT);
        ResultSet rs = stmt.executeQuery(testSql);
        while (rs.next()) {
           String firstName = rs.getString("firstname");
           String lastName = rs.getString("lastname");
 
           System.out.println(firstName + " " + lastName);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (stmt != null) {
           try {
              stmt.close();
           } catch (SQLException e) {
              log.info("关闭Statement对象出现异常！");
              e.printStackTrace();
           }
        }
        if (conn != null) {
           try {
              conn.close();
           } catch (SQLException e) {
              log.error("关闭数据库连接失败！");
              e.printStackTrace();
           }
        }
      }
   }
}
```



在ApplicationContext.xml中添加一个bean

```xml
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
  <property name="driverClassName" value="com.mysql.jdbc.Driver" />
  <property name="url" value="jdbc:mysql://127.0.0.1:3306/testdb"/>
  <property name="username" value="root" />
  <property name="password" value="root" />
</bean>
```

运行Test.java中的main方法。



# 参考

Spring实战（第三版）