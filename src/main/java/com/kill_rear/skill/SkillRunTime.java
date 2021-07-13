package com.kill_rear.skill;

import java.util.ArrayList;

import com.kill_rear.skill.util.SkillHandleStage;

public class SkillRunTime {
    
    public CommonSkill skill;                 // 指向技能
    public SkillHandleStage skillHandleStage; //技能处理状态
    public int[] mask;                      // 状态是否屏蔽
    public String result;                     // 结果
    public int sender;                        // 触发源
    public ArrayList<Integer> accepters;         // 目标对象 
    public SkillRunTime skillRunTime;          

    public SkillRunTime() {
        mask = new int[3];
        accepters = new ArrayList<>();
    }

    public SkillRunTime init() {
        mask[0] = 0;
        mask[1] = 0;
        mask[2] = 0;
        accepters.clear();
        skill = null;
        skillHandleStage.setExecuteState();
        return this;
    }

    public void destory() {
        skill = null;
        mask = null;
        result = null;
        accepters = null;
    }
}

