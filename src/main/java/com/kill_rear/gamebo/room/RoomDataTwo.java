package com.kill_rear.gamebo.room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.edition.CardSet;
import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.gamebo.game.edition.Standard;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.gamebo.game.stage.GameStage;
import com.kill_rear.gamebo.game.stage.RoundStage;

import org.springframework.beans.factory.annotation.Autowired;


// 双人模式下的房间数据结构
public class RoomDataTwo {
    
    @Autowired
    Standard standardEdition; //标准版

    /* game no concern */
    int roomId;

    /* 全局数据 */
    GameStage gStage;    // 游戏阶段
    
    RoundStage rStage;   // 回合阶段
    
    Queue<Card> cardPile; // 牌组

    Stack<Card> disCard;  // 废牌

    /* 输入缓存，用于记录玩家的输入，该房间每收到一个选择，控制器会根据类型进行对应的处理*/
    // ArrayList<Input> inputCache;
    /* 技能处理栈, 注意第一个输入类型一定是技能（包括），不管是武将、卡牌*/ 
    Stack<ArrayList<Input>> skillHandleStack;


    ArrayList<String> players;    // 玩家账号，我们通过这个账号和玩家通信。注意在身份场，还有一个有关身份数据
 
    /* 玩家操作盘 */
    OperationPanel[] ops;

    
    public RoomDataTwo(int Id, ArrayList<String> players, EditionType ed) {

        /* 游戏无关的数据结构 */

        this.roomId = Id;

        gStage = GameStage.GAMESTART;
        rStage = RoundStage.ROUNDPREPARE;
 
        cardPile = new LinkedList<Card>();   // 牌堆
        disCard = new Stack<Card>();         // 弃牌
        
        if(EditionType.STANDARD == ed) {
            initStandard();
        } else {
            System.out.println("暂时不支持其他模式");
            initStandard();
        }
        skillHandleStack = new Stack<ArrayList<Input>>(); // 技能处理栈
         
        this.players = players;                           // 玩家编号

        int playerAmounts = players.size();
         
        ops = new OperationPanel[playerAmounts];
        for(int i = 0; i < playerAmounts;i++) {
            ops[i].playerSelect = new boolean[playerAmounts];
            for(int j=0;j<playerAmounts;j++) {
                ops[i].playerSelect[j] = false;
            }
        }

        // 到此除了各个玩家的武将信息，其他都已经完成初始化

    }

    public void initStandard() {
        // 初始化牌组, 遍历对象Standard的CardSet集合
        int index  = 0;
        for(int i=0; i<standardEdition.cardSets.length ;i++) {
            CardSet cardSet = standardEdition.cardSets[i];
            for(int j = 0; j < cardSet.card_colors.length;j++) {
                cardPile.add(new Card(index, cardSet.card_colors[j], cardSet.card_points[j], cardSet.name));
                index++;
            }
        }
    }

}
