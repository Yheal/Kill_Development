package com.kill_rear.gamebo.game.operate;

public enum ChooseState {
    UNSELECTABLEANDHIDE(0),
    UNSELECTABLEB(1),
    NOTCHOOSE(2),
    CHOOSE(3);

    private int tag;

    ChooseState(int tag) {
        this.tag = tag;
    }
    public int getTag() {
        return tag;
    }
}
