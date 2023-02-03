package com.example.springboot.bean.dao;



import com.example.springboot.bean.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yy
 * @Date: 2019/12/31 16:37
 * @Version 1.0
 */

@Repository
@Mapper
public interface UserDao {
    /**
     * 查询用户是否存在，若是存在则提示已存在
     *
     * @param name
     * @return
     */

    User findUserByName(String name);

    /**
     * 新增用户
     */
    void register(String name, Integer age, String pwd);

    /**
     * 用户登录--根据用户名和密码查询ID是否存在
     */
    Long login(String name, String pwd);

    /**
     * 根据ID查询用户信息
    */
    User findUserById(Long id);

}
