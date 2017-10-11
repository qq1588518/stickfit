//app.js
const wxx = require('./utils/wxx.js')
const user = require('./utils/user.js')
const exercise = require('./utils/exercise.js')
const util = require('./utils/util.js')

App({
  onLaunch: function (res) {
    wx.showLoading({ mask: true })
    util.initPromise.then(e => { this.done() })
  },
  done: function () {
    console.log('onLaunch: wxx.getEnv() = ', wxx.getEnv());
    console.log('onLaunch: wxx.getPath() = ', wxx.getPath('/'));
    console.log('onLaunch: user.user = ', user.user);
    console.log('onLaunch: exercise.exercise = ', exercise.exercise);
    wx.hideLoading()
  },
  globalData: {
  }
})