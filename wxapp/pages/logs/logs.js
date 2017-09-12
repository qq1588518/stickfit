//logs.js
const util = require('../../utils/util.js')

Page({
  data: {
    records: []
  },
  onShow: function () {
    var data = wx.getStorageSync('records');
    var records = [];
    Object.keys(data).forEach(k => {
      var record = data[k];
      record.msg = new Date(record.date).getDate() + '日 ' + record.item.desc + record.amount + record.item.unit;
      records.push(data[k]);
    });
    records.sort((a, b) => new Date(a.date) - new Date(b.date));
    this.setData({
      records: records
      // records: (wx.getStorageSync('records') || []).map(record => {
      //   record.checked = false;
      //   record.msg = new Date(record.date).getDate() + '日 ' + record.item.desc + record.amount + record.item.unit;
      //   console.log(JSON.stringify(record));
      //   return record;
      // })
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
