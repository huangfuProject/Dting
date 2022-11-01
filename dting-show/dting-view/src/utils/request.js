//********************************************请求相关的方法********************************************

import axios from "axios";

// 加载进度条
import NProgress from "nprogress";
import "nprogress/nprogress.css";

//初始化相关的方法
var instance = axios.create({
  //接口的根目录
  baseURL: "http://127.0.0.1:8081",
  //超时时间
  timeout: 30000,
  headers: {},
});

instance.defaults.headers.post['Content-Type'] = 'application/json';

// 添加请求拦截器
instance.interceptors.request.use(
  function(config) {
    // 在发送请求之前做些什么
    //进度条开启
    NProgress.start();
    return config;
  },
  function(error) {
    // 对请求错误做些什么
    //进度条关闭
    NProgress.done();
    return Promise.reject(error);
  }
);

// 添加响应拦截器
instance.interceptors.response.use(
  function(response) {
    // 对响应数据做点什么
    //进度条关闭
    NProgress.done();
    let result = response.data;
    if (result.code !== "000000") {
      alert(result.message)
      return null;
    }
    return response;
  },
  function(error) {
    // 对响应错误做点什么
    //进度条关闭
    NProgress.done();
    return Promise.reject(error);
  }
);

//get请求的工具类
let get = async function(url, params) {
  let { data } = await instance.get(url, {
    params,
  });
  return data;
};

//post请求的工具类
let post = async function(url, params) {
  if (!(typeof params == 'string')) {
        params = JSON.stringify(params)
  }
  let { data } = await instance.post(url, params);
  return data;
};

//post请求的工具类
let postHead = async function(url, params, headers) {
  if (!(typeof params == 'string')) {
    params = JSON.stringify(params)
  }
  let { data } = await instance.post(url, params, {
    headers: headers,
  });
  return data;
};

// 设置token
let setToken = function() {
  instance.defaults.headers.common["token"] = sessionStorage.getItem(
    "myh-job-token"
  );
};

//导出对应的方法
export { get, post, postHead, setToken };
