package com.kill_rear.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralDao {
    int generalId, blood;
    String category, name;
    String gImage, auido;
    String general_skill_name;

    public int getGeneralId() { return generalId;}
}
