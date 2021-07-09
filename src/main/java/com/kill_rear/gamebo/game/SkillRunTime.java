package com.kill_rear.gamebo.game;

import java.util.ArrayList;

import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillHandleStage;

public class SkillRunTime {
    
    public CommonSkill skill;                 // 指向技能
    public ArrayList<Input> inputStorage;     // 保存输入
    public SkillHandleStage skillHandleStage; //技能处理状态
    public int[] shield;                      // 状态是否屏蔽
    public String result;                     // 结果
    public int source;                        // 触发源
    public ArrayList<Integer> target;         // 目标对象 

}

