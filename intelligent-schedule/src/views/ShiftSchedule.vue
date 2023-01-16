<template>
    <div class="container">
        <el-select v-model="storeValue" filterable class="store" size="large" @change="changeStore">
            <el-option v-for="store in stores" :key="store.storeId" :label="store.storeName" :value="store.storeName" />
        </el-select>

        <div class="time">
            <span>{{ current.format('YYYY年M月') }}</span>
        </div>

        <div class="view-weeks">
            <div class="view">
                <el-button type="primary" :class="week_day === 'week' ? 'view-choose' : 'view-notchoose'"
                    @click="chooseWorD('week')">按周查看</el-button>
                <el-button type="primary" :class="week_day === 'day' ? 'view-choose' : 'view-notchoose'"
                    @click="chooseWorD('day')">按日查看</el-button>
            </div>

            <div class="weekView">
                <el-icon :size="25" @click="shift('left')">
                    <ArrowLeft />
                </el-icon>
                <div class="weeks">
                    <span :class="curWeek === week1 ? 'weeks-cur' : 'weeks-other'" @click="chooseWeek(week1, 0)">{{
                        week1
                    }}</span>
                    <span :class="curWeek === week2 ? 'weeks-cur' : 'weeks-other'" @click="chooseWeek(week2, 1)">{{
                        week2
                    }}</span>
                    <span :class="curWeek === week3 ? 'weeks-cur' : 'weeks-other'" @click="chooseWeek(week3, 2)">{{
                        week3
                    }}</span>
                </div>
                <el-icon :size="25" @click="shift('right')">
                    <ArrowRight />
                </el-icon>
            </div>
        </div>

        <div class="view-button">
            <el-select v-model="viewValue" filterable class="store" size="large">
                <el-option v-for="viewMethod in viewMethods" :key="viewMethod.label" :label="viewMethod.value"
                    :value="viewMethod.value" />
            </el-select>

            <div class="buttonView">
                <el-button type="primary" :icon="Delete" @click="handleDelete">删除</el-button>
                <el-button :class="edit === '取消' ? 'button-choose' : null" type="primary" :icon="Edit"
                    @click="handleEdit">{{ edit }}</el-button>
                <el-button type="primary" :icon="Postcard">一键生成排班</el-button>
            </div>
        </div>

        <div class="schedule">
            <table class="table-out">
                <thead>
                    <tr>
                        <template v-for='(item, index) in tabel'>
                            <th class="item">{{ item }}</th>
                        </template>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for='(row, index1) in data' class="tr-out">
                        <td class="td-out">
                            {{ times[index1] }}
                        </td>
                        <template v-for='(col, index2) in row'>
                            <td class="td-out td" :id="'td' + index1 + '-' + index2"
                                @click="drag === false ? handleClickTd(index1, index2) : null">
                                <draggable :model-value="col" group="people" animation="300" item-key="key"
                                    @start="startDrag($event, index1, index2)" @end="endDrag($event)"
                                    @dragenter="enterDrag($event, index1, index2)">
                                    <template #item="{ element, index }">
                                        <div class="item" :class="(drag === true) ? 'move' : null"
                                            :title="element.employeeName + ' ' + element.position">
                                            <el-icon v-if="drag === true" :size="19" class="icon-delete"
                                                @click="deleteItem(index1, index2, index)">
                                                <CircleClose />
                                            </el-icon>
                                            <span v-if="viewValue === '全部查看'">
                                                {{ element.employeeName + ' ' + element.position }}
                                            </span>
                                            <span v-if="viewValue === '按岗位分组'">{{ element.position }}</span>
                                            <span v-else>{{ element.employeeName }}</span>
                                        </div>
                                    </template>
                                </draggable>
                                <el-icon v-if="drag === true" :size="22" class="icon-add"
                                    @click="showAddView(col, index1, index2)">
                                    <CirclePlus />
                                </el-icon>
                            </td>
                        </template>
                    </tr>
                </tbody>
            </table>
        </div>

        <el-dialog v-model="showDialog" title="添加员工" width="30%" center draggable>
            <el-form>
                <el-form-item label="员工">
                    <el-select v-model="employeeValue" filterable>
                        <el-option v-for="employee in employeesAvail" :key="employee.employeeId"
                            :label="employee.employeeName + '(' + employee.position + ')'" :value="employee" />
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
import { ref } from 'vue';
import moment from "moment";
import draggable from 'vuedraggable';
import { Delete, Edit, Postcard } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';

/**
 * 初始化获取数据库数据：stores、startTime、endTime、data、employees
 */
// 门店切换，最好按storeName字母拼音顺序排序
const stores = [
    {
        storeId: 'store1',
        storeName: '安踏运动鞋',
    },
    {
        storeId: 'store2',
        storeName: '特步运动鞋',
    },
    {
        storeId: 'store3',
        storeName: '乔丹运动鞋',
    }
];
const storeValue = ref(stores[0].storeName);

