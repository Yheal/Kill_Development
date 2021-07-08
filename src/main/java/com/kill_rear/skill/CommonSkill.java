package com.kill_rear.skill;

import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.service.twoplayers.GameRunner;



// 通用技能类，所有技能必须继承这个抽象类
public abstract class CommonSkill {
    
    public GameRunner runner = null;
    private String name;

    public CommonSkill(String name, GameRunner runner) {
        this.name = name;
        this.runner = runner;
    }

    public String getName() {
        return name;
    }

    public void beforeEffect(SkillRunTime myself) {
        myself.skillHandleStage.setInEffectState();
    }
    
    public void inEffect(SkillRunTime myself) {
        myself.skillHandleStage.setAfterEffectState();
    }


    public void afterEffect(SkillRunTime myself) {
        myself.result = "ok";
    }

    public void acceptResult(SkillRunTime myself ,SkillRunTime previous){}
    
    public void explainInput(Input input) {}
}
