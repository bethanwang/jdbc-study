# C/S项目实战之深入学习JDBC

> 本文通过一个C/S模式的程序，主要讲解：
>
> - 使用NetBeans，绘制窗口
>
> - JDBC实战及源码解析
> - 手动实现事务处理
> - properties配置文件的使用
> - 代码结构分层





[toc]

## 一、程序演示

先show一把，演示一遍窗口程序；让大家知道我们要做的一个C/S项目具体长啥样，有什么功能。

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/演示图.png)

功能介绍：

- 打开窗口，默认加载全部学员列表，点击列表的某一行，自动将数据填充到窗口下方对应的输入框中；

- 查找，用户输入用户名，点击“查找”按钮，通过输入的用户名模糊查找学员，并将查询结果加载到学员列表；
- 重置，清空学号、姓名、班级、专业对应的数据框中的内容；
- 保存，在输入框中填写对应信息，点击“保存”按钮，将数据保存到数据库中；
- 删除，先点击数据列表中需要删除的一行，将数据加载到对应输入框中，然后点击“删除”按钮，可删除数据；
- 退出，点击“退出”按钮，则退出程序，并关闭窗口，作用等同于点击窗口左上角的“X”符号。



## 二、产品需求、静态窗口及数据库设计

> 通过上文中程序演示，已经对该项目的需求点有所了解；通过Axure绘制项目原型图，并形成可以指导开发和测试的需求文档，即：PRD文档。
>
> 使用NetBeans开发工具，绘制窗口程序，由于本文不着重讲解GUI编程，绘制完窗口程序，增加代码实现静态窗口。



### 1. 产品需求

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/原型图.png)

使用Axure绘制原型图，形成prd文档。通过prd文档，我们可以看到，需要实现的窗口的大概布局及界面中各个控件功能；含有业务流程的功能通过流程图直观体现具体流程；界面交互和功能实现都附加上了必要的文字说明；该prd文档完全可以作为产品、开发、测试及项目管理人员之间沟通的依据，并能指导开发和测试工作。



### 2. 静态窗口

1）、在NetBeans中新建一个java项目，在`package`中新建一个`JFrame Form`，命名为`Window`，使用`Design`模式，按照原型图设计，通过从左侧`Palette`拖出对应的Swing控件，绘制出窗口、布局、控件。点击`Design`左侧的`Source`选项卡，可以查看源码。

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/静态窗口.png)

2）、右击“查找”按钮，添加`mouseClicked`事件；“重置”、“保存”、“删除”、“退出”按钮同样添加`mouseCliecked`事件；按同样的方式，给数据表`table`的每一行添加`mouseClicked`事件；添加完事件，点击`Source`可以查看对应生成的源代码，在生成的代码方法中添加对应业务代码。

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/添加事件.png)

生成代码：

```java
//点击数据表某一行
private void tableMouseClicked(java.awt.event.MouseEvent evt) {
  //todo - 
  /*
  1. 获取选中行的每一列数据
  2. 将对应数据填充到下方对应的数据框内
  */
}

//查找
private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {                                      
  //todo - 
  /*
  1. 查询数据库
  2. 清空数据列表，重新加载数据
  */
}                                     

//重置
private void resetBtnMouseClicked(java.awt.event.MouseEvent evt) {                                      
  //todo - 
  //清空输入框里的内容
}
    
//保存
private void saveBtnMouseClicked(java.awt.event.MouseEvent evt) {                                     
  //todo - 
  /*
  1. 获取输入框里的数据
  2. 封装对象
  3. 保存数据
  */
}

//删除
private void delBtnMouseClicked(java.awt.event.MouseEvent evt) {
  //todo -
  /*
  1. 删除数据
  2. 重新加载数据列表
  */
}
```

3）、创建Student和Clazz类，编写代码实现静态数据窗口。

Student:

```java
import lombok.Data;
//使用lombok自动生成get/set方法
@Data
public class Student {
    private String stuNo;
    private String name;
    private String major;
    private Clazz clazz;
}
```

Clazz:

```java
import lombok.Data;
//使用lombok自动生成get/set方法
@Data
public class Clazz {
    private int clazzId;
    private String clazzName;
}
```

静态数据代码：

