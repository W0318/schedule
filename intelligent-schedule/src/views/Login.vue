<template>
  <el-form ref="form"  class="login-container" label-width="150px" :model="form" :inline="true">
    <!-- :rules="rules" -->
    <h3 class="login_title">系统登录</h3>
    <el-form-item class="login_username" label="用户名">
      <el-input v-model="form.username" placeholder="请输入账号" />
    </el-form-item>
    <el-form-item label="密 码">
      <el-input type="password" v-model="form.password" placeholder="请输入密码" />
    </el-form-item>
    <el-form-item>
      <el-button @click="submit" style="margin-left :30px ;margin-top:10px" type="primary">登录</el-button>
    </el-form-item>
  </el-form>
</template>
<script>

import Mock from 'mockjs'
import Cookie from 'js-cookie'
import {getMenu} from '../api'
export default {
  data() {
    return{
        form:{
            username:'',
            password:'',
        },
        rules:{
            username:[
                {required: true,trigger:'blur',message:'请输入用户名'}

            ],
            password:[
                {required: true,trigger:'blur',message:'请输入密码'}
            ]
        }

    }
  },methods:{
    submit(){
        // // token信息
        // const token = Mock.Random.guid();
        // //将token信息存入cookie用于不同页面的通讯
        // Cookie.set('token',token)

        // 校验通过
        this.$refs.form.validate((vaild)=>{
            // console.log(getMenu(from))
            if(vaild){
                console.log(vaild)
                // console.log(this.form)
                getMenu(this.form)
                .then(({data})=>{
                    console.log(data)
                    if(data.code === 20000){
                        //将token信息存入cookie中用于不同页面间的通讯
                        Cookie.set('token',data.data.token)

                        //获取菜单的数据，存入store
                        // data.data.menu 
                        this.$store.commit('setMenu',data.data.menu)


                        //跳转到首页
                        this.$router.push('/home')
                    } else{
                        this.$message.error(data.data.message)
                    }
                }).catch((err)=>{
                    console.log(err)
                })
            }else{
                console.log(vaild)
            }
        })

    }
  }
};

</script>
<style lang="less">
.login-container {
  width: 500px;
  border: 1px solid #eaeaea;
  margin: 280px auto;
  padding: 35px 35px 15px 35px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 0 25px #cac6c6;
  box-sizing: border-box;

  .login_title {
    text-align: center;
    margin-bottom: 40px;
    color: #505458;
  }
  .login_username {
    align-items: center;
  }
  .el-input {
    width: 198px;
  }
}
</style>