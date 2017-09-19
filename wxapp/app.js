//app.js
App({
  onLaunch: function (res) {
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        console.log('login: code=' + res.code);
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo;

              console.log('userInfo: ' + JSON.stringify(res.userInfo));

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    sportItems: [
      { name: '跑步', desc: '跑步', unit: "公里", min: 6, value: '0', checked: true },
      { name: '走步', desc: '走步', unit: "步", min: 10000, value: '1' },
      { name: '游泳', desc: '游泳', unit: "米", min: 1000, value: '3' },
      { name: '骑车', desc: '骑车', unit: "公里", min: 12, value: '4' },
      { name: '其他', desc: '其他运动', unit: "分钟", min: 45, value: '2' },
    ]
  }
})