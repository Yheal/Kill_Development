package com.kill_rear.skill.round;

import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillType;


public class RoundEnd extends CommonSkill{

    public RoundEnd(GameRunner runner) {
        super("RoundEnd", SkillType.CONTORLL, runner);
    }
        
}
