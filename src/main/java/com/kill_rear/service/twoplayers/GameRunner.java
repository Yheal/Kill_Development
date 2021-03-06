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
import com.kill_rear.gamebo.game.operate.ChooseState;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.InputType;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.gamebo.game.stage.GameStage;
import com.kill_rear.service.MyService;
import com.kill_rear.service.ajax.GeneralService;
import com.kill_rear.service.ajax.SkillService;
import com.kill_rear.service.common.SessionPools;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
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
    public Input input;

    /* 游戏控制 */
    public SkillHandleStack skillHandleStack;
    private HashMap<String, CommonSkill> skills = null;                  // 技能和技能逻辑处理类的映射
    
                                                                         // 三个栈的大小应该相同
    
    /* 游戏同步 */
    private int[] synchornization;

    /* 数据 */
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
            ops[i] = new OperationPanel(playerAmounts, i, gen.blood, gen);
        

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

    public void calculateTargetPlayer(SkillRunTime myself) {
        
        OperationPanel op = ops[myself.sender];
        for(int i=0;i<op.playerSelect.length;i++) {
            if(op.playerSelect[i] == ChooseState.CHOOSE) 
                myself.accepters.add(i);
        }  
        if(myself.accepters.size() == 0)
            myself.accepters.add(myself.sender);
    }

    public void executeSkill() throws RunningException{

        // 检查是否需要停止运行，条件是:存在前端没有回应消息
        
        // 按照skillRunTime的值，按照不同的状态调用不同的函数栈顶技能
        // 没有同步，取栈顶，分析栈顶
        // 一直循环下去
        while(isAllOK()) {
            try {

                SkillRunTime myself = skillHandleStack.getTop();
                switch(myself.skillHandleStage.getTag()) {
                    case 0:

                    case 1:
                    
                    case 2:
                    
                    case 3:
                    
                    case 4:
                    default:
                        throw new RunningException("错误控制");
                }
            } catch(RunningException re) {
                re.printStackTrace();
            }
    
        }

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
        // 计算一些奖励等等
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
        return skillHandleStack.getNew().init();
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
    public void handleMessage(String username, JSONObject dataObj){
        // 处理玩家的输入操作
        int num = -1;

        try {
            num = getPlayerNumber(username);
            synchornization[num] = 1;
        } catch(RunningException re) {
            re.printStackTrace();
        }

        try{
            switch(gStage){
                case GAMESTART:
                    SkillRunTime skillRunTime =  skillHandleStack.getNew();
                    skillRunTime.skill = new Round(this);
                    skillRunTime.sender = curPlayer;
                    skillRunTime.accepters.add(curPlayer);
                    skillRunTime.mask[0] = 0;
                    skillRunTime.mask[1] = 0;
                    skillRunTime.mask[2] = 0; 
                    gStage = GameStage.GAMELOOP;
                    executeSkill();            
                    break;
                case GAMELOOP:
                    // 提取输入
                    input.player = getPlayerNumber(username);
                    
                    // 不接受不是当前技能目标的玩家
                    if(!skillHandleStack.isInputAcceptable(input.player))
                        return;
                    
                    input.value = dataObj.getString("value");
                    
                    switch(dataObj.getInteger("type")) {
                        case 0:
                            input.type = InputType.PLAYER;
                            break;
                        case 1:
                            input.type = InputType.GENERALSKILL;
                            break;
                        case 2:
                            input.type = InputType.HANDCARD;
                            break;
                        case 3:
                            input.type = InputType.EQUIPMENT;
                            break;
                        case 4:
                            input.type = InputType.CARD;
                            break;
                        case 5:
                            input.type = InputType.BUTTON;
                            break;
                        case 6:
                            input.type = InputType.ACK;
                            break;
                        case 7:
                            input.type = InputType.NON;
                            break;
                        default:
                            throw new RunningException("错误的输入");
                    }
                    SkillRunTime myself = skillHandleStack.getTop();
                    myself.skill.acceptInput(myself, input); 
                    executeSkill();
                    break;
                case GAMEEND:

            }
        }catch(RunningException e) {
            e.printStackTrace();
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
        
        CommonSkill skill = skills.get(name);
        if(skill == null) 
            throw new RunningException("找不到技能的引用");
        
        skill.init();
        SkillRunTime res = skillHandleStack.getNew().init();
        res.skill = skill;
        res.sender = sender;
        
        calculateTargetPlayer(res);
        
        skillHandleStack.spreadTop();
        res.skill.launchMySelf(res);
        
        return res;
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
    
    // 玩牌技能，参数是玩家、卡牌、卡牌目标对象可选
    public SkillRunTime playCard(int player, Card card) throws RunningException{
        
        SkillRunTime res = skillHandleStack.getNew();
        
        CommonSkill skill = skills.get("PlayCard");
        res.sender = player;
        res.skill = skill;
        calculateTargetPlayer(res);

        PlayCard playCard = (PlayCard)skill;
        playCard.cardToUse = card;

        skillHandleStack.spreadTop();
        res.skill.launchMySelf(res);
        
        return res;
    }

    public void setLoopExecute(SkillRunTime skillRunTime) {
        skillRunTime.skillHandleStage.setBeforeExecute();
    }
    
    public void setiThAck(int i) {
        
        try{
            if(i>=playersName.size() || i<0)
                throw new RunningException("确认错误");
            this.synchornization[i] = 1;
        }catch(RunningException re) {
            re.printStackTrace();
        }
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

    public void sendInteractionData(int ...players) {

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
