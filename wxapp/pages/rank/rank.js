// pages/rank/rank.js
const wxx = require('../../utils/wxx.js')
const user = require('../../utils/user.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    rank: null,
    historyRange: [],
    selectedIndex: 0,
    yearMonth: null
  },
  init: function () {
    this.setData({
      rank: null,
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
    // history range
    wxx.request({
      url: '/exercise/historyRange',
      success: res => {
        const historyRange = res.data;
        console.log('historyRange: ', historyRange);
        this.setData({
          historyRange: historyRange,
          selectedIndex: historyRange.length - 1,
          yearMonth: historyRange[historyRange.length - 1]
        })
        this.loadRank();
      }
    });
    // 
  },
  onPullDownRefresh: function () {
    this.onLoad({});
    wx.stopPullDownRefresh();
  },
  changeMonth: function (e) {
    const selectedIndex = e.detail.value;
    this.loadRank(selectedIndex);
  },
  loadRank(selectedIndex = this.data.selectedIndex) {
    const yearMonth = this.data.historyRange[selectedIndex];
    const data = JSON.parse(JSON.stringify(yearMonth));
    data.groupId = user.user.customer.groupId;
    // 
    wx.showNavigationBarLoading();
    wxx.request({
      url: '/exercise/rank',
      data,
      success: res => {
        const rank = res.data;
        console.log('rank: ', rank);
        this.setData({
          rank: rank,
          selectedIndex,
          yearMonth
        })
        wx.hideNavigationBarLoading()
      }
    });
  }
})