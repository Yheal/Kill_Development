package com.kill_rear.skill;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.stage.GameStage;
import com.kill_rear.gamebo.game.stage.PlayerState;
import com.kill_rear.gamebo.room.RoomDataTwo;
import com.kill_rear.service.common.RoomManager;

import org.springframework.beans.factory.annotation.Autowired;

/*
    scope="singleton"，那么该Bean是单例，任何人获取该Bean实例的都为同一个实例；
    scope="prototype"
*/

// 单例模式
public class ChartacterDeadForTwo extends ChartacterDead{

    @Autowired
    RoomManager roomManager;
    
    private static ChartacterDeadForTwo deadSkill = null;

    public static ChartacterDeadForTwo getInstance() {
        if(deadSkill == null)
            deadSkill = new ChartacterDeadForTwo();
        return deadSkill;
    }

    @Override
    public void handleChartacterDeath(int roomId, int playerNumber) throws RunningException {
        /*
        RoomDataTwo roomData = roomManager.getSpecifiedRoom(roomId);
        if(roomData == null) {
            throw new RunningException("room not found");
        }
        
        roomData.setgStage(GameStage.GAMEEND);
        roomData.getOperationPanel(playerNumber).state = PlayerState.DEAD;
        
        JSONObject resData = new JSONObject();
        for(int i=0;i<roomData.getOps().length;i++) {
            if(i != playerNumber ) {
                resData.put("result", "fail");
                roomData.sendMessage(i, resData.toJSONString());
            }else {
                resData.put("result", "win");
                roomData.sendMessage(i, resData.toJSONString());
            }
        }
        */
    }
}
