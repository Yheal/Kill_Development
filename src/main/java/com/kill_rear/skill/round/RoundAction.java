package com.kill_rear.skill.round;

import java.util.List;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillDelayRun;
import com.kill_rear.skill.util.SkillType;

// 核心类之一
public class RoundAction extends CommonSkill {


    private CommonSkill generalSkillPrepareToLaunch = null;
    private Card handCardPrepareToThrow = null;
    private Card equipmentCard = null;

    public RoundAction(String name, GameRunner runner) { 
        super(runner);
    }
    
    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {
        myself.skillHandleStage.setExecuteState();
        return false;
    }

    
    @Override
    public void destory() {
        generalSkillPrepareToLaunch = null;
        handCardPrepareToThrow = null;
        equipmentCard = null;        
    }

    @Override
    public String getName() { return "RoundAction";}

    @Override
    public SkillType getSkillType() { return SkillType.CONTORLL;}

    @Override
    public CommonSkill init() {
        generalSkillPrepareToLaunch = null;
        handCardPrepareToThrow = null;
        equipmentCard = null;

        return this;
    }

    @Override
    public boolean isNeedCheck() {    
        return false;
    }

    public boolean isExitPreparingSkill() {
        return !(this.generalSkillPrepareToLaunch == null && this.handCardPrepareToThrow == null
                    && this.equipmentCard == null);
    }

    /*
        PLAYER(0),
    GENERALSKILL(1),
    HANDCARD(2),
    EQUIPMENT(3),
    CARD(4),           // 非以上两种的牌
    BUTTON(5),
    NON(6);           // 空
     */

    @Override
    public void acceptInput(SkillRunTime myself, Input input) throws RunningException {
        
        OperationPanel op = runner.ops[myself.sender];
        int num = -1;
        // input好像没有什么用，先留着

        switch(input.type.name()) {
            case "PLAYER":
                num = Integer.parseInt(input.value);
                op.playerSelect[num] = true;
                letSkillSetGameObjSelectable(myself.sender);
                break;

            case "GENERALSKILL":
                // 武将是要发动的
                List<CommonSkill> skills = op.general.skills;
                for(int i=0;i<skills.size();i++) {
                    if(skills.get(i).getName().equals(input.value)) {
                        this.generalSkillPrepareToLaunch = skills.get(i);
                        op.general.skillSelectAble.set(i, true);
                    }
                }
                if(this.generalSkillPrepareToLaunch == null)
                    throw(new RunningException("找不到技能引用"));
                
                letSkillSetGameObjSelectable(myself.sender);
                break;

            case "HANDCARD":
                // 检查是否在一些技能已经存在的情况下，点击了手牌，因为有些技能需要丢弃一些牌才能发动
                Card target = null;
                num = Integer.parseInt(input.value);
                for(Card card:op.handCards) {
                    if(card.num == num) {
                        card.selectAble = true;
                        target = card;
                    }
                }
                if(target == null)
                    throw(new RunningException("找不到手牌"));
                
                if(generalSkillPrepareToLaunch != null || equipmentCard !=null) {
                    // nothing
                } else if(handCardPrepareToThrow != null) {
                    handCardPrepareToThrow.selectAble = false;
                    handCardPrepareToThrow = target;
                } else{
                    handCardPrepareToThrow = target;
                }
            
                letSkillSetGameObjSelectable(myself.sender);
                break;

            case "EQUIPMENT":
                // 检查发动条件
                if(isExitPreparingSkill() == false)
                    throw(new RunningException("错误的控制"));
                num = Integer.parseInt(input.value);
                for(Card card:op.equipment) {
                    if(card.num == num) {
                        this.equipmentCard = card;
                        this.equipmentCard.selectAble = true;
                    }
                }
                if(this.equipmentCard == null)
                    throw(new RunningException("找不到技能的引用"));
                letSkillSetGameObjSelectable(myself.sender);
                break;

            case "CARD":
                // 绝对无效点击
                throw new RunningException("无效的输入");
            case "BUTTON":
                
                // 检查类型，分为三个，确定和取消或者是回合结束
                switch(input.value) {
                    case "confrim":
                        // 启动当前技能
                        launchCurrentSkill(myself);
                        break;
                    case "cancel":
                        // 取消准备启动技能过程, 重新设置可以使用的对象
                        execute(myself);
                        break; 
                    case "end":
                        // 结束回合
                        myself.skillHandleStage.setAfterEffectState();
                        break;
                    default:
                        throw new RunningException("不认识的按钮");
                }
            case "NON":
                myself.skillHandleStage.setAfterEffectState();
                return;
                // 回合结束
        }
    }
    

