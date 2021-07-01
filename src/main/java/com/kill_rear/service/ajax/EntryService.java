package com.kill_rear.service.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kill_rear.common.ResponseData;
import com.kill_rear.common.ResponseEnum;
import com.kill_rear.mapper.UserLoginMapper;
import com.kill_rear.dao.User;


@Service
public class EntryService implements EntryInterface{

    @Autowired
    UserLoginMapper authentiation;
    
    public ResponseData userAuthentic(String username, String password){
       User u = authentiation.queryUser(username, password);
       if(u != null) {
           // 登录成功
           return ResponseData.getInstance().data(u).responseEnum(ResponseEnum.LOGIN_SUCCESS);
       }
       return ResponseData.getInstance().responseEnum(ResponseEnum.LOGIN_FAILED);
    }
    public boolean addNewUser(String username, String password) {
        
        int i = authentiation.add(username, password);
        if(i>=0)
            return true;
        return false;
    }
}
