import axios from "axios";
// import { ElMessage } from "element-plus";
// const NETWORKE_ERROR = '网络请求错误，请稍后重试……'
const service = axios.create({
    //通用请求地址前缀
    baseURL: '/api',
    timeout: 1000,//超时时间
    // headers: {'Content-Type': 'application/x-www-form-urlencoded'}

})

service.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  return config;  
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 添加响应拦截器
service.interceptors.response.use(function (response) {
  // 2xx 范围内的状态码都会触发该函数。
  // 对响应数据做点什么
  console.log(response.status)
  // console.log('2222')
  return response;
}, function (error) {
  // 超出 2xx 范围的状态码都会触发该函数。
  // 对响应错误做点什么
  return Promise.reject(error);
});
export default service