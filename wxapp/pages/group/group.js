// pages/group/group.js
const user = require('../../utils/user.js')
const wxx = require('../../utils/wxx.js')

Page({
  data: {
    inputShowed: false,
    inputVal: "",
    inGroup: false,
    group: null,
    buttonHidden: {
      transfer: false,
      dismiss: false,
      leave: false,
      join: false,
      create: false,
    }
  },
  onShow: function () {
    new Promise(user.getUserGroup)
      .then(e => {
        const groupVo = e.data;
        const isOwner = (groupVo.group.ownerId == user.user.customer.id)
        this.setData({
          group: groupVo,
          inGroup: true,
          // buttonHidden: {
          //   transfer: isOwner, dismiss: isOwner, leave: !isOwner,
          //   join: false, create: false,
          // }
        })
      })
      .catch(e => {
        console.log('user.getUserGroup - catch: ', e)
        this.setData({
          inGroup: false,
          // buttonHidden: {
          //   transfer: false, dismiss: false, leave: false,
          //   join: false, create: true,
          // }
        })
      })
  },
  showInput: function () {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function () {
    this.setData({
      inputVal: "",
      inputShowed: false
    });
  },
  clearInput: function () {
    this.setData({
      inputVal: ""
    });
  },
  inputTyping: function (e) {
    this.setData({
      inputVal: e.detail.value
    });
  },
  leaveGroup: function () {
    new Promise((resolve, reject) => {
      wx.showModal({
        title: '注意', content: '离开跑团后, 您的所有运动记录将被删除.',
        confirmColor: '#E64340', confirmText: '离开', cancelColor: '#3CC51F', cancelText: '返回',
        success: resolve, fail: reject
      })
    }).then(res => {
      if (res.confirm) {
        wx.request({
          url: wxx.getPath('/group/leave'),
          data: { customerId: user.user.customer.id, groupId: user.user.customer.groupId },
          success: e => {
            this.setData({
              inGroup: false,
              group: null,
              buttonHidden: {
                transfer: false, dismiss: false, leave: false,
                join: false, create: true,
              }
            })
          }
        })
      } else {
        console.log('用户点击取消')
      }
    })
  },
  dismissGroup: function () {
    new Promise((resolve, reject) => {
      wx.showModal({
        title: '注意', content: '解散跑团后, 所有成员的所有运动记录将被删除.',
        confirmColor: '#E64340', confirmText: '解散', cancelColor: '#3CC51F', cancelText: '返回',
        success: resolve, fail: reject
      })
    }).then(res => {
      if (res.confirm) {
        wx.request({
          url: wxx.getPath('/group/dismiss'),
          data: { customerId: user.user.customer.id, groupId: user.user.customer.groupId },
          success: e => {
            user.refreshUserCustomer((resolve, reject) => {
              this.setData({
                inGroup: false,
                group: null,
                buttonHidden: {
                  transfer: false, dismiss: false, leave: false,
                  join: false, create: true,
                }
              })
            })
          }
        })
      } else {
        console.log('用户点击取消')
      }
    })
  },
  transferGroup: function () {
    console.log('transferGroup')
  },
  joinGroup: function () {
    console.log('joinGroup')
  },
  createGroup: function () {
    wx.navigateTo({
      url: '../groupCreate/groupCreate'
    })
  },
});