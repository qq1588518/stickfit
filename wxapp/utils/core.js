const wxx = require('./wxx.js')

const user = {
  // customer: {"id":1,"openId":"oqZ4f0VOsFEyPM9Gq4vJsmlx-r8I","username":"新阳"}
  customer: null,
  // userInfo: {"nickName":"新阳","gender":1,"language":"zh_CN","city": .,"province": .,"country": .,"avatarUrl": .}
  userInfo: null,

  getUser: function (resolve, reject) {
    if (this.customer) {
      resolve(this)
      return;
    }
    var user = wx.getStorageSync('user');
    if (user && user.customer) {
      this.customer = user.customer
      this.userInfo = user.userInfo
      resolve(this)
    } else {
      // 登录
      wx.login({
        success: res => {
          // 发送 res.code 到后台换取 openId, sessionKey, unionId
          const code = res.code;
          console.log('login: code=' + code);
          if (code) {
            // 获取用户信息 nickName
            wx.getSetting({
              success: res => {
                if (res.authSetting['scope.userInfo']) {
                  // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                  wx.getUserInfo({
                    success: res => {
                      // 可以将 res 发送给后台解码出 unionId
                      this.userInfo = res.userInfo;
                      // 发起网络请求 - nickName
                      wx.request({
                        url: wxx.getPath('/customer/login'),
                        data: { code, username: res.userInfo.nickName },
                        success: e => {
                          this.customer = e.data;
                          resolve(this);
                          wx.setStorageSync('user', this);
                        },
                        fail: reject
                      })
                    }
                  })
                } else {
                  // 发起网络请求 - 无nickName
                  wx.request({
                    url: wxx.getPath('/customer/login'),
                    data: {
                      code: code
                    },
                    success: e => {
                      resolve(this);
                      wx.setStorageSync('user', this);
                    },
                    fail: reject
                  })
                }
              }
            })
          } else {
            reject(res.errMsg)
          }
        }
      })
    }
  },
  updateUserInfo: function (userInfo) {
    this.getUser(user => {
      this.userInfo = userInfo;
      wx.request({
        url: wxx.getPath('/customer/update'),
        data: {
          customerId: user.customer.id,
          username: userInfo.nickName
        },
        success: e => {
          this.customer = e.data;
        }
      })
    })
  }
}

const exercise = {
  
  // exerciseTypePo Array
  exerciseTypes: null,
  // id -> exerciseTypePo
  exerciseTypeMap: {},
  getExercise: function (resolve, reject) {
    if (this.exerciseTypes) {
      resolve(this)
      return;
    }
    var exercise = wx.getStorageSync('exercise');
    if (exercise) {
      this.exerciseTypes = exercise.exerciseTypes
      this.exerciseTypeMap = exercise.exerciseTypeMap
      resolve(this)
      return;
    }
    // 获取运动信息
    wx.request({
      url: wxx.getPath('/exerciseTypePoes'),
      success: res => {
        this.exerciseTypes = res.data._embedded.exerciseTypePoes;
        for (let i in this.exerciseTypes) {
          let exerciseType = this.exerciseTypes[i];
          this.exerciseTypeMap[exerciseType.id] = exerciseType;
        }
        resolve(this);
        wx.setStorageSync('exercise', this);
      }
    })
  }
}


module.exports = {
  user,
  exercise
}