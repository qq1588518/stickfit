// pages/rank/rank.js
const wxx = require('../../utils/wxx.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    rank: null,
    summary: ''
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    wx.request({
      url: wxx.getPath('/exercise/rank'),
      success: res => {
        console.log('rank: ', res.data);
        res.data.map(item => {
          item.msg = item.username + '打卡' + item.count + '次 - ' + item.jogAmount + '公里';
        });
        var summary = '共' + res.data.length + '人打卡'
        this.setData({
          rank: res.data,
          summary: summary
        })
      }
    });
  }
  
})