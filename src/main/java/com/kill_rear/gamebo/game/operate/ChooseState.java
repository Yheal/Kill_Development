package com.kill_rear.gamebo.game.operate;

public enum ChooseState {
    UNSELECTABLE(0),
    NOTCHOOSE(1),
    CHOOSE(2);

    private int tag;

    ChooseState(int tag) {
        this.tag = tag;
    }
}
