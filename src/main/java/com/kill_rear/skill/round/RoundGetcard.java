package com.kill_rear.skill.round;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillHandleResult;

public class RoundGetcard extends CommonSkill{
    
    private static SkillHandleResult result;
    private static String name = "Game";
    private static String action = "getCard";
    

    public static SkillHandleResult skillResult() { 
        return result;
    }
    
    public static String getName() {
        return name;
    }
    
    public RoundGetcard(GameRunner gameRunner) {
        super(gameRunner);
    }
    
    @Override
    public void inEffect(SkillRunTime skillRunTime) {
        OperationPanel cur = gameRunner.ops[gameRunner.curPlayer];
        ArrayList<Card> gets = new ArrayList<>();
        int n = 2;
        while(n > 0) {
            Card tmp = gameRunner.getOneCardFromCardPile();
            cur.handCards.add(tmp);
            gets.add(tmp);
        }
        
        /* 向玩家发送数据 */
        JSONObject dataObj = new JSONObject();
        dataObj.put("action", action);
        dataObj.put("user", gameRunner.curPlayer);
        dataObj.put("data", gets);
        
    }
}
