//index.js
const util = require('../../utils/util.js')

//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    radioItems: [
      { name: '跑步', desc: '跑步', unit: "公里", min: 6, value: '0', checked: true },
      { name: '走步', desc: '走步', unit: "步", min: 10000, value: '1' },
      { name: '游泳', desc: '游泳', unit: "米", min: 1000, value: '3' },
      { name: '骑车', desc: '骑车', unit: "公里", min: 12, value: '4' },
      { name: '其他', desc: '其他运动', unit: "分钟", min: 45, value: '2' },
    ],
    date: "2017-09-01",
    unit: "公里",
    amount: null
  },
  //事件处理函数
  radioChange: function (e) {
    console.log('radio发生change事件，携带value值为：', e.detail.value);

    var radioItems = this.data.radioItems;
    var unit = this.data.unit;
    for (var i = 0, len = radioItems.length; i < len; ++i) {
      radioItems[i].checked = radioItems[i].name == e.detail.value;
      if (radioItems[i].checked) {
        unit = radioItems[i].unit;
      }
    }

    this.setData({
      radioItems: radioItems,
      unit: unit,
      amount: null
    });
  },
  onLoad: function () {
    // 
    this.setData({
      date: util.formatDate(new Date())
    })
    // 
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  bindDateChange: function (e) {
    this.setData({
      date: e.detail.value
    })
  },
  changeAmount: function (e) {
    this.setData({
      amount: e.detail.value
    })
  },
  //
  formSubmit: function (e) {
    var formData = e.detail.value;
    console.log('formSubmit: formData = ' + JSON.stringify(formData));
    var radioItems = this.data.radioItems;
    for (var i = 0; i < radioItems.length; i++) {
      if (radioItems[i].checked) {
        if (this.data.amount == null) {
          // Amount 未输入
          wx.showModal({
            title: '提示',
            content: '请输入运动量',
            showCancel: false,
            success: function (res) {
              if (res.confirm) {
                console.log('用户点击确定')
              } else if (res.cancel) {
                console.log('用户点击取消')
              }
            }
          })
        } else if (this.data.amount < radioItems[i].min) {
          // Amount 不达标
          var content;
          if (radioItems[i].name == "其他") {
            content = radioItems[i].name + '运动的运动量不能少于' + radioItems[i].min + radioItems[i].unit;
          } else {
            content = radioItems[i].name + '的运动量不能少于' + radioItems[i].min + radioItems[i].unit;
          }
          wx.showModal({
            title: '提示',
            content: content,
            showCancel: false,
            success: function (res) {
              if (res.confirm) {
                console.log('用户点击确定')
              } else if (res.cancel) {
                console.log('用户点击取消')
              }
            }
          })
        } else {
          // 打卡成功
          // 保存到本地
          var records = wx.getStorageSync('records') || new Object();
          console.log('before' + JSON.stringify(records));
          formData.item = radioItems[i];
          formData.id = Date.now();
          records[formData.id] = formData;
          console.log('after' + JSON.stringify(records))
          wx.setStorageSync('records', records)
          // 提示信息
          wx.showToast({
            title: '打卡完成',
            duration: 1200
          });
          this.setData({
            amount: null
          })
        }
      }
    }

  },
})
