<template>
    <div class="container">
        <el-calendar ref="calendar">
            <template #header="{ date }">
                <span>{{ date }}</span>
                <el-button-group>
                    <el-button @click="selectDate('prev-year')">
                        <el-icon>
                            <DArrowLeft />
                        </el-icon>
                        上一年
                    </el-button>
                    <el-button @click="selectDate('prev-month')">
                        <el-icon>
                            <ArrowLeft />
                        </el-icon>
                        上一月
                    </el-button>
                    <el-button @click="selectDate('today')">今天</el-button>
                    <el-button @click="selectDate('next-month')">
                        下一月
                        <el-icon>
                            <ArrowRight />
                        </el-icon>
                    </el-button>
                    <el-button @click="selectDate('next-year')">
                        下一年
                        <el-icon>
                            <DArrowRight />
                        </el-icon>
                    </el-button>
                </el-button-group>
            </template>
            <template #date-cell="{ data }">
                <div class="item" @click="handleClick(data.day)">
                    <p :class="data.isSelected ? 'is-selected' : ''">
                        {{ parseInt(data.day.split('-').slice(2).join('-')) }}
                        {{ data.isSelected ? '✔️' : '' }}
                    </p>
                    <div v-if="assignAll.includes(data.day)" class="dot"></div>
                </div>
            </template>
        </el-calendar>
        <div class="schedule">
            <div class="title">
                <span>值班安排</span>
            </div>
            <div v-for="(item, index) in assigns">
                <div class="assign">{{ item }}</div>
            </div>
        </div>
    </div>
    <div>{{ emp }}</div>
</template>

<script setup>
import { ref } from 'vue';

const emp = ref('emp');
fetch("http://localhost:8081/employee/all")
    .then(res => res.json())
    .then(resJson => {
        console.log(resJson);
        emp.value = resJson;
    })

const calendar = ref();
const selectDate = (val) => {
    calendar.value.selectDate(val)
};

//登录用户一月里有工作的日子
const assignAll = ref([
    '2023-01-01',
    '2023-01-02',
    '2023-01-10',
    '2023-01-13',
    '2023-01-18',
    '2023-01-19',
    '2023-01-23',
    '2023-01-24',
    '2023-01-31'
]);

const assigns = ref([
    '2023年1月18日 8:00-10:00',
    '2023年1月18日 10:00-12:00'
]);

const handleClick = (day) => {
    console.log(day)

    //根据用户和选中时间day检索数据库
}
</script>

<style lang="less" scoped>
.container {
    display: flex;

    .el-calendar {
        background-color: #fff;
        border-radius: 15px;
        padding: 20px;
        margin-right: 20px;
        flex: 60%;

        .item {
            width: 100%;
            height: 100%;
        }

        .is-selected {
            color: #1989fa;
        }

        .dot {
            width: 8px;
            height: 8px;
            background-color: red;
            border-radius: 20px;
        }
    }

    .schedule {
        display: flex;
        flex-direction: column;
        width: 40%;

        .title {
            background-color: #fff;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 10px;
            display: flex;
            justify-content: center;

            span {
                font-size: 18px;
            }
        }

        .assign {
            background-color: #fff;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 5px;
            display: flex;
            justify-content: center;
        }
    }
}
</style>