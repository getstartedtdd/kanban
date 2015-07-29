1.项目构建
  thiki是maven聚合项目，主要分为common，biz，dmo，war，web五大模块。其  中  Servers配置的web服务器，这里采用的jetty7，jetty-env.xml中配置了数据源。

2.各个模块
  common：主要是存放一些工具类啊，系统错误码之类的。
  biz：主要的业务层，持久层，数据传输对象等。
  dmo：数据库模型层，mybatis映射文件。
  war：web相关的配置文件。
  web：control层。
  后期可调。

3.各个模块的引用关系
  biz引用common，dmo；
  web引用了biz；
  war引用了web；

