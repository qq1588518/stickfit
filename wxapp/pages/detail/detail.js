// pages/detail/detail.js
const exercise = require('../../utils/exercise.js')
const wxx = require('../../utils/wxx.js')

Page({
  data: {
    customer: {},
    records: [],
    summary: '',
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
        res.data.exercisePos.map(exercisePo => {
          var exerciseType = exercise.exercise.exerciseTypeMap[exercisePo.typeId];
          exercisePo.msg = new Date(exercisePo.time).getDate() + '日 ' + exerciseType.description + exercisePo.amount + exerciseType.unit;
        });
        console.log('monthSummary: ', res.data.exercisePos);
        this.setData({
          records: res.data.exercisePos,
          summary: res.data.summary
        })
      }
    });
  },
})
