package com.kill_rear.skill.util;


// 技能处理阶段， 当一个技能被加到栈顶的时候，模型按照以下步骤操作
public class SkillHandleStage {

    // 0是BEFOREEFFECT --- M检查是否有其他依赖技能可以在之前发动，技能需要设置可以被响应的技能的为可操作
    // 1是INEFFECT     --  处理用户操作
    // 2是AFTEREFFECT  --  效果被处理之后，M检查是否有其他依赖技能可以发动
    // 3是ACCEPT
    //  调用AFTEREFFECT后，M调用对应技能的 acceptResult();
    private int tag;
    
    public SkillHandleStage() {
        tag = 0;              
    }
    public void reset() {tag = 0;}

    public void setExecuteState() { tag = 0; }
    public void setAfterEffectState() {tag = 2;}
    public void setAcceptState() {tag = 1;}

    public boolean isExecuteEffectState() { return tag == 0; }
    public boolean isAfterEffectState() {return tag == 2;}
    public boolean isAcceptState() {return tag == 1;}

    public int getTag() { return tag;}
}

// 出牌阶段负责出东西
// 玩家在出牌阶段BEFOREEFFECT，按照上面的介绍，会去设置可以被响应的技能为可操作
// 然后进入INEFFECT，
// 出牌阶段，在INEFFECT中接受到HANDCARD类型，那么，调用对于卡片的技能说明，设置下一步可以操作的游戏对象。
// 出牌阶段依然在INEFFECT中，当接受到确认按钮时，new新的SKILLRUNTIME，然后再把当前牌打出去，发出声音和动画，然后同步操作
// 之后，进入到当前所承载的技能的运行时间，首先，在BEFOREEFFECT阶段，M会去检查此刻是否有依赖技能可以发动。
// 如果没有，那么进入生效阶段，此时这个技能会检查可操作对象给目标玩家。
