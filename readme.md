# MobikeSearch

LBS课的课程设计。

MobikeSearch是一款简单便捷的查询类小程序，主要是为用户提供地图，查询附近共享单车和查询最近共享单车的功能，利用ColorUI组件以及代理摩拜接口实现。

## 小程序界面

- 关于界面

  <img src="https://raw.githubusercontent.com/Snake8859/MobikeSearch/master/%E7%94%B5%E5%AD%90%E7%A8%BF/images/about.png" alt="about" style="zoom:67%;" />



- 首页

  <img src="https://raw.githubusercontent.com/Snake8859/MobikeSearch/master/%E7%94%B5%E5%AD%90%E7%A8%BF/images/index.png" alt="about" style="zoom:67%;" />

- 搜索界面

  <img src="https://raw.githubusercontent.com/Snake8859/MobikeSearch/master/%E7%94%B5%E5%AD%90%E7%A8%BF/images/search1.png" style="zoom:67%;" />

<img src="https://raw.githubusercontent.com/Snake8859/MobikeSearch/master/%E7%94%B5%E5%AD%90%E7%A8%BF/images/search2.png" style="zoom:67%;" />

- 放大和缩小

  <img src="E:\本科\01课程项目\MobikeSearch小程序\电子稿\images\zoomin.png" style="zoom:67%;" />

<img src="https://raw.githubusercontent.com/Snake8859/MobikeSearch/master/%E7%94%B5%E5%AD%90%E7%A8%BF/images/zoomout.png" style="zoom:67%;" />

## 小程序后端

小程序后端使用SpringBoot框架，以Maven构建工程，同时利用HTTPClient发起内部网络请求。其中部分尝试地利用HttpClient+Jsoup，爬取西刺代理和米扑代理提供的代理ip和端口，但最终以失败告终，原因在不足就将说明。

在处理摩拜接口方面，根据百度，谷歌等信息资料，得知摩拜本身不提供官方API接口服务，而是通过抓包方式，将摩拜单车API抓取，对其入口参数和出口参数进行一定分析，通过本地服务器代理，作为小程序和摩拜接口的中转站，在接收到小程序的网络请求后，通过后端对摩拜接口发起网络请求，接收其摩拜单车数据，然后响应给小程序。

同时，摩拜接口对ip有一定限制，但可以通过代理池方式避免，通过获取大量代理ip和端口，经过校验其有效性，再通过代理ip和端口发起网络请求，伪装本地ip，来规避其对ip限制。代理池需要注意几点，第一是ip和端口的有效性检验，第二是定时获取最新ip和端口，并清洗过期的ip和端口。

**摩拜查询附近共享单车接口：**

https://mwx.mobike.com/mobike-api/rent/nearbyBikesInfo.do

- 入口参数：
  - Longitude
  - latitude
  - citycode
  - errMsg

- 出口参数：

  bikeIds，biketype，boundary，distId，distNum，distX，distY，distance，operateType，type

摩拜单车接口详见参考[传送门1](https://zhuanlan.zhihu.com/p/74576583)，[传送门2](https://zhuanlan.zhihu.com/p/31171275)

**西刺代理：**

https://www.xicidaili.com/

**米扑代理：**

https://proxy.mimvp.com/freesecret.php

## 不足

1. 小程序方面，内容和形式过于简单，不够丰富，有点显得太简陋了。
2. 小程序方面，功能过于单一，需要不断增加其他功能。
3. 后端方面，由于摩拜接口对ip访问进行限制，并不是每一次请求，都会成功响应，需要通过代理池方式，避免反爬虫。
4. 后端方面，代理池实现过程中，存在许多问题。首先通过了解，可以通过第三方提供代理ip和端口，避免接口对ip访问的限制，于是考虑通过爬取西刺代理和米扑代理中提供的ip，来创建自己的代理池。在西刺代理爬虫运行中，成功获取前5页的500个代理ip和端口，经过ip和端口有效性校验，最终大概还有50个代理ip和端口可使用，但由于爬取次数太多，西刺代理将本地ip封锁，不能再爬取到其可用ip和端口，十分可惜。米扑代理，爬取过程也类似，但由于其端口特地以图片形式放置，导致端口爬取难度加大，最终也失败。