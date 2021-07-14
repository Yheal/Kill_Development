package com.kill_rear.skill.round;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillType;


// 回合技能始终有效，也就是不存在把回合技能撤销的情况，除非游戏结束
public class Round extends CommonSkill{

    private static String effect = "";

    // 分别对应于回合前的状态检查、六个阶段，共7个
    public int[] stages = null;
    int next;

    public static String skillResult() {  return effect; }
    
    public Round(GameRunner gameRunner) {
        super(gameRunner);
        stages = new int[7];
        for(int i=0;i<=6;i++) {
            stages[i] = 1;
        }
        next =  0; 
    }

    private void turnToNext(SkillRunTime myself) {
        sendMessage(myself, "end");
        for(int i=0;i<6;i++)
            stages[i] = 1;
        runner.curPlayer = (runner.curPlayer + 1) % runner.playersName.size();
        next = 0;
    }

    @Override
    public boolean acceptResult(SkillRunTime myself ,SkillRunTime previous)
    {
        // 回合技能永远不会结束
        myself.skillHandleStage.setExecute();
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
        return false;
    }
    
    @Override
    public void acceptInput(SkillRunTime myself, Input input) throws RunningException {
        // nothing    
    }

    @Override
    public void launchMySelf(SkillRunTime myself) throws RunningException{}

    @Override
    public void destory() {
        runner = null;
        stages = null;
    }
    
    @Override
    public String getName() { return "Round"; }

    @Override
    public SkillType getSkillType() { return SkillType.CONTORLL; }

    @Override
    public boolean isNeedCheck() { return false;}

    public void sendMessage(SkillRunTime skillRunTime, String data) {
        
        JSONObject dataObj = new JSONObject();
        dataObj.put("data", data);
        runner.broadcast(skillRunTime, dataObj);
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) { return false;}

    @Override
    public void execute(SkillRunTime myself) throws RunningException {
        int curPlayer = myself.sender;
        OperationPanel op =  runner.ops[curPlayer];
        myself.skillHandleStage.setAccept();  

        // 执行一个个回合的阶段
        switch(next) {

            case 0:
                if(next == 0 && op.state.name().equals("DEAD")) {
                    runner.curPlayer = (curPlayer + 1) % runner.playersName.size();
                    return;
                } else 
                    next = 1;

            case 1:
                if(stages[next] == 1){
                    // 可以执行准备阶段
                    runner.launchNewSkill("RoundPrepare", curPlayer, curPlayer);    
                } else
                    next++;
                break;
        
            case 2:

                if(stages[next] == 1){
                    runner.launchNewSkill("RoundJudge", curPlayer, curPlayer);    
                } else
                    next++;
                break;

            case 3:
                if(stages[next] == 1){
                    runner.launchNewSkill("RoundGetCard", curPlayer, curPlayer);    
                } else
                    next++;
                break;
    
            case 4:
                if(stages[next] == 1) {
                    runner.launchNewSkill("RoundPlay", curPlayer, curPlayer);    
                } else
                    next++;
                    break;
    
            case 5:
                if(stages[next] == 1){
                    runner.launchNewSkill("RoundDiscard", curPlayer, curPlayer);    
                } else
                    next++;
                break;
            
            case 6:
                if(stages[next] == 1) {
                    runner.launchNewSkill("RoundEnd", curPlayer, curPlayer);    
                } else 
                    // 不可以执行回合结束阶段，那么说明直接到下一位玩家
                    turnToNext(myself);
                break;
                
            }
    }


}
