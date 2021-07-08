package com.kill_rear.skill.round;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;


public class RoundPrepare extends CommonSkill {

    public RoundPrepare(GameRunner gameRunner) {
        super("RoundPrepare" ,gameRunner);
    }

    @Override
    public void inEffect(SkillRunTime myself) {
        JSONObject dataObj = new JSONObject();
        dataObj.put("data", "start");
        runner.broadcast(myself, dataObj);
        myself.skillHandleStage.setAfterEffectState();
    }
}
