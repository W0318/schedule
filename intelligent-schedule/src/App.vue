<template>
  <div id="app">
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
import { useStore } from "vuex";

export default {
  name: 'App',
  provide(){
    return{
      reload:this.reload
    }
  },
  data(){
    return{
      isRouterAlive:true
    }
  },
  methods:{
    reload(){
      this.isRouterAlive = false;
      this.$nextTick(function(){
        this.isRouterAlive = true;
      })
    }
  },
  setup() {
    const store = useStore();
    store.commit("addMenu");
  },
};
</script>
<style lang="less">
html,
body,
h3 {
  margin: 0;
  padding: 0;
}
</style>