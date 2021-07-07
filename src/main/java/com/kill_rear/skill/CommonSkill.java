package com.kill_rear.skill;

import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.util.SkillHandleResult;



// 通用技能类，所有技能必须继承这个抽象类
public abstract class CommonSkill {
    
    public GameRunner gameRunner = null;
    
    public CommonSkill(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
    }

    public void beforeEffect(SkillRunTime skillRunTime) {}
    public void inEffect(SkillRunTime skillRunTime) {}
    public void afterEffect(SkillRunTime skillRunTime) {}
    public void acceptResult(SkillRunTime skillRunTime ,SkillHandleResult result){}

}
