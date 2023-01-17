<template>
    <h1>我是home</h1>
</template>
<script>
import { getData } from '../api'
export default{
    
    data(){
        return{}
    },
    mounted(){
        getData().then((data)=>{
            console.log(data)
        })
    }
}
</script>