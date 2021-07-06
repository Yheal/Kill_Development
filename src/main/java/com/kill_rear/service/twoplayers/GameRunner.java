package com.kill_rear.service.twoplayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.Pair;
import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.gamebo.room.RoomDataTwo;
import com.kill_rear.service.MyService;
import com.kill_rear.service.ajax.SkillService;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillHandleStage;

import org.springframework.beans.factory.annotation.Autowired;

// 
public class GameRunner implements MyService {
    
    @Autowired
    SkillService skillService;


    // 游戏数据
    private int roomId;
    private RoomDataTwo gameData;

    private HashMap<String, CommonSkill> skills = null;                  // 技能和技能逻辑处理类的映射
    private Stack<CommonSkill> skillStack;                               // 技能栈
    private Stack<ArrayList<Input>> skillHandleStack;                    // 技能处理阶段，用户的输入，可能不需要！填到用户的OperaionPanel板
    private Stack<SkillHandleStage> skillStageStack;                     // 技能处理阶段
                                                                         // 三个栈的大小应该相同
    public int getRoomId() { return roomId; }

    public void setRoomId(int roomId) { this.roomId = roomId; }

    public RoomDataTwo getGameData() { return gameData; }

    public void setGameData(RoomDataTwo gameData) { this.gameData = gameData; }


    public GameRunner(int Id, ArrayList<Pair<String, Integer>> players, EditionType ed) {
        
        this.roomId = Id;
        this.gameData = new RoomDataTwo(players, ed);
        this.skillHandleStack = new Stack<>();

        if(EditionType.STANDARD != ed) {
            System.out.println("暂时不支持其他的模式");
        }
        // 加载卡牌技能
        this.skills = skillService.getSkillByEdition(ed);

    }

    // 与游戏相关的核心逻辑函数

    public void start() {
        // 启动游戏, 先洗牌

        Queue<Card> cardQ = this.gameData.getCardPile();
        ArrayList<Card> cards = new ArrayList<>();
        while(cardQ.isEmpty() == false)
            cards.add(cardQ.poll());
        shuffleCard(cards);

        // 分发四张牌，从1号玩家开始
        for(int i=0;i<this.gameData.getOps().length;i++){
            try{
                OperationPanel op = this.gameData.getOperationPanel(i);
                int n = 4;
                while(n > 0) {
                    op.handCards.add(getOneCardFromCardPile());
                }
            }catch(RunningException re) {
                re.printStackTrace();
            }
        }
    }


    public void shuffleCard(ArrayList<Card> cards) {
        
        Queue<Card> cardQ = this.gameData.getCardPile();
        cardQ.remove();
        Collections.shuffle(cards);
        while(cards.isEmpty() == false) {
            Card card = cards.remove(0);
            cardQ.add(card);
        }
    }

    public Card getOneCardFromCardPile() {
        if(this.gameData.getCardPile().size() <= 0) {
            // 重新洗牌
            ArrayList<Card> cards = new ArrayList<>();
            Stack<Card> disCards = this.gameData.getDisCard();
            while(disCards.isEmpty() == false)
                cards.add(disCards.pop());
            shuffleCard(cards);
        } 
        return this.gameData.getCardPile().poll();
    }   


    public void characterDead(int playerNumber) {
        // 角色死亡处理逻辑
    }


    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        // TODO Auto-generated method stub
        
    }
}
