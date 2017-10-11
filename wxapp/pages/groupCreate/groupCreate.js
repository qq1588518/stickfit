// pages/groupCreate/groupCreate.js
const user = require('../../utils/user.js')
const wxx = require('../../utils/wxx.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    afterCreate: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  formSubmit: function (e) {
    const group = e.detail.value;
    group.owner = user.user.customer.id;
    wx.request({
      url: wxx.getPath('/group/create'),
      method: 'GET',
      data: group,
      success: res => {
        user.refreshUser((resolve, reject) => {
          this.setData({
            afterCreate: true
          })
        })
      }
    });
  }
})