package com.kill_rear.gamebo.game.general;

import java.util.LinkedList;

// 武将数据
public class General {
  
    int generalId;       // 武将Id
    String category;     // 种族
    String name;         // 名字
    String gimage;       // 图片
    int blood;
    LinkedList<String> skills;      // 技能名
    LinkedList<String> audios;      // 音效
                          // String[] animiation; // 动画，暂时没有

    public General() {
        skills = new LinkedList<>();
        audios = new LinkedList<>();        
    }
    public int getGeneralId() {
        return generalId;
    }

    public void setGeneralId(int generalId) {
        this.generalId = generalId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGimage() {
        return gimage;
    }

    public void setGimage(String gimage) {
        this.gimage = gimage;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }


    public void addSkill(String name, String audio) {
        skills.add(name);
        audios.add(audio);
    }
    
}
