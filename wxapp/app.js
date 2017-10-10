//app.js
const wxx = require('./utils/wxx.js')
const user = require('./utils/user.js')
const exercise = require('./utils/exercise.js')

App({
  onLaunch: function (res) {
    wx.showLoading({ mask: true })
    new Promise(wxx.initEnv)
      .then(e => { return new Promise(wxx.checkIfClearStorage) })
      .then(e => { return new Promise(exercise.getExercise) })
      .then(e => { return new Promise(user.getUser) })
      .then(e => { this.done() })
      .catch(e => { console.log('onLaunch: error = ', e) })
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