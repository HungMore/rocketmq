# RocketMQ note

## 源码编译

从[github](https://github.com/apache/rocketmq)克隆源码，然后执行命令`mvn -Prelease-all -DskipTests -Dspotbugs.skip=true clean install -U`进行编译，编译后的文件在`distribution/target/`目录下。
将`rocketmq-4.7.1.tar.gz`二进制包上传到Linux服务器，`tar -zxvf rocketmq-4.7.1.tar.gz`解压即可。（在windows下编译源代码会有与Linux换行符不兼容的问题，所以简单求见还是直接官网下载二进制包吧）

# RocketMQ Dashboard

使用命令进行RocketMQ运维的学习成本比较大，RocketMQ官方提供了运维管理界面RocketMQ Dashboard。它的源码：[github](https://github.com/apache/rocketmq-dashboard)。
将项目导入到IDEA，然后修改application.yml中指向nameserver的地址。（它是一个springboot项目，将它运行起来还是很简单的。）

rocketmq nameserver所在服务器需要开放端口9876，broker所在服务器需要开放端口10909。








