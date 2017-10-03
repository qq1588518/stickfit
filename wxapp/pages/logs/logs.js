//logs.js
const core = require('../../utils/core.js')

Page({
  data: {
    customer: {},
    records: [],
    summary: ''
  },
  onLoad: function (options) {
    core.user.getUser(user => {
      this.setData({
        customer: user.customer
      })
    })
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
  checkboxChange: function (e) {
    console.log('checkbox发生change事件，携带value值为：', e.detail.value);

    var records = this.data.records, values = e.detail.value;
    for (var i = 0, lenI = records.length; i < lenI; ++i) {
      records[i].checked = false;

      for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
        if (records[i].id == values[j]) {
          records[i].checked = true;
          break;
        }
      }
    }

    this.setData({
      records: records
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
      url: 'https://www.panxinyang.cn/stickfit/exercise/deleteExercisesByIds',
      data: {
        ids: ids
      },
      success: res => {
        this.onShow();
      }
    });
  }
})
