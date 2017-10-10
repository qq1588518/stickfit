const wxx = require('./wxx.js')

const exercise = wx.getStorageSync('exercise') || {
  // exerciseTypePo Array
  exerciseTypes: null,
  // id -> exerciseTypePo
  exerciseTypeMap: {},
}

const getExercise = function (resolve, reject) {
  if (exercise.exerciseTypes) {
    resolve(exercise)
    return;
  }
  // 获取运动信息
  wx.request({
    url: wxx.getPath('/exerciseTypePoes'),
    success: res => {
      exercise.exerciseTypes = res.data._embedded.exerciseTypePoes;
      for (let i in exercise.exerciseTypes) {
        let exerciseType = exercise.exerciseTypes[i];
        exercise.exerciseTypeMap[exerciseType.id] = exerciseType;
      }
      wx.setStorageSync('exercise', exercise);
      resolve(exercise);
    }
  })
}

module.exports = {
  exercise,
  getExercise
}