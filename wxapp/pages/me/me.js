// pages/me/me.js
const user = require('../../utils/user.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    user: {},
    userInfo: {},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    user.getUser(user => {
      if (user && user.userInfo) {
        this.setData({
          user,
          userInfo: user.userInfo
        })
      }
    })
  }

})