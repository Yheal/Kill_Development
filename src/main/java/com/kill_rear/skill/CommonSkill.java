package com.kill_rear.skill;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.util.SkillType;



// 通用技能类，所有技能必须继承这个抽象类
public abstract class CommonSkill {
    
    public GameRunner runner = null;


    public CommonSkill(GameRunner runner) {
        this.runner = runner;
    }

    // 接受输入
    public void acceptInput(SkillRunTime myself,Input input) throws RunningException {}

    // -接受输入之后
    public void afterEffect(SkillRunTime myself) { myself.result = "ok"; }

    // 设置前端可以被响应的对象
    public void setGameObjSelectable(int target){}

    public CommonSkill init(){ return this; }

    public boolean isActivatable(SkillRunTime skillRunTime) { return false; }

    public abstract void launchMySelf(SkillRunTime myself) throws RunningException;
    public abstract void destory();
    public abstract String getName();
    public abstract boolean isNeedCheck();
    public abstract SkillType getSkillType();
    public abstract boolean acceptResult(SkillRunTime myself ,SkillRunTime previous);
    public abstract boolean modifyActivatedSkill(SkillRunTime skillRunTime);
}
