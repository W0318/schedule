package com.example.springboot.bean.controller;

import com.example.springboot.bean.bean.User;
import com.example.springboot.bean.services.UserServices;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: yy
 * @Date: 2019/12/31 16:37
 * @Version 1.0
 */
@Api(description="用户登录注册")
@Slf4j
@Controller
@RequestMapping(value = "/api/user")
public class UserController {

    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServices userServices;

    @ApiOperation(value="用户注册", notes = "注册会员")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="用户名",dataType = "String",required=true),
            @ApiImplicitParam(name="age",value="年龄",dataType = "Int",required=true),
            @ApiImplicitParam(name="pwd",value="密码",dataType = "String",required=true)
    })
    public String register(String name, Integer age, String pwd, Model model, HttpServletRequest request, HttpServletResponse response)throws Exception{

        try{
            //打印日志
            log.info(name+","+age+","+pwd);
            //获取注册的结果
            User result = userServices.register(name, age, pwd);

            if(result.isSuccess()){
                //将结果存到model里面，用于前端view层展示
                model.addAttribute("result",result);
                //跳转至注册结果页面
                return "/registerResult";
            }else{
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().print("{\"code\":\"0002\",\"msg\":\"用户名已存在，注册失败！\"}");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //第一种写法
    @ApiOperation(value="用户登录", notes = "用户名密码登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="用户名",dataType = "String",required=true),
            @ApiImplicitParam(name="pwd",value="密码",dataType = "String",required=true),
    })
    public String login(String name, String pwd,  Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
            //打印日志
            log.info(name+","+pwd);
            //获取登录查询的结果
            User result = userServices.login(name, pwd);

            if(result.isSuccess()){

                //将结果存到model里面，用于前端view层展示
                model.addAttribute("result",result);

                HttpSession session = request.getSession();

                session.setAttribute("name", result.getName());
                session.setAttribute("password",result.getPwd());

                System.out.println("登录后的sessionID:"+session.getId());

                //跳转至登录结果页面
                return "/loginResult";
            }else{
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().print("{\"code\":\"0001\",\"msg\":\"用户名或密码错误！\"}");
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }

//    //第二种写法
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ModelAndView login(User user){
//
//        //打印日志
//        log.info(user.getName()+","+user.getPwd());
//
//        //创建一个模型对象
//        ModelAndView mav = new ModelAndView();
//
//        //获得登录查询的结果
//        User result = userServices.login(user);
//
//        //将数据放到ModelAndView对象中
//        mav.addObject("result", result);
//
//        //将跳转页面路径存入mav
//        mav.setViewName("loginResult");
//
//        //返回ModelAndView对象mav
//        return mav;
//
//    }

//    /**
//     * 判断用户的session是否有效（在同一个浏览器中，同一个域中，每次Request请求，都会带上Session）
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/sessionResult",method = RequestMethod.GET)
//    public String isSessionValid( Model model, HttpServletRequest request){
//        boolean sessionValid=request.isRequestedSessionIdValid();
//
//        String sessionId=request.getSession().getId();
//
//        //将结果存到model里面，用于前端view层展示
//        model.addAttribute("sessionValid",sessionValid);
//        model.addAttribute("sessionId",sessionId);
//
//        return "/sessionResult";
//    }
//
//    /**
//     * 注销登录
//     * @param session
//     * @return
//     */
//    @RequestMapping(value = "/loginout",method = RequestMethod.POST)
//    public String logout(HttpSession session){
////        session.removeAttribute(session.getId());
//        session.invalidate();
//        System.out.println("注销后的sessionID："+session.getId());
//        return "/loginNew";
//    }
}
