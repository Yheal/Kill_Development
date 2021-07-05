package com.kill_rear.gamebo.room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.kill_rear.common.util.Pair;
import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.edition.CardSet;
import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.gamebo.game.edition.Standard;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.gamebo.game.stage.GameStage;
import com.kill_rear.gamebo.game.stage.RoundStage;
import com.kill_rear.service.ajax.GeneralService;
import com.kill_rear.service.ajax.SkillService;

import org.springframework.beans.factory.annotation.Autowired;


// 双人模式下的房间数据结构
public class RoomDataTwo {
    
    @Autowired
    Standard standardEdition; //标准版

    @Autowired
    GeneralService generalService;

    @Autowired
    SkillService skillService;


    GameStage gStage;    // 游戏阶段
    RoundStage rStage;   // 回合阶段
    Queue<Card> cardPile; // 牌组
    Stack<Card> disCard;  // 废牌
                            /* 输入缓存，用于记录玩家的输入，该房间每收到一个选择，控制器会根据类型进行对应的处理*/
                            // ArrayList<Input> inputCache;
    OperationPanel[] ops;         // 对玩家操作的抽象

    public GameStage getgStage() { return gStage; }

    public void setgStage(GameStage gStage) { this.gStage = gStage; }

    public RoundStage getrStage() { return rStage; } 

    public void setrStage(RoundStage rStage) { this.rStage = rStage; }

    public Queue<Card> getCardPile() { return cardPile; }

    public void setCardPile(Queue<Card> cardPile) { this.cardPile = cardPile; }

    public Stack<Card> getDisCard() { return disCard; }

    public void setDisCard(Stack<Card> disCard) { this.disCard = disCard; }

    public OperationPanel[] getOps() { return ops; }

    public void setOps(OperationPanel[] ops) { this.ops = ops; }

    public RoomDataTwo(ArrayList<Pair<String, Integer>> players, EditionType ed) {
        
        // 完成一场游戏所有需要的数据结构的初始化
        gStage = GameStage.GAMESTART;
        rStage = RoundStage.ROUNDPREPARE;
        cardPile = new LinkedList<Card>();
        disCard = new Stack<Card>();

        if(EditionType.STANDARD == ed) {
            initStandard();
        } else {
            System.out.println("暂时不支持其他模式");
            initStandard();
        }
         
        int playerAmounts = players.size();
        ops = new OperationPanel[playerAmounts];
        
        for(int i = 0; i < playerAmounts;i++) {

            General gen = generalService.queryGeneralById(players.get(i).getSecond());
            ops[i] = new OperationPanel(playerAmounts, gen.getBlood(), gen);
            for(int j=0;j<playerAmounts;j++) {
                ops[i].playerSelect[j] = false;
            }
        }
        
    }
    
    public void initStandard() {
        // 初始化牌组, 遍历对象Standard的CardSet集合
        int index  = 0;
        for(int i=0; i<standardEdition.cardSets.length ;i++) {
            CardSet cardSet = standardEdition.cardSets[i];
            for(int j = 0; j < cardSet.card_colors.length;j++) {
                cardPile.add(new Card(index, cardSet.card_colors[j], cardSet.card_points[j], cardSet.name));
                // 加载全部类
                index++;
            }
        }
    }


    public OperationPanel getOperationPanel(int i) throws RunningException {
        if(i >= ops.length || i < 0)
            throw new RunningException("failed to get game data");
        return ops[i];
    }   

}


