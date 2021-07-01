package com.kill_rear.controller.match;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.service.MyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchController implements MyService{
    
    @Autowired
    TwoPlayerMatch twoPlayerMatch;

    // 匹配服务
    // 提供两个阶段的服务：寻找对手、找到指定人数的玩家后，等待确认
    public void handleMessage(String username, JSONObject dataObj) {
        String pattern = dataObj.getString("pattern");
        switch(pattern) {
            case "two":
                twoPlayerMatch.handleMessage(username, dataObj);
                break;
            default:
                System.out.println("暂时不支持其他模式");
        }
    }
}
