package com.kill_rear.skill;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.operate.Input;

// 依然是一个抽象类
public abstract class ChartacterDead extends CommonSkill{
    
    public void readyLaunch(int roomId){
        // nothing
    }
    public void checkInput(int RoomId, Input input) {
        // nothing
    }
    public void launch(int roomId) {
        // nothing
    }

    public abstract void handleChartacterDeath(int roomId, int playerNumber) throws RunningException; 
}
