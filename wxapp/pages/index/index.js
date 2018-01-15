//index.js
const util = require('../../utils/util.js')
const user = require('../../utils/user.js')
const exercise = require('../../utils/exercise.js')
const wxx = require('../../utils/wxx.js')

//获取应用实例
const app = getApp()

Page({
  data: {
    customer: {},
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    radioItems: null,
    date: null,
    unit: '',
    amount: null
  },
  onLoad: function () {
    util.initPromise.then(e => { this.done() })
  },
  onShow: function () {
    this.setData({
      date: util.formatDate(new Date())
    })
  },
  done: function () {
    if (!wxx.isPrd()) {
      wx.setNavigationBarTitle({
        title: '下一马(开发)'
      })
    }
    let radioItems = JSON.parse(JSON.stringify(exercise.exercise.exerciseTypes));
    radioItems[0].checked = true;
    this.setData({
      radioItems: radioItems,
      unit: radioItems[0].unit
    })
    // 
    user.getUser(user => {
      if (user && user.userInfo) {
        this.setData({
          userInfo: user.userInfo,
          customer: user.customer,
          hasUserInfo: true
        })
      }
    })
  },
  //事件处理函数
  radioChange: function (e) {
    console.log('radio发生change事件，携带value值为：', e.detail.value);
    let radioItems = this.data.radioItems;
    let unit = this.data.unit;
    for (let i = 0, len = radioItems.length; i < len; ++i) {
      radioItems[i].checked = (radioItems[i].id == e.detail.value);
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
  getUserInfo: function (e) {
    let userInfo = e.detail.userInfo;
    this.setData({
      userInfo: userInfo,
      hasUserInfo: true
    })
    user.updateUserInfo(userInfo, user => {
      if (user && user.userInfo) {
        this.setData({
          userInfo: user.userInfo,
          customer: user.customer,
          hasUserInfo: true
        })
      }
    });
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
    if (!user.user.customer.groupId) {
      // 未加入任何跑团
      wx.showModal({
        title: '提示',
        content: '请先加入一个跑团',
        showCancel: false
      })
      return;
    }
    const formData = e.detail.value;
    formData.amount = new Number(formData.amount);
    console.log('formSubmit: formData = ' + JSON.stringify(formData));
    const radioItems = this.data.radioItems;
    for (let i = 0; i < radioItems.length; i++) {
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
          const content = radioItems[i].description + '的运动量不能少于' + radioItems[i].min + radioItems[i].unit;
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
          const exercise = {};
          exercise.amount = formData.amount;
          exercise.typeId = formData.type;
          exercise.time = new Date(formData.date);
          exercise.customerId = user.user.customer.id;
          exercise.groupId = user.user.customer.groupId;
          // 远端添加
          wxx.request({
            url: '/exercisePoes',
            method: 'POST',
            data: exercise,
            success: res => {
              console.log('exercisePoes: ' + JSON.stringify(res));
              // 提示信息
              wx.showToast({
                title: '打卡完成',
                duration: 1200
              });
              this.setData({
                amount: null
              })
            }
          });
        }
      }
    }

  },
})
