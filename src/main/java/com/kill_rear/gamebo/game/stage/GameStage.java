package com.kill_rear.gamebo.game.stage;


// 游戏状态
// 选择身份 -> 选择武将 -> 游戏开始阶段 -> 游戏进行时 -> 游戏结束
public enum GameStage {
    
    GAMESTART(0),
    GAMELOOP(1),
    GAMEEND(2);

    private int tag;

    GameStage(int t) { this.tag = t;}
     
    public int getTag() { return tag; }
}
