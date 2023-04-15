<template>
  <div class="container">
    <el-select v-model="storeValue" filterable class="store" size="large" @change="changeStore">
      <el-option v-for="store in stores" :key="store.storeId" :label="store.storeName" :value="store.storeId"/>
    </el-select>

    <div class="time">
      <span>{{ current.format('YYYY年M月') }}</span>
    </div>

    <div class="view-options">
      <div class="view">
        <el-button type="primary" :class="week_day === 'week' ? 'view-choose' : 'view-notchoose'"
                   @click="chooseWorD('week')">按周查看
        </el-button>
        <el-button type="primary" :class="week_day === 'day' ? 'view-choose' : 'view-notchoose'"
                   @click="chooseWorD('day')">按日查看
        </el-button>
      </div>

      <SlideSelection ref="slideRef" :week_day="week_day" @emitCurrent="(current1) => current = current1"
                      @emitTable="(table1) => table = table1" @updateTableData="updateTableData"
                      @updateDayDate="updateDayDate"
                      @initVariable="initVariable" @dragInit="dragInit"/>
    </div>

    <div class="view-button">
      <el-select v-model="viewValue" filterable class="store" size="large">
        <el-option v-for="viewMethod in viewMethods" :key="viewMethod.label" :label="viewMethod.value"
                   :value="viewMethod.value"/>
      </el-select>

      <div v-if="root === 1" class="buttonView">
        <el-button type="danger" :icon="Delete" @click="handleDelete" title="删除单元格内容">删除</el-button>
        <el-button :class="edit === '完成' ? 'button-choose' : null" type="primary" :icon="Edit" @click="handleEdit">{{
            edit
          }}
        </el-button>
        <el-button @click="confermessage" v-if="week_day === 'week'" type="success" :icon="Postcard">一键生成排班</el-button>


        <el-button v-if="week_day === 'week'" type="success" :icon="Finished" @click="updateSchedule">保存排班
        </el-button>
      </div>
    </div>

    <el-dialog v-model="getmessage">
      <el-form ref="returnForm" label-width="100px" class="demo-ruleForm">
        <el-form-item style="float:right">
          <p><label>可排班人数：</label>
            <span v-text="persons" style="margin-right: 20px"></span>
          </p>
        </el-form-item>
        <el-form-item style="float:right;width: 350px">
          <p><label>建议排班人数：</label>
            <el-input v-model="needpersons" placeholder="18" style="margin-top: 10px;width: 100px"></el-input>
          </p>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(needpersons)" style="float:right">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <div class="schedule">
      <weekDrag v-if="week_day === 'week'" ref="weekDragRef" :loading="loading" :table="table" :tableData="tableData"
                :viewValue="viewValue" :week_day="week_day" @emitTableData="(tableData1) => tableData = tableData1"
                @handleClickTd="(a, b) => handleClickTd(a, b)" @message="(msg) => message(msg)"
                @showAddView="(a, b, c) => showAddView(a, b, c)"/>

      <dayDrag v-if="week_day === 'day'" ref="dayDragRef" :current="current" :dayData="dayData" :loading="loading"
               :viewValue="viewValue" :week_day="week_day" @emitDayData="(dayData1) => dayData = dayData1"
               @handleClickTd="(a, b) => handleClickTd(a, b)" @message="(msg) => message(msg)"
               @showAddView="(a, b, c) => showAddView(a, b, c)"/>
    </div>

    <el-dialog v-model="showDialog" title="添加员工" width="30%" center draggable>
      <el-form>
        <el-form-item label="员工">
          <el-select v-model="employeeValue" value-key="employeeId" filterable>
            <el-option v-for="employee in employeesAvail" :key="employee.employeeId"
                       :label="employee.employeeName + '(' + employee.position + ')'" :value="employee"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showDialog = false">取消</el-button>
          <el-button type="primary" @click="addEmplyee">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import moment from "moment";
import {Delete, Edit, Finished, Postcard} from '@element-plus/icons-vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import SlideSelection from '../components/SlideSelection.vue'
import WeekDrag from '../components/WeekDrag'
import DayDrag from '../components/DayDrag'
import {
  autoSchedul,
  deleteDaySchedule,
  deleteScheduling,
  getADaywork,
  getAllEmployee,
  getAllStore,
  getAWeekwork,
  getStorePersons,
  getStuffDayWork,
  getStuffWeekWork,
  replaceDaySchedule,
  updateWeekData
} from '@/api';

