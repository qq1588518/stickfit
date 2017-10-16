//logs.js
const user = require('../../utils/user.js')
const exercise = require('../../utils/exercise.js')
const wxx = require('../../utils/wxx.js')

Page({
  data: {
    customer: {},
    monthSummary: {},
    selected: []
  },
  onLoad: function (options) {
    this.setData({
      customer: user.user.customer
    })
  },
  onShow: function () {
    this.monthSummary();
  },
  monthSummary: function () {
    wx.request({
      url: wxx.getPath('/exercise/monthSummary'),
      data: {
        customerId: this.data.customer.id
      },
      success: res => {
        console.log('monthSummary: ', res);
        this.setData({
          monthSummary: res.data,
          selected: []
        })
      }
    });
  },
  checkboxChange: function (e) {
    console.log('checkboxChange: ', e);
    const selected = e.detail.value;
    const monthSummary = this.data.monthSummary;
    console.log('monthSummary: ', monthSummary);
    for (let i = 0, lenI = monthSummary.exerciseVos.length; i < lenI; ++i) {
      monthSummary.exerciseVos[i].checked = false;
      for (let j = 0, lenJ = selected.length; j < lenJ; ++j) {
        if (monthSummary.exerciseVos[i].exercisePo.id == selected[j]) {
          monthSummary.exerciseVos[i].checked = true;
          break;
        }
      }
    }
    this.setData({
      monthSummary,
      selected
    });
  },
  clearSelected: function () {
    var ids = '';
    this.data.records.map(record => {
      console.log('clear selected' + JSON.stringify(record))
      if (record.checked) {
        if (ids === '') {
          ids += record.id;
        } else {
          ids += ',' + record.id;
        }
      }
    });
    console.log('clear selected', ids);
    wx.request({
      url: wxx.getPath('/exercise/deleteExercisesByIds'),
      data: {
        ids: ids
      },
      success: res => {
        this.onShow();
      }
    });
  }
})
