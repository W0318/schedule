import request from '../util/request'

//定义接口
export const getData = () => {
  //返回一个promise对象
  return request.get('/home/getData')

}

// export default {
//     getMenu(params){
//         return request({
//             url:'/permission/getMenu',
//             method :'post',
//             mock:false,
//             data:params
//         })
//     }
// }
export const getLogin = (data) => {
  console.log(data)
  console.log('拦截到请求')
  return request.post('http://localhost:8082/employee/login', {//请求登录接口
    username: data.username,
    password: data.password
  })
  // return request.post('/permission/getMenu',data)
}

export const getMenu = (data) => {
  console.log(data)
  console.log('拦截到请求')
  return request.post('/permission/getMenu', data)
}

export const getEchartsData = () => {
  console.log('拦截到请求')
  return request.get('/home/getStatisticalData')
}
export default {
  getCountData(params) {
    return request({
      url: '/home/getCountData',
      method: 'get',
      mock: true,
    })
  }
}

export const getWorkday = (employeeId) => {
  return request.get('http://localhost:8082/scheduling/getWorkday/' + employeeId);
}

export const getDaywork = (employeeId, day) => {
  return request.get('http://localhost:8082/scheduling/getDaywork/' + employeeId + '/' + day);
}

export const getAllStore = () => {
  return request.get('http://localhost:8082/store/all');
}

export const getAllEmployee = (storeId) => {
  return request.get('http://localhost:8082/employee/all/' + storeId);
}

export const getAWeekwork = (Monday, Sunday) => {
  return request.get('http://localhost:8082/scheduling/getAWeekwork/' + Monday + '/' + Sunday);
}