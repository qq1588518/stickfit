const wxx = require('./wxx.js')

const user = wx.getStorageSync('user') || {
  // customer: {"id":1,"openId":"oqZ4f0VOsFEyPM9Gq4vJsmlx-r8I","username":"新阳"}
  customer: null,
  // userInfo: {"nickName":"新阳","gender":1,"language":"zh_CN","city": .,"province": .,"country": .,"avatarUrl": .}
  userInfo: null,
  // group: {"id":1,"name":"下一马"}
  group: null,
}

const getUser = function (resolve, reject) {
  console.log('getUser');
  if (user.customer) {
    resolve(user)
    return;
  }
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
                  user.userInfo = res.userInfo;
                  // 发起网络请求 - nickName
                  wx.request({
                    url: wxx.getPath('/customer/login'),
                    data: { code, username: res.userInfo.nickName },
                    success: e => {
                      serverLogin(e, resolve, reject);
                      resolve(user);
                    },
                    fail: reject
                  })
                }
              })
            } else {
              // 未授权, 发起网络请求 - 无nickName
              wx.request({
                url: wxx.getPath('/customer/login'),
                data: {
                  code: code
                },
                success: e => {
                  serverLogin(e, resolve, reject);
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

const refreshUser = function (resolve, reject) {
  wx.request({
    url: wxx.getPath(`/customerPoes/${user.customer.id}`),
    success: e => {
      serverLogin(e, resolve, reject);
    },
    fail: reject
  })
}

const serverLogin = function (e, resolve, reject) {
  console.log('serverLogin: code =', e);
  user.customer = e.data;
  // if (user.customer.groupId) {
  //   wx.request({
  //     url: wxx.getPath(`/groupPoes/${user.customer.groupId}`),
  //     success: e => {
  //       user.group = e.data;
  //       wx.setStorageSync('user', user);
  //       resolve(user);
  //     },
  //     fail: reject
  //   })
  // } else {
    wx.setStorageSync('user', user);
    resolve(user);
  // }
}

const updateUserInfo = function (userInfo) {
  user.getUser(user => {
    user.userInfo = userInfo;
    wx.request({
      url: wxx.getPath('/customer/update'),
      data: {
        customerId: user.customer.id,
        username: userInfo.nickName
      },
      success: e => {
        user.customer = e.data;
      }
    })
  })
}

module.exports = {
  user,
  getUser,
  refreshUser,
  updateUserInfo
}