package com.kill_rear.skill.round;

import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillType;


public class RoundPrepare extends CommonSkill {

    public RoundPrepare(GameRunner gameRunner) {
        super("RoundPrepare", SkillType.CONTORLL, gameRunner);
    }

    @Override
    public void beforeEffect(SkillRunTime myself) {
        myself.skillHandleStage.setAfterEffectState();
    }

}
