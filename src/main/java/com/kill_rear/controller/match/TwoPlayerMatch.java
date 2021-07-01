package com.kill_rear.controller.match;


import java.util.ArrayList;


import com.alibaba.fastjson.JSONObject;
import com.kill_rear.service.twoplayers.MatchSeek;
import com.kill_rear.service.MyService;
import com.kill_rear.service.twoplayers.MatchConfirm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TwoPlayerMatch implements MyService{

    @Autowired
    MatchSeek matchSeek;

    @Autowired
    MatchConfirm matchConfirm;

    
    public void handleMessage(String username, JSONObject dataObj) {
        String stage = dataObj.getString("stage");

        if(stage.equals("seek")) {
            solveSeek(username, dataObj);
        } else if(stage.equals("found")){
            solveConfirm(username, dataObj);
        }
    }

    public synchronized void solveSeek(String username, JSONObject dataObj) {
        
        String mes = dataObj.getString("message");
        if(mes.equals("cancel")){
            // 注意：并发条件下，某个用户可能被组合到一个游戏中，我们需要调用MatchRooms取消那个游戏
            // 如果成功，那么就不需要在把该会话从waitMatch 删除，否则需要删除
        } else{
            // 有时间再改一下，让这个类完全成为控制类
           matchSeek.add(username);
           matchSeek.launch(username);
        }
        
    }

    public void solveConfirm(String username, JSONObject dataObj) {
        
        String mes = dataObj.getString("message");
        // 都需要调用MatchRooms的接口
        if(mes.equals("cancel")){
            // TO Code
            matchConfirm.solveUserExit(username);
        } else{
            matchConfirm.solveUserAgree(username);
        }
    }
}