//表格最左侧时间段
var startTime = '8:00';
var endTime = '22:00';
var minutes = moment('2023-1-1 ' + endTime).diff(moment('2023-1-1 ' + startTime), 'minutes');
var hours = Math.ceil(minutes / 120);
var start = startTime;
const times = ref(new Array(hours).fill(''));
times.value = times.value.map(() => {
    let temp = start.split(':');
    temp[0] = parseInt(temp[0]) + 2 + '';
    let end = temp.join(':');
    let time = start + '-' + end;
    start = end;
    return time;
});

//表格单元格数据
var id = 0;
const data = ref([
    {
        week1: [
            { key: id++, employeeId: 'employee1', employeeName: 'www', position: '门店经理' },
            { key: id++, employeeId: 'employee2', employeeName: 'aaa', position: '副经理' },
            { key: id++, employeeId: 'employee3', employeeName: 'zzz', position: '小组长' }
        ],
        week2: [
            { key: id++, employeeId: 'employee4', employeeName: 'yyy', position: '门店经理' },
            { key: id++, employeeId: 'employee5', employeeName: 'bbb', position: '店员' }
        ],
        week3: [
            { key: id++, employeeId: 'employee6', employeeName: 'zzz', position: '门店经理' }
        ],
        week4: [],
        week5: [],
        week6: [],
        week7: [],
    },
    {
        week1: [],
        week2: [],
        week3: [],
        week4: [],
        week5: [],
        week6: [],
        week7: [],
    },
    {
        week1: [],
        week2: [],
        week3: [],
        week4: [],
        week5: [],
        week6: [],
        week7: [],
    },
    {
        week1: [],
        week2: [],
        week3: [],
        week4: [],
        week5: [],
        week6: [],
        week7: [],
    },
    {
        week1: [],
        week2: [],
        week3: [],
        week4: [],
        week5: [],
        week6: [],
        week7: [],
    },
    {
        week1: [],
        week2: [],
        week3: [],
        week4: [],
        week5: [],
        week6: [],
        week7: [],
    },
    {
        week1: [],
        week2: [],
        week3: [],
        week4: [],
        week5: [],
        week6: [],
        week7: [],
    }
]);

//所有员工数据
const employees = ref([
    {
        employeeId: 'employee1',
        employeeName: 'www',
        position: '门店经理'
    },
    {
        employeeId: 'employee2',
        employeeName: 'aaa',
        position: '副经理'
    },
    {
        employeeId: 'employee3',
        employeeName: 'zzz',
        position: '小组长'
    },
    {
        employeeId: 'employee4',
        employeeName: 'yyy',
        position: '门店经理'
    },
    {
        employeeId: 'employee5',
        employeeName: 'bbb',
        position: '店员'
    },
    {
        employeeId: 'employee6',
        employeeName: 'zzz',
        position: '门店经理'
    }
]);
const employeesAvail = ref([...employees.value]);



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

/* 需修改变量 */
//按周查看|按日查看 的切换
const week_day = ref('week');

//全部查看|按岗位分组|按员工分组 的切换
const viewValue = ref(viewMethods[0].value);

