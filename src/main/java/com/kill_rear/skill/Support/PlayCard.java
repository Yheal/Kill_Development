package com.kill_rear.skill.Support;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillType;

public class PlayCard extends CommonSkill{

    public Card cardToUse;

    public PlayCard(GameRunner runner) {
        super(runner);
    }

    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {
        return true;
    }

    public void replaceEquipment(int i, SkillRunTime myself) throws RunningException {

        int sender = myself.sender;
        OperationPanel op = runner.ops[sender];

        if(op.equipment[i] != null) {

            SkillRunTime runTime = runner.getNewSkillRunTime("DisCard", sender, sender);
            DisCard disCard = (DisCard)runTime.skill;
            disCard.cards.add(op.equipment[i]);
            
        }
        op.equipment[i] = cardToUse;

    }

    @Override
    public void destory() {
        cardToUse = null;
        runner = null;
    }

    @Override
    public void execute(SkillRunTime myself) throws RunningException {
        // 如果我们这个字段没有被屏蔽，那么执行cardToUse的效果，也就是new一个信息的skillRunTime出来
        // 事实上，不需要new一个新的，直接执行即可。
        myself.skill = cardToUse.skill.init();
        myself.skill.execute(myself);
    }

    @Override
    public String getName() { return "PlayCard";}

    @Override
    public SkillType getSkillType() { return SkillType.SUPPORT;}

    @Override
    public boolean isNeedCheck() { return true;}

    @Override
    public void launchMySelf(SkillRunTime myself) throws RunningException {
        // 如果打出的是装备牌，那么我们需要替换启动打牌玩家的装备区，然后不执行我们的效果
        // 如果是延迟牌，那么需要分情况，如果已经在判定区里，那么执行它功能，否则不执行，创建一个新的SkillDealay
        // 其余的不需要

        int divide = -1;  // 分区
        switch(myself.skill.getSkillType()) {   
            case WEAPON:
                divide = 0;
                replaceEquipment(0, myself);
                break;
            case DEFENDER:
                divide = 0;
                replaceEquipment(1, myself);
                break;
            case ATTACKHORSE:
                divide = 0;
                replaceEquipment(2, myself);
                break;
            case DEFENDERHORSE:
                divide = 0;
                replaceEquipment(3, myself);
                break;
            case DELAYTIPS:
                divide = 1; 
                break;
            default:
                divide = 2;
        }

        // 如果不是手牌，也就是判定牌或者装备牌，那么不允许执行其功能
        if(divide != 2) 
            myself.mask[0] = 1;
        
        JSONObject dataObj = new JSONObject();
        dataObj.put("divide", divide);
        dataObj.put("card", cardToUse);
        runner.broadcast(myself, dataObj);
    }

    public void launchDelayTip(SkillRunTime myself, Card delayCard) {
        // 启动延时技能, 针对对象和引发对象已经准备好了
        cardToUse = delayCard;
        JSONObject dataObj = new JSONObject();
        dataObj.put("divide", 1);                 // 判定区，分区为1
        dataObj.put("card", cardToUse);
        runner.broadcast(myself, dataObj);
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) {
        return false;
    }

    public void init(SkillRunTime myself, Card card) {
        this.cardToUse = card;
    }


}
