import Mock  from "mockjs";
import  permission from './mockServeData/permission'
import  homeAPI from './mockServeData/home'

Mock.mock('/api/home/getData',homeAPI.getStatisticalData)
Mock.mock(/permission\/getMenu/,'post',permission.getMenu)
// Mock.mock('/api/permission/getMenu','post',permission.getMenu)