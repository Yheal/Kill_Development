package com.kill_rear.skill.round;

import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.stage.RoundStage;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillHandleResult;



public class RoundPrepare extends CommonSkill{

    private static SkillHandleResult result;
    private static String name = "GamePrepare";

    public static SkillHandleResult skillResult() { 
        return result;
    }
    
    public static String getName() {
        return name;
    }

    public RoundPrepare(GameRunner gameRunner) {
        super(gameRunner);
    }
    
    @Override
    public void afterEffect(SkillRunTime skillRunTime) {
        this.gameRunner.rStage = RoundStage.ROUNDJUDGE;
    }
        
}
