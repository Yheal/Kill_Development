package com.kill_rear.gamebo.game;

import java.util.ArrayList;

import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.util.SkillHandleStage;

public class SkillRunTime {
    
    public CommonSkill skill;
    public ArrayList<Input> inputStorage;
    public SkillHandleStage skillHandleStage;

    public String result;
    public int source;
    public ArrayList<Integer> target;

}

