package com.kill_rear.gamebo.game.card;

// 牌数据结构
public class Card {

    int num;                // 编号
    CardColor card_color;   // 卡牌颜色
    String card_point;         // 卡牌点数
    String skill;           // 技能名
    boolean select;         // 卡牌是否可选中
    // 前端凭借这些字段就可以画出对应的字段

    public Card(int n, CardColor cc, String cp, String skillName) {
        num = n;
        card_color = cc;
        card_point = cp;
        skill = skillName;
        select = false;
    }
    public Card() {}
}
