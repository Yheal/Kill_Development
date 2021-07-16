package com.kill_rear.skill.round;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillType;


public class RoundGetcard extends CommonSkill{
    
    private static String effect = "getCard";
        
    public String getEffect() { return effect;}

    public RoundGetcard(GameRunner runner) { super(runner); }
    
    @Override
    public void acceptInput(SkillRunTime myself, Input input) throws RunningException {
        runner.setiThAck(input.player);
    }

    @Override
    public void destory() {
        runner = null;        
    }

    @Override
    public String getName() { return "RoundGetCard"; }

    @Override
    public SkillType getSkillType() { return SkillType.CONTORLL;}

    @Override
    public boolean isNeedCheck() { return true; }

    @Override
    public void launchMySelf(SkillRunTime myself) { 
        myself.accepters.add(myself.sender);
        runner.broadcast(myself, "start");  
    }

    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {
        return false;
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) { return false;}

    @Override
    public void execute(SkillRunTime myself) throws RunningException {
        OperationPanel cur = runner.ops[runner.curPlayer];
        ArrayList<Card> gets = new ArrayList<>();
        int n = 2;
        while(n > 0) {
            Card tmp = runner.getOneCardFromCardPile();
            cur.handCards.add(tmp);
            gets.add(tmp);
        }
        
        /* 向玩家发送数据 */
        JSONObject data1 = new JSONObject();
        data1.put("data", gets);
        JSONObject data2 = new JSONObject();
        data2.put("data", 2);

        myself.skillHandleStage.setAfterExecute(); // 摸牌不需要接受其他技能
        runner.sendSeparateData(myself.sender, data1, data2, myself);
    }

    @Override
    public void setGameObjSelectable(SkillRunTime previous, int target) throws RunningException {
        // nothing
    }

    @Override
    public void end(SkillRunTime myself) throws RunningException {
        // nothing
    }

}
