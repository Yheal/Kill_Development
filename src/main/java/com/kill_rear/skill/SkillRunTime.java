package com.kill_rear.skill;

import java.util.ArrayList;
import java.util.List;

import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.skill.util.SkillHandleStage;

public class SkillRunTime {
    
    public CommonSkill skill;                 // 指向技能
    public List<Input> inputStorage;     // 保存输入
    public SkillHandleStage skillHandleStage; //技能处理状态
    public int[] mask;                      // 状态是否屏蔽
    public String result;                     // 结果
    public int sender;                        // 触发源
    public ArrayList<Integer> accepters;         // 目标对象 
    public SkillRunTime skillRunTime;          

    public SkillRunTime() {
        mask = new int[3];
        accepters = new ArrayList<>();
        inputStorage = new ArrayList<>();  
    }

    public SkillRunTime init() {
        mask[0] = 0;
        mask[1] = 0;
        mask[2] = 0;
        accepters.clear();
        skill = null;
        skillHandleStage.setLaunchState();
        return this;
    }

    public void destory() {
        skill = null;
        inputStorage.clear();
        inputStorage = null;
        mask = null;
        result = null;
        accepters = null;
    }
}

