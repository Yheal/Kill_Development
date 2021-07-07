package com.kill_rear.skill.round;

import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.gamebo.game.stage.RoundStage;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillHandleResult;


public class RoundJudge extends CommonSkill {
    
    private static SkillHandleResult result;
    private static String name = "GameJudge";

    public static SkillHandleResult skillResult() { 
        return result;
    }
    
    public static String getName() {
        return name;
    }

    public RoundJudge(GameRunner gameRunner) {
        super(gameRunner);
    }

    @Override
    public void inEffect(SkillRunTime skillRunTime) {

        OperationPanel curPanel = gameRunner.ops[gameRunner.curPlayer];
        if(curPanel.judge.isEmpty()){
           skillRunTime.state.setAfterEffectState();
           return;
        }
        SkillRunTime judgeTarget =  curPanel.judge.pop();
        gameRunner.launchNewSkillRunTime(judgeTarget);
    } 


    @Override
    public void afterEffect(SkillRunTime skillRunTime) {
        this.gameRunner.rStage = RoundStage.ROUNDGETCARD;
    }

}
