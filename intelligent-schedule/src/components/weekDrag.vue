<template>
    <table class="table-out">
        <thead>
            <tr>
                <template v-for='(item, index) in table'>
                    <th class="item">{{ item }}</th>
                </template>
            </tr>
        </thead>
        <tbody v-loading="loading" element-loading-text="Loading..." :element-loading-spinner="svg"
            element-loading-svg-view-box="-10, -10, 50, 50" element-loading-background="rgba(130, 130, 130, 0.7)">
            <tr v-for='(time, i) in times' class="tr-out">
                <td class="td-out" :class="'out-' + i">
                    {{ time[0] }}
                </td>
                <template v-for="(value, j) in tableData[0]">
                    <td v-if="time[1] !== -1 && j < 5" class="td-out td" :id="'td' + week_day + time[1] + '-' + j"
                        :rowspan="weekdaySpans[time[1]]"
                        @click="(drag === false && time[1] !== 6) ? emit('handleClickTd', time[1], j) : null">
                        <draggable v-if="time[1] !== 6" :model-value="tableData[time[1]][j][0]" group="people"
                            animation="300" item-key="key" @start="startDrag($event, time[1], j)" @end="endDrag($event)"
                            @dragenter="enterDrag($event, time[1], j)">
                            <template #item="{ element, index }">
                                <div class="item" :class="(drag === true) ? 'move' : null"
                                    :title="element.employeeName + ' ' + element.position">
                                    <el-icon v-if="drag === true" :size="19" class="icon-delete"
                                        @click="deleteItem(time[1], j, index)">
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
                        <el-icon v-if="drag === true && time[1] !== 6" :size="22" class="icon-add"
                            @click="emit('showAddView', tableData[time[1]][j][0], time[1], j)">
                            <CirclePlus />
                        </el-icon>
                    </td>
                    <td v-else-if="time[2] !== -1 && j >= 5" class="td-out td" :id="'td' + week_day + time[2] + '-' + j"
                        :rowspan="weekendSpans[time[2]]"
                        @click="(drag === false && time[2] !== 0) ? emit('handleClickTd', time[2], j) : null">
                        <draggable v-if="time[2] !== 0" :model-value="tableData[time[2] - 1][j][0]" group="people"
                            animation="300" item-key="key" @start="startDrag($event, time[2] - 1, j)" @end="endDrag($event)"
                            @dragenter="enterDrag($event, time[2] - 1, j)">
                            <template #item="{ element, index }">
                                <div class="item" :class="(drag === true) ? 'move' : null"
                                    :title="element.employeeName + ' ' + element.position">
                                    <el-icon v-if="drag === true" :size="19" class="icon-delete"
                                        @click="deleteItem(time[2] - 1, j, index)">
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
                        <el-icon v-if="drag === true && time[2] !== 0" :size="22" class="icon-add"
                            @click="emit('showAddView', tableData[time[2] - 1][j][0], time[2] - 1, j)">
                            <CirclePlus />
                        </el-icon>
                    </td>
                </template>
            </tr>
        </tbody>
    </table>
</template>

<script setup>
import { ref } from 'vue';
import draggable from 'vuedraggable';

const props = defineProps({
    table: Object,
    tableData: Array,
    week_day: String,
    viewValue: String,
    loading: Boolean
});
const emit = defineEmits(['emitTableData', 'handleClickTd', 'showAddView', 'message']);

//加载数据
const svg = `
        <path class="path" d="
          M 30 15
          L 28 17
          M 25.61 25.61
          A 15 15, 0, 0, 1, 15 30
          A 15 15, 0, 1, 1, 27.99 7.5
          L 15 15
        " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"/>
      `

//是否可拖拽drag，拖拽起点dragging，拖拽终点ending
const drag = ref(false);
const ending = ref(null);
const dragging = ref(null);

