<template>
    <div class="container">
        <el-select v-model="storeValue" filterable class="store" size="large">
            <el-option v-for="store in stores" :key="store.storeId" :label="store.storeName" :value="store.storeName" />
        </el-select>
        <div class="time">
            <span>{{ current }}</span>
        </div>
        <div>
            <div>
                <el-button-group>
                    <el-button type="primary">按周查看</el-button>
                    <el-button type="primary">按日查看</el-button>
                </el-button-group>
                <div>
                    <el-icon>
                        <ArrowLeft />
                    </el-icon>
                    <span>{{ curWeek }}</span>
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div>
                <el-select v-model="viewValue" filterable class="store" size="large">
                    <el-option v-for="viewMethod in viewMethods" :key="viewMethod.label" :label="viewMethod.value"
                        :value="viewMethod.value" />
                </el-select>
                <div>
                    <el-button type="primary" :icon="Delete">删除</el-button>
                    <el-button type="primary" :icon="Edit">删除</el-button>
                    <el-button type="primary" :icon="Postcard">一键生成排班</el-button>
                </div>
            </div>
        </div>
        <div>

        </div>
    </div>
</template>

<script setup>
/** 获取数据库 */

import { ref } from 'vue';
import moment from "moment"
import { Delete, Edit, Postcard } from '@element-plus/icons-vue'

const current = moment().format('YYYY年M月');
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

const curWeek = moment().startOf('isoWeek').format('M月D日') + '-' + moment().endOf('isoWeek').format('M月D日');
</script>

<style lang="less" scoped>
.container {
    width: 100%;
    height: 100%;
    background-color: rgb(223, 223, 223);
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    padding: 10px;
}

.store {
    margin-top: 10px;
    border-radius: 10px;
    display: flex;
    align-self: center;
}
</style>