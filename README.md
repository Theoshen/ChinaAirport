
# 中国机场 

本项目统计了中国范围内的所有民航运输机场的基本信息，包括了名称、地址、等级、经纬度、机场代码等信息

- 1，数据来源于 [**中国民用机场数据网**](https://www.chinairport.net/)、百度百科、维基百科。

- 2，代码中注释部分区分的中国境内泛指大陆地区及海南岛。

- 3，**台湾是中国永不分割的一部分**。

配图

# 技术栈

- JDK 17

- Maven Wrapper

- Jsoup

- EasyPoi

- Poi 

- FastJson

- Spring Boot

# 目录结构

```Java
|-- ChinaAirPort
    |-- .java-version
    |-- .mvn
    |-- mvnw
    |-- mvnw.cmd
    |-- pom.xml
    |-- output
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
cd ChinaAirport
./mvnw clean verify
```

1. 确认本地使用 JDK 17。仓库已提供 `.java-version`，支持 jenv/asdf 的环境会自动切换。

2. IDE 打开项目，导入 Maven 依赖。

3. 启动 `SingleThreadMain` 类（单线程爬取）。

4. 或者启动 `MultithreadingMain` 类（多线程爬取，默认打包入口）。

5. 等待程序运行完毕，在 `output/` 目录查看 Excel。

## 调试

如果使用 VS Code，可以使用本地 `.vscode/launch.json` 中的配置：

- Debug MultithreadingMain

- Debug SingleThreadMain

# 配置

- 线程池大小自行根据情况配置

- Excel 默认保存到项目根目录 `output/`，该目录已加入 `.gitignore`，不会提交到仓库。

```Java
SavePathConstant.OUTPUT
```

# 验证

```Bash
./mvnw clean verify
```

当前项目使用 Maven Wrapper，首次执行会自动下载 Maven 和项目依赖。

# License

MIT

# Links

- [Github.com](https://www.github.com/)
