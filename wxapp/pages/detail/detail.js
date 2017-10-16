// pages/detail/detail.js
const exercise = require('../../utils/exercise.js')
const wxx = require('../../utils/wxx.js')

Page({
  data: {
    customer: {},
    monthSummary: {},
    yearMonth: {}
  },
  onLoad: function (options) {
    console.log('onLoad: ', options);
    // options.customerId & options.username
    var customerId = options.customerId;
    var username = options.username;
    var year = options.year;
    var month = options.month;
    this.setData({
      customer: { 'id': customerId, 'username': options.username },
      yearMonth: { year: year, month: month}
    })
  },
  onShow: function () {
    this.monthSummary();
  },
  monthSummary: function () {
    wx.request({
      url: wxx.getPath('/exercise/monthSummary'),
      data: {
        customerId: this.data.customer.id,
        year: this.data.yearMonth.year,
        month: this.data.yearMonth.month
      },
      success: res => {
        console.log('monthSummary: ', res);
        this.setData({
          monthSummary: res.data
        })
      }
    });
  },
})
