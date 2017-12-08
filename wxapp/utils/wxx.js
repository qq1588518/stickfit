const version = '2.0.5'
const contextPath = 'https://www.nextmarathon.cn/stickfit'
const contextPathDev = 'https://www.nextmarathon.cn/stickfit/dev'
let cacheTs = wx.getStorageSync('cacheTs')

function initEnv(resolve, reject) {
  request({
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
  request({
    url: '/core/cacheTimestamp',
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

function getPath(path, env) {
  if (env == 'prd') {
    return contextPath + path;
  }
  if (isPrd()) {
    return contextPath + path;
  } else {
    return contextPathDev + path;
  }
}

function request(obj) {
  if (!obj.url.startsWith('http')) {
    obj.url = getPath(obj.url);
  }
  obj.header = { 'Authorization': 'Basic c2VhbjoxMWFhMjJiYg==' };
  wx.request(obj);
}

module.exports = {
  initEnv,
  checkIfClearStorage,
  getPath,
  getEnv,
  isPrd,
  request
}