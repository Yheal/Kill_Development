package com.kill_rear.gamebo.game.operate;

import java.util.ArrayList;
import java.util.List;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.stage.PlayerState;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillDelayRun;

import jdk.nashorn.internal.ir.ReturnNode;

// 操作类，每个玩家一个
public class OperationPanel {
    

    public PlayerState state;  // 玩家状态
    public int num;

    public int shaNumbers;

    /* 数据区 */
    public int blood;   // 血量
    public int maxBlood;
    public int attackDistance, reachDistance; // 攻击距离，达到距离
    /* 武将 */
    public General general;
    
    /*其他玩家是否可以被选中状态 */
    public ChooseState[] playerSelect;


    
    /*手牌以及是否被选中状态 */
    public ArrayList<Card> handCards;

    /*装备区 */
    public Card[] equipment;

    /* 判定区 */
    public List<SkillDelayRun> judge;

    public Button[] buttons;

    /* 撤销操作 */
    public ArrayList<Integer> playerSelectOrder;
    public ArrayList<Integer> equipmentSelectOrder;

    /* 待启动的武将技能 */
    public CommonSkill generalSkillToLaunch = null;
    /* 待打出的手牌 */
    public Card handcardToLaunch = null;
    /* 待触发的装备牌 */
    public Card equipmentCardToLaunch = null;

    public OperationPanel(int number,int num, int blood, General general) {
        
        this.state = PlayerState.COMMON;
        this.num = num;
        this.blood = blood;                // 血量默认为4
        this.maxBlood = blood;
        this.attackDistance  = this.reachDistance = 1;   // 距离默认为1
        this.general = general;                        // 先填为空
        
        /* 交互数据结构 */
        playerSelect = new ChooseState[number];                   // 玩家是否可以选中               
        playerSelectOrder = new ArrayList<>();
        equipmentSelectOrder = new ArrayList<>();

        for(int i=0;i<number;i++) 
            playerSelect[i] = ChooseState.UNSELECTABLEANDHIDE;

        handCards = new ArrayList<Card>();                      // 手牌 
        equipment = new Card[4];               // 装备
        judge = new ArrayList<>();        // 判定区
        buttons = new Button[3];
        
        for(int i=0;i<3;i++) {
            buttons[i] = new Button();
            buttons[i].hide = true;
        }
        shaNumbers = 1;
    }

    public boolean IsExitingSkill() {
        return !(this.generalSkillToLaunch == null && this.handcardToLaunch == null
        && this.equipmentCardToLaunch == null);
    }

    public void flushSkillToLaunch() {

    }

    public boolean cancelLaunchSkill(SkillRunTime runTime) {
        
        return false;
    }

    // 获取已经选中了玩家人数
    public int getChoosenOtherPlayers() {
        
        int res = 0;
        for(int i=0;i<playerSelect.length;i++) {
            if(playerSelect[i] == ChooseState.CHOOSE && i != num) {
                res++;
            }
        }
        return res;
    }

    // 设置攻击范围内的玩家可选
    public void setAttackablePlayerSelectable() {
        
        for(int i=0;i<playerSelect.length;i++) {
            if(i != num && Math.abs(i-num) <= attackDistance) {
                playerSelect[i] = ChooseState.NOTCHOOSE;
            }else {
                playerSelect[i] = ChooseState.UNSELECTABLEANDHIDE;
            }
        }
    }

    // 设置只有手牌可选
    public void setOnlyHandCardSelectable() {
        
        for(int i=0;i<playerSelect.length;i++)
            playerSelect[i] = ChooseState.UNSELECTABLEANDHIDE;

        for(int i=0;i<4;i++)
            equipment[i].chooseState = ChooseState.UNSELECTABLEANDHIDE;
        
        for(int i=0;i<general.chooseStates.size();i++) 
            general.chooseStates.set(i, ChooseState.UNSELECTABLEANDHIDE);

        for(int i=0;i<buttons.length;i++) 
            buttons[i].selectAble = false;
        
        buttons[2].hide = true;
    }

    // 设置第num号玩家被选中
    public void setPlayerChoosen(int num) {
        playerSelect[num] = ChooseState.CHOOSE;
        playerSelectOrder.add(num);
    }

    // 保留最后一个玩家被选中
    public void remainLastPlayerChoosen() {
        
        while(playerSelectOrder.size() > 1) {
            int t = playerSelectOrder.remove(0);
            playerSelect[t] = ChooseState.NOTCHOOSE;
        }
    }
    
