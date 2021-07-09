package com.kill_rear.skill.round;

import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillType;


public class RoundJudge extends CommonSkill {
    

    public RoundJudge(GameRunner runner) {
        super("RoundJudge", SkillType.CONTORLL, runner);
    }

    @Override
    public void beforeEffect(SkillRunTime myself) {
        OperationPanel curPanel = runner.ops[runner.curPlayer];
        if(curPanel.judge.isEmpty()) {
           myself.skillHandleStage.setAfterEffectState();
           return;
        }
        SkillRunTime judgeTarget =  curPanel.judge.pop();
        myself.skillHandleStage.setAcceptState();

        runner.broadcast(myself, judgeTarget.skill.getName());  // 传递数据
        runner.launchNewSkillRunTime(judgeTarget);
        
    }

    @Override
    public void acceptResult(SkillRunTime myself, SkillRunTime previous) {
        myself.skillHandleStage.setBeforeEffectState();
    }

}
