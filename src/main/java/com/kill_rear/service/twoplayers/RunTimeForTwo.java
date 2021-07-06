package com.kill_rear.service.twoplayers;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.RunningException;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.service.MyService;

import org.springframework.stereotype.Service;



@Service
public class RunTimeForTwo implements MyService {
    
    private HashMap<Integer, GameRunner> running = new HashMap<Integer, GameRunner>();
    

    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        // 处理用户的数据
        
    }

    public void setGameRunner(int roomId, GameRunner gameRunner) throws RunningException {
        if(running.get(roomId) != null) 
            throw new RunningException("错误的设置游戏数据, Error From service.twoplayers.RunTimeForTwo.java");
        running.put(roomId, gameRunner);
    }

    public void removeGameProcess(int roomId) {
        running.remove(roomId);
    }
}
