<template>
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
</template>

<script setup>
import { ref } from 'vue';
import moment from "moment";

const props = defineProps({
    week_day: String
});
const emit = defineEmits(['emitCurrent', 'emitTable', 'updateTableData', 'updateDayDate', 'initVariable']);

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

//选择周|日
const chooseOption = (button, index) => {
    // console.log(optionIndex, index, lastIndex)
    if (button !== curWeek) {
        if (props.week_day === 'week') {
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
            emit('emitCurrent', current.value)
            optionIndex = 0;
            lastIndex = index;

            table.value = {
                time: '时间',
                week1: labels.value[0],
                week2: labels.value[1],
                week3: labels.value[2],
                week4: labels.value[3],
                week5: labels.value[4],
                week6: labels.value[5],
                week7: labels.value[6],
            }
            emit('emitTable', table.value)

            emit('updateTableData');
        }
        else {
            curDay.value = button;

            if (optionIndex + index - lastIndex < 0) {
                current.value = current.value.subtract(-(optionIndex + index - lastIndex), 'days');
            }
            else if (optionIndex + index - lastIndex > 0) {
                current.value = current.value.add(optionIndex + index - lastIndex, 'days');
            }
            emit('emitCurrent', current.value)

            optionIndex = 0;
            lastIndex = index;

            emit('updateDayDate');
        }

        emit('initVariable');
    }
}
//左右箭头
const shift = (direction) => {
    // console.log(leftOption, rightOption, optionIndex)
    if (direction === 'left') {
        if (props.week_day === 'week') {
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
        if (props.week_day === 'week') {
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
    if (props.week_day === 'week') {
        //周切换
        initWeeks();
    }
    else {
        //日切换
        initDays();
    }

    emit('initVariable');
}

//工具类
const initWeeks = () => {
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
    emit('emitCurrent', current.value);

    optionIndex = 0;
    lastIndex = 0;
    leftOption = moment();
    rightOption = moment().add(2, 'w');

    //表格标签
    table.value = {
        time: '时间',
        week1: labels.value[0],
        week2: labels.value[1],
        week3: labels.value[2],
        week4: labels.value[3],
        week5: labels.value[4],
        week6: labels.value[5],
        week7: labels.value[6],
    };
    emit('emitTable', table.value);

    emit('updateTableData');
}
const initDays = () => {
    day1.value = moment().format('M月D日');
    day2.value = moment().add(1, 'days').format('M月D日');
    day3.value = moment().add(2, 'days').format('M月D日');
    current.value = moment();
    emit('emitCurrent', current.value);

    curDay.value = day1.value;
    leftOption = moment();
    rightOption = moment().add(2, 'days');

    emit('updateDayDate');
}
defineExpose({
    initWeeks,
    initDays
})
</script>

<style lang="less" scoped>
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
</style>