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
    group.ownerId = user.user.customer.id;
    wxx.request({
      url: '/group/create',
      method: 'GET',
      data: group,
      success: res => {
        user.refreshUserCustomer((resolve, reject) => {
          this.setData({
            afterCreate: true
          })
        })
      }
    });
  }
})