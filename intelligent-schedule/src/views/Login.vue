<template>
  <div id="building">
    <el-form ref="form" class="login-container" :model="form" :inline="true">
      <!-- :rules="rules" -->
      <h3 class="login_title">用户登录</h3>
      <div>
        <el-form-item class="login_username" label="用户名">
          <el-input v-model="form.username" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="密&ensp;&ensp;码" class="login">
          <el-input
            type="password"
            v-model="form.password"
            placeholder="请输入密码"
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="submit" class="button" type="primary"
            >登录</el-button
          >
        </el-form-item>
      </div>
    </el-form>
  </div>
  <el-form ref="form" class="login-container" label-width="150px" :model="form" :inline="true">
    <!-- :rules="rules" -->
    <h3 class="login_title">系统登录</h3>
    <el-form-item class="login_username" label="用户名">
      <el-input v-model="form.username" placeholder="请输入账号" />
    </el-form-item>
    <el-form-item label="密 码">
      <el-input type="password" v-model="form.password" placeholder="请输入密码" />
    </el-form-item>
    <el-form-item>
      <el-button @click="submit" style="margin-left :170px ;margin-top:10px" type="primary">登录</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import Mock from "mockjs";
import Cookie from "js-cookie";
import { getLogin, getMenu } from "../api";
import axios from "axios";
export default {
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          { required: true, trigger: "blur", message: "请输入用户名" },
        ],
        password: [{ required: true, trigger: "blur", message: "请输入密码" }],
      },
    };
  },
  methods: {
    submit() {
      // // token信息
      // const token = Mock.Random.guid();
      // //将token信息存入cookie用于不同页面的通讯
      // Cookie.set('token',token)

      // 校验通过
      this.$refs.form.validate((vaild) => {
        // console.log(getMenu(from))
        if (vaild) {
          console.log(vaild);
          // console.log("from"+this.form.password)
          getLogin(this.form).then(({ data }) => {
            // console.log("数据"+data)
            // console.log("falg   "+data.flag)
            // console.log("root   "+data.employee.root)
            sessionStorage.setItem("employee", JSON.stringify(data.employee)); //存储user对象
            let a = sessionStorage.getItem("employee");
            console.log("这是什么" + a.trim());
            console.log(a.employeeId);
            console.log("猜猜我是谁" + JSON.parse(a).employeeId);
            // console.log(data.employee)
            if (data.flag === "ok") {
              getMenu(parseInt(data.employee.root))
                .then(({ data }) => {
                  if (data.code === 200) {
                    //将token信息存入cookie中用于不同页面间的通讯
                    Cookie.set("token", data.data.token);
                    //获取菜单的数据，存入store
                    // data.data.menu
                    this.$store.commit("setMenu", data.data.menu);
                    //跳转到首页
                    this.$router.push("/home");
                  } else {
                    this.$message.error(data.data.message);
                  }
                })
                .catch((err) => {
                  console.log(err);
                });
            } else {
              console.log(data.flag);
            }
          });
          console.log(vaild)
          // console.log("from"+this.form.password)
          getLogin(this.form)
            .then(({ data }) => {
              // console.log("数据"+data)
              // console.log("falg   "+data.flag)
              // console.log("root   "+data.employee.root)
              sessionStorage.setItem("employee",JSON.stringify(data.employee));//存储user对象
              let a = sessionStorage.getItem("employee");
              console.log("这是什么"+a.trim())
              console.log(a.employeeId)
              console.log("猜猜我是谁"+JSON.stringify(a).employeeId);
              // console.log(data.employee)
              if(data.flag==="ok"){
                getMenu(parseInt(data.employee.root)).then(({ data }) => {
                  if (data.code === 200) {
                    //将token信息存入cookie中用于不同页面间的通讯
                    Cookie.set('token', data.data.token)
                    //获取菜单的数据，存入store
                    // data.data.menu
                    this.$store.commit('setMenu', data.data.menu)
                    //跳转到首页
                    this.$router.push('/home')
                  } else {
                    this.$message.error(data.data.message)
                  }
                }).catch((err) => {
                  console.log(err)
                })
              }
              else {
                console.log(data.flag);
              }
            })
        } else {
          console.log(vaild);
        }
      });
    },

    login() {
      var that = this;
      axios
        .post("http://localhost:8082/employee/login", {
          //请求登录接口
          username: this.form.username,
          password: this.form.password,
        })
        .then(function (response) {
          console.log(response.data);
          that.response = response.data;
          that.$emit("lisentcurrent", [that.response]);
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log("pass", that.response);
      this.$router.push("/home");
    },
  },
};
</script>
<style lang="less">
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  align-self: center;
  justify-self: center;
  width: 500px;
  height: 270px;
  // border: 1px solid #eaeaea;
  margin: 0 auto;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  // padding: 35px 80px 15px 35px;
  // box-shadow: 0 0 25px #cac6c6;
  // box-sizing: border-box;
  // box-shadow:0 0 10px 10px #fff;

  div {
    display: flex;
    align-self: center;
    justify-self: center;
    flex-direction: column;
  }

  .login_title {
    // text-align: center;
    margin-bottom: 40px;
    color: #505458;
  }

  .login_username {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    // border: 1px solid black;
    width: 250px;
    // align-items: center;
  }

  .login {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    // border: 1px solid black;
    width: 250px;
    // align-items: center;
  }

  // .el-input {
  //   // width: 198px;
  //   border: 1px solid black;
  // }
}
#building {
  background: url("../assets/background2.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  display: flex;
  // filter: brightness(90%);
}
.button {
  display: inline-block;
  border-radius: 4px;
  border: none;
  color: #ffffff;
  text-align: center;
  font-size: 15px;
  padding: 10px;
  width: 80px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
}

.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: "»";
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 15px;
}

.button:hover span:after {
  opacity: 1;
  right: 0;
}
</style>