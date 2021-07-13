package com.kill_rear.skill.round;

import com.kill_rear.common.util.RunningException;
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
        myself.skillHandleStage.setExecuteState();
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
        // 发给单个人，考虑不使用广播
        myself.accepters.add(myself.sender);
        runner.broadcast(myself, "start");  
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) { return false; }


    @Override
    public void execute(SkillRunTime myself) throws RunningException {
        OperationPanel curPanel = runner.ops[runner.curPlayer];
        if(curPanel.judge.isEmpty()) {
           myself.skillHandleStage.setAfterEffectState();
           return;
        }        
        // 启动延迟出牌技能
        myself.skillHandleStage.setAcceptState();
        SkillDelayRun skillDelayRun = curPanel.judge.get(0);
        curPanel.judge.remove(0);
        runner.launchDelaySkill(skillDelayRun, runner.curPlayer);
        
    }

}
