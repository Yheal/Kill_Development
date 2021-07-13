package com.kill_rear.service.twoplayers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.Pair;
import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.edition.CardSet;
import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.gamebo.game.edition.Standard;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.gamebo.game.stage.GameStage;
import com.kill_rear.service.MyService;
import com.kill_rear.service.ajax.GeneralService;
import com.kill_rear.service.ajax.SkillService;
import com.kill_rear.service.common.SessionPools;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.Support.DisCard;
import com.kill_rear.skill.Support.PlayCard;
import com.kill_rear.skill.round.Round;
import com.kill_rear.skill.util.SkillDelayRun;
import com.kill_rear.skill.util.SkillHandleStack;

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
    public ArrayList<String> playersName;

    /* 游戏数据 */
    public int roomId;
    public GameStage gStage;              // 游戏阶段
    public boolean[] roundStageAvailable;
    public Queue<Card> cardPile;          // 牌组
    public Stack<Card> disCard;           // 废牌
    public OperationPanel[] ops;          // 玩家操作的数据结构
    public int curPlayer;
    
    /* 游戏控制 */
    public SkillHandleStack skillHandleStack;
    private HashMap<String, CommonSkill> skills = null;                  // 技能和技能逻辑处理类的映射
    
                                                                         // 三个栈的大小应该相同
    
    /* 游戏同步 */
    private int[] synchornization;
    private JSONObject dataUpdate;

    public int getRoomId() { return roomId; }

    public void setRoomId(int roomId) { this.roomId = roomId; }

    public GameRunner(int Id, ArrayList<Pair<String, Integer>> players, EditionType ed) {
        
        this.roomId = Id;
        
        gStage = GameStage.GAMESTART;       // 游戏阶段
        cardPile = new LinkedList<Card>();  // 牌堆
        disCard = new Stack<Card>();        // 废牌堆
        this.skills = new HashMap<>();

        ArrayList<Card> cards;              
        if(EditionType.STANDARD == ed) {
            cards = initStandard();
        } else {
            System.out.println("暂时不支持其他模式");
            cards = initStandard();
        }
        shuffleCard(cards);                // 洗牌
        
        int playerAmounts = players.size();
        ops = new OperationPanel[playerAmounts];  // 玩家操作
        playersName = new ArrayList<>();

        for(int i = 0; i < playerAmounts;i++) {
            General gen = generalService.queryGeneralById(players.get(i).getSecond());
            ops[i] = new OperationPanel(playerAmounts, gen.blood, gen);
            
            for(int j=0;j<playerAmounts;j++) 
                ops[i].playerSelect[j] = false;

            for(CommonSkill skill:gen.skills) 
                this.skills.put(skill.getName(), skill);
            playersName.add(players.get(i).getFirst());
        }

        skillHandleStack = new SkillHandleStack();
        synchornization = new int[playerAmounts];
        dataUpdate = new JSONObject();
        dataUpdate.put("api", "play");
        dataUpdate.put("stage", "start");
    }

    public void broadcast(SkillRunTime skillRunTime ,String dataObj) {

        setAllSynchronization();

        dataUpdate.put("action", skillRunTime.skill.getName());
        dataUpdate.put("sender", skillRunTime.sender);
        dataUpdate.put("accepter", skillRunTime.accepters);
        dataUpdate.put("data", dataObj);  
        
        for(String s: playersName) 
            sessionPools.sendMessage(s, dataUpdate);
       
        // 释放引用
        dataUpdate.put("data", null);
    }

    public void broadcast(SkillRunTime skillRunTime ,JSONObject dataObj) {

        setAllSynchronization();

        dataUpdate.put("action", skillRunTime.skill.getName());
        dataUpdate.put("source", skillRunTime.sender);
        dataUpdate.put("receivers", skillRunTime.accepters);
        dataUpdate.put("data", dataObj);  
        
        for(String s: playersName) 
            sessionPools.sendMessage(s, dataUpdate);
       
        // 释放引用
        dataUpdate.put("data", null);
    }

    public void characterDead(int playerNumber) {
        // 角色死亡处理逻辑
    } 

    public void disCards(int sender, ArrayList<Card> cards) {

    }

    private void gameStart() {

        dataUpdate.put("action", "start");
        for(int i=0;i<ops.length;i++){
            OperationPanel op = ops[i];
            int n = 4;
            while(n > 0) {
                op.handCards.add(getOneCardFromCardPile());
            }
        }

        setAllSynchronization();     
        // 向玩家发送游戏数据
        ArrayList<General> generals = new ArrayList<>();
        for(int i=0;i<ops.length;i++) {
            generals.add(ops[i].general);
        }
        dataUpdate.put("generals", generals);

        for(int i=0;i<ops.length;i++) {
            dataUpdate.put("owns", ops[i]);
            sessionPools.sendMessage(playersName.get(i), dataUpdate);
        }
        curPlayer = 0;
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

    public int getPlayerNumber(String username) throws RunningException{
        int res = -1;
        for(int i=0;i<playersName.size();i++) {
            if(username.equals(playersName.get(i))) {
                res = i;
                break;
            }
        }
        
        if(res == -1)
            throw new RunningException("找不到玩家编号"); 
        return res;
    }

    public SkillRunTime createNewSkillRunTime() {
        return skillHandleStack.getNew();
    }

    public SkillRunTime getNewSkillRunTime(String name, int sender, int accepter) throws RunningException{
        CommonSkill skill = skills.get(name).init();

        if(skill == null)
            throw new RunningException("找不到该技能");

        SkillRunTime newOne = skillHandleStack.getNew().init();
        newOne.sender = sender;
        newOne.accepters.add(accepter);
        newOne.skill = skill;
        return newOne;
    }

    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        // 处理玩家的输入操作
        int num = -1;

        try {
            num = getPlayerNumber(username);
            synchornization[num] = 1;
            if(!isAllOK())
                return;
        } catch(RunningException re) {
            re.printStackTrace();
        }

        switch(gStage){
            case GAMESTART:
                SkillRunTime skillRunTime =  skillHandleStack.getNew();
                    skillRunTime.skill = new Round(this);
                    skillRunTime.sender = curPlayer;
                    skillRunTime.mask[0] = 0;
                    skillRunTime.mask[1] = 0;
                    skillRunTime.mask[2] = 0; 
                    gStage = GameStage.GAMELOOP;
                    executeSkill();            
                break;
            case GAMELOOP:
                
            case GAMEEND:

        }
    }           

    public void executeSkill(){

        // 检查是否需要停止运行，条件是:存在前端没有回应消息
        if(!isAllOK())
            return;

        // 按照skillRunTime的值，按照不同的状态调用不同的函数栈顶技能
        try {

            SkillRunTime myself = skillHandleStack.getTop();
            if(myself.skillHandleStage.isExecuteEffectState() && myself.mask[0] == 0) {
                myself.skill.execute(myself);
            } else if(myself.skillHandleStage.isAfterEffectState() && myself.mask[2] == 0){
                // 退出当前技能状态，并向下传播本次处理的结果
                myself.skill.afterEffect(myself);
                skillHandleStack.popTop();
            } else if(myself.mask[1] == 0) {
                // 该状态好像不存在
            }

        } catch(RunningException re) {
            re.printStackTrace();
        }

    }

    public ArrayList<Card> initStandard() {
        // 获得标准版的全部卡片
        ArrayList<Card> cards = new ArrayList<>();
        int index  = 0;
        CommonSkill cardSkill;

        for(int i=0; i<standardEdition.cardSets.length ;i++) {
            CardSet cardSet = standardEdition.cardSets[i];
            cardSkill = skillService.getCardSkillByName(cardSet.name);
            for(int j = 0; j < cardSet.cardColors.length;j++) {
                cards.add(new Card(index, cardSet.cardColors[j], cardSet.cardPoints[j], cardSkill));
                // 加载卡片，并设置技能的引用
                index++;
            }
        }
        return cards;
    }

    private boolean isAllOK() {
        boolean res = true;
        for(int ok:synchornization) {
            if(ok == 0)
                res = false;
        }
        return res;
    }

    public SkillRunTime launchNewSkill(String name, int sender) throws RunningException{
        
        SkillRunTime res =  launchSkillCommonCode(name, sender);
        skillHandleStack.spreadTop();
        res.skill.launchMySelf(res);
        
        return res;
    }
    

    public SkillRunTime launchSkillCommonCode(String name, int sender) throws RunningException {
        
        CommonSkill skill = skills.get("name");
        if(skill == null) 
            throw new RunningException("找不到技能的引用");
        skill.init();
        
        SkillRunTime skillRunTime = skillHandleStack.getNew().init();
        skillRunTime.skill = skill;
        skillRunTime.sender = sender;
        return skillRunTime;
    }

    public SkillRunTime launchDelaySkill(SkillDelayRun skillDelayRun, int accepter) throws RunningException {
        
        if(skillDelayRun.card == null) 
            throw new RunningException("目前不支持的延迟处理");

        SkillRunTime res = getNewSkillRunTime("PlayCard", skillDelayRun.sender, accepter);
        PlayCard playCard = (PlayCard)res.skill;
        playCard.launchDelayTip(res, skillDelayRun.card);
        return res;
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
    
    // 出牌技能，参数是玩家、卡牌
    public SkillRunTime playCard(int player, Card card) {
        SkillRunTime res = skillHandleStack.getNew();
        PlayCard playCard = (PlayCard)skills.get("PlayCard");
        
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

    // 启用同步
    private void setAllSynchronization() {
        for(int i=0;i<synchornization.length;i++)
            synchornization[i] = 0;
    }

    // 向一名玩家发送指定的数据，而其余玩家接受不同的数据
    public void sendSeparateData(int p1, JSONObject data1, JSONObject data2, SkillRunTime skillRunTime) {
        
        setAllSynchronization();

        dataUpdate.put("action", skillRunTime.skill.getName());
        dataUpdate.put("sender", skillRunTime.sender);
        dataUpdate.put("accepters", skillRunTime.accepters);
        dataUpdate.put("data", data1);
        sessionPools.sendMessage(playersName.get(p1), dataUpdate);
        dataUpdate.put("data", data2);
        for(int i=0;i < playersName.size();i++) {
            if(i != p1) 
                sessionPools.sendMessage(playersName.get(i), dataUpdate);
        }
        dataUpdate.remove("data");

    }
    
    public void sendSingleMessage(int i, SkillRunTime skill, JSONObject data) {
        dataUpdate.put("data", data);
        sessionPools.sendMessage(playersName.get(i), data);
        dataUpdate.remove("data");
    }

 

}
