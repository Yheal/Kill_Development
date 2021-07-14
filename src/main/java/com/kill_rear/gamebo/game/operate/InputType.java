package com.kill_rear.gamebo.game.operate;


// 共有四种输入类型
public enum InputType {

    /*
    PLAYER(),        // 玩家，值是玩家标号
    GENERALSKILL(),  // 武将技能，值是技能id
    HANDCARD(2),     // 手牌
    EQUIPMENT(3),    // 装备区
    CARD(),          // 非以上两种类型的牌, 牌的类型的值全为整数，也就是编号
    BUTTON(),        // 按钮，有几种类型
    */
    
    PLAYER(0),
    GENERALSKILL(1),
    HANDCARD(2),
    EQUIPMENT(3),
    CARD(4),           // 非以上两种的牌
    BUTTON(5),
    ACK(6),
    NON(7);           // 空

    private int type;
    InputType(int t) { this.type = t; }
    
    public int getType() { return this.type; }


}
