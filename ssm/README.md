ssm项目依赖:spring-core,spring-webmvc,mybatis

通过spring-mvc接收请求，由后端处理

启动项目命令：mvn jetty:run

生成mapper命令：mvn mybatis-generator:generate

访问地址：http://127.0.0.1:8080/ssm/hello

#总结
spring framework的模块图

![模块分类](https://docs.spring.io/spring/docs/5.0.0.RELEASE/spring-framework-reference/images/spring-overview.png)

##spring-core
提供依赖注入、bean实例化等的功能，是整个应用的对象容器，对应于【core container】层
##spring-webmvc
提供前端的请求处理与分发，对应于【web】层
##mybatis
提供与数据库的交互，封装了数据库的操作，对应于【data access】层
##其他

###spring-jdbc
抽象化了不同数据库之间的差异，提供统一访问不同数据库的操作，还是依赖与对应的jdbc驱动，对应于【data access】层

###aspectjweaver
提供切面的功能，处理像事物、日志记录等操作
