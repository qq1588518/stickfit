//logs.js
const user = require('../../utils/user.js')
const exercise = require('../../utils/exercise.js')
const wxx = require('../../utils/wxx.js')

Page({
  data: {
    customer: {},
    monthSummary: {},
    selected: [],
    deleteAction: { status: 'initial', text: '删除' } // initial(删除), deleting(删除选中)
  },
  onLoad: function (options) {
    this.setData({
      customer: user.user.customer
    })
  },
  onShow: function () {
    this.setDelete();
    this.monthSummary();
  },
  monthSummary: function () {
    wx.showNavigationBarLoading();
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
        wx.hideNavigationBarLoading();
      }
    });
  },
  checkboxChange: function (e) {
    if (this.data.deleteAction.status === 'initial') {
      return;
    }
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
    if (this.data.deleteAction.status === 'initial') {
      this.setDelete('deleting')
      this.setData({
        deleteAction: { status: 'deleting', text: '删除选中' }
      })
      return;
    }
    const ids = this.data.selected.join();
    console.log('clear selected', ids);
    wx.request({
      url: wxx.getPath('/exercise/deleteExercisesByIds'),
      data: {
        ids: ids
      },
      success: res => {
        this.setDelete('initial')
        this.onShow();
      }
    });
  },
  resetDelete: function(){
    this.setDelete();
  },
  setDelete: function (status = 'initial') {
    console.log('setDelete: ', status);
    if (status === 'initial') {
      this.setData({
        deleteAction: { status: 'initial', text: '删除' }
      })
    } else {
      this.setData({
        deleteAction: { status: 'deleting', text: '删除选中' }
      })
    }
  }
})
