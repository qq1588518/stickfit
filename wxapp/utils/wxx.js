const clearStorageIfVersionNotMatch = function (version, versionKey = 'version'){
  if (wx.getStorageSync(versionKey) != version) {
    wx.clearStorageSync()
    wx.setStorageSync(versionKey, version)
    console.log('Storage cleared!')
  } else {
    console.log('version matched!')
  }
}

module.exports = {
  clearStorageIfVersionNotMatch
}