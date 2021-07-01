package com.kill_rear.mapper;


import com.kill_rear.dao.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserLoginMapper {
    //查c询
    public List<User> queryAll();
    //添加数据
    public int add(String username, String password);
    //根据用户名查询数据
    public User queryUser(String username, String password);
}