/**
 * 初始化获取数据库数据：stores、data、employees
 */
// 门店切换
const stores = ref([]);
const storeValue = ref();

//所有员工数据
const employees = ref([]);
const employeesAvail = ref([]);

//表格单元格数据
var data = [];
const tableData = ref([]);
const dayData = ref([]);
const weekIndex = [0, 1, 1, 1, 1, 1, 0];

let persons = ref();
const needpersons = ref(18);

const getmessage = ref(false);

const submitForm = (needperson) => {
  let monday = current.value.startOf('isoWeek').format('YYYY-MM-DD');
  console.log(storeValue.value + "   " + needperson + "   " + monday)
  autoSchedul(storeValue.value, needperson, monday).then((datas) => {
    console.log(datas.data)
  });
  getmessage.value = false;
}

const confermessage = () => {
  console.log("店铺id" + storeValue.value)
  // console.log("我的测试点"+getStorePersons(storeId.value))
  getStorePersons(storeValue.value).then(({data}) => {
    console.log("测试数据    " + data)
    persons.value = data;
  })
  getmessage.value = true;
  console.log("猜猜我是谁" + getmessage.value)
}

//登录用户ID和权限
const employee = sessionStorage.getItem("employee")
console.log("employee" + employee)
const employeeId = JSON.parse(employee).employeeId;
const root = JSON.parse(employee).root;

//初始化获取数据库
getAllStore().then((datas) => {
  stores.value = datas.data;
  storeValue.value = stores.value[0].storeId;

  getAllEmployee(stores.value[0].storeId).then((datas) => {
    employees.value = datas.data;
    employeesAvail.value = [...employees.value];
  });

  console.log(root)
  loading.value = true;
  if (root === 1) {
    getAWeekwork(moment().startOf('isoWeek').format('YYYY-MM-DD'), moment().endOf('isoWeek').format('YYYY-MM-DD'), storeValue.value)
        .then((datas) => {
          data = datas.data;
          var index = 0;
          let tmp = data[0].map(function (col, i) {
            return data.map(function (row) {
              if (index === i) {
                let dataRow = [];
                dataRow.push(row[i][2]);
                tableData.value.push(dataRow);
                index++;
              }
              return row[i].slice(0, 2);
            })
          });
          tableData.value = tableData.value.map((val, index) => {
            let t = [...val];
            t.push([...tmp[index]]);
            return t;
          })
          console.log(tableData.value)
          loading.value = false;
        });

    loading.value = true;
    getADaywork(moment().format('YYYY-MM-DD'), weekIndex[moment().day()], storeValue.value)
        .then((datas) => {
          dayData.value = datas.data;
          loading.value = false;
        });
  } else {
    getStuffWeekWork(moment().startOf('isoWeek').format('YYYY-MM-DD'), moment().endOf('isoWeek').format('YYYY-MM-DD'), storeValue.value, employeeId)
        .then((datas) => {
          data = datas.data;
          var index = 0;
          let tmp = data[0].map(function (col, i) {
            return data.map(function (row) {
              if (index === i) {
                let dataRow = [];
                dataRow.push(row[i][2]);
                tableData.value.push(dataRow);
                index++;
              }
              return row[i].slice(0, 2);
            })
          });
          tableData.value = tableData.value.map((val, index) => {
            let t = [...val];
            t.push([...tmp[index]]);
            return t;
          })
          console.log(tableData.value)
          loading.value = false;
        });

    loading.value = true;
    getStuffDayWork(moment().format('YYYY-MM-DD'), weekIndex[moment().day()], storeValue.value, employeeId)
        .then((datas) => {
          dayData.value = datas.data;
          loading.value = false;
        });
  }
});


/**
 * 初始化本地变量
 */
/* 固定变量 */
//全部查看|按岗位分组|按员工分组
const viewMethods = [
  {
    label: 1,
    value: '全部查看'
  },
  {
    label: 2,
    value: '按岗位分组'
  },
  {
    label: 3,
    value: '按员工分组'
  }
]

const slideRef = ref();
const weekDragRef = ref();
const dayDragRef = ref();

/* 需修改变量 */
//按周查看|按日查看 的切换
const week_day = ref('week');

//全部查看|按岗位分组|按员工分组 的切换
const viewValue = ref(viewMethods[0].value);

