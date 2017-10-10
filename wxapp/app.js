//app.js
const wxx = require('./utils/wxx.js')
const core = require('./utils/core.js')

App({
  onLaunch: function (res) {
    wx.showLoading({ mask: true })
    wxx.init
      .then(e => {
        core.user.getUser(e => {
          core.exercise.getExercise (e => {
            console.log('onLaunch: wxx.getEnv() = ', wxx.getEnv());
            console.log('onLaunch: wxx.getPath() = ', wxx.getPath('/'));
            console.log('onLaunch: core.user = ', core.user);
            console.log('onLaunch: core.exercise = ', core.exercise);
            wx.hideLoading()
          })
        })
      })
      .catch(e => {
        console.log('onLaunch: error = ', e);
      })
  },

  globalData: {
    version: '1.1.0'
  }
})