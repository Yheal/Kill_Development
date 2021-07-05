package com.kill_rear.controller.init;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.service.MyService;
import com.kill_rear.service.twoplayers.GameInit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GameInitController implements MyService{
    @Autowired
    GameInit initModeTwo;

    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        
        switch((String)dataObj.get("pattern")){
            case "two":
                initModeTwo.handleMessage(username, dataObj);
                break;
            default:
                System.out.println("暂时不支持其他的模式！ERROR FROM controller.init.gameInitController");
        }
    }



}