//周切换
const week1 = ref(moment().startOf('isoWeek').format('M月D日') + '-' + moment().endOf('isoWeek').format('M月D日'));
const week2 = ref(moment().add(1, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().add(1, 'w').endOf('isoWeek').format('M月D日'));
const week3 = ref(moment().add(2, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().add(2, 'w').endOf('isoWeek').format('M月D日'));
const current = ref(moment());
const curWeek = ref(week1.value);
const labels = ref([
    '周一 ' + current.value.startOf('isoWeek').format('M月D日'),
    '周二 ' + current.value.startOf('isoWeek').add(1, 'days').format('M月D日'),
    '周三 ' + current.value.startOf('isoWeek').add(2, 'days').format('M月D日'),
    '周四 ' + current.value.startOf('isoWeek').add(3, 'days').format('M月D日'),
    '周五 ' + current.value.startOf('isoWeek').add(4, 'days').format('M月D日'),
    '周六 ' + current.value.startOf('isoWeek').add(5, 'days').format('M月D日'),
    '周日 ' + current.value.startOf('isoWeek').add(6, 'days').format('M月D日'),
]);
current.value = moment();
var weekIndex = 0;
var lastIndex = 0;
var leftWeek = moment();
var rightWeek = moment().add(2, 'w');

//被点击单元格的坐标s
var arrTd = [];

//编辑button的文本切换
const edit = ref('编辑');

//表格标签
const tabel = ref({
    time: '时间',
    week1: labels.value[0],
    week2: labels.value[1],
    week3: labels.value[2],
    week4: labels.value[3],
    week5: labels.value[4],
    week6: labels.value[5],
    week7: labels.value[6],
});

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
    //从数据库中获取数据，更新data、时间段
    
}

//修改按周查看|按日查看
const chooseWorD = (button) => {
    if (button === 'week' && week_day.value === 'day'
        || button === 'day' && week_day.value === 'week') {
        week_day.value === 'week' ? week_day.value = 'day' : week_day.value = 'week'
    }
}

//选择周
const chooseWeek = (button, index) => {
    if (button !== curWeek) {
        curWeek.value = button;

        if (weekIndex + index - lastIndex < 0) {
            current.value = current.value.subtract(-(weekIndex + index - lastIndex), 'w');
        }
        else if (weekIndex + index - lastIndex > 0) {
            current.value = current.value.add(weekIndex + index - lastIndex, 'w');
        }

        labels.value = [
            '周一 ' + current.value.startOf('isoWeek').format('M月D日'),
            '周二 ' + current.value.startOf('isoWeek').add(1, 'days').format('M月D日'),
            '周三 ' + current.value.startOf('isoWeek').add(2, 'days').format('M月D日'),
            '周四 ' + current.value.startOf('isoWeek').add(3, 'days').format('M月D日'),
            '周五 ' + current.value.startOf('isoWeek').add(4, 'days').format('M月D日'),
            '周六 ' + current.value.startOf('isoWeek').add(5, 'days').format('M月D日'),
            '周日 ' + current.value.startOf('isoWeek').add(6, 'days').format('M月D日'),
        ];
        current.value = current.value.startOf('isoWeek');
        weekIndex = 0;
        lastIndex = index;

        tabel.value = {
            time: '时间',
            week1: labels.value[0],
            week2: labels.value[1],
            week3: labels.value[2],
            week4: labels.value[3],
            week5: labels.value[4],
            week6: labels.value[5],
            week7: labels.value[6],
        }
        viewValue.value = viewMethods[0].value;

        //从数据库中获取数据，更新data
    }
}
//左右箭头
const shift = (direction) => {
    if (direction === 'left') {
        leftWeek = leftWeek.subtract(1, 'w');
        rightWeek = rightWeek.subtract(1, 'w');
        weekIndex = weekIndex - 1;
        week3.value = week2.value;
        week2.value = week1.value;
        week1.value = leftWeek.startOf('isoWeek').format('M月D日') + '-' + leftWeek.endOf('isoWeek').format('M月D日');
    }
    else {
        leftWeek = leftWeek.add(1, 'w');
        rightWeek = rightWeek.add(1, 'w');
        weekIndex = weekIndex + 1;
        week1.value = week2.value;
        week2.value = week3.value;
        week3.value = rightWeek.startOf('isoWeek').format('M月D日') + '-' + rightWeek.endOf('isoWeek').format('M月D日');
    }
}

//开始拖拽、拖拽终点、结束拖拽
const startDrag = (e, index1, index2) => {
    if (drag.value === false) return;
    dragging.value = [index1, index2];
}
const enterDrag = (e, index1, index2) => {
    if (drag.value === false) return;
    ending.value = [index1, index2];
}
const endDrag = (e) => {
    if (drag.value === false) return;
    if (ending.value[0] === dragging.value[0] &&
        ending.value[1] === dragging.value[1]) {
        return;
    }

    var flag = false;
    data.value[ending.value[0]][ending.value[1]].map((value) => {
        if (value.employeeId === data.value[ending.value[0]][ending.value[1]][e.oldIndex].employeeId) {
            flag = true;
            return;
        }
    })
    if (flag === true) {
        ElMessage({
            showClose: true,
            message: '不能给同一个员工分配同一时间段的工作',
            type: 'warning',
            grouping: true
        })
        return;
    }

    var newData = [...data.value];

    if (dragging.value[0] === ending.value[0]) {
        let item = { ...newData[dragging.value[0]] };
        item[ending.value[1]].push(item[dragging.value[1]][e.oldIndex]);
        item[dragging.value[1]].splice(e.oldIndex, 1);

        newData[dragging.value[0]] = { ...item };
    }
    else {
        let item1 = { ...newData[dragging.value[0]] };
        let item2 = { ...newData[ending.value[0]] };
        item2[ending.value[1]].push(item1[dragging.value[1]][e.oldIndex]);
        item1[dragging.value[1]].splice(e.oldIndex, 1);

        newData[dragging.value[0]] = { ...item1 };
        newData[ending.value[0]] = { ...item2 };
    }

    data.value = [...newData];
}

//点击多选单元格
const handleClickTd = (row, col) => {
    var flag = 0;
    var td = document.getElementById('td' + row + '-' + col);

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
    if (edit.value === '取消') {
        ElMessage({
            showClose: true,
            message: '正处于编辑状态，不可删除',
            type: 'warning',
            grouping: true
        })
    }
    else if (arrTd.length === 0) {
        ElMessage({
            showClose: true,
            message: '请选择要删除内容的单元格',
            type: 'warning',
            grouping: true
        })
    }
    else {
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
                let newData = [...data.value];
                arrTd.map(value => {
                    newData[value[0]][value[1]] = [];
                    let td = document.getElementById('td' + value[0] + '-' + value[1]);
                    console.log(td)
                    td.style.backgroundColor = '';
                })
                data.value = [...newData];
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
//点击编辑|取消
const handleEdit = () => {
    if (edit.value === '编辑') {
        var tds = document.querySelectorAll('td');
        for (var i = 1; i < tds.length; i++) {
            tds[i].style.backgroundColor = '';
        }
    }

    drag.value = (drag.value === true ? false : true);
    edit.value = (edit.value === '编辑' ? '取消' : '编辑');
}

//显示添加员工对话框、添加员工
const showAddView = (added, index1, index2) => {
    employeeValue.value = '请选择要添加的员工';
    addIndex = [index1, index2];

    var newEAvil = [...employees.value];
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
    employeesAvail.value = [...newEAvil];

    showDialog.value = true;
}
const addEmplyee = () => {
    let newData = [...data.value];
    newData[addIndex[0]][addIndex[1]].push(employeeValue.value);
    console.log(newData)
    data.value = [...newData];

    showDialog.value = false;
}
//删除员工
const deleteItem = (index1, index2, index) => {
    // console.log(index1, index2, index);

    let newData = [...data.value];
    let item = { ...newData[index1] };

    item[index2].splice(index, 1);
    newData[index1] = { ...item };

    data.value = [...newData];
}

</script>

<style lang="less" scoped>
.container {
    background-color: #fff;
    border-radius: 15px;
    display: flex;
    flex-direction: column;
    padding: 20px;

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

    .view-weeks {
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

        .weekView {
            display: flex;
            justify-self: center;

            .el-icon {
                color: #409EFF;
                padding: 5px;
            }

            .el-icon:hover {
                background-color: rgb(245, 245, 245);
                border-radius: 20px;
                cursor: pointer;
            }

            .weeks {
                margin: 0 10px;
                display: flex;

                span {
                    margin: 0 40px;
                    padding: 5px 10px;
                    color: gray;
                }

                span:hover {
                    color: #000;
                    cursor: pointer;
                }

                .weeks-cur {
                    border-bottom: #409EFF 2px solid;
                }
            }
        }
    }

    .view-button {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;

        .el-button {
            background-color: #fff;
            color: #0a85ff;
        }

        .el-button:hover {
            background-color: #409EFF;
            color: #fff;
        }

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

        .table-out {
            table-layout: fixed;
            width: 100%;
            text-align: center;
            border-collapse: collapse;
            border-spacing: 0;

            thead tr th {
                padding: 20px 15px;
                font-weight: 500;
                background: #e3e3e3;
                transition: background .3s ease;
                border: 1px solid #eee;

                &:first-child {
                    border-top-left-radius: 5px;
                    border-left: none;
                    border-top: none;
                }

                &:last-child {
                    border-top-right-radius: 5px;
                    border-right: none;
                    border-top: none;
                }
            }

            tbody .tr-out {
                .td-out {
                    border: 1px solid #eee;
                    transition: background .3s;
                    overflow: hidden;
                    white-space: nowrap;
                    text-overflow: ellipsis;
                    padding: 10px;

                    .item {
                        padding: 5px 10px;
                        transition: background .3s;
                        overflow: hidden;
                        white-space: nowrap;
                        text-overflow: ellipsis;
                    }

                    .icon-delete {
                        margin-right: 5px;
                        vertical-align: middle;
                        color: #ff6b6b;
                        transition: all 0.5s;
                    }

                    .icon-delete:hover {
                        cursor: pointer;
                        transform: rotateZ(180deg);
                    }

                    .icon-add {
                        margin-top: 10px;
                        transition: all 0.5s;
                        color: #0a85ff;
                    }

                    .icon-add:hover {
                        cursor: pointer;
                        border: #0a85ff 1px solid;
                        border-radius: 10px;
                        padding: 5px;
                    }
                }

                .td:hover {
                    cursor: pointer;
                }

                .move {
                    margin: 2px;
                    padding: 10px;
                    background-color: #f1f1f1;
                    border-radius: 5px;
                }

                .move:hover {
                    background-color: #b6dbff;
                    cursor: move;
                }
            }
        }
    }

    .el-form {
        display: flex;
        justify-content: center;
    }
}
</style>