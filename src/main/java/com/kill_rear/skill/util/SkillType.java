package com.kill_rear.skill.util;

// 技能类型
public enum SkillType {
    
    CONTORLL(0),
    SUPPORT(1),
    BASICCARD(2),
    COMMONTIPS(3),
    DELAYTIPS(4),
    WEAPON(5),
    DEFENDER(6),
    ATTACKHORSE(7),
    DEFENDERHORSE(8),
    GENERAL(9);

    private int tag;
    public void setTag(int tag) { this.tag = tag; }
    SkillType(int tag) {
        this.tag = tag;
    }
    public int getTag() {return tag;}
}
