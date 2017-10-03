// pages/detail/detail.js
const core = require('../../utils/core.js')

Page({
  data: {
    customer: {},
    records: [],
    summary: ''
  },
  onLoad: function (options) {
    console.log('onLoad: ', options);
    // options.customerId & options.username
    var customerId = options.customerId;
    var username = options.username;
    this.setData({
      customer: { 'id': customerId, 'username': options.username },
    })
    wx.setNavigationBarTitle({ title: username + '本月运动记录' })
  },
  onShow: function () {
    this.currentMonthHistory();
  },
  currentMonthHistory: function () {
    wx.request({
      url: 'https://www.panxinyang.cn/stickfit/exercise/currentMonthHistory',
      data: {
        customerId: this.data.customer.id
      },
      success: res => {
        console.log('currentMonthHistory: ', res);
        res.data.exercisePos.map(exercisePo => {
          var exerciseType = core.exercise.exerciseTypeMap[exercisePo.typeId];
          exercisePo.msg = new Date(exercisePo.time).getDate() + '日 ' + exerciseType.description + exercisePo.amount + exerciseType.unit;
        });
        console.log('currentMonthHistory: ', res.data.exercisePos);
        this.setData({
          records: res.data.exercisePos,
          summary: this.data.customer.username + res.data.summary
        })
      }
    });
  },
})
