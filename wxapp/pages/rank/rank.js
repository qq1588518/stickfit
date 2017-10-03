// pages/rank/rank.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    years: [2017, 2018, 2019, 2020, 2021, 2022],
    months: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
    rank: null,
    summary: '',
    multiArray:[],
    multiIndex: [0,0]
  },
  onLoad() {
    this.setData({
      multiArray: [this.data.years,this.data.months]
    })
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
  bindPickerChange: function (e) {
    console.log('picker: ', e.detail.value)
    const yearIdx = e.detail.value[0];
    const monthIdx = e.detail.value[1];
    const year = this.data.years[yearIdx];
    const month = this.data.months[yearIdx];

    console.log('year: ', year)
    console.log('month: ', month)
  }
  
})