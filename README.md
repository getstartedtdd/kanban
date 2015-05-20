# 看板项目原型

## 项目目录结构
```
├─kanban-app 看板后端开发根目录
│  └─src
│      ├─main
│      │  ├─java        后端源码目录
│      │  ├─resources   后端资源
│      │  └─webapp      后端网站根目录
│      └─test
│          └─java       测试源码目录
│
└─kanban-resources  看板前端开发根目录
    ├─js    前端js源码目录
    ├─less  前端样式存放目录，存放.less文件
    ├─libs  第三方js,css类库，使用bower管理
    └─views 看板界面存放目录
```

## 安装

1.  在使用项目之前确保你已安装如下软件:
![Maven](http://maven.apache.org/images/maven-logo-black-on-white.png) | ![NodeJs](http://images.51cto.com/files/uploadimg/20121108/1001291.jpg)
---------------------------------------------------------------------- | ------------------------------------------------------------------------
[Maven](http://maven.apache.org/download.cgi)                          | [NodeJs](https://nodejs.org/download/)


2.  安装less,bower,grunt

    ```cmd
    npm install -g less
    npm install -g bower
    npm install -g grunt
    ```

3.  下载项目到本地

     ```cmd
     git clone https://github.com/get-started-tdd/kanban-bootstrap.git
     ```

4.  进入到项目根目录,运行如下命令:

    ```cmd
        cd kanban-resources
        npm install
        bower install
    ```
