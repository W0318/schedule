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

export const getStore = () => {
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

export const getAllEmployee = (storeId) => {
    return request.get('http://localhost:8082/employee/all/' + storeId);
}

export const getAWeekwork = (Monday, Sunday, storeId) => {
    console.log('http://localhost:8082/scheduling/getAWeekWork/' + Monday + '/' + Sunday + '/' + storeId)
    return request.get('http://localhost:8082/scheduling/getAWeekWork/' + Monday + '/' + Sunday + '/' + storeId);
}

export const getStuffWeekWork = (Monday, Sunday, storeId, employeeId) => {
    return request.post('http://localhost:8082/scheduling/getStuffWeekWork', {
        Monday: Monday,
        Sunday: Sunday,
        storeId: storeId,
        employeeId: employeeId
    });
}

export const getADaywork = (day, week, storeId) => {
    return request.get('http://localhost:8082/scheduling/getADayWork/' + day + '/' + week + '/' + storeId);
}

export const getStuffDayWork = (day, week, storeId, employeeId) => {
    return request.post('http://localhost:8082/scheduling/getStuffDayWork', {
        day: day,
        week: week,
        storeId: storeId,
        employeeId: employeeId
    });
}

export const deleteScheduling = (ids) => {
    return request.get('http://localhost:8082/scheduling/deleteScheduling/' + ids);
}

export const deleteDaySchedule = (items, day, storeId) => {
    return request.post('http://localhost:8082/scheduling/deleteDaySchedule', {
        items: items,
        day: day,
        storeId: storeId
    });
}

export const updateWeekData = (items, storeId) => {
    return request.post('http://localhost:8082/scheduling/replaceScheduling', {
        items: items,
        storeId: storeId
    });
}

export const replaceDaySchedule = (items, storeId, day) => {
    return request.post('http://localhost:8082/scheduling/replaceDaySchedule', {
        items: items,
        storeId: storeId,
        day: day
    });
}

export const getStorePersons = (storeId) => {
    return request.get('http://localhost:8082/store/' + storeId);
}
export const autoSchedul = (storeId, limited, Monday) => {
    console.log("信息数据   " + storeId + " " + limited + " " + Monday)
    return request.get('http://localhost:8082/autoSchedule/' + storeId + '/' + limited + '/' + Monday);
    // console.log('拦截到请求')
    // return request.post('http://localhost:8082/autoSchedule/limitedpresons', {//请求登录接口
    //   str: data,
    // })
    // return request.post('/permission/getMenu',data)
}