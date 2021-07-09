package com.kill_rear.skill.util;

// 技能类型
public enum SkillType {
    
    POSITIVE(0),
    PASSIVE(1),
    CONTORLL(2);
    
    private int tag;
    public void setTag(int tag) { this.tag = tag; }
    SkillType(int tag) {
        this.tag = tag;
    }
}
