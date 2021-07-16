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

    // -效果执行完毕之后
    public void afterEffect(SkillRunTime myself) { myself.result = "ok"; }

    // 注意，很之前的状态有关，也就是和启动自己的技能有关

    public CommonSkill init(){ return this; }

    public boolean isActivatable(SkillRunTime skillRunTime) { return false; }

    public abstract void launchMySelf(SkillRunTime myself) throws RunningException;
    public abstract void destory();
    public abstract String getName();
    public abstract boolean isNeedCheck();
    public abstract SkillType getSkillType();
    public abstract boolean modifyActivatedSkill(SkillRunTime skillRunTime);
    public abstract void setGameObjSelectable(SkillRunTime previous, int target) throws RunningException;

    // 接受上层的结果
    public abstract boolean acceptResult(SkillRunTime myself ,SkillRunTime previous);
    // 执行效果
    public abstract void execute(SkillRunTime myself) throws RunningException;
    // 效果执行结束后
    public abstract void end(SkillRunTime myself) throws RunningException;
    // 接受玩家的输入
    public abstract void acceptInput(SkillRunTime myself,Input input) throws RunningException;

}
