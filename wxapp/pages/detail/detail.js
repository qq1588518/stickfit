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
    const customerId = options.customerId;
    const username = options.username;
    const year = options.year;
    const month = options.month;
    this.setData({
      customer: { id: customerId, username: username },
      yearMonth: { year: year, month: month}
    })
  },
  onShow: function () {
    this.monthSummary();
  },
  monthSummary: function () {
    wxx.request({
      url: '/exercise/monthSummary',
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
