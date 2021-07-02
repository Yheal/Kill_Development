package com.kill_rear.service.twoplayers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.Pair;
import com.kill_rear.dao.GeneralDao;
import com.kill_rear.service.MyService;
import com.kill_rear.service.ajax.GeneralService;
import com.kill_rear.service.common.RoomManager;
import com.kill_rear.service.common.SessionPools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class GameInit implements MyService{
    

    @Autowired
    SessionPools sessionPools;
    
    @Autowired
    RoomManager roomManager;

    @Autowired
    GeneralService generalService;

    // 初始化游戏所需数据以及技能的服务类，主要包括两项：
    // 1、一个房间对战数据结构，主要就是起始的卡牌、武将、玩家的血量等信息
    // 2、在RunTime中，创建一个有关技能处理的服务类。
    // 3、此外，还有一些其他的东西，包括决定玩家的编号的什么的
    
    private static ConcurrentHashMap<Integer, ArrayList<Pair<String, Integer>>> generalChoose = new ConcurrentHashMap<>();
    
    private static int selectRound = 3;  // 选取武将的次数


    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        // 处理数据
        // roomManager.setRoomData(roomId, new RoomDataTwo(roomId, players, EditionType.STANDARD));
    }


    public void initGame(ArrayList<String> players) {
       
        // 实现步骤：
        // 1、在数据库里面查找给定玩家的所用户的全部武将，为玩家选择出他们拥有的3个武将并给到前端
        // 2、玩家选择好了之后，我们才可以完全完成上面1、2项
        // 3、完成后，调用RunTime的launchGame（参数为roomId),开始整个游戏的运转。
        
        ArrayList<Pair<String, Integer>> playerGeneralChoose = new ArrayList<>();  // 玩家选择的武将
        int roomId = roomManager.getNextNumber();        // 获取一个新的房间号
        
        for(int i=0;i<players.size();i++) 
            playerGeneralChoose.add(new Pair<String, Integer>(players.get(i), -1));    
        
        if(roomId  == -1) {
            System.out.println("Error"); // 请注意，如果拿不到房间号，那么停止创建；
                                         // 那么应该报错。我们可以封装一个异常类来处理，不过这里简单处理
            return;
        }
        generalChoose.put(roomId, playerGeneralChoose);
        allocateGeneral(roomId, players);
        
        /* 上面的步骤，可以在选择完武将后完成 */
        
    }


    
    // 执行分配武将功能，也是游戏初始化的收尾工作
    public void allocateGeneral(int roomId, ArrayList<String> players) {
        // 请注意，每人原本分配3张，但是可能玩家拥有的武将数不够，甚至完全没有武将，所以这里分配1张
        
        HashMap<String, ArrayList<GeneralDao>> playerOwns = new HashMap<String, ArrayList<GeneralDao>>();
        // 已选择武将
        Set<Integer> choosed = new HashSet<>();
        HashMap<String, ArrayList<GeneralDao>> generalChoosen = new HashMap<>();

        for(String p:players) {
            playerOwns.put(p, (ArrayList<GeneralDao>) generalService.queryUserOwns(p));
            generalChoosen.put(p, new ArrayList<GeneralDao>());
        }

        // 按照player的顺序，一定的策略算法发放武将牌
        // 采用简单的选取第一个（事实上，可以采取许多的策略，例如根据玩家的身份等信息给予不同的好处）
        // 需要注意的是各个玩家拥有的武将数量不同

        for(int i=0;i<selectRound;i++) {
            for(String p:players) {
                for(GeneralDao general:playerOwns.get(p)) {
                    if(!choosed.contains(general.getGeneralId())) {
                        generalChoosen.get(p).add(general);
                        choosed.add(general.getGeneralId());
                    }
                }
            }
        }


        JSONObject dataObj = new JSONObject();
        // 在上面的简单选取策略之后，我们向玩家发送已被选出的武将信息
        dataObj.put("api", "select");
        dataObj.put("roomId", roomId);
        

        for(String p:players) {
            dataObj.put("generals", generalChoosen.get(p));
            sessionPools.sendMessage(p, dataObj);        
        }

        playerOwns.clear();
        choosed.clear();
        generalChoosen.clear();
    }


    
}
