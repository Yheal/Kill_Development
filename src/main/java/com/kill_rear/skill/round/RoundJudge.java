package com.kill_rear.skill.round;

import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillDelayRun;
import com.kill_rear.skill.util.SkillType;


public class RoundJudge extends CommonSkill {
    

    public RoundJudge(GameRunner runner) {
        super(runner);
    }


    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {
        myself.skillHandleStage.setLaunchState();
        return false;
    }

    @Override
    public void destory() { runner = null; }

    @Override
    public String getName() { return "RoundJudge"; }

    @Override
    public SkillType getSkillType() { return SkillType.CONTORLL; }

    @Override
    public boolean isNeedCheck() { return false; }

    @Override
    public void launchMySelf(SkillRunTime myself) {
        OperationPanel curPanel = runner.ops[runner.curPlayer];
        if(curPanel.judge.isEmpty()) {
           myself.skillHandleStage.setAfterEffectState();
           return;
        }

        SkillDelayRun judgeTarget =  curPanel.judge.pop();
        myself.skillHandleStage.setAcceptState();
        
    }


    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) { return false; }

}
