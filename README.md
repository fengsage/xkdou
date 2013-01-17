采集失踪儿童数据，提供接口和一些工具，方便站长调用丢失儿童数据

开放接口:

	1. 随机返回1-10条失踪儿童信息
	http://www.xkdou.com/api/random?size=10 (default 1)
	

开始步骤：
	
	1.编译jfinal,介绍(http://www.oschina.net/p/jfinal)
	git clone https://github.com/fengsage/jfinal-maven.git
	cd jfinal-maven
	mvn clean install
	
	2.下载xkdou
	git clone https://github.com/fengsage/xkdou.git
	cd xkdou
	
	3.导入数据
	mysql -u root -p
	create database db_xkdou charset 'UTF8';
	source doc/db_xkdou.ddl;
	source doc/db_xkdou_init.ddl;
	exit;
	
	4.运行
	mvn jetty -P test  (test对应测试,prd对应线上,kuci是我在公司调试用的)
	
	5.浏览器输入
	http://localhost:8080
	帐号:admin  密码:admin

需要修改的配置都在  pom.xml 里面


TODO

	1.首页设计和实现 
	2.数据整理
	3.数据权重建模
	4.完善API 性别，年龄，地区，丢失时间调用
	.....
	
