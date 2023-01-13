<template>
  <el-form class="login-container" label-width="150px" :model="form" :rules="rules">
    <h3 class="login_title">系统登录</h3>
    <el-form-item class="login_username" label="用户名">
        <el-input v-model="form.username" placeholder="请输入账号" />
    </el-form-item>
    <el-form-item label="密 码">
        <el-input type="password" v-model="form.password" placeholder="请输入密码" />
    </el-form-item>
    <el-form-item>
        <el-button style="margin-left :30px ;margin-top:10px" type="primary">登录</el-button>
        <!-- <el-button type="primary">注册</el-button> -->
    </el-form-item>
  </el-form>
</template>
<script>
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
  }
};
</script>
<style lang="less">
.login-container{
    width: 500px;
    border: 1px solid #eaeaea;
    margin: 280px auto;
    padding: 35px 35px 15px 35px;
    background-color: #fff;
    border-radius: 15px;
    box-shadow: 0 0 25px #cac6c6;
    box-sizing: border-box;
    
    .login_title{
        text-align: center;
        margin-bottom: 40px;
        color: #505458;
    }
    .login_username{
        align-items: center;
        
    }
    .el-input{
        width: 198px;
    }
}
</style>