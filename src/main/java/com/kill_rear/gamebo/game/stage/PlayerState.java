package com.kill_rear.gamebo.game.stage;


public enum PlayerState {
    
    COMMON(0),           // 正常
    TURNBEHIND(1),       // 翻面
    DEAD(2),             // 死亡
    ROBOT(3);            // 托管

    private int tag;

    PlayerState(int t) { this.tag = t;}
     
    public int getTag() { return tag; }
}
