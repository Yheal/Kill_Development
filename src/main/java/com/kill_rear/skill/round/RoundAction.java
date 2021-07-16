package com.kill_rear.skill.round;

import java.util.List;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.operate.ChooseState;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillDelayRun;
import com.kill_rear.skill.util.SkillType;

// 核心类之一
public class RoundAction extends CommonSkill {

    private int step = 0;
    private CommonSkill generalSkillPrepareToLaunch = null;
    private Card handCardPrepareToThrow = null;
    private Card equipmentCard = null;

    public RoundAction(String name, GameRunner runner) { 
        super(runner);
    }
    
    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {        
        myself.handleRestart();
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
        step = 0;
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
        
        if(step == 0) {
            runner.setiThAck(input.player);
            return;
        }
        OperationPanel op = runner.ops[myself.sender];
        int num = -1;
        // input好像没有什么用，先留着

        switch(input.type.name()) {
            case "PLAYER":
                num = Integer.parseInt(input.value);
                op.setPlayerChoosen(num);
                letGameObjSelectable(myself);
                break;

            case "GENERALSKILL":
                // 武将是要发动的
                List<CommonSkill> skills = op.general.skills;
                for(int i=0;i<skills.size();i++) {
                    if(skills.get(i).getName().equals(input.value)) {
                        this.generalSkillPrepareToLaunch = skills.get(i);
                        op.general.chooseStates.set(i, ChooseState.CHOOSE);
                    }
                }
                if(this.generalSkillPrepareToLaunch == null)
                    throw(new RunningException("找不到技能引用"));
                
                letGameObjSelectable(myself);
                break;

            case "HANDCARD":
                // 检查是否在一些技能已经存在的情况下，点击了手牌，因为有些技能需要丢弃一些牌才能发动
                Card target = null;
                num = Integer.parseInt(input.value);
                for(Card card:op.handCards) {
                    if(card.num == num) {
                        card.chooseState = ChooseState.CHOOSE;
                        target = card;
                    }
                }
                if(target == null)
                    throw(new RunningException("找不到手牌"));
                
                if(generalSkillPrepareToLaunch != null || equipmentCard !=null) {
                    // nothing
                } else if(handCardPrepareToThrow != null) {
                    handCardPrepareToThrow.chooseState = ChooseState.NOTCHOOSE;
                    handCardPrepareToThrow = target;
                } else{
                    handCardPrepareToThrow = target;
                }
            
                letGameObjSelectable(myself);
                break;

            case "EQUIPMENT":
                // 检查发动条件    
                num = Integer.parseInt(input.value);
                op.setEqiupmentChoose(num);
                for(Card card:op.equipment) {
                    if(card.num == num) {
                        this.equipmentCard = card;
                        this.equipmentCard.chooseState = ChooseState.CHOOSE;
                    }
                }
                if(this.equipmentCard == null)
                    throw(new RunningException("找不到技能的引用"));
                letGameObjSelectable(myself);
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
                        myself.skillHandleStage.setAfterExecute();
                        break;
                    default:
                        throw new RunningException("不认识的按钮");
                }
            case "NON":
                myself.skillHandleStage.setAfterExecute();
                return;
                // 回合结束
        }
    }
    
    public void letGameObjSelectable(SkillRunTime myself) throws RunningException {

        if(generalSkillPrepareToLaunch != null) {
            generalSkillPrepareToLaunch.setGameObjSelectable(myself, myself.sender);
        }else if(handCardPrepareToThrow != null) {
            handCardPrepareToThrow.skill.setGameObjSelectable(myself, myself.sender);
        }else if(equipmentCard != null){
            equipmentCard.skill.setGameObjSelectable(myself, myself.sender);
        }
        runner.sendInteractionData(myself.sender); 
    }

    private void launchCurrentSkill(SkillRunTime myself) throws RunningException {

        // 设置接受态
        myself.skillHandleStage.setAccept();
        if(this.handCardPrepareToThrow != null) {
            // 手牌，启动需要先触发丢牌动作，再执行效果，其它的不需要
            Card tmp = this.handCardPrepareToThrow;
            this.handCardPrepareToThrow = null;
            runner.playCard(myself.sender, tmp);
        } else if(this.generalSkillPrepareToLaunch != null){
           // runner.launchNewSkill(this.generalSkillPrepareToLaunch.getName(), myself.sender);
        } else if(this.equipmentCard != null){
           // runner.launchNewSkill(this.equipmentCard.skill.getName(), myself.sender);
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

        step = 1;                           // 阶段1 
        generalSkillPrepareToLaunch = null; // 浮空
        handCardPrepareToThrow = null;
        equipmentCard = null;

        OperationPanel op = runner.ops[myself.sender];

        // 没有启动技能前，禁止选中其他玩家
        for(int i=0;i<op.playerSelect.length;i++)
            op.playerSelect[i] = ChooseState.UNSELECTABLEANDHIDE;

        
        for(int i=0;i < op.handCards.size();i++) {
            
            Card card = op.handCards.get(i);

            switch(card.skill.getName()) {
                case "Shan":
                    card.chooseState = ChooseState.UNSELECTABLEANDHIDE;
                    break;

                case "Tao":
                    if(op.blood == op.maxBlood)
                        card.chooseState = ChooseState.UNSELECTABLEANDHIDE;
                    else
                        card.chooseState = ChooseState.NOTCHOOSE;
                    break;
                
                case "Sha":
                    if(op.shaNumbers <= 0)
                        card.chooseState = ChooseState.UNSELECTABLEANDHIDE;
                    else
                        card.chooseState = ChooseState.CHOOSE;

                case "WuXiekeJi":
                    card.chooseState = ChooseState.UNSELECTABLEANDHIDE;
                    break;
                
                case "ShanDian":
                    card.chooseState = ChooseState.NOTCHOOSE;
                    for(SkillDelayRun skillDelayRun:op.judge) {
                        if(skillDelayRun.card != null && skillDelayRun.skill.getName() == "ShanDian") 
                            card.chooseState = ChooseState.UNSELECTABLEANDHIDE;
                    }
                    break;

                default:
                    card.chooseState = ChooseState.NOTCHOOSE;
            } 
        }
        
        op.equipment[0].chooseState = ChooseState.NOTCHOOSE;
        for(int i=1;i<4;i++) 
            op.equipment[i].chooseState = ChooseState.UNSELECTABLEANDHIDE;

        General general = op.general;
        for(int i=0;i<general.skills.size();i++) {
            if(general.skills.get(i).isActivatable(myself)) 
                op.general.chooseStates.set(i, ChooseState.NOTCHOOSE);
            else
                op.general.chooseStates.set(i, ChooseState.UNSELECTABLEANDHIDE);
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
        
        runner.sendInteractionData(myself.sender);
    }

    @Override
    public void setGameObjSelectable(SkillRunTime previous, int target) throws RunningException {}

    @Override
    public void end(SkillRunTime myself) throws RunningException {
        // TODO Auto-generated method stub
        
    }



}