const current = ref(moment());
const labels = ref([
  '周一 ' + current.value.startOf('isoWeek').format('M月D日'),
  '周二 ' + current.value.startOf('isoWeek').add(1, 'days').format('M月D日'),
  '周三 ' + current.value.startOf('isoWeek').add(2, 'days').format('M月D日'),
  '周四 ' + current.value.startOf('isoWeek').add(3, 'days').format('M月D日'),
  '周五 ' + current.value.startOf('isoWeek').add(4, 'days').format('M月D日'),
  '周六 ' + current.value.startOf('isoWeek').add(5, 'days').format('M月D日'),
  '周日 ' + current.value.startOf('isoWeek').add(6, 'days').format('M月D日'),
]);

//被点击单元格的坐标s
var arrTd = [];

//编辑button的文本切换
const edit = ref('编辑');

//表格标签
const table = ref({
  time: '时间',
  week1: labels.value[0],
  week2: labels.value[1],
  week3: labels.value[2],
  week4: labels.value[3],
  week5: labels.value[4],
  week6: labels.value[5],
  week7: labels.value[6],
});

//加载数据
const loading = ref(true)

//是否可拖拽drag，拖拽起点dragging，拖拽终点ending
const drag = ref(false);
const ending = ref(null);
const dragging = ref(null);

//是否显示添加员工对话框
const showDialog = ref(false);
//对话框里的员工下拉框
const employeeValue = ref('请选择要添加的员工');
//已处于选中单元格中的员工s
var addIndex = null;


/**
 * 函数
 */
//修改门店
const changeStore = () => {
  getStoreData();

  //周切换
  slideRef.value.initWeeks();

  //日切换
  slideRef.value.initDays();

  initVariable();
}

//修改按周查看|按日查看
const chooseWorD = (button) => {
  //获取数据库data、dayData
  if (button === 'week' && week_day.value === 'day') {
    week_day.value = 'week';

    //周切换
    slideRef.value.initWeeks();
  } else if (button === 'day' && week_day.value === 'week') {
    week_day.value = 'day';

    //日切换
    slideRef.value.initDays();
  }

  initVariable();
}

//点击多选单元格
const handleClickTd = (row, col) => {
  var flag = 0;
  var td = document.getElementById('td' + week_day.value + row + '-' + col);

  arrTd.map((value, index) => {
    if (value.toString() === [row, col].toString()) {
      arrTd.splice(index, 1);
      td.style.backgroundColor = '';
      flag = 1;
      return;
    }
  })
  if (flag === 0) {
    arrTd.push([row, col]);
    td.style.backgroundColor = '#ffe0e0';
  }

  console.log(arrTd);
}
//点击删除button删除单元格内容
const handleDelete = () => {
  if (edit.value === '完成') {
    message('正处于编辑状态，不可删除');
  } else if (arrTd.length === 0) {
    message('请选择要删除内容的单元格');
  } else {
    ElMessageBox.confirm(
        '单元格内容将永久被删除，确定吗?',
        'Warning',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )
        .then(() => {
          if (week_day.value === 'week') {
            var newData = [...tableData.value];
            var deleteIds = [];
            arrTd.map(value => {
              console.log(tableData.value[value[0]][1][value[1]])
              if (tableData.value[value[0]][1][value[1]][1] !== null)
                deleteIds.push(tableData.value[value[0]][1][value[1]][1]);
              newData[value[0]][1][value[1]][0] = [];
              newData[value[0]][1][value[1]][1] = null;
              let td = document.getElementById('td' + week_day.value + value[0] + '-' + value[1]);
              // console.log(td)
              td.style.backgroundColor = '';
            })
            tableData.value = [...newData];
            deleteScheduling(deleteIds);
          } else {
            var newDayData = [...dayData.value];
            var updateIndexs = [];
            var updateEmployeeIds = "";
            arrTd.map(value => {
              if (JSON.stringify(newDayData[value[0]][0][value[1]]) !== '{}'
                  && newDayData[value[0]][0][value[1]].employeeId !== null)
                updateIndexs.push(value[0]);
              newDayData[value[0]][0][value[1]] = {};
              let td = document.getElementById('td' + week_day.value + value[0] + '-' + value[1]);
              td.style.backgroundColor = '';
            })
            dayData.value = [...newDayData];

            let items = [];
            updateIndexs = [...new Set(updateIndexs)];
            updateIndexs.map(value => {
              let employeeIds = "";
              newDayData[value][0].map(v => {
                if (v.employeeId !== null && JSON.stringify(v) !== '{}' && employeeIds === "")
                  employeeIds += v.employeeId;
                else if (v.employeeId !== null && JSON.stringify(v) !== '{}')
                  employeeIds += "," + v.employeeId;
              });
              let item = {};
              item.index = value;
              item.period = newDayData[value][1];
              item.employeeIds = employeeIds;
              items.push(item);
            });
            console.log(items)
            deleteDaySchedule(items, current.value.format('YYYY-MM-DD'), storeValue.value);
          }

          arrTd.splice(0, arrTd.length);

          ElMessage({
            type: 'success',
            message: '删除成功',
          })
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '取消删除',
          })
        })
  }
}
//点击编辑|完成
const handleEdit = () => {
  if (edit.value === '编辑') {
    var tds = document.querySelectorAll('td');
    for (var i = 1; i < tds.length; i++) {
      if (tds[i].id !== 'td-time')
        tds[i].style.backgroundColor = '';
    }
  } else {
    if (week_day.value === 'week') {
      updateSchedule();
    } else {
      let items = [];
      dayData.value.map((value, index) => {
        let item = {};
        item.period = value[1];
        let employeeIds = "";
        let flag = 0;
        value[0].map(v => {
          if (JSON.stringify(v) !== '{}' && v.employeeId !== null && flag === 0) {
            employeeIds += v.employeeId;
            flag = 1;
          } else if (JSON.stringify(v) !== '{}' && v.employeeId !== null)
            employeeIds += "," + v.employeeId;
        });
        item.employeeIds = employeeIds;
        items.push(item);
      });
      console.log(items);
      replaceDaySchedule(items, storeValue.value, current.value.format('YYYY-MM-DD'));
    }
  }

  if (week_day.value === 'week')
    weekDragRef.value.weekEdit();
  else
    dayDragRef.value.dayEdit();

  edit.value = (edit.value === '编辑' ? '完成' : '编辑');
}

