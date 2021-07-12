package com.kill_rear.skill.round;

import java.util.List;


import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
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
        myself.skillHandleStage.setLaunchState();
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
    public boolean isNeedCheck() {     // TODO Auto-generated method stub
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
        
        // input好像没有什么用，先留着

        switch(input.type.name()) {
            case "PLAYER":
                letSkillSetGameObjSelectable(myself.sender);
                break;

            case "GENERALSKILL":
                // 一定是要发动的
                for(CommonSkill skill:op.general.skills) {
                    if(skill.getName().equals(input.value)) {
                        this.generalSkillPrepareToLaunch = skill;
                    }
                }
                if(this.generalSkillPrepareToLaunch == null)
                    throw(new RunningException("找不到技能引用"));
                letSkillSetGameObjSelectable(myself.sender);
                break;

            case "HANDCARD":
                // 检查是否在一些技能已经存在的情况下，点击了手牌，因为有些技能需要丢弃一些牌才能发动
                if(isExitPreparingSkill() == false) {
                    int num = Integer.parseInt(input.value);
                    for(Card card:op.handCards) {
                        if(card.num == num) {
                            this.handCardPrepareToThrow = card;
                        }
                    }
                    if(this.handCardPrepareToThrow == null)
                        throw(new RunningException("找不到手牌"));

                } else {
                    // 与其他技能有关, 设置该目标已经被选择。
                } 
                letSkillSetGameObjSelectable(myself.sender);
                break;

            case "EQUIPMENT":
                // 检查发动条件
                if(isExitPreparingSkill() == false)
                    throw(new RunningException("错误的控制"));
                int num = Integer.parseInt(input.value);
                for(Card card:op.equipment) {
                    if(card.num == num) {
                        this.equipmentCard = card;
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
                        launchNewSkill(myself);
                        break;
                    case "cancel":
                        // 取消准备启动技能过程, 重新设置可以使用的对象
                        launchMySelf(myself); 
                        break; 
                    case "end":
                        // 结束回合
                        myself.skillHandleStage.setAcceptState();
                        break;
                    default:
                        throw new RunningException("不认识的按钮");
                }
            case "NON":
                myself.skillHandleStage.setAfterEffectState();
                return;
                // 回合结束
        }
        myself.inputStorage.add(input);     // 这个是通用的操作
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

    private void launchNewSkill(SkillRunTime myself) throws RunningException {

        if(this.handCardPrepareToThrow != null) {
            // 手牌，启动需要先触发丢牌动作，再执行效果，其它的不需要
        } else if(this.generalSkillPrepareToLaunch != null){

        } else if(this.equipmentCard != null){

        } else {
            throw new RunningException("找不到技能引用");
        }
    }

    @Override
    public void launchMySelf(SkillRunTime myself) {
         // 设置当前玩家所有可操作对象
         setGameObjSelectable(myself.sender);        
         myself.skillHandleStage.setLaunchState();      
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) { return false;}

    public void setGameObjSelectable(int target){
        // 没有启动技能前，禁止选中其他玩家aaa
        OperationPanel op = runner.ops[target];

        for(int i=0;i<op.playerSelect.length;i++)
            op.playerSelect[i] = false;

        
        for(int i=0;i < op.handCards.size();i++) {
            Card card = op.handCards.get(i);
            if(card.skill.getSkillType().compareTo(SkillType.POSITIVE) == 0) {
                
                // 一回合只能输入一张杀
                if(card.skill.getName() != "Sha") {
                    card.selectAble = true;
                    continue;
                }                
                if(op.shaNumbers > 0) 
                    card.selectAble = true;
                else
                    card.selectAble = false;
            }
            else
                card.selectAble = false;
        }
        
        List<CommonSkill> generalSkills = op.general.skills;
        for(int i=0;i < generalSkills.size();i++) {
            // 这里判断可不可出，有问题
            if(generalSkills.get(i).getSkillType().compareTo(SkillType.POSITIVE) == 0) {
                op.general.skillSelectAble.set(i, true);
            }else
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