    private void letSkillSetGameObjSelectable(int target) throws RunningException {

        if(generalSkillPrepareToLaunch != null) {
            generalSkillPrepareToLaunch.setGameObjSelectable(target);
        }else if(handCardPrepareToThrow != null) {
            handCardPrepareToThrow.skill.setGameObjSelectable(target);
        }else if(equipmentCard != null){
            equipmentCard.skill.setGameObjSelectable(target);
        } 
    }

    private void launchCurrentSkill(SkillRunTime myself) throws RunningException {

        // 设置接受态
        myself.skillHandleStage.setAcceptState();
        if(this.handCardPrepareToThrow != null) {
            // 手牌，启动需要先触发丢牌动作，再执行效果，其它的不需要
            Card tmp = this.handCardPrepareToThrow;
            this.handCardPrepareToThrow = null;
            runner.playCard(myself.sender, tmp);
        } else if(this.generalSkillPrepareToLaunch != null){
            runner.launchNewSkill(this.generalSkillPrepareToLaunch.getName(), myself.sender);
        } else if(this.equipmentCard != null){
            runner.launchNewSkill(this.equipmentCard.skill.getName(), myself.sender);
        } else {
            throw new RunningException("找不到技能引用");
        }
    }

    @Override
    public void launchMySelf(SkillRunTime myself) {
        
        myself.accepters.add(myself.sender);
        runner.broadcast(myself, "start");
        
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) { return false;}


    @Override
    public void execute(SkillRunTime myself) throws RunningException {

        // 没有启动技能前，禁止选中其他玩家aaa
        generalSkillPrepareToLaunch = null;
        handCardPrepareToThrow = null;
        equipmentCard = null;

        OperationPanel op = runner.ops[myself.sender];

        for(int i=0;i<op.playerSelect.length;i++)
            op.playerSelect[i] = false;

        
        for(int i=0;i < op.handCards.size();i++) {
            
            Card card = op.handCards.get(i);

            switch(card.skill.getName()) {
                case "Shan":
                    card.selectAble = false;
                    break;

                case "Tao":
                    if(op.blood == op.maxBlood)
                        card.selectAble = false;
                    else
                        card.selectAble = true;
                    break;
                
                case "Sha":
                    if(op.shaNumbers <= 0)
                        card.selectAble = false;
                    else
                        card.selectAble = true;

                case "WuXiekeJi":
                    card.selectAble = false;
                    break;
                
                case "ShanDian":
                    boolean has = false;
                    for(SkillDelayRun skillDelayRun:op.judge) {
                        if(skillDelayRun.card != null && skillDelayRun.skill.getName() == "ShanDian") 
                            has = true;
                    }
                    card.selectAble = !has;
                    break;

                default:
                    card.selectAble = true;
            } 
        }
        
        op.equipment[0].selectAble = true;
        for(int i=1;i<4;i++) 
            op.equipment[i].selectAble = false;

        General general = op.general;
        for(int i=0;i<general.skills.size();i++) {
            if(general.skills.get(i).isActivatable(myself)) 
                op.general.skillSelectAble.set(i, true);
            else
                op.general.skillSelectAble.set(i, false);
        }
        
        // 按钮，第一个按钮，不可选中，第二个内容是游戏结束。
        op.buttons[0].color = "#212121";
        op.buttons[0].content = "出牌阶段";
        op.buttons[0].selectAble = false;
        op.buttons[0].hide = false;

        op.buttons[1].color = "#F9A825";
        op.buttons[1].content = "结束回合";
        op.buttons[1].selectAble = true;
        op.buttons[1].hide = false;

        op.buttons[2].hide = true;
        
    }



}
