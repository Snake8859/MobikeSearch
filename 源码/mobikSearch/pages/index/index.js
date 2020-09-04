Page({

  /**
   * 页面的初始数据
   */
  data: {
    longitude: "",
    latitude: "",
    markers: [],
    polyline:[],
    scale: 16,
    flag:true
  },
  /**
   * 放大
   */
  zoomIn: function (res) {
    var scale = this.data.scale
    scale = scale + 1
    this.setData({
      scale: scale
    })
  },
  /**
   * 缩小
   */
  zoomOut: function (res) {
    var scale = this.data.scale
    scale = scale - 1
    this.setData({
      scale: scale
    })
  },
  /**
   * 搜索附近共享单车
   */
  searchBike: function (res) {
    var that = this
    //console.log(res)
    wx.request({
      url: 'http://localhost:9090/getNearByBike',
      data: {
        longitude: that.data.longitude,
        latitude: that.data.latitude,
        cityCode: "021"
      },
      success: function (res) {
        console.log(res)
        //确认获取附近单车
        if (res.data.code == 0) {
          // that.data.markers = res.data.object.map(function(item){
          //   item.id = item.bikeIds
          //   item.latitude = item.distY
          //   item.longitude = item.distX
          //   item.iconPath = "/images/location.jpg"
          //   return item
          // })
          var markers =[]
          //设置当前位置
          markers[0] = {
            id:"current",
            latitude:that.data.latitude,
            longitude:that.data.longitude,
            iconPath: "/images/location.jpg",
            width: 20,
            height: 20
          }
          //设置共享单车位置
          for (var i = 0; i < res.data.object.length; i++) {
            var marker = {
              id: "",
              longitude: "",
              latitude: "",
              iconPath: "",
              width: 20,
              height: 20
            }
            marker.id = res.data.object[i].bikeIds
            marker.latitude = res.data.object[i].distY
            marker.longitude = res.data.object[i].distX
            marker.distance = res.data.object[i].distance
            marker.iconPath = "/images/danche.png"
            markers[i+1] = marker
          }
          that.setData({
            markers: markers,
            flag:false
          })
        }
        else {
          console.log("调用失败")
        }
      }
    })
  },
  /**
   * 查询附近最近的共享单车
   */
  searchNearBy:function(res){
    var markers = this.data.markers
    if (markers.length>1){
      //最小距离与索引
      var minDistance = markers[1].distance
      var index = 1;
        for(var i = 0;i<markers.length;i++){
          if(i==0){
            continue
          }
          //进行判断
          if(minDistance>markers[i].distance){
            minDistance = markers[i].distance
            index = i
          } 
        }
      //console.log(minDistance)
      //设置气泡
      var callout = {
        content:"距离最近的单车",
        fontSize:10,
        borderRadius:20,
        color:"#4169E1",
        bgColor:"#D3D3D3",
        display:"ALWAYS"
      }
      markers[index].callout = callout
      wx.showLoading({
        title: '加载中...',
      })
      var that = this
      //路线规划
      wx.request({
        url: 'https://apis.map.qq.com/ws/direction/v1/walking/',
        data:{
          from:that.data.latitude+","+that.data.longitude,
          to:markers[index].latitude+","+markers[index].longitude,
          key:"4LABZ-AMZOF-MPQJK-NJMU4-LOY66-ASB27"
        },
        success:function(res){
          //console.log(res)
          //解压坐标，规划路线
          var polyline = [{
            points: [],
            color: "#FF0000DD",
            width: 2,
            dottedLine: true
          }
          ]
          //设置起始点
          polyline[0].points.push({
            longitude: that.data.longitude,
            latitude: that.data.latitude
          })
          var coors = res.data.result.routes[0].polyline
          for (var i = 2; i < coors.length; i++) { 
            coors[i] = coors[i - 2] + coors[i] / 1000000 
          }
          //console.log(coors)
          for(var i = 0;i<(coors.length+1)/2;i=i+2){
              var point = {}
              point.longitude = coors[i + 1]
              point.latitude = coors[i]
              polyline[0].points.push(point)
          }

          //设置终点
          polyline[0].points.push({
            longitude: markers[index].longitude,
            latitude: markers[index].latitude
          })

          //console.log(polyline)
          
          that.setData({
            markers: markers,
            polyline: polyline,
            scale:19
          })
        },
        fail:function(err){
          console.log(err)
        },
        complete:function(e){
          wx.hideLoading()
        }
      })

    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    
    //获得当前位置
    wx.getLocation({
      success: function (res) {
        //console.log(res)
        //初始化定位
        var markers = [{
          longitude: res.longitude,
          latitude: res.latitude,
          iconPath: "/images/location.jpg",
          width: 20,
          height: 20
        }]
        that.setData({
          longitude: res.longitude,
          latitude: res.latitude,
          markers: markers
        })
      },
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})