//更新数据库排班数据
const updateSchedule = () => {
  let items = [];
  tableData.value.forEach(row => {
    row[1].forEach((col, i) => {
      let item = {};

      if (col[1] != null) item.id = col[1];
      else item.id = null;

      let employeeIds = "";
      if (col[0] != null) {
        col[0].forEach((stuff, index) => {
          if (index === 0) employeeIds += stuff.employeeId;
          else employeeIds += "," + stuff.employeeId;
        })
      }
      item.employeeIds = employeeIds;

      if (item.id != null || employeeIds != "") {
        console.log(row[0])
        item.period = row[0];
        item.day = current.value.startOf('W').add(i, 'days').format("YYYY-MM-DD");

        console.log(item);
        items.push(JSON.stringify(item));
      }
    })
  })

  updateWeekData(items, storeValue.value);
}

const dragInit = () => {
  if (week_day.value === 'week')
    weekDragRef.value.initDrag();
  else
    dayDragRef.value.initDrag();
}

//显示添加员工对话框、添加员工
const showAddView = (added, index1, index2) => {
  console.log(added, index1, index2)
  employeeValue.value = '请选择要添加的员工';
  addIndex = [index1, index2];

  var newEAvil = [...employees.value];
  if (week_day.value === 'week') {
    newEAvil = newEAvil.filter(item => {
      var isInclude = false;

      added.map((value) => {
        if (value.employeeId === item.employeeId) {
          isInclude = true;
          return;
        }
      })
      // console.log(isInclude);
      return !isInclude;
    });
  } else {
    newEAvil = newEAvil.filter(item => {
      var isInclude = false;

      console.log(dayData.value[index1])
      dayData.value[index1].map(value => {
        console.log(value)
        if (JSON.stringify(value) !== '{}' && value.employeeId !== null) {
          if (value.employeeId === item.employeeId) {
            isInclude = true;
            return;
          }
        }
      });
      // console.log(isInclude);
      return !isInclude;
    });
  }
  employeesAvail.value = [...newEAvil];
  console.log(employeesAvail)

  showDialog.value = true;
}
const addEmplyee = () => {
  if (employeeValue.value === '请选择要添加的员工') {
    message("请选择要添加的员工");
  } else {
    if (week_day.value === 'week') {
      let newData = [...tableData.value];
      newData[addIndex[0]][1][addIndex[1]][0].push(employeeValue.value);
      tableData.value = [...newData];
    } else {
      let newData = [...dayData.value];
      newData[addIndex[0]][1][addIndex[1]] = employeeValue.value;
      dayData.value = [...newData];
    }
  }

  showDialog.value = false;
}

