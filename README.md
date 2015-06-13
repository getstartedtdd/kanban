# Thiki

## 项目目录结构
```
├─db
│  └─migrations 数据库脚本
│
├─kanban-app 看板后端开发根目录
│  └─src
│      ├─main
│      │  ├─java        后端源码目录
│      │  ├─resources   后端资源
│      │  └─webapp      后端网站根目录
│      ├─test
│      │  ├─java        单元测试源码目录
│      │  └─resources   单元测试资源文件目录
│      └─test-integration
│          └─java       集成测试源码目录
│          └─resources  集成测试资源文件目录
│
└─kanban-resources  看板前端开发根目录
    ├─js    前端js源码目录
    ├─less  前端样式存放目录，存放.less文件
    ├─libs  第三方js,css类库，使用bower管理
    └─views 看板界面存放目录
```

## 安装

####在使用项目之前确保你已安装如下软件:

![Maven](http://maven.apache.org/images/maven-logo-black-on-white.png)      | ![NodeJs](http://images.51cto.com/files/uploadimg/20121108/1001291.jpg)    | ![MySQL](http://dev.mysql.com/common/logos/logo-mysql-110x57.png)         | ![Git](http://git-scm.com/images/logo@2x.png)
----------------------------------------------------------------------------| ------------------------------------------------------------------------   | ------------------------------------------------------------------------  | --------------------------------------------------------------------------
[Maven](http://maven.apache.org/download.cgi)                               | [NodeJs](https://nodejs.org/download/)                                     | [MySQL](http://dev.mysql.com/downloads/windows/installer/)                | [Git](http://git-scm.com/download)
[安装教程](http://jingyan.baidu.com/article/d8072ac45d3660ec94cefd51.html)　 | [安装教程](http://jingyan.baidu.com/article/a948d6515d4c850a2dcd2e18.html) | [安装教程](http://jingyan.baidu.com/article/48b558e37c20e77f38c09a16.html) | [安装教程](http://jingyan.baidu.com/article/90895e0fb3495f64ed6b0b50.html)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

1.  安装less,bower,grunt

    ```cmd
        npm install -g less
        npm install -g bower
        npm install -g grunt
    ```

2.  下载项目到本地

     ```cmd
        git clone https://github.com/getstartedtdd/kanban.git
     ```

3.  进入到项目根目录,运行如下命令:

    ```cmd
        cd kanban-resources
        npm install ##在nodejs依赖包发生变化时需要再次执行该命令，下载依赖包
        bower install  ##在bower依赖包发生变化时需要再次执行该命令，下载依赖包
    ```

4.  安装本地MySQL数据库,用户名：root;密码:root。进入到项目根目录，运行如下命令（前端开发人员可以跳过该安装）。

    ```cmd
        mvn install -P bootstrap ##前端开发人员无需运行该命令来初始化项目
    ```



##使用

项目开发人员在进行开发之前需要首先运行如下命令:


### 前端开发人员

```
    cd kanban-resources
    grunt less:dev
    grunt watch:dev
```

### 后端开发人员

```
    cd kanban-resources
    grunt less:war
    grunt watch:war
```

## 涉及的技术

- [springframework](http://projects.spring.io/spring-framework/)
- [thymeleaf](http://www.thymeleaf.org)
- [less](http://lesscss.org/)
- [requirejs](http://www.requirejs.cn)
- [bower](http://bower.io)
- [npm](https://www.npmjs.com/)
