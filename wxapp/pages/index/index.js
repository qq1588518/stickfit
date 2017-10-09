//index.js
const util = require('../../utils/util.js')
const core = require('../../utils/core.js')
const wxx = require('../../utils/wxx.js')

//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    radioItems: null,
    date: '2017-09-01',
    unit: '',
    amount: null
  },
  onLoad: function () {
    // 
    core.exercise.getExercise(exercise => {
      exercise.exerciseTypes[0].checked = true;
      this.setData({
        date: util.formatDate(new Date()),
        radioItems: exercise.exerciseTypes,
        unit: exercise.exerciseTypes[0].unit
      })
    })
    // 
    core.user.getUser(user => {
      if (user && user.userInfo) {
        this.setData({
          userInfo: user.userInfo,
          hasUserInfo: true
        })
      }
    })
  },
  //事件处理函数
  radioChange: function (e) {
    console.log('radio发生change事件，携带value值为：', e.detail.value);

    var radioItems = this.data.radioItems;
    var unit = this.data.unit;
    for (var i = 0, len = radioItems.length; i < len; ++i) {
      radioItems[i].checked = radioItems[i].id == e.detail.value;
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
    core.user.updateUserInfo(userInfo);
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
    formData.amount = new Number(formData.amount);
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
          core.user.getUser(user => {
            var exercise = {};
            exercise.amount = formData.amount;
            exercise.typeId = formData.type;
            exercise.time = new Date(formData.date);
            exercise.customerId = user.customer.id;
            // 远端添加
            wx.request({
              url: wxx.getPath('/exercisePoes'),
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
          })
        }
      }
    }

  },
})
