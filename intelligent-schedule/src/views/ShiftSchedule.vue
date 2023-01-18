<template>
    <div class="container">
        <el-select v-model="storeValue" filterable class="store" size="large" @change="changeStore">
            <el-option v-for="store in stores" :key="store.storeId" :label="store.storeName" :value="store.storeName" />
        </el-select>

        <div class="time">
            <span>{{ current.format('YYYY年M月') }}</span>
        </div>

        <div class="view-options">
            <div class="view">
                <el-button type="primary" :class="week_day === 'week' ? 'view-choose' : 'view-notchoose'"
                    @click="chooseWorD('week')">按周查看</el-button>
                <el-button type="primary" :class="week_day === 'day' ? 'view-choose' : 'view-notchoose'"
                    @click="chooseWorD('day')">按日查看</el-button>
            </div>

            <div class="optionView">
                <el-button class="option-back" @click="backToCurrent">
                    <span v-if="week_day === 'week'">返回本周</span>
                    <span v-else>返回当天</span>
                </el-button>

                <el-icon :size="25" @click="shift('left')">
                    <ArrowLeft />
                </el-icon>
                <div v-if="week_day === 'week'" class="options">
                    <span class="option-week" :class="curWeek === week1 ? 'options-cur' : 'options-other'"
                        @click="chooseOption(week1, 0)">{{
                            week1
                        }}</span>
                    <span class="option-week" :class="curWeek === week2 ? 'options-cur' : 'options-other'"
                        @click="chooseOption(week2, 1)">{{
                            week2
                        }}</span>
                    <span class="option-week" :class="curWeek === week3 ? 'options-cur' : 'options-other'"
                        @click="chooseOption(week3, 2)">{{
                            week3
                        }}</span>
                </div>

                <div v-else class="options">
                    <span class="option-day" :class="curDay === day1 ? 'options-cur' : 'options-other'"
                        @click="chooseOption(day1, 0)">{{
                            day1
                        }}</span>
                    <span class="option-day" :class="curDay === day2 ? 'options-cur' : 'options-other'"
                        @click="chooseOption(day2, 1)">{{
                            day2
                        }}</span>
                    <span class="option-day" :class="curDay === day3 ? 'options-cur' : 'options-other'"
                        @click="chooseOption(day3, 2)">{{
                            day3
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
                <el-button type="primary" :icon="Delete" @click="handleDelete" title="删除单元格内容">
                    删除</el-button>
                <el-button :class="edit === '取消' ? 'button-choose' : null" type="primary" :icon="Edit"
                    @click="handleEdit">{{ edit }}</el-button>
                <el-button v-if="week_day === 'week'" type="primary" :icon="Postcard">一键生成排班</el-button>
            </div>
        </div>

        <div class="schedule">
            <table v-if="week_day === 'week'" class="table-out">
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
                            {{ times.period[index1] }}
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
                                            <span v-else-if="viewValue === '按岗位分组'">{{ element.position }}</span>
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

            <table v-if="week_day === 'day'" class="table-out">
                <tbody>
                    <tr v-for='(row, index1) in dayData' class="tr-out">
                        <td id="td-time" class="td-out" style="background-color: #e3e3e3;">
                            {{ times.period[index1] }}
                        </td>
                        <template v-for='(item, index2) in row'>
                            <td class="td-out" :class="drag === true ? 'move-day' : null"
                                :id="'td' + index1 + '-' + index2" :title="(JSON.stringify(item) !== '{}') ?
                                (item.employeeName + ' ' + item.position) : null"
                                @click="drag === false ? handleClickTd(index1, index2) : null"
                                :draggable="drag === true ? true : false"
                                @dragstart="handleDragStart($event, index1, index2)"
                                @dragover.prevent="handleDragOver($event)"
                                @dragenter="handleDragEnter($event, index1, index2)" @dragend="handleDragEnd($event)">
                                <div v-if="JSON.stringify(item) !== '{}'">
                                    <span v-if="viewValue === '全部查看'">
                                        {{ item.employeeName + ' ' + item.position }}
                                    </span>
                                    <span v-else-if="viewValue === '按岗位分组'">{{ item.position }}</span>
                                    <span v-else>{{ item.employeeName }}</span>
                                </div>
                                <el-icon v-else-if="drag === true" :size="22" class="icon-add"
                                    @click="showAddView([], index1, index2)">
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
import { reactive, ref } from 'vue';
import moment from "moment";
import draggable from 'vuedraggable';
import { Delete, Edit, Postcard } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';

/**
 * 初始化获取数据库数据：stores、startTime、endTime、data、employees
 */
// 门店切换，最好按storeName字母拼音顺序排序
const stores = ref([
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
]);
const storeValue = ref(stores.value[0].storeName);

//表格最左侧时间段
var startTime = '8:00';
var endTime = '22:00';
var minutes = moment('2023-1-1 ' + endTime).diff(moment('2023-1-1 ' + startTime), 'minutes');
var hours = Math.ceil(minutes / 120);
var start = startTime;
var times = reactive({ period: new Array(hours).fill('') });
times.period = times.period.map(() => {
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
const dayData = ref([
    [
        { key: id++, employeeId: 'employee1', employeeName: 'www', position: '门店经理' },
        { key: id++, employeeId: 'employee2', employeeName: 'aaa', position: '副经理' },
        { key: id++, employeeId: 'employee3', employeeName: 'zzz', position: '小组长' },
        {}, {}, {}
    ],
    [{}, {}, {}, {}, {}, {}],
    [{}, {}, {}, {}, {}, {}],
    [{}, {}, {}, {}, {}, {}],
    [{}, {}, {}, {}, {}, {}],
    [{}, {}, {}, {}, {}, {}],
    [{}, {}, {}, {}, {}, {}]
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
var optionIndex = 0;
var lastIndex = 0;
var leftOption = moment();
var rightOption = moment().add(2, 'w');
//日切换
const day1 = ref(moment().format('M月D日'));
const day2 = ref(moment().add(1, 'days').format('M月D日'));
const day3 = ref(moment().add(2, 'days').format('M月D日'));
const curDay = ref(day1.value);

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

    startTime = '9:00';
    endTime = '21:00';
    minutes = moment('2023-1-1 ' + endTime).diff(moment('2023-1-1 ' + startTime), 'minutes');
    hours = Math.ceil(minutes / 120);
    start = startTime;
    times = reactive({ period: new Array(hours).fill('') });
    times.period = times.period.map(() => {
        let temp = start.split(':');
        temp[0] = parseInt(temp[0]) + 2 + '';
        let end = temp.join(':');
        let time = start + '-' + end;
        start = end;
        return time;
    });

    //表格最左侧时间段
    var startTime = '8:00';
    var endTime = '22:00';
    var minutes = moment('2023-1-1 ' + endTime).diff(moment('2023-1-1 ' + startTime), 'minutes');
    var hours = Math.ceil(minutes / 120);
    var start = startTime;
    times = reactive({ period: new Array(hours).fill('') });
    times.period = times.period.map(() => {
        let temp = start.split(':');
        temp[0] = parseInt(temp[0]) + 2 + '';
        let end = temp.join(':');
        let time = start + '-' + end;
        start = end;
        return time;
    });

    //表格单元格数据
    var id = 0;
    data.value = [
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
        }
    ];

    //所有员工数据
    employees.value = [
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
    ];
    employeesAvail.value = [...employees.value];

    //按周查看|按日查看 的切换
    week_day.value = 'week';

    //全部查看|按岗位分组|按员工分组 的切换
    viewValue.value = viewMethods[0].value;

    //周切换
    week1.value = moment().startOf('isoWeek').format('M月D日') + '-' + moment().endOf('isoWeek').format('M月D日');
    week2.value = moment().add(1, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().add(1, 'w').endOf('isoWeek').format('M月D日');
    week3.value = moment().add(2, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().add(2, 'w').endOf('isoWeek').format('M月D日');
    current.value = moment();
    curWeek.value = week1.value;
    labels.value = [
        '周一 ' + current.value.startOf('isoWeek').format('M月D日'),
        '周二 ' + current.value.startOf('isoWeek').add(1, 'days').format('M月D日'),
        '周三 ' + current.value.startOf('isoWeek').add(2, 'days').format('M月D日'),
        '周四 ' + current.value.startOf('isoWeek').add(3, 'days').format('M月D日'),
        '周五 ' + current.value.startOf('isoWeek').add(4, 'days').format('M月D日'),
        '周六 ' + current.value.startOf('isoWeek').add(5, 'days').format('M月D日'),
        '周日 ' + current.value.startOf('isoWeek').add(6, 'days').format('M月D日'),
    ];
    current.value = moment();
    optionIndex = 0;
    lastIndex = 0;
    leftOption = moment();
    rightOption = moment().add(2, 'w');

    //被点击单元格的坐标s
    arrTd = [];

    //编辑button的文本切换
    edit.value = '编辑';

    //表格标签
    tabel.value = {
        time: '时间',
        week1: labels.value[0],
        week2: labels.value[1],
        week3: labels.value[2],
        week4: labels.value[3],
        week5: labels.value[4],
        week6: labels.value[5],
        week7: labels.value[6],
    };

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
        tds[i].style.backgroundColor = '';
    }
}

//修改按周查看|按日查看
const chooseWorD = (button) => {
    //获取数据库data、dayData
    if (button === 'week' && week_day.value === 'day') {
        week_day.value = 'week';

        //周切换
        week1.value = moment().startOf('isoWeek').format('M月D日') + '-' + moment().endOf('isoWeek').format('M月D日');
        week2.value = moment().add(1, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().add(1, 'w').endOf('isoWeek').format('M月D日');
        week3.value = moment().add(2, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().add(2, 'w').endOf('isoWeek').format('M月D日');
        current.value = moment();
        curWeek.value = week1.value;
        labels.value = [
            '周一 ' + current.value.startOf('isoWeek').format('M月D日'),
            '周二 ' + current.value.startOf('isoWeek').add(1, 'days').format('M月D日'),
            '周三 ' + current.value.startOf('isoWeek').add(2, 'days').format('M月D日'),
            '周四 ' + current.value.startOf('isoWeek').add(3, 'days').format('M月D日'),
            '周五 ' + current.value.startOf('isoWeek').add(4, 'days').format('M月D日'),
            '周六 ' + current.value.startOf('isoWeek').add(5, 'days').format('M月D日'),
            '周日 ' + current.value.startOf('isoWeek').add(6, 'days').format('M月D日'),
        ];
        current.value = moment();
        optionIndex = 0;
        lastIndex = 0;
        leftOption = moment();
        rightOption = moment().add(2, 'w');

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
    }
    else if (button === 'day' && week_day.value === 'week') {
        week_day.value = 'day';

        //日切换
        day1.value = moment().format('M月D日');
        day2.value = moment().add(1, 'days').format('M月D日');
        day3.value = moment().add(2, 'days').format('M月D日');
        current.value = moment();
        curDay.value = day1.value;
        leftOption = moment();
        rightOption = moment().add(2, 'days');
    }

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
        tds[i].style.backgroundColor = '';
    }
}

//选择周
const chooseOption = (button, index) => {
    // console.log(optionIndex, index, lastIndex)
    if (button !== curWeek) {
        if (week_day.value === 'week') {
            curWeek.value = button;

            if (optionIndex + index - lastIndex < 0) {
                current.value = current.value.subtract(-(optionIndex + index - lastIndex), 'w');
            }
            else if (optionIndex + index - lastIndex > 0) {
                current.value = current.value.add(optionIndex + index - lastIndex, 'w');
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
            optionIndex = 0;
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

            //从数据库中获取数据，更新data
        }
        else {
            curDay.value = button;

            if (optionIndex + index - lastIndex < 0) {
                current.value = current.value.subtract(-(optionIndex + index - lastIndex), 'days');
            }
            else if (optionIndex + index - lastIndex > 0) {
                current.value = current.value.add(optionIndex + index - lastIndex, 'days');
            }

            optionIndex = 0;
            lastIndex = index;

            //从数据库中获取数据，更新dayData
        }

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
}
//左右箭头
const shift = (direction) => {
    console.log(leftOption, rightOption, optionIndex)
    if (direction === 'left') {
        if (week_day.value === 'week') {
            leftOption = leftOption.subtract(1, 'w');
            rightOption = rightOption.subtract(1, 'w');
            optionIndex = optionIndex - 1;
            week3.value = week2.value;
            week2.value = week1.value;
            week1.value = leftOption.startOf('isoWeek').format('M月D日') + '-' + leftOption.endOf('isoWeek').format('M月D日');
        }
        else {
            leftOption = leftOption.subtract(1, 'days');
            rightOption = rightOption.subtract(1, 'days');
            optionIndex = optionIndex - 1;
            day3.value = day2.value;
            day2.value = day1.value;
            day1.value = leftOption.format('M月D日');
        }
    }
    else {
        if (week_day.value === 'week') {
            leftOption = leftOption.add(1, 'w');
            rightOption = rightOption.add(1, 'w');
            optionIndex = optionIndex + 1;
            week1.value = week2.value;
            week2.value = week3.value;
            week3.value = rightOption.startOf('isoWeek').format('M月D日') + '-' + rightOption.endOf('isoWeek').format('M月D日');
        }
        else {
            leftOption = leftOption.add(1, 'days');
            rightOption = rightOption.add(1, 'days');
            optionIndex = optionIndex + 1;
            day1.value = day2.value;
            day2.value = day3.value;
            day3.value = rightOption.format('M月D日');
        }
    }
}
//返回本周/当天
const backToCurrent = () => {
    if (week_day.value === 'week') {
        //周切换
        week1.value = moment().startOf('isoWeek').format('M月D日') + '-' + moment().endOf('isoWeek').format('M月D日');
        week2.value = moment().add(1, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().add(1, 'w').endOf('isoWeek').format('M月D日');
        week3.value = moment().add(2, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().add(2, 'w').endOf('isoWeek').format('M月D日');
        current.value = moment();
        curWeek.value = week1.value;
        labels.value = [
            '周一 ' + current.value.startOf('isoWeek').format('M月D日'),
            '周二 ' + current.value.startOf('isoWeek').add(1, 'days').format('M月D日'),
            '周三 ' + current.value.startOf('isoWeek').add(2, 'days').format('M月D日'),
            '周四 ' + current.value.startOf('isoWeek').add(3, 'days').format('M月D日'),
            '周五 ' + current.value.startOf('isoWeek').add(4, 'days').format('M月D日'),
            '周六 ' + current.value.startOf('isoWeek').add(5, 'days').format('M月D日'),
            '周日 ' + current.value.startOf('isoWeek').add(6, 'days').format('M月D日'),
        ];
        current.value = moment();
        optionIndex = 0;
        lastIndex = 0;
        leftOption = moment();
        rightOption = moment().add(2, 'w');

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

        //从数据库中获取数据，更新data
    }
    else {
        //日切换
        day1.value = moment().format('M月D日');
        day2.value = moment().add(1, 'days').format('M月D日');
        day3.value = moment().add(2, 'days').format('M月D日');
        current.value = moment();
        curDay.value = day1.value;
        leftOption = moment();
        rightOption = moment().add(2, 'days');

        //从数据库中获取数据，更新dayData
    }

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
                if (week_day.value === 'week') {
                    let newData = [...data.value];
                    arrTd.map(value => {
                        newData[value[0]][value[1]] = [];
                        let td = document.getElementById('td' + value[0] + '-' + value[1]);
                        console.log(td)
                        td.style.backgroundColor = '';
                    })
                    data.value = [...newData];
                }
                else {
                    let newDayData = [...dayData.value];
                    arrTd.map(value => {
                        newDayData[value[0]][value[1]] = {};
                        let td = document.getElementById('td' + value[0] + '-' + value[1]);
                        td.style.backgroundColor = '';
                    })
                    dayData.value = [...newDayData];
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
//点击编辑|取消
const handleEdit = () => {
    if (edit.value === '编辑') {
        var tds = document.querySelectorAll('td');
        for (var i = 1; i < tds.length; i++) {
            if (tds[i].id !== 'td-time')
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
    if (week_day.value === 'week') {
        let newData = [...data.value];
        newData[addIndex[0]][addIndex[1]].push(employeeValue.value);
        console.log(newData)
        data.value = [...newData];
    }
    else {
        let newData = [...dayData.value];
        newData[addIndex[0]][addIndex[1]] = employeeValue.value;
        dayData.value = [...newData];
    }

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

//按日查看拖拽
const handleDragStart = (e, row, col) => {
    // console.log(row, col);
    dragging.value = [row, col];
}
const handleDragEnd = (e) => {
    if (ending.value[0] === dragging.value[0] &&
        ending.value[1] === dragging.value[1]) {
        return;
    }
    if (dragging.value[1] === 'time' || ending.value[1] === 'time')
        return;

    var newData = [...dayData.value];

    if (dragging.value[0] === ending.value[0]) {
        let item = { ...newData[dragging.value[0]] };
        let temp = item[dragging.value[1]];
        item[dragging.value[1]] = item[ending.value[1]];
        item[ending.value[1]] = temp;

        newData[dragging.value[0]] = { ...item };
    }
    else {
        let item1 = { ...newData[dragging.value[0]] };
        let item2 = { ...newData[ending.value[0]] };
        let temp = item1[dragging.value[1]];
        item1[dragging.value[1]] = item2[ending.value[1]];
        item2[ending.value[1]] = temp;

        newData[dragging.value[0]] = { ...item1 };
        newData[ending.value[0]] = { ...item2 };
    }

    dayData.value = [...newData];
    dragging.value = null;
    ending.value = null;
}
const handleDragOver = (e) => {
    e.dataTransfer.dropEffect = "move";
}
const handleDragEnter = (e, row, col) => {
    // console.log(row, col);
    e.dataTransfer.effectAllowed = "move";
    ending.value = [row, col];
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

        .optionView {
            display: flex;
            justify-self: center;

            .option-back {
                align-self: center;
                margin-right: 10px;
            }

            .el-icon {
                color: #409EFF;
                padding: 5px;
            }

            .el-icon:hover {
                background-color: rgb(245, 245, 245);
                border-radius: 20px;
                cursor: pointer;
            }

            .options {
                margin: 0 10px;
                display: flex;

                span {
                    color: gray;
                }

                span:hover {
                    color: #000;
                    cursor: pointer;
                }

                .option-week {
                    margin: 0 25px;
                    padding: 5px 10px;
                }

                .option-day {
                    margin: 0 45px;
                    padding: 5px 25px;
                }

                .options-cur {
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

                .move-day:hover {
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