```java
//刷新数据表
private void refreshTabel(String name){
  //假数据
  List<Student> stuList = new ArrayList<Student>();
  for(int i=1; i < 21; i++){
    Student s = new Student();
    s.setMajor("专业"+i);
    s.setName("姓名"+i);
    Clazz c = new Clazz();
    c.setClazzName("班级"+i);
    s.setClazz(c);
    s.setStuNo("20160101100"+i);
    stuList.add(s);
  }
  //清除table中的原来数据
  DefaultTableModel model = (DefaultTableModel) table.getModel();
  while(model.getRowCount() > 0){
    model.removeRow(0);
  }
	//渲染table中数据
  stuList.forEach(s->{
    Vector data = new Vector();
    data.add(s.getStuNo());
    data.add(s.getName());
    data.add(s.getClazz().getClazzName());
    data.add(s.getMajor());
    model.addRow(data);
  });
}

public Window() {
  //初始化窗口，此方法是NteBeans自动生成，不需要修改
  initComponents();
  //设置窗口大小
  this.setSize(815, 750);
  //设置窗口在屏幕中央显示
  this.setLocationRelativeTo(null);
  //初始化table数据
  refreshTabel(null);
}

//启动类
public static void main(String args[]) {
  java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
      new Window().setVisible(true);
    }
  });
}
```

运行程序效果：

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/静态数据窗口.png)



### 3. 数据库设计

在`mysql`中新建数据库`cs-demo`，分别设计Student表和Clazz表，创建表时保留主外键关系，不启用外键约束，E-R如下：

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/E-R.png)



## 三、JDBC

### 1. 创建maven项目

<u>*关于maven的配置和使用本文不再讲解，不了解的同学可以自己学习一下。*</u>

1）打开idea，在idea中创建maven项目，项目名：`jdbc-study`，新建包：`com.study.cs.demo`，将NetBeans中创建的类：`Window.java、Student.java、Clazz.java`，复制到包`com.study.cs.demo`中。

（这一波操作，是因为笔者不喜欢使用NetBeans开发，习惯了使用idea。囧～～）

<img src="https://github.com/bethanwang/jdbc-study/tree/master/pic/idea新建项目.png" style="zoom:50%;" />

2）添加`lombok`依赖。

```xml
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <version>1.18.12</version>
</dependency>
```



### 2. JDBC

1）添加`mysql`驱动jar包，在`maven`的`pom.xml`文件中添加对应依赖。

```xml
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.19</version>
</dependency>
```

2）在`/src/test/java/`目录下，新建包`com.study.cs.demo.test`，新建类`JdbcTest.java`。

```java
package com.study.cs.demo.test;

import com.study.cs.demo.Clazz;
import com.study.cs.demo.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTest {

    //驱动类
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    //数据库位置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cs-demo?useSSL=false&characterEncoding=utf8";
    //登陆数据库用户名
    private static final String USER_NAME = "root";
    //登陆数据库密码
    private static final String PASSWORD = "123456";

    public static void main(String[] a) {
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            //1 加载驱动
            Class.forName(DRIVER_CLASS);
            //2 打开链接
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            //3 创建会话
            sta = conn.createStatement();
            //4 执行sql
            String sql = "select s.*,c.clazz_name from student s left join clazz c on s.clazz_id=c.clazz_id";
            rs = sta.executeQuery(sql);
            //5 处理sql执行结果，并将查询结果封装成对象集合
            List<Student> list = new ArrayList<Student>();
            while(rs.next()){
                Student s = new Student();
                s.setMajor(rs.getString("major"));
                s.setName(rs.getString("name"));
                s.setStuNo(rs.getString("stu_no"));
                Clazz c = new Clazz();
                c.setClazzId(rs.getInt("clazz_id"));
                c.setClazzName(rs.getString("clazz_name"));
                s.setClazz(c);
                list.add(s);
            }
            //遍历对象集合，打印数据
            list.forEach(s->{
                System.out.println(s.toString());
            });
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            /**
             * 无论程序执行到哪个位置出现了异常，都需要释放数据库连接资源
             * 所以，释放资源的代码放在finlly块中执行
             */
            /*
            conn/sta/set都有可能是null，所以在使用这几个对象之前先判断是否是null
            */
            if(conn != null){
                try {
                    conn.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(sta != null){
                try {
                    sta.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(rs != null){
                try {
                    rs.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
  
}
```

运行单元测试结果：

```java
Student(stuNo=20010101001, name=李白, major=文学, clazz=Clazz(clazzId=2, clazzName=2017级1班))
Student(stuNo=20160101002, name=张三丰, major=英语, clazz=Clazz(clazzId=12, clazzName=2016级1班))
```

一次完整的jdbc访问数据库，可分为以下步骤：

1. 加载驱动

   通过反射机制，生成驱动类的实例，即生成`com.mysql.cj.jdbc.Driver`类的对象，并将驱动类实例注册到`java.sql.DriverManager`进行管理。

```java
//驱动类
private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
//加载驱动
Class.forName(DRIVER_CLASS);
```

<u>*关于反射和类的加载机制本文不展开讲解。*</u>

源码解析：

​		先来看一下代码中`private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver"`这一行里面`com.mysql.cj.jdbc.Driver`类的源码，如下：

