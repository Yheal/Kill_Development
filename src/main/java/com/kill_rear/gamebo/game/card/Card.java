package com.kill_rear.gamebo.game.card;

import com.kill_rear.skill.CommonSkill;

// 牌数据结构
public class Card {

    public int num;                // 编号
    public CardColor card_color;   // 卡牌颜色
    public String card_point;         // 卡牌点数
    public CommonSkill skill;           // 技能名
    public boolean selectAble;         // 卡牌是否选中
    public boolean choosen;
    // 前端凭借这些字段就可以画出对应的字段

    public Card(int n, CardColor cc, String cp, CommonSkill skill) {
        this.num = n;
        this.card_color = cc;
        this.card_point = cp;
        this.skill = skill;
        this.selectAble = false;
        this.choosen = false;
    }
    public Card() {}
    
    public void reset() {
        skill.init();
        selectAble = false;
        choosen = false;        
    }
}
