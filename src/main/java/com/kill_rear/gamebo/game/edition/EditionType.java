package com.kill_rear.gamebo.game.edition;

public enum EditionType {
    
    STANDARD(0),
    JUNZHEN(1);

    private int tag;

    EditionType(int t) { this.tag = t; }
    public int getTag() { return this.tag; }
}
