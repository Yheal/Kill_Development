package com.kill_rear.controller.ajax;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.ResponseData;
import com.kill_rear.service.ajax.EntryInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class UserController {
    
    @Autowired
    EntryInterface entrys;

    @RequestMapping(value="login", method=RequestMethod.POST)
    public ResponseData doLogin(@RequestBody JSONObject jsonParam, HttpServletRequest req){
        String username = jsonParam.getString("username");
        String password = jsonParam.getString("password");
        ResponseData rd =  entrys.userAuthentic(username, password);
        if(rd.getCode() == 20000 ) {
            req.getSession().setAttribute("username", username);
        }
        return rd;
    }
}
