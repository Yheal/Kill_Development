package com.kill_rear.skill.util;


// 技能处理阶段
public class SkillHandleState {

    // 0是BEFOREEFFECT
    // 1是INEFFECT
    // 2是AFTEREFFECT
    private int tag;
    
    public SkillHandleState() {
        tag = 0;              
    }
    public void reset() {tag = 0;}

    public void setBeforeEffectState() { tag = 0; }
    public void setInEffectState() {tag = 1;}
    public void setAfterEffectState() {tag = 2;}

    public boolean isBeforeEffectState() { return tag == 0; }
    public boolean isInEffectState() {return tag == 1;}
    public boolean isAfterEffectState() {return tag == 2;}
    
}
