// pages/rank/rank.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    rank: null,
    summary: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
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
    wx.request({
      url: 'https://www.panxinyang.cn/stickfit/exercise/rank',
      success: res => {
        console.log('rank: ', res.data);
        res.data.map(item => {
          item.msg = item.username + '打卡' + item.count + '次'
        });
        var summary = '共' + res.data.length + '人打卡'
        this.setData({
          rank: res.data,
          summary: summary
        })
      }
    });
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