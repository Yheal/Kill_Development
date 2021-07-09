package com.kill_rear.skill.round;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillType;


// 回合技能始终有效，也就是不存在把回合技能撤销的情况，除非游戏结束
public class Round extends CommonSkill{

    private static String effect = "";
    // 分别对应于回合前的状态检查、六个阶段，共7个
    public int[] stages = null;
    int next;

    public static String skillResult() {  return effect; }
    
    public Round(GameRunner gameRunner) {
        super("Round", SkillType.CONTORLL, gameRunner);
        stages = new int[7];
        for(int i=0;i<=6;i++) {
            stages[i] = 1;
        }
        next =  0; 
    }

    public void turnToNext(SkillRunTime myself) {
        sendMessage(myself, "end");
        for(int i=0;i<6;i++)
            stages[i] = 1;
        runner.curPlayer = (runner.curPlayer + 1) % runner.playersName.size();
        next = 0;
    }

    @Override
    public void beforeEffect(SkillRunTime myself) {
        
        // 执行一个个的回合阶段
        int cur = runner.curPlayer;
        myself.skillHandleStage.setAcceptState();  // 
        switch(next) {
            case 0:
                // 状态预检 
                OperationPanel op =  runner.ops[cur];
                if(op.state.name() == "DEAD") {
                    sendMessage(myself, "jump");
                    runner.curPlayer = (cur + 1) % runner.playersName.size();
                    return;
                }
                next = 1;
            
            case 1:
                if(stages[next] == 1){
                    // 可以执行准备阶段
                    sendMessage(myself, "start");
                    runner.launchNewSkill("RoundPrepare", cur, cur);    
                } else
                    next++;
                break;
        
            case 2:

                if(stages[next] == 1){
                    sendMessage(myself, "judge");
                    runner.launchNewSkill("RoundJudge", cur, cur);    
                } else
                    next++;
                break;

            case 3:
                if(stages[next] == 1){
                    sendMessage(myself, "getCard");
                    runner.launchNewSkill("RoundGetCard", cur, cur);    
                } else
                    next++;
                break;
        
            case 4:
                if(stages[next] == 1) {
                    sendMessage(myself, "action");
                    runner.launchNewSkill("RoundPlay", cur, cur);    
                } else
                    next++;
                    break;
        
            case 5:
                if(stages[next] == 1){
                    sendMessage(myself, "discard");
                    runner.launchNewSkill("RoundDiscard", cur, cur);    
                } else
                    next++;
                break;
                
            case 6:
                if(stages[next] == 1) {
                    sendMessage(myself, "end");
                    runner.launchNewSkill("RoundEnd", cur, cur);    
                } else 
                    // 不可以执行回合结束阶段，那么说明直接到下一位玩家
                    turnToNext(myself);
                break;
                    
            }
        
    }

    @Override
    public void acceptResult(SkillRunTime myself ,SkillRunTime previous)
    {
               // 回合技能永远不会结束
               myself.skillHandleStage.setBeforeEffectState();
               switch(previous.skill.getName()) {
                   case "RoundPrepare":
                   case "RoundJudge"  :
                   case "RoundGetCard":
                   case "RoundAction" :
                   case "RoundDisCard":
                                       next++;
                                       break;
                   case "RoundEnd":
                                       turnToNext(myself);
               }
    }

    public void sendMessage(SkillRunTime skillRunTime, String data) {
        
        JSONObject dataObj = new JSONObject();
        dataObj.put("data", data);
        runner.broadcast(skillRunTime, dataObj);
    }
    
}