    public void resetSkillToLaunch() {
        generalSkillToLaunch = null;
        handcardToLaunch = null;
        equipmentCardToLaunch = null;
    }

    // 对于当前的技能，让玩家所有可以被响应的对象为可选状态
    public void setActivableGameObjects(SkillRunTime current) {
        
        for(int i=0;i<general.skills.size();i++) {
            if(general.skills.get(i).isActivatable(current)) 
                general.chooseStates.set(i, ChooseState.NOTCHOOSE);
            else
                general.chooseStates.set(i, ChooseState.UNSELECTABLEANDHIDE);
        }

        for(Card card:handCards) { 
            if(card.skill.isActivatable(current))
                card.chooseState = ChooseState.NOTCHOOSE;
            else
                card.chooseState = ChooseState.UNSELECTABLEANDHIDE;
        }

        for(Card card:equipment) {
            if(card.skill.isActivatable(current))
                card.chooseState = ChooseState.NOTCHOOSE;
            else
                card.chooseState = ChooseState.UNSELECTABLEANDHIDE;
        }
    } 
    
    // 设置通常的按钮输入，第一个为确定，第二个取消，第三个隐藏，且第一个不被选中
    public void setUsuallyButtons() {

        buttons[0].color = "#212121";
        buttons[0].content = "确定";
        buttons[0].hide = false;
        buttons[0].selectAble = false;
        
        buttons[1].color = "#F9A825";
        buttons[1].content = "取消";
        buttons[1].selectAble = true;
        buttons[1].hide = false;

        buttons[2].hide = true;
    }

    public boolean checkInputIsRight(SkillRunTime skillRunTime, Input input) {
        return false;
    }

    // 设置某个手牌选中
    public Card setHandCardChoose(int i) throws RunningException{
        
        for(Card card:handCards) {
            if(card.num == i) {
                card.chooseState = ChooseState.CHOOSE;
                return card;
            }
        }
        throw new RunningException("控制错误");
    }

    // 设置某个装备牌选中
    public Card setEqiupmentChoose(int num) throws RunningException {
        

        for(int i=0;i<equipment.length;i++) {
            if(equipment[i].num == num) {
                equipment[i].chooseState = ChooseState.CHOOSE;
                equipmentSelectOrder.add(i);
                return equipment[i];
            }
        }

        throw new RunningException("参数错误");
    }
    
    // 设置所有玩家不可以被选中，注意如果之前已经有玩家被选中，那么跳过
    public void setPlayerUnselectable() {

        for(int i=0;i<playerSelect.length;i++) {
            if(playerSelect[i] == ChooseState.CHOOSE)
                continue;
            playerSelect[i] = ChooseState.UNSELECTABLEANDHIDE;
        }       
    }

    public void switchSkillToLaunch(Input input) throws RunningException {
        
        int num;

        if(input.type == InputType.GENERALSKILL) {
            
            this.generalSkillToLaunch = null;
            this.handcardToLaunch = null;
            this.equipmentCardToLaunch = null;

            for(int i=0;i<general.skills.size();i++) {
                if(general.skills.get(i).getName().equals(input.value)) {
                    this.generalSkillToLaunch = general.skills.get(i);
                }
            }
            if(this.generalSkillToLaunch == null)
                throw new RunningException("找不到武将技能");
 
        } else if(input.type == InputType.EQUIPMENT) {    
            // 如果之前有武将技能的存在，那么我们记录这次为输入
            num = Integer.parseInt(input.value);
            this.equipmentCardToLaunch =  setEqiupmentChoose(num);
            
            if(this.generalSkillToLaunch != null) {
                this.equipmentCardToLaunch = null;
            }  else {
                this.generalSkillToLaunch = null;
                this.handcardToLaunch = null;
            }

        } else if(input.type == InputType.HANDCARD) {
            
            num = Integer.parseInt(input.value);
            this.handcardToLaunch = setHandCardChoose(num);
            
            // 如果之前有武将技能或装备区技能，那么记录本次输入
            if(this.generalSkillToLaunch != null 
                && this.equipmentCardToLaunch == null) 
                this.handcardToLaunch = null;

        }else {
            throw new RunningException("禁止提交’切换技能‘函数其他输入类型");
        }
           
        // 重新刷新本次启动的技能
        flushSkillToLaunch();
    }
    
    public void launchSkill() throws RunningException{

    }
}   