```java
package com.mysql.cj.jdbc;

import java.sql.SQLException;

public class Driver extends NonRegisteringDriver implements java.sql.Driver {

    // Register ourselves with the DriverManager
    static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    /**
     * Construct a new driver and register it with DriverManager
     * 
     * @throws SQLException
     *             if a database error occurs.
     */
    public Driver() throws SQLException {
        // Required for Class.forName().newInstance()
    }
}
```

​		当我们执行`Class.forName("com.mysql.cj.jdbc.Driver")`这一行代码时，会通过反射实例化`com.mysql.cj.jdbc.Driver`类的对象，通过查看`com.mysql.cj.jdbc.Driver`类的源码，我们知道在实例化其对象时会执行`static`代码块中的`java.sql.DriverManager.registerDriver(new Driver())`方法。

`java.sql.DriverManager`类中的源码：

```java
package java.sql;

public class DriverManager {
  
  /*
  一个线程安全的List集合，注册后的com.mysql.cj.jdbc.Driver的对象，封装到DriverInfo的对象中，然后将DriverInfo的对象，放到registeredDrivers集合内，完成注册
  */
  private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<>();
  
  public static synchronized void registerDriver(java.sql.Driver driver)
    throws SQLException {
    registerDriver(driver, null);
  }
  
  public static synchronized void registerDriver(java.sql.Driver driver,
            DriverAction da)
        throws SQLException {

        /* Register the driver if it has not already been added to our list */
        if(driver != null) {
            registeredDrivers.addIfAbsent(new DriverInfo(driver, da));
        } else {
            // This is for compatibility with the original DriverManager
            throw new NullPointerException();
        }

        println("registerDriver: " + driver);

    }
  
}

class DriverInfo {

    final Driver driver;
    DriverAction da;
    DriverInfo(Driver driver, DriverAction action) {
        this.driver = driver;
        da = action;
    }
}
```

​		通过`java.sql.DriverManager`的源码我们看到，执行`registerDriver(java.sql.Driver driver)`方法后，会调用`registerDriver(java.sql.Driver driver, DriverAction da)`方法；通过`registeredDrivers.addIfAbsent(new DriverInfo(driver, da))`这一行代码可以看出，创建了一个`DriverInfo`对象，并将`com.mysql.cj.jdbc.Driver`的对象封装到`DriverInfo`中，最后将`DriverInfo`对象添加到`registeredDrivers`集合中，完成驱动的注册。

***扩展：基于SPI隐式加载驱动：**

所谓`Driver`隐式加载，就是`Class.forName(DRIVER_CLASS);`这一行代码不需要再写了。

```markdown
	JDBC4.0开始，这个显式的初始化驱动的代码就可以不用写了，很多时候之所以这样写是考虑到线上兼容。
	在java 6以后，加入了SPI（Service Provider Interface）功能，SPI提供了一种JVM级别的服务发现机制，只需要在jar包中按照SPI的格式要求进行配置，JVM就会在运行时通过懒加载，帮我们找到所需的服务并加载。如果系统中没有使用到这个服务，则不会被加载，避免资源浪费。
	SPI的配置路径是：/META-INF/services/下面，我们程序中使用的”mysql-connector-java-8.0.19.jar”也添加了对应的配置，所以在我们的项目中，可以隐式加载驱动，即可以不写`Class.forName(DRIVER_CLASS);`这一行代码。
```

`mysql-connector-java-8.0.19.jar`中关于SPI的配置：

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/驱动jarSPI配置.png)



2. 打开数据库链接

   利用给定的数据库资源（url/username/password），通过调用`DriverManager`的`getConnection(url, userName, password)`方法，获取数据库链接对象。

   ```java
   //打开链接
   conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
   ```

   源码解析：

   `java.sql.DriverManager`类：

   ```java
   public class DriverManager {
     
     public static Connection getConnection(String url, String user, 
                                            String password) throws SQLException {
       java.util.Properties info = new java.util.Properties();
       if (user != null) {
         info.put("user", user);
       }
       if (password != null) {
         info.put("password", password);
       }
       return (getConnection(url, info, Reflection.getCallerClass()));
     }
     
     private static Connection getConnection(
   		/*
   		省略部分源码...
   		*/
       //遍历已注册的驱动对象集合
       for(DriverInfo aDriver : registeredDrivers) {
         // If the caller does not have permission to load the driver then
         // skip it.
         if(isDriverAllowed(aDriver.driver, callerCL)) {
           try {
             println("    trying " + aDriver.driver.getClass().getName());
             Connection con = aDriver.driver.connect(url, info);
             if (con != null) {
               // Success!
               println("getConnection returning " + aDriver.driver.getClass().getName());
               return (con);
             }
           } catch (SQLException ex) {
             if (reason == null) {
               reason = ex;
             }
           }
   
         } else {
           println("    skipping: " + aDriver.getClass().getName());
         }
       }
       //for循环结束
       /*
       省略部分源码...
       */
   	}
     
   }
   ```

   `for(DriverInfo aDriver : registeredDrivers){...}`循环遍历已经注册到`DriverManager`中的驱动对象，然后逐个利用驱动对象创建`Connection`，返回第一个创建成功的`Connection`对象。



