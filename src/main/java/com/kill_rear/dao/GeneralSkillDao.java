package com.kill_rear.dao;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralSkillDao {


    private String audio, general_skill_name; 
    // private String description; 技能介绍（实际有的，但是这里不加了） 
    
    public String getAudio() {return audio;}
    public String getName() {return general_skill_name;}

    public void setAudio(String audio) { this.audio = audio; }
    public void setName(String name) {this.general_skill_name = name;}

}
