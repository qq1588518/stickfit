//logs.js
const util = require('../../utils/util.js')

//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    records: [],
    summary: null
  },
  onLoad: function () {
    this.setData({
      userInfo: app.globalData.userInfo
    })
  },
  onShow: function () {
    var data = wx.getStorageSync('records');
    var records = [];
    var total = 0;
    var amountMap = {};
    Object.keys(data).forEach(k => {
      var record = data[k];
      record.msg = new Date(record.date).getDate() + '日 ' + record.item.desc + record.amount + record.item.unit;
      records.push(data[k]);
      // 
      total++;
      if (amountMap[record.item.desc]) {
        amountMap[record.item.desc]['amount'] += new Number(record.amount);
      } else {
        amountMap[record.item.desc] = { 'amount': new Number(record.amount), 'unit': record.item.unit };
      }
    });
    console.log('amountMap：', amountMap);
    var msg = '本月共打卡' + total + '次.'
    console.log('msg', amountMap);
    if (total != 0) {
      msg += ' 一共'
      var first = true;
      if (amountMap['跑步']) {
        if (first) {
          first = false;
        }
        msg += '跑步' + amountMap['跑步']['amount'] + amountMap['跑步']['unit']
      }
      if (amountMap['走步']) {
        if (first) {
          first = false;
        } else {
          msg += ', '
        }
        msg += '走步' + amountMap['走步']['amount'] + amountMap['走步']['unit']
      }
      if (amountMap['游泳']) {
        if (first) {
          first = false;
        } else {
          msg += ', '
        }
        msg += '游泳' + amountMap['游泳']['amount'] + amountMap['游泳']['unit']
      }
      if (amountMap['骑车']) {
        if (first) {
          first = false;
        } else {
          msg += ', '
        }
        msg += '骑车' + amountMap['骑车']['amount'] + amountMap['骑车']['unit']
      }
      if (amountMap['其他运动']) {
        if (first) {
          first = false;
        } else {
          msg += ', '
        }
        msg += '其他运动' + amountMap['其他运动']['amount'] + amountMap['其他运动']['unit']
      }
      msg += '.'
    }
    records.sort((a, b) => new Date(a.date) - new Date(b.date));
    this.setData({
      records: records,
      summary: msg
    })
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
    var records = wx.getStorageSync('records');
    this.data.records.map(record => {
      console.log('clear selected' + JSON.stringify(record))
      if (record.checked) {
        delete records[record.id]
      }
    });
    wx.setStorageSync('records', records);
    this.onShow();
  },
  clearData: function () {
    wx.removeStorageSync('records');
    this.onShow();
  }
})
