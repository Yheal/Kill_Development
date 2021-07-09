package com.kill_rear.gamebo.game.general;

import java.util.LinkedList;

import com.kill_rear.skill.CommonSkill;

// 武将数据
public class General {

    public int generalId;       // 武将Id
    public String category;     // 种族
    public String name;         // 名字
    public String gimage;       // 图片
    public int blood;
    public LinkedList<CommonSkill> skills;      // 技能名
    public LinkedList<String> audios;      // 音效
                          // String[] animiation; // 动画，暂时没有
    public LinkedList<Boolean> skillSelectAble;
    
    public General() {
        skills = new LinkedList<>();
        audios = new LinkedList<>();        
    }

    public void addSkill(CommonSkill skill, String audio) {
        skills.add(skill);
        audios.add(audio);
    }
    
}
