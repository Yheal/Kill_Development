package com.kill_rear.skill;

import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.gamebo.room.RoomDataTwo;


// 通用技能类，所有技能必须继承这个抽象类
public abstract class CommonSkill {
    
    private GameRunner gameRunner = null;
    private RoomDataTwo roomData = null;

    public abstract void readyLaunch(int roomId);
    public abstract void checkInput(int RoomId, Input input);
    public abstract void launch(int roomId);

    public GameRunner getGameRunner() {
        return gameRunner;
    }
    public void setGameRunner(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
    }
    public RoomDataTwo getRoomData() {
        return roomData;
    }
    public void setRoomData(RoomDataTwo roomData) {
        this.roomData = roomData;
    }
}
