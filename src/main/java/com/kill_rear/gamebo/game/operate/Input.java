package com.kill_rear.gamebo.game.operate;


public class Input {

    InputType type;  // 见枚举变量
    int value, player;   // 前端传递的值, 输入的玩家
    Object reference;    // 对象

    public InputType getType() { return type; }
    
    public void setType(InputType type) { this.type = type; }
    
    public int getValue() { return value; }
    
    public void setValue(int value) {this.value = value; }
    
    public int getPlayer() { return player; }
    
    public void setPlayer(int player) { this.player = player; }
    
    public Object getReference() { return reference;}

    public void setReference(Object reference) { this.reference = reference; }
 
}
