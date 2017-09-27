//app.js
App({
  onLaunch: function (res) {
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        var code = res.code;
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
                    this.globalData.userInfo = res.userInfo;
                    console.log('userInfo: ' + JSON.stringify(this.globalData.userInfo));
                    // 发起网络请求 - nickName
                    wx.request({
                      url: 'https://www.panxinyang.cn/stickfit/customer/login',
                      data: {
                        code: code,
                        username: res.userInfo.nickName
                      },
                      success: e => {
                        this.globalData.customer = e.data;
                        console.log('customer: ' + JSON.stringify(this.globalData.customer));
                      }
                    })
                    // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                    // 所以此处加入 callback 以防止这种情况
                    if (this.userInfoReadyCallback) {
                      this.userInfoReadyCallback(res)
                    }
                  }
                })
              } else {
                // 发起网络请求 - 无nickName
                wx.request({
                  url: 'https://www.panxinyang.cn/stickfit/customer/login',
                  data: {
                    code: code
                  },
                  success: e => {
                    this.globalData.customer = e.data;
                    console.log('customer: ', this.globalData.customer);
                  }
                })
              }
            }
          })
        } else {
          console.log('获取用户登录态失败！' + res.errMsg)
        }
      }
    })
    // 获取运动信息
    wx.request({
      url: 'https://www.panxinyang.cn/stickfit/exerciseTypePoes',
      success: res => {
        this.globalData.exerciseTypes = res.data._embedded.exerciseTypePoes;
        for (let i in this.globalData.exerciseTypes) {
          let exerciseType = this.globalData.exerciseTypes[i];
          this.globalData.exerciseTypeMap[exerciseType.id] = exerciseType;
        }
        console.log('exerciseTypeMap: ' + JSON.stringify(this.globalData.exerciseTypeMap));
        // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
        // 所以此处加入 callback 以防止这种情况
        if (this.exerciseTypesReadyCallback) {
          console.log('exerciseTypes: ' + JSON.stringify(this.globalData.exerciseTypes));
          this.exerciseTypesReadyCallback(this.globalData.exerciseTypes);
        }
      }
    })
  },

  globalData: {
    customer: null,
    userInfo: null,
    exerciseTypes: null,
    // id -> exerciseTypePo
    exerciseTypeMap: {},
  }
})