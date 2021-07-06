package com.kill_rear.skill.util;


// 技能处理阶段
public enum SkillHandleStage {
    BEFORE(0),
    IN(1),
    AFTER(2);

    private int tag;
    
    public int getTag() { return tag; }
    
    SkillHandleStage(int i){ this.tag = i; }
}
