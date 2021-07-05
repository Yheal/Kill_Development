package com.kill_rear.gamebo.room;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.Pair;
import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.service.MyService;

// 大类
public class GameRunner implements MyService {
    
    // 一大堆的技能类
    private int roomId;
    private RoomDataTwo gameData = null;
    // skillHandleStack = new Stack<ArrayList<Input>>(); 
    // 技能处理栈, 应该是skill对象，和对应的

    public int getRoomId() { return roomId; }

    public void setRoomId(int roomId) { this.roomId = roomId; }

    public RoomDataTwo getGameData() { return gameData; }

    public void setGameData(RoomDataTwo gameData) { this.gameData = gameData; }

    public GameRunner(int Id, ArrayList<Pair<String, Integer>> players, EditionType ed) {
        this.roomId = Id;
        this.gameData = new RoomDataTwo(players, ed);

    }

    public void loadSkill() {

    }

    // 与游戏相关的核心逻辑函数

    public void start() {
        // 启动游戏进程
    }

    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        // TODO Auto-generated method stub
        
    }
}
