//app.js
const wxx = require('./utils/wxx.js')

App({
  onLaunch: function (res) {
    wxx.clearStorageIfVersionNotMatch(this.globalData.version);
  },

  globalData: {
    version: '1.1.0'
  }
})