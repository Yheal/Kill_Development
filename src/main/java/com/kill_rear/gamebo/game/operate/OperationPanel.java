package com.kill_rear.gamebo.game.operate;

import java.util.ArrayList;
import java.util.Stack;

import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.stage.PlayerState;

// 操作类，每个玩家一个
public class OperationPanel {
    

    public PlayerState state;  // 玩家状态

    public int shaNumbers;

    /* 数据区 */
    public int blood;   // 血量
    public int attackDistance, reachDistance; // 攻击距离，达到距离
    /* 武将 */
    public General general;
    /*其他玩家是否被选中状态 */
    public boolean[] playerSelect; 
    
    /*手牌以及是否被选中状态 */
    public ArrayList<Card> handCards;

    /*装备区 */
    public Card[] equipment;

    /* 判定区 */
    public Stack<SkillRunTime> judge;

    public Button[] buttons;


    public OperationPanel(int number, int blood, General general) {
        
        this.state = PlayerState.COMMON;
        this.blood = blood;                // 血量默认为4
        this.attackDistance  = this.reachDistance = 1;   // 距离默认为1
        this.general = general;                        // 先填为空
        
        /* 交互数据结构 */
        playerSelect = new boolean[number];                   // 玩家是否选中               
        handCards = new ArrayList<Card>();                      // 手牌 
        equipment = new Card[4];               // 装备
        judge = new Stack<>();        // 判定区
        buttons = new Button[3];
        
        for(int i=0;i<3;i++) {
            buttons[i] = new Button();
            buttons[i].hide = true;
        }
        shaNumbers = 1;
    }
}
