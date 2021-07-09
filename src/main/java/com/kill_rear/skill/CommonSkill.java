package com.kill_rear.skill;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.util.SkillType;



// 通用技能类，所有技能必须继承这个抽象类
public abstract class CommonSkill {
    
    public GameRunner runner = null;
    private String name;
    private SkillType skillType;

    public CommonSkill(String name, SkillType skillType, GameRunner runner) {
        this.name = name;
        this.runner = runner;
        this.skillType = skillType;
    }

    public String getName() {
        return name;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public void beforeEffect(SkillRunTime myself) {}

    // 处理前一个技能的效果
    public void inEffect(SkillRunTime myself,Input input) throws RunningException {}

    public void afterEffect(SkillRunTime myself) {
        myself.result = "ok";
    }
    public void setGameObjSelectable(int target){}

    public void acceptResult(SkillRunTime myself ,SkillRunTime previous){}
    
    public void init() {

    }
}
