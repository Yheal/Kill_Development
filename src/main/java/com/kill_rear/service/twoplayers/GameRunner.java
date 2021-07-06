package com.kill_rear.service.twoplayers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.Pair;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.edition.CardSet;
import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.gamebo.game.edition.Standard;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.gamebo.game.stage.GameStage;
import com.kill_rear.gamebo.game.stage.RoundStage;
import com.kill_rear.service.MyService;
import com.kill_rear.service.ajax.GeneralService;
import com.kill_rear.service.ajax.SkillService;
import com.kill_rear.service.common.SessionPools;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillHandleStage;

import org.springframework.beans.factory.annotation.Autowired;

// 
public class GameRunner implements MyService {
    
    @Autowired
    SkillService skillService;

    @Autowired
    GeneralService generalService; 
    
    @Autowired
    Standard standardEdition; //标准版
    
    @Autowired
    SessionPools sessionPools; //会话

    /* 玩家账号 */
    ArrayList<String> playersName;

    /* 游戏数据 */
    private int roomId;
    private GameStage gStage;              // 游戏阶段
    private RoundStage rStage;             // 回合阶段
    private Queue<Card> cardPile;          // 牌组
    private Stack<Card> disCard;           // 废牌
    private OperationPanel[] ops;          // 玩家操作的数据结构

    /* 技能处理 */
    private HashMap<String, CommonSkill> skills = null;                  // 技能和技能逻辑处理类的映射
    private Stack<CommonSkill> skillStack;                               // 技能栈
    private Stack<ArrayList<Input>> skillHandleStack;                    // 技能处理阶段，用户的输入，可能不需要！填到用户的OperaionPanel板
    private Stack<SkillHandleStage> skillStageStack;                     // 技能处理阶段
                                                                         // 三个栈的大小应该相同

    public int getRoomId() { return roomId; }

    public void setRoomId(int roomId) { this.roomId = roomId; }

    public GameRunner(int Id, ArrayList<Pair<String, Integer>> players, EditionType ed) {
        
        this.roomId = Id;
        
        gStage = GameStage.GAMESTART;       // 游戏阶段
        rStage = RoundStage.ROUNDPREPARE;   // 回合阶段
        cardPile = new LinkedList<Card>();  // 牌堆
        disCard = new Stack<Card>();        // 废牌堆

        ArrayList<Card> cards;              
        if(EditionType.STANDARD == ed) {
            cards = initStandard();
        } else {
            System.out.println("暂时不支持其他模式");
            cards = initStandard();
        }
        shuffleCard(cards);                // 洗牌
        
        this.skills = skillService.getSkillByEdition(ed);   // 卡牌所有技能

        int playerAmounts = players.size();
        ops = new OperationPanel[playerAmounts];  // 玩家操作
        playersName = new ArrayList<>();

        for(int i = 0; i < playerAmounts;i++) {
            General gen = generalService.queryGeneralById(players.get(i).getSecond());
            ops[i] = new OperationPanel(playerAmounts, gen.getBlood(), gen);
            
            for(int j=0;j<playerAmounts;j++) 
                ops[i].playerSelect[j] = false;

            for(String name:gen.getSkills()) 
                this.skills.put(name, skillService.getGeneralSkillByname(name));
            playersName.add(players.get(i).getFirst());
        }

        skillStack = new Stack<>();
        skillHandleStack = new Stack<>();
        skillStageStack = new Stack<>();
    }

    public ArrayList<Card> initStandard() {
        // 初始化牌组, 遍历对象Standard的CardSet集合
        ArrayList<Card> cards = new ArrayList<>();
        int index  = 0;
        for(int i=0; i<standardEdition.cardSets.length ;i++) {
            CardSet cardSet = standardEdition.cardSets[i];
            for(int j = 0; j < cardSet.card_colors.length;j++) {
                cards.add(new Card(index, cardSet.card_colors[j], cardSet.card_points[j], cardSet.name));
                // 加载全部类
                index++;
            }
        }
        return cards;
    }

    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        // 处理玩家的输入操作
    }

    // 与游戏相关的核心逻辑函数
    public void run() {
        // 分发四张牌，从1号玩家开始
        switch(gStage) {
            case GAMESTART:
                gameStart();
                break;
            case GAMELOOP:
                gameLoop();
                break;
            case GAMEEND:
                gameEnd();
                break;
        }
    }
    
    private void gameStart() {
        for(int i=0;i<ops.length;i++){
            OperationPanel op = ops[i];
            int n = 4;
            while(n > 0) {
                op.handCards.add(getOneCardFromCardPile());
            }
        }
        // 向玩家发送游戏数据
        JSONObject dataObj = new JSONObject();
        dataObj.put("api", "game-play");
        dataObj.put("stage", "start");
        for(int i=0;i<ops.length;i++) {
            dataObj.put("data", getPlayerVisibleData(i));
            sessionPools.sendMessage(playersName.get(i), dataObj);
        }
    }

    private void gameLoop() {

    }

    private void gameEnd() {

    }

    public JSONObject getPlayerVisibleData(int i) {
        return null;
    }

    public void shuffleCard(ArrayList<Card> cards) {
        cardPile.clear();
        Collections.shuffle(cards);
        while(cards.isEmpty() == false) {
            Card card = cards.remove(0);
            cardPile.add(card);
        }
    }

    public Card getOneCardFromCardPile() {
        if(cardPile.size() <= 0) {
            // 重新洗牌
            ArrayList<Card> cards = new ArrayList<>();
            while(disCard.isEmpty() == false)
                cards.add(disCard.pop());
            shuffleCard(cards);
        } 
        return cardPile.poll();
    }   

    /* 玩家可见的技能区 */
    public void characterDead(int playerNumber) {
        // 角色死亡处理逻辑
    }


}
