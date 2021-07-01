package com.kill_rear.service.twoplayers;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.service.common.SessionPools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MatchConfirm {

    @Autowired
    SessionPools sessionPools;

    @Autowired
    GameInit gameInit;
    private static int curGroup = 0; 

    // 待确认玩家和对应房间号的映射
    private static ConcurrentHashMap<String, Integer>  allocates= new ConcurrentHashMap<>();
    // 组号和已确定人数的映射
    private static ConcurrentHashMap<Integer, Integer> rooms = new ConcurrentHashMap<>();
    // 匹配组
    private static ConcurrentHashMap<Integer, ArrayList<String>> matchGroup = new ConcurrentHashMap<>(); 

    // 等待用户确认的处理函数
    public void prepare(ArrayList<String> players) {
        int groupNumber = getNextNumber();
        
        JSONObject obj = new JSONObject();
        obj.put("api", "match");
        obj.put("message", "found");
        String message = obj.toJSONString();
        
        rooms.put(groupNumber, 0);
        matchGroup.put(groupNumber, players);

        for(int i=0;i<players.size();i++) {
            allocates.put(players.get(i), groupNumber);
            sessionPools.sendMessage(players.get(i), message);
        }

    }
    
    public int getNextNumber() {
        while(rooms.get(curGroup) != null) {
            curGroup = (curGroup + 1) % 1000000; 
        }
        return curGroup;
    }

    public void solveUserAgree(String username) {
        // 同意
        Integer num = allocates.get(username);
        if(num == null) {
            System.out.println("ERROR! 出现在MatchRoomsTwo.java中");
        } else {
            Integer approves = rooms.get(num);
            if(approves == null) {
                System.out.println("ERROR! 出现在MatchRoomsTwo.java中");
            } else {
                approves++;
                if(approves == 2) {
                    // 进入到游戏的初始化阶段，首先移除之前保留为该组保留的所有数据
                    ArrayList<String> players = matchGroup.get(num);
                    for(String user:players) {
                        allocates.remove(user);                    
                    }
                    rooms.remove(num);
                    matchGroup.remove(num);
                    gameInit.initGame(players);
                }
            }

        }        
    }
    public void solveUserExit(String username) {
        
    }
    
}
