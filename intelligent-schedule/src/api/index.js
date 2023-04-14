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

export const getStore=()=>{
  return request.get('http://localhost:8082/store/queryStoreList')
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

export const getWorkday = (employeeId) => {
  return request.get('http://localhost:8082/scheduling/getWorkDay/' + employeeId);
}

export const getDaywork = (employeeId, day) => {
  return request.get('http://localhost:8082/scheduling/getDayWork/' + employeeId + '/' + day);
}

export const getAllStore = () => {
  return request.get('http://localhost:8082/store/all');
}

export const getStoreName = () => {
  return request.get('http://localhost:8082/store/getStoreName');
}

export const getStoreByStoreName = (storeName) => {
  return request.get('http://localhost:8082/store/getStoreByStoreName/' + storeName);
}

export const getStoreByStoreType = (storeType) => {
  return request.get('http://localhost:8082/store/getStoreByStoreType/' + storeType);
}

export const getStoreByStore = (storeName, storeType) => {
  return request.get('http://localhost:8082/store/getStore/' + storeName + '/' + storeType);
}

export const getEmployeeStoreName = (employeeId) => {
  return request.get('http://localhost:8082/store/getEmployeeStoreName/' + employeeId);
}

export const getFindIfExistStore = (storeId) => {
  return request.get('http://localhost:8082/store/getFindIfExistStore/' + storeId);
}

export const updateStore = (storeId, storeName, storeType, address, size, manger, workers) => {
  return request.post('http://localhost:8082/store/updateStore', {
    storeId: storeId,
    storeName: storeName,
    storeType: storeType,
    address: address,
    size: size,
    manger: manger,
    workers: workers,
  });
}

export const updateStoreStateOne = (storeId) => {
  return request.post('http://localhost:8082/store/updateStoreStateOne', {
    storeId: storeId,
  });
}

export const updateStoreStateZero = (storeId) => {
  return request.post('http://localhost:8082/store/updateStoreStateZero', {
    storeId: storeId,
  });
}

export const deleteStore = (storeId) => {
  return request.post('http://localhost:8082/store/deleteStore', {
    storeId: storeId,
  });
}

export const deleteRuleByStoreId = (storeId) => {
  return request.post('http://localhost:8082/rule/deleteRuleByStoreId', {
    storeId: storeId,
  });
}

export const deleteSchedulingByStoreId = (storeId) => {
  return request.post('http://localhost:8082/scheduling/deleteSchedulingByStoreId', {
    storeId: storeId,
  });
}

export const deleteEmployeeStateByStoreId = (storeId) => {
  return request.post('http://localhost:8082/employee/deleteEmployeeStateByStoreId', {
    storeId: storeId,
  });
}

export const insertStore = (storeId, storeName, storeType, address, size, manger, workers) => {
  return request.post('http://localhost:8082/store/insertStore', {
    storeId: storeId,
    storeName: storeName,
    storeType: storeType,
    address: address,
    size: size,
    manger: manger,
    workers: workers,
  });
}

export const getEmployee = () => {
  return request.get('http://localhost:8082/employee/allEmployee');
}

export const getAllEmployee = (storeId) => {
  return request.get('http://localhost:8082/employee/all/' + storeId);
}

export const getEmployeeByStoreId = (storeName) => {
  return request.get('http://localhost:8082/employee/getEmployeeByStoreId/' + storeName);
}

export const getFindIfExistEmployee = (employeeId) => {
  return request.get('http://localhost:8082/employee/getFindIfExistEmployee/' + employeeId);
}

export const getEmployeeByEmployeeName = (employeeName) => {
  return request.get('http://localhost:8082/employee/getEmployeeByEmployeeName/' + employeeName);
}

export const getEmployeeByPosition = (position) => {
  return request.get('http://localhost:8082/employee/getEmployeeByPosition/' + position);
}

export const getEmployeeByEmployeeNameAndPosition = (employeeName, position) => {
  return request.get('http://localhost:8082/employee/getEmployeeByEmployeeNameAndPosition/' + employeeName + '/' + position);
}

export const updateEmployee = (employeeId, employeeName, storeId, email, position, phone) => {
  return request.post('http://localhost:8082/employee/updateEmployee', {
    employeeId: employeeId,
    employeeName: employeeName,
    storeId: storeId,
    email: email,
    position: position,
    phone: phone,
  });
}

export const updateEmployeeStateOne = (employeeId) => {
  return request.post('http://localhost:8082/employee/updateEmployeeStateOne', {
    employeeId: employeeId,
  });
}

export const updateEmployeeStateZero = (employeeId) => {
  return request.post('http://localhost:8082/employee/updateEmployeeStateZero', {
    employeeId: employeeId,
  });
}

export const deleteEmployee = (employeeId) => {
  return request.post('http://localhost:8082/employee/deleteEmployee', {
    employeeId: employeeId,
  });
}

export const insertEmployee = (employeeId, employeeName, storeId, email, position, phone) => {
  return request.post('http://localhost:8082/employee/insertEmployee', {
    employeeId: employeeId,
    employeeName: employeeName,
    storeId: storeId,
    email: email,
    position: position,
    phone: phone,
  });
}

export const insertPreferenceHaveOne = (employeeId, preferValue) => {
  return request.post('http://localhost:8082/preference/insertPreferenceHaveOne', {
    employeeId: employeeId,
    preferValue: preferValue,
  });
}

export const insertPreferenceHaveTwo = (employeeId, preferValue) => {
  return request.post('http://localhost:8082/preference/insertPreferenceHaveTwo', {
    employeeId: employeeId,
    preferValue: preferValue,
  });
}

export const insertPreferenceHaveThree = (employeeId, preferValue) => {
  return request.post('http://localhost:8082/preference/insertPreferenceHaveThree', {
    employeeId: employeeId,
    preferValue: preferValue,
  });
}

export const getPreferenceHaveOne = (employeeId) => {
  return request.get('http://localhost:8082/preference/getPreferenceHaveOne/' + employeeId);
}

export const getPreferenceHaveTwo = (employeeId) => {
  return request.get('http://localhost:8082/preference/getPreferenceHaveTwo/' + employeeId);
}

export const getPreferenceHaveThree = (employeeId) => {
  return request.get('http://localhost:8082/preference/getPreferenceHaveThree/' + employeeId);
}

export const updatePreferenceHaveOne = (employeeId, preferValue) => {
  return request.post('http://localhost:8082/preference/updatePreferenceHaveOne', {
    employeeId: employeeId,
    preferValue: preferValue,
  });
}

export const updatePreferenceHaveTwo = (employeeId, preferValue) => {
  return request.post('http://localhost:8082/preference/updatePreferenceHaveTwo', {
    employeeId: employeeId,
    preferValue: preferValue,
  });
}
export const updatePreferenceHaveThree = (employeeId, preferValue) => {
  return request.post('http://localhost:8082/preference/updatePreferenceHaveThree', {
    employeeId: employeeId,
    preferValue: preferValue,
  });
}

export const deletePreferenceHaveOne = (employeeId) => {
  return request.post('http://localhost:8082/preference/deletePreferenceHaveOne', {
    employeeId: employeeId,
  });
}

export const deletePreferenceHaveTwo = (employeeId) => {
  return request.post('http://localhost:8082/preference/deletePreferenceHaveTwo', {
    employeeId: employeeId,
  });
}

export const deletePreferenceHaveThree = (employeeId) => {
  return request.post('http://localhost:8082/preference/deletePreferenceHaveThree', {
    employeeId: employeeId,
  });
}

export const deletePreferenceByEmployeeId = (employeeId) => {
  return request.post('http://localhost:8082/preference/deletePreferenceByEmployeeId', {
    employeeId: employeeId,
  });
}

export const getAWeekwork = (Monday, Sunday, storeId) => {
  return request.get('http://localhost:8082/scheduling/getAWeekWork/' + Monday + '/' + Sunday + '/' + storeId);
}

export const getADaywork = (day, week, storeId) => {
  return request.get('http://localhost:8082/scheduling/getADayWork/' + day + '/' + week + '/' + storeId);
}

export const deleteScheduling = (ids) => {
  return request.get('http://localhost:8082/scheduling/deleteScheduling/' + ids);
}

export const deleteDaySchedule = (employeeIds, day, week, startIndex) => {
  return request.post('http://localhost:8082/scheduling/deleteDaySchedule', {
    employeeIds: employeeIds,
    day: day,
    week: week,
    startIndex: startIndex
  });
}

export const updateWeekData = (tableData, week) => {
  return request.post('http://localhost:8082/scheduling/replaceScheduling', {
    tableData: tableData,
    week: week
  });
}

export const replaceDaySchedule = (employeeIds, storeId, day, week) => {
  return request.get('http://localhost:8082/scheduling/replaceDaySchedule/' + employeeIds + '/' + storeId + '/' + day + '/' + week);
}