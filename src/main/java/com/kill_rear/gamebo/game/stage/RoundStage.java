package com.kill_rear.gamebo.game.stage;

// 回合类型
public enum RoundStage {
    
    ROUNDPREPARE(0),
    ROUNDJUDGE(1),
    ROUNDGETCARD(2),
    ROUNDPLAY(3),
    ROUNDDISCARD(4),
    ROUNDEND(5);

    private int tag;

    RoundStage(int t) { tag = t; }

    public int getTag() { return tag;}

}