3. 创建会话

   使用上文返回的`Connection`对象，创建`Statement`对象。

   ```java
   //3 创建会话
   sta = conn.createStatement();
   ```

   此处其实是调用的是`java.sql.Connection`类的子类`com.mysql.cj.jdbc.ConnectionImpl`中的代码，通过下图可以看到它们之间的继承关系。

   ![](https://github.com/bethanwang/jdbc-study/tree/master/pic/Connection继承关系.png)

   源码解析：

   `com.mysql.cj.jdbc.ConnectionImpl`类：

   ```java
   package com.mysql.cj.jdbc;
   
   public class ConnectionImpl implements JdbcConnection, SessionEventListener, Serializable {
     
     /** The database we're currently using. */
     private String database = null;
     
     private static final int DEFAULT_RESULT_SET_TYPE = ResultSet.TYPE_FORWARD_ONLY;
     
     private static final int DEFAULT_RESULT_SET_CONCURRENCY = ResultSet.CONCUR_READ_ONLY;
     
     private JdbcConnection topProxy = null;
     
     @Override
     public java.sql.Statement createStatement() throws SQLException {
       return createStatement(DEFAULT_RESULT_SET_TYPE, DEFAULT_RESULT_SET_CONCURRENCY);
     }
   
     @Override
     public java.sql.Statement createStatement(int resultSetType, 
                                             int resultSetConcurrency) throws SQLException {
       StatementImpl stmt = new StatementImpl(getMultiHostSafeProxy(), this.database);
       stmt.setResultSetType(resultSetType);
       stmt.setResultSetConcurrency(resultSetConcurrency);
       return stmt;
     }
     
     @Override
     public JdbcConnection getMultiHostSafeProxy() {
       return this.getProxy();
     }
     
     private JdbcConnection getProxy() {
       return (this.topProxy != null) ? this.topProxy : (JdbcConnection) this;
     }
   }
   ```

   `java.sql.ResultSet`类：

   ```java
   package java.sql;
   
   public interface ResultSet extends Wrapper, AutoCloseable {
     
     /**
      * The constant indicating the type for a <code>ResultSet</code> object
      * whose cursor may move only forward.
      * @since 1.2
      */
     int TYPE_FORWARD_ONLY = 1003;
     
     /**
      * The constant indicating the concurrency mode for a
      * <code>ResultSet</code> object that may NOT be updated.
      * @since 1.2
      */
     int CONCUR_READ_ONLY = 1007;
     
   }
   ```

   结合源码`StatementImpl stmt = new StatementImpl(getMultiHostSafeProxy(), this.database)`可以看出，在`com.mysql.cj.jdbc.ConnectionImpl`类中，创建了一个`StatementImpl`实例并返回；在创建`StatementImpl`实例时，同时设置了查询数据库返回的数据集合`ResultSet`对象的两个特性：`TYPE_FORWARD_ONLY`和`CONCUR_READ_ONLY`。

    - `TYPE_FORWARD_ONLY`表示返回的集合`ResultSet`只能顺序正向遍历。

    - `CONCUR_READ_ONLY`表示返回的集合`ResultSet`只可以读取其中的数据，不允许修改集合中的数据。



4. 执行sql

   在创建了`Statement`对象以后，通过调用其`executeQuery()`方法，执行`sql`语句。

   ```java
   String sql = "select s.*,c.clazz_name from student s left join clazz c on s.clazz_id=c.clazz_id";
   rs = sta.executeQuery(sql);
   ```

   此处其实是调用`com.mysql.cj.jdbc.StatementImpl`类中的`executeQuery(sql)`方法。

   源码解析：

   `com.mysql.cj.jdbc.StatementImpl`类：

   ```java
   package com.mysql.cj.jdbc;
   
   public class StatementImpl implements JdbcStatement {
   
     /** The current results */
     protected ResultSetInternalMethods results = null;
     
   	@Override
     public java.sql.ResultSet executeQuery(String sql) throws SQLException {
       synchronized (checkClosed().getConnectionMutex()) {
         /*
          省略代码...
          */
         this.results = ((NativeSession) locallyScopedConn.getSession())
           .execSQL(this, sql, this.maxRows, null, createStreamingResultSet(),                                                                         				 			getResultSetFactory(), cachedMetaData, false);
         /*
          省略代码...
          */
         return this.results;
       }
     }
   
   }
   ```


结合源码可以看到，其实是在`executeQuery`方法中，通过`locallyScopedConn.getSession()`这一行代码获得了数据库会话（`Session`），然后执行`sql`，将查询结果封装到`ResultSetInternalMethods`中。

​		另外源码中获取数据库会话（`Session`）并和数据库交互的代码都放到了`synchronized`的代码块中，所以如果程序中只有一个数据库链接（`Connection`），程序每次访问数据库的过程中，当前线程都会独占该数据库链（`Connection`）接资源，只有当前线程结束对数据库的访问（或访问超时），其他线程才能利用该数据库链接（`Connection`）访问数据库；因此，系统中一般使用数据库连接池来管理数据库的连接，数据库连接池中会持有一定数量的数据库链接（Connection），避免只有一个数据库链接（Connection）造成的并发瓶颈。

​		因此，系统中一般使用数据库连接池来管理数据库的连接，数据库连接池中会持有一定数量的数据库链接（`Connection`），避免只有一个数据库链接（`Connection`）造成的并发瓶颈。

​		<u>*数据库连接池，会在以后的文中讲解。*</u>



5. 处理查询结果集

   查询的数据集合封装在`ResultSet`中，遍历`ResultSet`集合将数据封装成`List<Student>`。

   ```java
   List<Student> list = new ArrayList<Student>();
   while(rs.next()){
     Student s = new Student();
     s.setMajor(rs.getString("major"));
     s.setName(rs.getString("name"));
     s.setStuNo(rs.getString("stu_no"));
     Clazz c = new Clazz();
     c.setClazzId(rs.getInt("clazz_id"));
     c.setClazzName(rs.getString("clazz_name"));
     s.setClazz(c);
     list.add(s);
   }
   //遍历从数据库中查询的对象集合
   list.forEach(s->{
     System.out.println(s.toString());
   });
   ```



6. 关闭数据库连接

   因为代码执行过程中，可能出现异常，所以关闭数据库连接的代码写在`finally`块中；此处是一个单元测试，测试完需要关闭数据库链接，在系统中可以不用关闭，避免重复创建数据库链接带来的消耗。

   ```java
   try {
     //省略代码...
   } catch(Exception e){
     e.printStackTrace();
   } finally {
     /* conn/sta/set都有可能是null，所以在使用这几个对象之前先判断是否是null */
     if(conn != null){
       try {
         conn.close();
       } catch(Exception e){
         e.printStackTrace();
       }
     }
     if(sta != null){
       try {
         sta.close();
       } catch(Exception e){
         e.printStackTrace();
       }
     }
     if(rs != null){
       try {
         rs.close();
       } catch(Exception e){
         e.printStackTrace();
       }
     }
   }
   ```



## 四、编写DBUtil实现动态窗口

### 1. 新建DBUtil类

```java
package com.study.cs.demo;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    /**
     * 返回数据库连接池
     * @return
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * 返回单例对象
     * @return
     */
    public static DBUtil getInstance(){
        if(util == null){
            synchronized (new Object()){
                if (util == null){
                    util = new DBUtil();
                }
            }
        }
        return util;
    }

    private static DBUtil util;

    /**
     * 私有构造器，在构造器中完成加载驱动类、创建数据库链接
     */
    private DBUtil(){
        try {
            //加载驱动
            Class.forName(DRIVER_CLASS);
            //创建数据库连接
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection conn;

    //驱动类
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    //数据库位置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cs-demo?useSSL=false&characterEncoding=utf8";
    //登陆数据库用户名
    private static final String USER_NAME = "root";
    //登陆数据库密码
    private static final String PASSWORD = "123456";

}
```

`DBUtil`类为单例模式，由于我们当前开发的是一个C/S模式的项目，所以不存在数据库链接竞争的情况，只需要一个数据库连接，在`DBUtil`中定义了一个成员变量`private Connection conn`，如果需要多个数据库连接，只需定一个一个数据库连接池即可；在系统运行中只需要`DBUtil`的一个实例。



### 2. 实现功能

> 本文中只实现“查询”功能，“保存”、“修改”、“删除”功能请读者自己实现。

- 查询

  新建`StudentDao`类：

  ```java
  package com.study.cs.demo;
  
  import java.sql.Connection;
  import java.sql.PreparedStatement;
  import java.sql.ResultSet;
  import java.util.ArrayList;
  import java.util.List;
  
  public class StudentDao {
  
      public List<Student> query(String name) throws Exception {
          Connection conn = DBUtil.getInstance().getConnection();
          String sql = "select s.*,c.clazz_name from student s left join clazz c on s.clazz_id=c.clazz_id";
          if(name != null && !name.trim().equals("")){
              sql = sql + " where s.`name` like ?";
          }
          PreparedStatement ptmt = conn.prepareStatement(sql);
          if(name != null && !name.trim().equals("")){
              ptmt.setString(1, name);
          }
          ResultSet rs = ptmt.executeQuery();
          List<Student> list = new ArrayList<Student>();
          while(rs.next()){
              Student s = new Student();
              s.setMajor(rs.getString("major"));
              s.setName(rs.getString("name"));
              s.setStuNo(rs.getString("stu_no"));
              Clazz c = new Clazz();
              c.setClazzId(rs.getInt("clazz_id"));
              c.setClazzName(rs.getString("clazz_name"));
              s.setClazz(c);
              list.add(s);
          }
          return list;
      }
  
  }
  ```

  `Window`类：

  ```java
  public class Window extends JFrame {
  		//刷新数据表
      private void refreshTable(String name){
          List<Student> stuList = new ArrayList<Student>();
          try {
              stuList = studentDao.query(name);
          } catch (Exception e) {
              e.printStackTrace();
          }
          //init table
          DefaultTableModel model = (DefaultTableModel) table.getModel();
          while(model.getRowCount() > 0){
              model.removeRow(0);
          }
          stuList.forEach(s->{
              Vector data = new Vector();
              data.add(s.getStuNo());
              data.add(s.getName());
              data.add(s.getClazz().getClazzName());
              data.add(s.getMajor());
              model.addRow(data);
          });
      }
    
    	private StudentDao studentDao = new StudentDao();
  }
  ```

  `StudentDao`中我们使用`PreparedStatement`类实现，避免`sql`注入的情况；修改`Window`中的代码，把查询到的数据显示到窗口中；启动程序查看效果。

- 修改/保存/删除

  <u>*读者自己实现。*</u>



## 五、代码重构

> 新建App类作为程序的启动类（main方法），通过分包实现代码分层：view、service、dao、db、model。

### 1. 调整代码结构

重构前代码结构：

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/重构前代码结构.png)

