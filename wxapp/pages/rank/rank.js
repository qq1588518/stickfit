// pages/rank/rank.js
const wxx = require('../../utils/wxx.js')
const user = require('../../utils/user.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    rank: null,
    summary: '',
    historyRange: [],
    selectedIndex: 0,
    yearMonth: null
  },
  init: function () {
    this.setData({
      rank: null,
      summary: '',
      historyRange: [],
      selectedIndex: 0,
      yearMonth: null
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onLoad: function (options) {
    console.log('rank.onLoad: ', options);
    this.init();
    if (!user.user.customer.groupId) {
      return;
    }
    // 
    wx.showNavigationBarLoading();
    wx.request({
      url: wxx.getPath('/exercise/rank'),
      data: {
        groupId: user.user.customer.groupId
      },
      success: this.setRankData
    });
    wx.request({
      url: wxx.getPath('/exercise/historyRange'),
      success: res => {
        console.log('historyRange: ', res.data);
        res.data.map(yearMonth => {
          if (yearMonth.month < 10) {
            yearMonth.display = `${yearMonth.year}年0${yearMonth.month}月`
          } else {
            yearMonth.display = `${yearMonth.year}年${yearMonth.month}月`
          }
        });
        this.setData({
          historyRange: res.data,
          selectedIndex: res.data.length - 1,
          yearMonth: res.data[res.data.length - 1]
        })
      }
    });
  },
  onPullDownRefresh: function () {
    this.onLoad({});
    wx.stopPullDownRefresh();
  },
  changeMonth: function (e) {
    const selectedIndex = e.detail.value;
    const yearMonth = this.data.historyRange[selectedIndex];
    const data = yearMonth;
    data.groupId = user.user.customer.groupId
    console.log('changeMonth:', yearMonth);
    wx.showNavigationBarLoading();
    wx.request({
      url: wxx.getPath('/exercise/rank'),
      data: data,
      success: res => {
        this.setRankData(res);
        this.setData({
          selectedIndex,
          yearMonth
        })
      }
    });
  },
  setRankData(res) {
    console.log('rank: ', res.data);
    res.data.map(item => {
      item.msg = item.username + '打卡' + item.count + '次 - ' + item.jogAmount + '公里';
    });
    var summary = '共' + res.data.length + '人打卡'
    this.setData({
      rank: res.data,
      summary: summary
    })
    wx.hideNavigationBarLoading()
  }
})