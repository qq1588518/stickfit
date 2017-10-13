//logs.js
const user = require('../../utils/user.js')
const exercise = require('../../utils/exercise.js')
const wxx = require('../../utils/wxx.js')

Page({
  data: {
    customer: {},
    records: [],
    summary: '',
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
        const exercisePos = res.data.exercisePos || [];
        exercisePos.map(exercisePo => {
          var exerciseType = exercise.exercise.exerciseTypeMap[exercisePo.typeId];
          exercisePo.msg = new Date(exercisePo.time).getDate() + '日 ' + exerciseType.description + exercisePo.amount + exerciseType.unit;
        });
        console.log('monthSummary: ', res.data.exercisePos);
        this.setData({
          records: res.data.exercisePos,
          summary: this.data.customer.username + res.data.summary,
          selected: []
        })
      }
    });
  },
  checkboxChange: function (e) {
    const selected = e.detail.value;
    console.log('checkbox发生change事件，携带value值为：', selected);
    const records = this.data.records;
    for (var i = 0, lenI = records.length; i < lenI; ++i) {
      records[i].checked = false;

      for (var j = 0, lenJ = selected.length; j < lenJ; ++j) {
        if (records[i].id == selected[j]) {
          records[i].checked = true;
          break;
        }
      }
    }

    this.setData({
      records: records,
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
