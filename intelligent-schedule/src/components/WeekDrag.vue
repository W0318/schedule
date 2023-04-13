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
            <tr v-for='(time, i) in timesWeek' class="tr-out">
                <td class="td-out">
                    {{ time.periodName }}
                </td>
<!--                <template v-for="(value, j) in tableData[0]">-->
<!--                    <td v-if="j < 5" class="td-out td" :class="i >= size[1] ? 'slash' : null"-->
<!--                        :id="'td' + week_day + i + '-' + j"-->
<!--                        @click="(drag === false && size[1]) ? emit('handleClickTd', i, j) : null">-->
<!--                        <draggable v-if="i < size[1]" -->
<!--                        :model-value="tableData[i][j][0]" group="people" animation="300"-->
<!--                            item-key="key" @start="startDrag($event, i, j)" @end="endDrag($event)"-->
<!--                            @dragenter="enterDrag($event, i, j)">-->
<!--                            <template #item="{ element, index }">-->
<!--                                <div class="item" :class="(drag === true) ? 'move' : null"-->
<!--                                    :title="element.employeeName + ' ' + element.position">-->
<!--                                    <el-icon v-if="drag === true" :size="19" class="icon-delete"-->
<!--                                        @click="deleteItem(i, j, index)">-->
<!--                                        <CircleClose />-->
<!--                                    </el-icon>-->
<!--                                    <span v-if="viewValue === '全部查看'">-->
<!--                                        {{ element.employeeName + ' ' + element.position }}-->
<!--                                    </span>-->
<!--                                    <span v-else-if="viewValue === '按岗位分组'">{{ element.position }}</span>-->
<!--                                    <span v-else>{{ element.employeeName }}</span>-->
<!--                                </div>-->
<!--                            </template>-->
<!--                        </draggable>-->
<!--                        <el-icon v-if="drag === true && i < size[1]" :size="22" class="icon-add"-->
<!--                            @click="emit('showAddView', tableData[i][j][0], i, j)">-->
<!--                            <CirclePlus />-->
<!--                        </el-icon>-->
<!--                    </td>-->
<!--                    <td v-else-if="j >= 5" class="td-out td" :class="i < size[0] - size[2] ? 'slash' : null"-->
<!--                        :id="'td' + week_day + i + '-' + j"-->
<!--                        @click="(drag === false && i >= size[0] - size[2]) ? emit('handleClickTd', i, j) : null">-->
<!--                        <draggable v-if="i >= size[0] - size[2]" :model-value="tableData[i - (size[0] - size[2])][j][0]" group="people"-->
<!--                            animation="300" item-key="key" @start="startDrag($event, i - (size[0] - size[2]), j)" @end="endDrag($event)"-->
<!--                            @dragenter="enterDrag($event, i - (size[0] - size[2]), j)">-->
<!--                            <template #item="{ element, index }">-->
<!--                                <div class="item" :class="(drag === true) ? 'move' : null"-->
<!--                                    :title="element.employeeName + ' ' + element.position">-->
<!--                                    <el-icon v-if="drag === true" :size="19" class="icon-delete"-->
<!--                                        @click="deleteItem(i - (size[0] - size[2]), j, index)">-->
<!--                                        <CircleClose />-->
<!--                                    </el-icon>-->
<!--                                    <span v-if="viewValue === '全部查看'">-->
<!--                                        {{ element.employeeName + ' ' + element.position }}-->
<!--                                    </span>-->
<!--                                    <span v-else-if="viewValue === '按岗位分组'">{{ element.position }}</span>-->
<!--                                    <span v-else>{{ element.employeeName }}</span>-->
<!--                                </div>-->
<!--                            </template>-->
<!--                        </draggable>-->
<!--                        <el-icon v-if="drag === true && i >= size[0] - size[2]" :size="22" class="icon-add"-->
<!--                            @click="emit('showAddView', tableData[i - (size[0] - size[2])][j][0], i - (size[0] - size[2]), j)">-->
<!--                            <CirclePlus />-->
<!--                        </el-icon>-->
<!--                    </td>-->
<!--                </template>-->
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
    loading: Boolean,
    timesWeek: Array,
    size: Array
});
const emit = defineEmits(['emitTableData', 'handleClickTd', 'showAddView', 'message']);

console.log(props.timesWeek)
console.log(props.size)
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
    console.log(index1, index2, index);

    let newData = [...props.tableData];
    let item = [...newData[index1]];

    item[index2][0].splice(index, 1);
    newData[index1] = [...item];

    emit('emitTableData', [...newData]);
}

const weekEdit = () => {
    drag.value = (drag.value === true ? false : true);
}

const initDrag = () => {
    drag.value = false;
}

defineExpose({
    weekEdit,
    initDrag
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

        .slash {
            background: #fff url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiPjxsaW5lIHgxPSIwIiB5MT0iMCIgeDI9IjEwMCUiIHkyPSIxMDAlIiBzdHJva2U9ImJsYWNrIiBzdHJva2Utd2lkdGg9IjEiLz48L3N2Zz4=) no-repeat 100% center;
        }

        .slash:hover {
            cursor: none;
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