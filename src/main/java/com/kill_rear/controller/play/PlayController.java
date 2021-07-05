package com.kill_rear.controller.play;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.service.MyService;
import com.kill_rear.service.twoplayers.RunTimeForTwo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayController implements MyService{

    @Autowired
    RunTimeForTwo runTimeForTwo;

    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        
        String pattern = dataObj.getString("pattern");
        switch(pattern) {
            case "two":
                runTimeForTwo.handleMessage(username, dataObj);
                break;
            default:
                System.out.println("暂时不支持其他模式");
        }
    }
    
}
