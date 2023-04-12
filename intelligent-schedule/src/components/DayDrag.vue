<template>
    <table class="table-out">
        <tbody v-loading="loading" element-loading-text="Loading..." :element-loading-spinner="svg"
            element-loading-svg-view-box="-10, -10, 50, 50" element-loading-background="rgba(130, 130, 130, 0.7)">
            <tr v-for='(row, index1) in dayData' class="tr-out">
                <td id="td-time" class="td-out" style="background-color: #e3e3e3;">
                    {{ (current.day() !== 0 && current.day() !== 6) ? timesDay[0][index1].periodName : timesDay[1][index1].periodName }}
                </td>
                <template v-for='(item, index2) in row'>
                    <td class="td-out td" :class="drag === true ? 'move-day' : null"
                        :id="'td' + week_day + index1 + '-' + index2" :title="(item.employeeId !== null && JSON.stringify(item) !== '{}') ?
                            (item.employeeName + ' ' + item.position) : null"
                        @click="drag === false ? emit('handleClickTd', index1, index2) : null"
                        :draggable="drag === true ? true : false" @dragstart="handleDragStart($event, index1, index2)"
                        @dragover.prevent="handleDragOver($event)" @dragenter="handleDragEnter($event, index1, index2)"
                        @dragend="handleDragEnd($event)">
                        <div v-if="item.employeeId !== null && JSON.stringify(item) !== '{}'">
                            <span v-if="viewValue === '全部查看'">
                                {{ item.employeeName + ' ' + item.position }}
                            </span>
                            <span v-else-if="viewValue === '按岗位分组'">{{ item.position }}</span>
                            <span v-else>{{ item.employeeName }}</span>
                        </div>
                        <el-icon v-else-if="drag === true" :size="22" class="icon-add"
                            @click="emit('showAddView', [], index1, index2)">
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
import moment from "moment";

const props = defineProps({
    dayData: Array,
    week_day: String,
    viewValue: String,
    loading: Boolean,
    current: typeof (moment()),
    timesDay: Array
});
const emit = defineEmits(['emitDayData', 'handleClickTd', 'showAddView', 'message']);

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

    var newData = [...props.dayData];
    var flag = false;
    var start = newData[dragging.value[0]][dragging.value[1]];
    var end = newData[ending.value[0]][ending.value[1]];
    props.dayData[dragging.value[0]].map(value => {
        if (flag === true) return;
        if (JSON.stringify(value) !== '{}' && value.employeeId !== null) {
            if (JSON.stringify(end) !== '{}' && end.employeeId !== null && value.employeeId === end.employeeId) {
                flag = true;
                return;
            }
        }
    });
    props.dayData[ending.value[0]].map(value => {
        if (flag === true) return;
        if (JSON.stringify(value) !== '{}' && value.employeeId !== null) {
            if (JSON.stringify(start) !== '{}' && start.employeeId !== null && value.employeeId === start.employeeId) {
                flag = true;
                return;
            }
        }
    });
    if (flag === true) {
        emit('message', '不能给同一个员工分配同一时间段的工作');
        return;
    }

    if (dragging.value[0] === ending.value[0]) {
        let item = [...newData[dragging.value[0]]];
        let temp = item[dragging.value[1]];
        item[dragging.value[1]] = item[ending.value[1]];
        item[ending.value[1]] = temp;

        newData[dragging.value[0]] = [...item];
    }
    else {
        let item1 = [...newData[dragging.value[0]]];
        let item2 = [...newData[ending.value[0]]];
        let temp = item1[dragging.value[1]];
        item1[dragging.value[1]] = item2[ending.value[1]];
        item2[ending.value[1]] = temp;

        newData[dragging.value[0]] = [...item1];
        newData[ending.value[0]] = [...item2];
    }

    console.log([...newData])
    emit('emitDayData', [...newData]);
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

const initDrag = () => {
    drag.value = false;
}

const dayEdit = () => {
    drag.value = (drag.value === true ? false : true);
}
defineExpose({
    dayEdit,
    initDrag
})
</script>

<style lang="less"></style>