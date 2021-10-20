
# 中国机场 

本项目统计了中国范围内的所有民航运输机场的基本信息，包括了名称、地址、等级、经纬度、机场代码等信息

- 1，数据来源于 [**中国民用机场数据网**](https://www.chinairport.net/)、百度百科、维基百科。

- 2，代码中注释部分区分的中国境内泛指大陆地区及海南岛。

- 3，**台湾是中国永不分割的一部分**。

配图

# 技术栈

- Jsoup

- EasyPoi

- Poi 

- FastJson

- Spring Boot

# 目录结构

```Java
|-- ChinaAirPort
    |-- pom.xml
    |-- src
    |   |-- main
    |       |-- java
    |           |-- com
    |               |-- shen
    |                   |-- MultithreadingMain.java
    |                   |-- SingleThreadMain.java
    |                   |-- config
    |                   |   |-- SavePathConstant.java
    |                   |   |-- UrlConstant.java
    |                   |-- entity
    |                   |   |-- Airport.java
    |                   |   |-- Area.java
    |                   |-- service
    |                   |   |-- AirportCallable.java
    |                   |   |-- AirportService.java
    |                   |-- utils
    |                       |-- ExcelUtil.java
    |                       |-- HtmlParseUtil.java

```

# 如何工作

1. 向 [**中国民用机场数据网**](https://www.chinairport.net/) 发起请求获取地区信息

2. Jsoup 爬取具体机场信息

3. 导出Excel

# 使用

```Bash
git clone https://github.com/Theoshen/ChinaAirport.git
```

1. IDE 打开项目，导入依赖

2. 启动 SingleThreadMain 类（单线程爬取，V1.0版本使用）

3. 或者启动MultithreadingMain类（v2.0版本加入 多线程）

4. 等待程序运行完毕，打开excel查看信息

# 配置

- 线程池大小自行根据情况配置

- Excel保存路径 

```Java
SavePathConstant 添加新的常量 来设置保存路径
```

# License

MIT

# Links

- [Github.com](https://www.github.com/)