​		现在代码结构比较混乱，全部的类都平铺在同一个包里面，如果项目功能越多，这个包里面的类就越多，最后就没法看啦，所以需要重构代码。

​		结合MVC模式，将系统中代码分为几个层次：显示层，业务处理层，数据访问层；再结合各种类的作用，将其放到不同的包：view、service、dao、db、model，增加service层主要用于处理复杂业务流程和事务，新建程序启动类`App.java`放到包`com.study.cs.demo`下。

重构后代码结构：

![](https://github.com/bethanwang/jdbc-study/tree/master/pic/重构后代码结构.png)

`com.study.cs.demo.service.StudentService`类：

```java
package com.study.cs.demo.service;

import com.study.cs.demo.model.Student;

import java.util.List;

public interface StudentService {

    /**
     * 根据学生姓名查找学生列表
     * @return
     */
    List<Student> queryByName(String name);

}
```

`com.study.cs.demo.service.impl.StudentServiceImpl`类：

```java
package com.study.cs.demo.service.impl;

import com.study.cs.demo.dao.StudentDao;
import com.study.cs.demo.db.DBUtil;
import com.study.cs.demo.model.Student;
import com.study.cs.demo.service.StudentService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    @Override
    public List<Student> queryByName(String name) {
        List<Student> list = new ArrayList<Student>();
        try {
            list = stuDao.query(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public StudentServiceImpl() {
        conn = DBUtil.getInstance().getConnection();
        stuDao = new StudentDao(conn);
    }

    private StudentDao stuDao;
    private Connection conn;
}
```

运行`App`中的`main`方法，验证程序可以执行。



### 2. 提取BasicDao

> 利用`commons-dbutils`工具包，封装`BasicDao`基类，将公共的数据库访问方法封装到`BasicDao`类中。

1. 添加`Maven`依赖。

   在`pom`增加配置：

   ```xml
   <dependency>
     <groupId>commons-dbutils</groupId>
     <artifactId>commons-dbutils</artifactId>
     <version>1.7</version>
   </dependency>
   ```

2. 编写`BasicDao`类，将一些通用的数据库访问的封装其中，对应的具体`model`类，以泛型方式传给被调用的方法，其他具体`dao`类继承`BasicDao`。

   `com.study.cs.demo.dao.BasicDao`类：

   ```java
   package com.study.cs.demo.dao;
   
   import org.apache.commons.dbutils.QueryRunner;
   import org.apache.commons.dbutils.handlers.BeanListHandler;
   
   import java.sql.Connection;
   import java.util.List;
   
   public abstract class BasicDao<T> {
   
       /**
        * 查询
        * @param sql
        * @param params
        * @param clazz
        * @return
        * @throws Exception
        */
       public List<T> query(String sql, Object[] params, Class<T> clazz) throws Exception {
           QueryRunner runner = new QueryRunner();
           List<T> list = runner.query(conn, sql, new BeanListHandler<T>(clazz), params);
           return list;
       }
   
       /**
        * 更新数据库，写操作，包含：update, insert, delete
        * @param sql
        * @param params
        * @return
        * @throws Exception
        */
       public boolean update(String sql, Object[] params) throws Exception {
           QueryRunner runner = new QueryRunner();
           int cou = runner.update(conn, sql, params);
           if (cou > 0) {
               return true;
           } else {
               return false;
           }
       }
   
       /**
        * 批量更新，批量对数据库写操作
        * @param sql
        * @param params
        * @return
        * @throws Exception
        */
       public boolean updateBatch(String sql, Object[][] params) throws Exception {
           QueryRunner runner = new QueryRunner();
           int cou = runner.batch(sql, params).length;
           if (cou > 0) {
               return true;
           } else {
               return false;
           }
       }
   
       public BasicDao(Connection connection){
           this.conn = connection;
       }
   
       private Connection conn;
   
   }
   ```



## 六、使用Properties

### 1. Properties的使用

 1. 新建`db.properties`文件。

    <img src="https://github.com/bethanwang/jdbc-study/tree/master/pic/新建properties.png" style="zoom:120%;" />

 2. 在`db.properties`文件中，新增配置内容。

    ```java
    driver-class=com.mysql.cj.jdbc.Driver
    db-url=jdbc:mysql://localhost:3306/cs-demo?useSSL=false&characterEncoding=utf8
    user-name=root
    password=123456
    ```

 3. 修改`com.study.cs.demo.db.DBUtil`类，从`properties`文件中加载数据库配置，并创建数据库连接。

    `com.study.cs.demo.db.DBUtil`类：

    ```java
    package com.study.cs.demo.db;
    
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.InputStream;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.util.Properties;
    
    public class DBUtil {
    
        /**
         * 返回数据库连接池
         * @return
         */
        public Connection getConnection() {
            return conn;
        }
    
        /**
         * 返回单例对象
         * @return
         */
        public static DBUtil getInstance(){
            if(util == null){
                synchronized (new Object()){
                    if (util == null){
                        util = new DBUtil();
                    }
                }
            }
            return util;
        }
    
        /**
         * 构造器，从properties中加载配置信息，并初始化数据库连接
         */
        private DBUtil(){
            try {
                //获得properties文件的位置
                String proFilePath = new File("").getCanonicalPath() + "/src/main/resources/db.properties";
                InputStream is = new FileInputStream(proFilePath);
                Properties pro = new Properties();
                pro.load(is);
                DRIVER_CLASS = pro.getProperty("driver-class");
                DB_URL = pro.getProperty("db-url");
                USER_NAME = pro.getProperty("user-name");
                PASSWORD = pro.getProperty("password");
                Class.forName(DRIVER_CLASS);
                //根据properties中的配置，创建数据库连接
                conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        private static DBUtil util;
        //数据库连接
        private Connection conn;
        //配置驱动类
        private static String DRIVER_CLASS;
        //数据库位置
        private static String DB_URL;
        //登陆数据库用户名
        private static String USER_NAME;
        //登陆数据库密码
        private static String PASSWORD;
    
    }
    ```



## 七、事务处理

### 1. 事务介绍

 1. 概念

    如果一个业务需要操作数据库中多张表，就会在`service`层多次调用`dao`层，通过多次数据库访问完成整个业务流程的处理，我们就把这样整个的一个业务处理，称为一个事务（transaction）。

    - 在 MySQL 中只有使用了 Innodb 数据库引擎的数据库或表才支持事务。
    - 事务处理可以用来维护数据库的完整性，保证成批的 SQL 语句要么全部执行，要么全部不执行。
    - 事务用来管理 对数据库的写操作（insert,update,delete 语句）。

 2. 事务的特性：

    - 原子性：一个事务中的所有操作，要么全部完成，要么全部失败，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态。
    - 一致性：在事务开始之前和事务结束以后，数据库的完整性不会被破坏，数据是完整的，数据之间（表与表之间）的关联关系健全。
    - 隔离性：数据库允许多个并发事务同时对其数据进行读写操作，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。
    - 持久性：事务处理结束后，会对修改后的数据持久化保存到数据库中。



### 2. 实现事务处理

 1. 业务流程

    本文通过实现“保存”学员功能，编写代码手动实现相关事务处理；“保存”功能的业务逻辑已经在产品需求说明，对应业务流程如下图：

    <img src="https://github.com/bethanwang/jdbc-study/tree/master/pic/保存学员流程图.png" style="zoom:50%;" />

    由业务流程图可以看出，保存班级和保存学员在同一个事务中，因此在提交保存后开启事务，然后经过查询班级、保存班级、查询学员、保存学员一系列业务处理，都成功以后提交事务，如果中间某个环节出了异常，则回滚事务。

 2. 编码实现

    `com.study.cs.demo.view.Window`类：

    ```java
    package com.study.cs.demo.view;
    
    public class Window extends JFrame {
    		//保存
        private void saveBtnMouseClicked(java.awt.event.MouseEvent evt) {
            String stuNo = this.stuNoField.getText().toString();
            String name = this.nameField.getText().toString();
            String stuClass = this.classField.getText().toString();
            String major = this.majorField.getText().toString();
            Student s = new Student();
            if (stuNo.trim().length() > 0) {
                s.setStuNo(stuNo);
            }
            if (name.trim().length() > 0) {
                s.setName(name);
            }
            if (major.trim().length() > 0) {
                s.setMajor(major);
            }
            Clazz c = new Clazz();
            if (stuClass.trim().length() > 0) {
                c.setClazzName(stuClass);
            }
            s.setClazz(c);
            //保存学员信息
            stuService.add(s);
            resetField();
            refreshTable(null);
        }
    }
    ```

    在“保存”按钮的点击事件方法`saveBtnMouseClicked(java.awt.event.MouseEvent evt)`中，代码实现获取窗口中对应的数据，封装成`Student`对象，调用`service`层，保存学员。



    `com.study.cs.demo.service.StudentService`接口：

    ```java
    package com.study.cs.demo.service;
    
    public interface StudentService {
    
        /**
         * 保存学员信息
         * @param stu
         * @return
         */
        void add(Student stu);
    
    }
    ```

    增加`add(Student s)`方法。

    `com.study.cs.demo.service.impl.StudentServiceImpl`类：

    ```java
    package com.study.cs.demo.service.impl;
    
    public class StudentServiceImpl implements StudentService {
        @Override
        public void add(Student stu) {
            try{
                //设置手动处理事务
                conn.setAutoCommit(false);
                //clazz bp
                List<Clazz> clazzlist = clazzDao.queryByName(stu.getClazz().getClazzName());
                Clazz c;
                if(clazzlist != null && clazzlist.size() > 0){
                    c = clazzlist.get(0);
                }else{
                    //save clazz
                    c = clazzDao.save(stu.getClazz());
                }
                //student bp
                stu.setClazz(c);
                stuDao.save(stu);
                //提交事务
                conn.commit();
            }catch(Exception e){
                try {
                    //回滚事务
                    conn.rollback();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    
        public StudentServiceImpl() {
            conn = DBUtil.getInstance().getConnection();
            stuDao = new StudentDao(conn);
            clazzDao = new ClazzDao(conn);
        }
    
        private StudentDao stuDao;
        private ClazzDao clazzDao;
        private Connection conn;
    }
    ```

    实现`add(Student stu)`方法，在方法中实现“保存”学员的业务逻辑处理，将事务设置为手动处理`conn.setAutoCommit(false);`，当事务成功处理完成以后，提交事务`conn.commit();`，若整个业务没有处理完，中间出现异常，则会执行`catch`中的`conn.rollback();`回滚事务。

    验证事务生效：

    ```markdown
    	数据库中student表的name字段、clazz_id字段、major字段设置为“不可为空”，clazz表的clazz_name字段设置为“不可为空”。
    	以debug形式运行程序，在service的add方法中打断点，在窗口中输入“学号”、“姓名”、“班级”（这里班级名输入一个数据库中不存在的名字），不要填“专业”，跟踪代码可以看到，clazz保存成功以后，在保存student时，由于“专业”字段做了“不可为空”限制，会报异常，导致事务回滚，数据库查看，刚才虽然执行了保存clazz的sql，因为事务中出现异常，事务回滚，clazz表中没有保存刚才的班级。
    	窗口中的“学号”、“姓名”、“班级”（输入一个数据库中不存在的班级名）、“专业”都输入数据，以上面的方式跟踪代码，会发现程序先保存clazz，再保存student，然后提交事务，查看数据库clazz表和student表各自都增加了一条数据，即为此次事务提交保存的数据。
    ```



## 八、总结

​		本章节通过一个C/S项目的例子，以项目化的形式系统讲解jdbc使用、事务处理、properties的使用等；产品需求阶段以产品经理的角度思考处理问题，根据业务场景梳理出各个业务流程，并设计出产品原型图；jdbc的实现过程，深入源码讲解，通过源码解析，帮助读者更深入的理解各个步骤的实现原理。

​		读者可以根据产品需求，自己完成整个学员管理的功能，可以自己查阅资料，通过`commons-dbutils`包实现数据库访问，`commons-dbutils`的`maven`依赖：

```xml
<dependency>
  <groupId>commons-dbutils</groupId>
  <artifactId>commons-dbutils</artifactId>
  <version>1.7</version>
</dependency>
```

​

​		**受作者水平限制，文中难免有不足之处，若读者阅读过程中发现问题，还望及时指正，感谢支持！**

​		**作者邮箱：547317812@qq.com**

