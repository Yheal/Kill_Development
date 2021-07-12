package com.kill_rear.skill.util;

import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.skill.CommonSkill;

// 延迟执行的数据结构，用于描述某个技能是延迟执行，目前适用于乐不思蜀、兵粮寸断这些

public class SkillDelayRun {    
    public int sender;
    public Card card;
    public CommonSkill skill;

    public SkillDelayRun() { card = null; }
}
