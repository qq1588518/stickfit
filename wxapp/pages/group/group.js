// pages/group/group.js
const user = require('../../utils/user.js')
const wxx = require('../../utils/wxx.js')

Page({
  data: {
    inputShowed: false,
    inputVal: "",
    inGroup: false,
    group: {},
    groupMembers: [],
    buttonHidden: {
      transfer: false,
      dismiss: false,
      leave: false,
      join: false,
      create: false,
    }
  },
  onShow: function () {
    if (user.user.customer.groupId) {
      new Promise((resolve, reject) => {
        wx.request({
          url: wxx.getPath(`/groupPoes/${user.user.customer.groupId}`),
          success: resolve,
          fail: reject
        })
      }).then(e => {
        const group = e.data;
        const isOwner = (group.id == user.user.customer.groupId)
        this.setData({
          group: group,
          inGroup: true,
          buttonHidden: {
            transfer: isOwner, dismiss: isOwner, leave: !isOwner,
            join: false, create: false,
          }
        })
        return new Promise((resolve, reject) => {
          wx.request({
            url: wxx.getPath(`/customerPoes/${group.owner}`),
            success: resolve,
            fail: reject
          })
        })
      }).then(e => {
        const owner = e.data;
        const group = this.data.group;
        group.ownerName = owner.username;
        this.setData({
          group: group
        })
        return new Promise((resolve, reject) => {
          wx.request({
            url: wxx.getPath(`/customerPoes/search/findByGroupIdAndUsernameIsNotNull?groupId=${group.id}`),
            success: resolve,
            fail: reject
          })
        })
      }).then(e => {
        const groupMembers = e.data._embedded.customerPoes;
        console.log('groupMembers', groupMembers)
        this.setData({
          groupMembers: groupMembers
        })
      })
    } else {
      this.setData({
        buttonHidden: {
          transfer: false, dismiss: false, leave: false,
          join: false, create: true,
        }
      })
    }
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
              group: {},
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
            user.refreshUser((resolve, reject) => {
              this.setData({
                inGroup: false,
                group: {},
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
    wx.showModal({
      title: '注意',
      content: '离开跑团后, 您的所有运动记录将被删除.',
      confirmColor: '#E64340',
      confirmText: '离开',
      cancelColor: '#3CC51F',
      cancelText: '返回',
      success: res => {
        if (res.confirm) {
          this.setData({
            inGroup: false
          })
        } else {
          console.log('用户点击取消')
        }
      }
    })
  },
  createGroup: function () {
    wx.navigateTo({
      url: '../groupCreate/groupCreate'
    })
  },
});