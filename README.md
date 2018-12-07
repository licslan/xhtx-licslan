本次会设计一个单点登陆系统

模拟三个系统
demo-sys1  demo-sys2  demo-sys3


设计思路
方案1.在三个系统中登陆一次后  登陆信息会session被记录到redis中





方案2.
oauth2  sso  大致流程
在一个公司中，肯定会存在多个不同的应用，比如公司的OA系统，HR系统等等，
如果每个系统都用独立的账号认证体系，会给用户带来很大困扰，也给管理带来很大不便。
所以通常需要设计一种统一登录的解决方案。比如我登陆了OA系统账号，进入HR系统时发现已经登录了，
进入公司其他系统发现也自动登录了。使用SSO解决效果是一次输入密码多个应用都可以识别在线状态。

浏览器向客户端服务器请求接口触发要求安全认证
跳转到授权服务器获取授权许可码
从授权服务器带授权许可码跳回来
客户端服务器向授权服务器获取AccessToken
返回AccessToken到客户端服务器
发出/resource请求到客户端服务器
客户端服务器将/resource请求转发到Resource服务器
Resource服务器要求安全验证,于是直接从授权服务器获取认证授权信息进行判断后
(最后会响应给客户端服务器,客户端服务器再响应给浏览中器）


[权限验证系统]   to be done ...


小伙伴介绍（原始成员） ：[薛文娇]  [黄威林]  [童星]  [黄秋飞] <br>
比如  com.example.demo  按照姓氏年龄大小来写<br>

1.进入spring.io<br>
2.将下载好的项目进行改造（springboot  spring  mariadb  mybatis）<br>
3.服务器阿里云<br>
4.gitlab管理系统搭建（提交代码）<br>
5. 后面详细写过程。。。。。<br>

系统架构设计

    基于SSM（SpringBoot+spring+mybatis+maven+mariadb+aliyun-Centos7） 
                               | 
                               | 
                               | 
                              \|/ 
    由垂直架构改造成SOA,基于SpringCloud的微服务架构(方案一)    (也可以使用基于dubbo方案二) 

    拆分思想：  基于应用服务拆分系统，将不同的应用结构单独部署，部署在多台阿里云服务器上（仅测试学习使用） 

    并对测试的结构进行压力测试使用jmeter测试  并对压测接口进行改造 会用到多线程知识（需要熟悉JUC包下面的并发容器类和线程池技术） 

    使用VisualVM.exe（jdk自带）工具监控内存/cpu/线程数  或是一些dump文件对代码中可能产生内存泄漏或者内存溢出/GC 进行分析
    
    对于后续发展起来的服务越来越多会使用docker技术  k8s容器编排  来管理服务 CICD  devops模式来高效迭代项目和不断更新内容
    
    对于项目设计的内容会加入big data （Hadoop spark flink storm） 数据挖掘  数据分析  数据清洗  机器学习(ML)等等   但这些技术的使用都不得不依赖强大的数据来支持
    
    所以数据收集系统也需要构建  数据仓库  数据湖等等  微型的系统 demo级别的来做实验  不断来提升我们的技术实力
    
    对于互联网高新技术 我们不断追求和使用 和学习 不断提高自己业务的水平  了解市场行情和技术更新  总结计算机行业的技术 
    
    不断复习和使用技术  保持学习的心态
    

    to be continued .... 

    

系统 目录结构介绍<br>
<br>
blog <br>
    --省略<br>
    --main<br>
        --com.example.demo<br>
          --common                           ----------->公共类<br>
          --config                           ----------->配置类 比如安全过滤<br>
          --controller                       ----------->web请求入口 controller<br>
          --Mapper                           ----------->数据访问层接口<br>
          --model                            ----------->实体类<br>
          --enums                            ----------->枚举类<br>
          --service                          ----------->接口&实现类<br>
          --utils                            ----------->工具类<br>
          --BlogApplication                  ----------->springBoot启动类<br>
        --resources                          ----------->各种资源配置文件<br>
          --mapping                          ----------->mapper  xml文件<br>
          --generator                        ----------->mybatis 自动生产<br>
          --static                           ----------->静态文件<br>
          --templates                        ----------->html文件 访问页面<br>
    --test 测试代码单元测试<br>
    --pom.xml                                ----------->pom.xml  依赖jar坐标文件<br>
    --README.md                              ----------->README.md plz read me first<br>



1.首先下载项目  你可以直接download or use git commond to download<br>
2.运行该项目请修改ip地址和数据库密码 如何运行springBoot项目 [plz go here &rarr;](https://spring.io/guides/gs/spring-boot/)<br> 
2.建议使用IDEA/eclipse/sts 等IDE 来开发该项目<br>
3.需要熟悉git命令 [plz go here &rarr;](http://git.mydoc.io/)<br>
4.后面有时间会将该项目改造------>soa------>springCloud<br>
5.学习和分享互联网技术  并转化到该项目 作为demo改造<br>

#建表语句
CREATE DATABASE test;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8 










