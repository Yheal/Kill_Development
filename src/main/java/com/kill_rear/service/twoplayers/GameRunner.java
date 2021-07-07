package com.kill_rear.service.twoplayers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.Pair;
import com.kill_rear.gamebo.game.SkillRunTime;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.edition.CardSet;
import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.gamebo.game.edition.Standard;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.gamebo.game.stage.GameStage;
import com.kill_rear.gamebo.game.stage.RoundStage;
import com.kill_rear.service.MyService;
import com.kill_rear.service.ajax.GeneralService;
import com.kill_rear.service.ajax.SkillService;
import com.kill_rear.service.common.SessionPools;
import com.kill_rear.skill.CommonSkill;

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
    public int roomId;
    public GameStage gStage;              // 游戏阶段
    public RoundStage rStage;             // 回合阶段
    public boolean[] roundStageAvailable;
    public Queue<Card> cardPile;          // 牌组
    public Stack<Card> disCard;           // 废牌
    public OperationPanel[] ops;          // 玩家操作的数据结构
    public int curPlayer;
    
    /* 游戏控制 */
    private HashMap<String, CommonSkill> skills = null;                  // 技能和技能逻辑处理类的映射
    public Stack<SkillRunTime> skillRunTimeStack;
                                                                         // 三个栈的大小应该相同
    
    /* 游戏同步 */
    private int[] isOk;
    private JSONObject dataUpdate;

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

        skillRunTimeStack = new Stack<>();
        isOk = new int[playerAmounts];
        dataUpdate = new JSONObject();
        dataUpdate.put("api", "play");
        dataUpdate.put("stage", "update");
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
        String stage = dataObj.getString("stage");
        switch(stage){
            case "start":

            case "loop":
            
            case "end":

        }
    }
    
    /* 与游戏相关的逻辑函数，按字典序排序 */
    
    public void broadcast(JSONObject data) {

        dataUpdate.put("data", data);
        for(String s: playersName) 
            sessionPools.sendMessage(s, dataUpdate);
        
    }

    public void characterDead(int playerNumber) {
        // 角色死亡处理逻辑
    } 
    private boolean checkOKAll() {
        boolean res = true;
        for(int ok:isOk) {
            if(ok == 0)
                res = false;
        }
        return res;
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
        dataObj.put("api", "play");
        dataObj.put("stage", "start");
        for(int i=0;i<ops.length;i++) {
            dataObj.put("data", getPlayerVisibleData(i));
            sessionPools.sendMessage(playersName.get(i), dataObj);
        }
        curPlayer = 0;
        rStage = RoundStage.ROUNDPREPARE;
    }

    private void gameLoop() {

    }

    private void gameEnd() {

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

    public JSONObject getPlayerVisibleData(int i) {
        return null;
    }

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
    
    private void setNotOkAll(){
        for(int i=0;i<isOk.length;i++) 
            isOk[i] = 0;
    }

    public void shuffleCard(ArrayList<Card> cards) {
        cardPile.clear();
        Collections.shuffle(cards);
        while(cards.isEmpty() == false) {
            Card card = cards.remove(0);
            cardPile.add(card);
        }
    }

    public void launchNewSkill(String name) {
        
    }
    public void launchNewSkillRunTime(SkillRunTime skillRunTime) {
        skillRunTimeStack.add(skillRunTime);
    }

}