//表格最左侧时间段
const times = [
    ["8:30-9:30", 0, 0],
    ["9:30-11:00", -1, 1],
    ["11:00-12:00", 1, -1],
    ["12:00-14:00", -1, 2],
    ["14:00-17:00", 2, 3],
    ["17:00-19:00", 3, 4],
    ["19:00-20:00", 4, -1],
    ["20:00-21:00", -1, 5],
    ["21:00-22:00", 5, -1],
    ["22:00-23:00", -1, 6],
    ["23:00-24:00", 6, -1]
];
const weekdaySpans = [2, 2, 1, 1, 2, 2, 1];
const weekendSpans = [1, 2, 1, 1, 2, 2, 2];

//开始拖拽、拖拽终点、结束拖拽
const startDrag = (e, index1, index2) => {
    if (drag.value === false) return;
    dragging.value = [index1, index2];
    // console.log(index1, index2);
}
const enterDrag = (e, index1, index2) => {
    if (drag.value === false) return;
    ending.value = [index1, index2];
    // console.log(index1, index2);
}
const endDrag = (e) => {
    if (drag.value === false) return;
    if (ending.value[0] === dragging.value[0] &&
        ending.value[1] === dragging.value[1]) {
        return;
    }

    var flag = false;
    props.tableData[ending.value[0]][ending.value[1]][0].map((value) => {
        if (value.employeeId === props.tableData[dragging.value[0]][dragging.value[1]][0][e.oldIndex].employeeId) {
            flag = true;
            return;
        }
    })
    if (flag === true) {
        emit('message', '不能给同一个员工分配同一时间段的工作');
        return;
    }

    var newData = [...props.tableData];

    if (dragging.value[0] === ending.value[0]) {
        let item = [...newData[dragging.value[0]]];
        item[ending.value[1]][0].push(item[dragging.value[1]][0][e.oldIndex]);
        item[dragging.value[1]][0].splice(e.oldIndex, 1);

        newData[dragging.value[0]] = [...item];
    }
    else {
        let item1 = [...newData[dragging.value[0]]];
        let item2 = [...newData[ending.value[0]]];
        item2[ending.value[1]][0].push(item1[dragging.value[1]][0][e.oldIndex]);
        item1[dragging.value[1]][0].splice(e.oldIndex, 1);

        newData[dragging.value[0]] = [...item1];
        newData[ending.value[0]] = [...item2];
    }

    emit('emitTableData', [...newData]);
}

//删除员工
const deleteItem = (index1, index2, index) => {
    // console.log(index1, index2, index);

    let newData = [...props.tableData];
    let item = [...newData[index1]];

    item[index2][0].splice(index, 1);
    newData[index1] = [...item];

    emit('emitTableData', [...newData]);
}

const weekEdit = () => {
    drag.value = (drag.value === true ? false : true);
}
defineExpose({
    weekEdit
})
</script>

<style lang="less">
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
                padding: 3px 10px;
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

        #tdweek6-0,
        #tdweek6-1,
        #tdweek6-2,
        #tdweek6-3,
        #tdweek6-4,
        #tdweek0-5,
        #tdweek0-6 {
            background: #fff url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiPjxsaW5lIHgxPSIwIiB5MT0iMCIgeDI9IjEwMCUiIHkyPSIxMDAlIiBzdHJva2U9ImJsYWNrIiBzdHJva2Utd2lkdGg9IjEiLz48L3N2Zz4=) no-repeat 100% center;
        }

        #tdweek6-0:hover,
        #tdweek6-1:hover,
        #tdweek6-2:hover,
        #tdweek6-3:hover,
        #tdweek6-4:hover,
        #tdweek0-5:hover,
        #tdweek0-6:hover {
            cursor: default;
        }

        .out-0,
        .out-2,
        .out-6,
        .out-7,
        .out-8,
        .out-9,
        .out-10 {
            height: 20px;
        }

        .out-1 {
            height: 30px;
        }

        .out-3,
        .out-5 {
            height: 40px;
        }

        .out-4 {
            height: 60px;
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
</style>