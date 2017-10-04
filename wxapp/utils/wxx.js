const version = '1.1.5'
const contextPath = 'https://www.panxinyang.cn/stickfit'
let cacheTs = wx.getStorageSync('cacheTs')

const init = new Promise(function (resolve, reject) {
  new Promise(initEnv)
    .then(e => {return new Promise(checkIfClearStorage)})
    .then(resolve)
    .catch(reject);
})

function initEnv (resolve, reject) {
  wx.request({
    url: getPath('/core/version', 'prd'),
    success: e => {
      const serverVersion = e.data;
      if (version == serverVersion) {
        wx.setStorageSync('env', 'prd');
      } else {
        wx.setStorageSync('env', 'dev');
      }
      console.log('wxx.initEnv: env =', getEnv())
      resolve(e)
    },
    fail: reject
  })
}

function checkIfClearStorage(resolve, reject) {
  wx.request({
    url: getPath('/core/cacheTimestamp'),
    success: e => {
      const serverCacheTs = e.data;
      if (cacheTs != serverCacheTs) {
        console.log('wxx.checkIfClearStorage: clearing cache')
        const env = getEnv();
        wx.clearStorageSync();
        cacheTs = serverCacheTs;
        wx.setStorageSync('cacheTs', cacheTs);
        wx.setStorageSync('env', env)
      } else {
        console.log('wxx.checkIfClearStorage: NOT clearing cache')
      }
      resolve(e)
    },
    fail: reject
  })
}

function isPrd() {
  if (getEnv() == 'prd') {
    return true;
  } else {
    return false;
  }
}

function getEnv() {
  const env = wx.getStorageSync('env');
  if (!env) {
    throw new Error('env is not set, call initEnv() first.');
  }
  return env;
}

function getPath (path, env) {
  if (env == 'prd') {
    return contextPath + path;
  }
  if (isPrd()) {
    return contextPath + path;
  } else {
    return contextPath + '/dev' + path;
  }
}

module.exports = {
  init,
  getPath,
  getEnv,
  isPrd
}