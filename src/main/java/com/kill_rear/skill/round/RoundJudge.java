package com.kill_rear.skill.round;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;


public class RoundJudge extends CommonSkill {
    

    public RoundJudge(GameRunner runner) {
        super("RoundJudge", runner);
    }

    @Override
    public void beforeEffect(SkillRunTime myself) {
        JSONObject dataObj = new JSONObject();
        dataObj.put("data", "start");
        runner.broadcast(myself, dataObj);
    }

    @Override
    public void inEffect(SkillRunTime myself) {

        OperationPanel curPanel = runner.ops[runner.curPlayer];
        if(curPanel.judge.isEmpty()){
            myself.skillHandleStage.setAfterEffectState();
           return;
        }
        SkillRunTime judgeTarget =  curPanel.judge.pop();
        runner.launchNewSkillRunTime(judgeTarget);
    } 


}
