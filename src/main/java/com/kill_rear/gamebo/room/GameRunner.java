package com.kill_rear.gamebo.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import com.alibaba.fastjson.JSONObject;
import com.kill_rear.common.util.Pair;
import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.service.MyService;
import com.kill_rear.service.ajax.SkillService;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillHandleStage;
import com.kill_rear.skill.ChartacterDeadForTwo;

import org.springframework.beans.factory.annotation.Autowired;

// 大类
public class GameRunner implements MyService {
    
    @Autowired
    SkillService skillService;

    // 模式特有的技能名
    private static String[] modeSkillList = new String[]{"CharacterDead", "GameEnd"}; 

    // 游戏数据
    private int roomId;
    private RoomDataTwo gameData = null;
    private HashMap<String, CommonSkill> skills = null;                  // 技能和技能逻辑处理类的映射
    
    private Stack<CommonSkill> skillStack;                               // 技能栈
    private Stack<ArrayList<Input>> skillHandleStack;                    // 技能处理阶段，用户的输入，可能不需要！填到用户的OperaionPanel板
    private Stack<SkillHandleStage> skillStageStack;                     // 技能处理阶段
                                                                         // 三个栈的大小应该一模一样
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

        // 加载与双人模式特有的技能
        this.skills.put("ChartacterDead", ChartacterDeadForTwo.getInstance());
    }

    // 与游戏相关的核心逻辑函数

    public void start() {
        // 启动游戏进程
    }

    @Override
    public void handleMessage(String username, JSONObject dataObj) {
        // TODO Auto-generated method stub
        
    }
}
