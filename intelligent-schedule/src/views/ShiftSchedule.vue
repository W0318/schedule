<template>
    <div class="container">
        <el-select v-model="storeValue" filterable class="store" size="large">
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
                <el-button type="primary" :icon="Delete">删除</el-button>
                <el-button type="primary" :icon="Edit">删除</el-button>
                <el-button type="primary" :icon="Postcard">一键生成排班</el-button>
            </div>
        </div>

        <div class="schedule">
            <el-row>
                
            </el-row>
            <draggable v-model="column1" group="people" animation="300" dragClass="dragClass" ghostClass="ghostClass"
                chosenClass="chosenClass" @start="drag = true" @end="drag = false" item-key="id">
                <template #item="{ element }">
                    <div class="item">{{ element.name }}</div>
                </template>
            </draggable>
            <draggable v-model="column2" group="people" animation="300" dragClass="dragClass" ghostClass="ghostClass"
                chosenClass="chosenClass" @start="drag = true" @end="drag = false" item-key="id">
                <template #item="{ element }">
                    <div class="item">{{ element.name }}</div>
                </template>
            </draggable>
        </div>
    </div>
</template>
<!-- <div class="schedule">
    <el-table :data="tableData" border>
        <el-table-column prop="index" label="时间" align="center" :row-style="{ height: '100' }" fixed>
            <draggable v-model="column1" group="site" animation="300" dragClass="dragClass"
                ghostClass="ghostClass" chosenClass="chosenClass" @start="onStart" @end="onEnd">
                <transition-group>
                    <div class="item" v-for="item in column1" :key="item.id">{{ item.name }}</div>
                </transition-group>
            </draggable>
        </el-table-column>
        <el-table-column prop="week1" :label="labels[0]" align="center" />
        <el-table-column prop="week2" :label="labels[1]" align="center" />
        <el-table-column prop="week3" :label="labels[2]" align="center" />
        <el-table-column prop="week4" :label="labels[3]" align="center" />
        <el-table-column prop="week5" :label="labels[4]" align="center" />
        <el-table-column prop="week6" :label="labels[5]" align="center" />
        <el-table-column prop="week7" :label="labels[6]" align="center" />
    </el-table>
</div> -->
<script setup>
import { ref } from 'vue';
import moment from "moment";
import draggable from 'vuedraggable';
import { Delete, Edit, Postcard } from '@element-plus/icons-vue'

/** 获取数据库 */

// 最好按storeName字母拼音顺序排序
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
const viewValue = ref(viewMethods[0].value);

/** 按周、按日查看 */
var week_day = ref('week');
const chooseWorD = (button) => {
    if (button === 'week' && week_day.value === 'day'
        || button === 'day' && week_day.value === 'week') {
        week_day.value === 'week' ? week_day.value = 'day' : week_day.value = 'week'
    }
}

/** 周选择 */
const week1 = ref(moment().startOf('isoWeek').format('M月D日') + '-' + moment().endOf('isoWeek').format('M月D日'));
const week2 = ref(moment().add(1, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().endOf('isoWeek').format('M月D日'));
const week3 = ref(moment().add(2, 'w').startOf('isoWeek').format('M月D日') + '-' + moment().endOf('isoWeek').format('M月D日'));
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
var weekIndex = 0;
var lastIndex = 0;
var leftWeek = moment();
var rightWeek = moment().add(2, 'w');
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
        weekIndex = 0;
        lastIndex = index;
    }
}
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

/** 排班表 */
const startTime = '8:00';
const endTime = '22:00';
const minutes = moment('2023-1-1 ' + endTime).diff(moment('2023-1-1 ' + startTime), 'minutes');
const hours = Math.ceil(minutes / 120);
var times = new Array(hours).fill('');
var start = startTime;
times = times.map(() => {
    let temp = start.split(':');
    temp[0] = parseInt(temp[0]) + 2 + '';
    let end = temp.join(':');
    let time = start + '-' + end;
    start = end;
    return time;
});

// var tableData = new Array(hours).fill({});
// tableData = tableData.map((value, index) => {
//     return ({
//         index: times[index],
//         week1: '-',
//         week2: '-',
//         week3: '-',
//         week4: '-',
//         week5: '-',
//         week6: '-',
//         week7: '-'
//     });
// })
// console.log(tableData)
var drag = ref(false);
const column1 = [
    { id: 1, name: times[0] },
    { id: 2, name: times[1] },
    { id: 3, name: times[2] },
    { id: 4, name: times[3] },
    { id: 5, name: times[4] },
    { id: 6, name: times[5] },
    { id: 7, name: times[6] }
];
const column2 = [
    { id: 1, name: '-' },
    { id: 2, name: '-' },
    { id: 3, name: '-' },
    { id: 4, name: '-' },
    { id: 5, name: '-' },
    { id: 6, name: '-' },
    { id: 7, name: '-' }
];
</script>

<style lang="less" scoped>
.container {
    width: 100%;
    height: 100%;
    background-color: #fff;
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    padding: 10px;

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
            margin-top: 8px;

            .el-icon {
                color: #409EFF;
            }

            .el-icon:hover {
                color: #79BBFF;
            }

            .weeks {
                margin: 0 10px;

                span {
                    margin: 40px;
                    padding: 10px 10px 5px 10px;
                    color: gray;
                }

                span:hover {
                    color: #000;
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
    }

    .schedule {
        display: flex;
    }

    .ghostClass {
        background-color: blue !important;
    }

    .chosenClass {
        background-color: red !important;
        opacity: 1 !important;
    }

    .dragClass {
        background-color: blueviolet !important;
        opacity: 1 !important;
        box-shadow: none !important;
        outline: none !important;
        background-image: none !important;
    }

    .item {
        padding: 10px;
        margin: 0;
        border: solid 1px #eee;
        background-color: #fff;
    }

    .item:hover {
        background-color: #eee;
        cursor: move;
    }
}
</style>