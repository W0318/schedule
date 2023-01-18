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
export const getMenu=(data) => {
    console.log(data)
    console.log('拦截到请求')
  return request.post('/permission/getMenu',data)
}
  
export const getEchartsData=() => {
  console.log('拦截到请求')
return request.get('/home/getStatisticalData')
}
export default {
    getCountData(params){
        return request({
            url:'/home/getCountData',
            method :'get',
            mock:true,
        })
    }
}

