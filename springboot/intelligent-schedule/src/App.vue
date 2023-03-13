<template>
  <div id="app">
  <router-view></router-view>
  </div>
</template>

<script setup>
// export default{
//   name:'App'
// }
import { useStore } from 'vuex';
const store = useStore();
store.commit('addMenu')
</script>
<style lang="less">
html,body,h3{
  margin: 0;
  padding: 0;
}
</style>