//app.js
const wxx = require('./wxx.js')
const user = require('./user.js')
const exercise = require('./exercise.js')

const initPromise = new Promise(wxx.initEnv)
  .then(e => { return new Promise(wxx.checkIfClearStorage) })
  .then(e => { return new Promise(exercise.getExercise) })
  .then(e => { return new Promise(user.getUser) })
  .catch(e => { console.log('initPromise: error = ', e) })

const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':');
}

const formatDate = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()

  return [year, month, day].map(formatNumber).join('-');
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

module.exports = {
  formatTime,
  formatDate,
  initPromise,
}