//工具类
const message = (str) => {
  return (
      ElMessage({
        showClose: true,
        message: str,
        type: 'warning',
        grouping: true
      })
  );
}
const initVariable = () => {
  //全部查看|按岗位分组|按员工分组 的切换
  viewValue.value = viewMethods[0].value;

  //被点击单元格的坐标s
  arrTd = [];

  //编辑button的文本切换
  edit.value = '编辑';

  //是否可拖拽drag，拖拽起点dragging，拖拽终点ending
  drag.value = false;
  ending.value = null;
  dragging.value = null;

  //是否显示添加员工对话框
  showDialog.value = false;
  //对话框里的员工下拉框
  employeeValue.value = '请选择要添加的员工';
  //已处于选中单元格中的员工s
  addIndex = null;

  var tds = document.querySelectorAll('td');
  for (var i = 1; i < tds.length; i++) {
    if (tds[i].id !== 'td-time')
      tds[i].style.backgroundColor = '';
  }
}
const updateTableData = () => {
  loading.value = true;
  tableData.value = [];
  if (root === 1) {
    getAWeekwork(current.value.startOf('isoWeek').format('YYYY-MM-DD'), current.value.endOf('isoWeek').format('YYYY-MM-DD'), storeValue.value)
        .then((datas) => {
          data = datas.data;
          var index = 0;
          let tmp = data[0].map(function (col, i) {
            return data.map(function (row) {
              if (index === i) {
                let dataRow = [];
                dataRow.push(row[i][2]);
                tableData.value.push(dataRow);
                index++;
              }
              return row[i].slice(0, 2);
            })
          });
          tableData.value = tableData.value.map((val, index) => {
            let t = [...val];
            t.push([...tmp[index]]);
            return t;
          })
          console.log(tableData.value)
          loading.value = false;
        });
  } else {
    getStuffWeekWork(current.value.startOf('isoWeek').format('YYYY-MM-DD'), current.value.endOf('isoWeek').format('YYYY-MM-DD'), storeValue.value, employeeId)
        .then((datas) => {
          data = datas.data;
          var index = 0;
          let tmp = data[0].map(function (col, i) {
            return data.map(function (row) {
              if (index === i) {
                let dataRow = [];
                dataRow.push(row[i][2]);
                tableData.value.push(dataRow);
                index++;
              }
              return row[i].slice(0, 2);
            })
          });
          tableData.value = tableData.value.map((val, index) => {
            let t = [...val];
            t.push([...tmp[index]]);
            return t;
          })
          console.log(tableData.value)
          loading.value = false;
        });
  }
}
const updateDayDate = () => {
  loading.value = true;
  if (root === 1) {
    getADaywork(current.value.format('YYYY-MM-DD'), weekIndex[current.value.day()], storeValue.value)
        .then((datas) => {
          dayData.value = datas.data;
          loading.value = false;
        });
  } else {
    console.log("this")
    loading.value = true;
    getStuffDayWork(current.value.format('YYYY-MM-DD'), weekIndex[moment().day()], storeValue.value, employeeId)
        .then((datas) => {
          console.log(datas.data)
          dayData.value = datas.data;
          loading.value = false;
        });
  }
}
const getStoreData = () => {
  console.log(storeValue.value)

  getAllEmployee(stores.value[0].storeId).then((datas) => {
    employees.value = datas.data;
    employeesAvail.value = [...employees.value];
  });
}
</script>

<style lang="less" scoped>
.container {
  background-color: #fff;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  padding: 20px;
  overflow: hidden;

  .store {
    margin-top: 10px;
    margin-bottom: 10px;
    border-radius: 10px;
    display: flex;
    align-self: center;
  }

  .time {
    margin-bottom: 10px;
  }

  .view-options {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;

    .view {
      background-color: hsl(0, 0%, 89%);
      padding: 5px;
      border-radius: 5px;

      .el-button {
        border: none;
        padding: 10px 30px;
      }

      .view-choose {
        background-color: #fff;
        color: #0a85ff;
      }

      .view-notchoose {
        background-color: #e3e3e3;
        color: #000;
      }

      .view-notchoose:hover {
        background-color: #fff;
        color: #000;
      }
    }
  }

  .view-button {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;

    .button-choose {
      background-color: #409EFF;
      color: #fff;
    }

    .button-choose:hover {
      background-color: #fff;
      color: #0a85ff;
    }
  }

  .schedule {
    display: flex;
    justify-content: center;
  }

  .el-form {
    display: flex;
    justify-content: center;
  }
}
</style>