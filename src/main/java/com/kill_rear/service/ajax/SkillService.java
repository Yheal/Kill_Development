package com.kill_rear.service.ajax;

import java.util.HashMap;

import com.kill_rear.gamebo.game.edition.EditionType;
import com.kill_rear.skill.CommonSkill;

import org.springframework.stereotype.Service;

// 返回所有技能对象的实例
@Service
public class SkillService {
    


    public CommonSkill getCardSkillByName(String name) {
        return null;
    }

    public CommonSkill getGeneralSkillByname(String name) {
        return null;
    }

    // 返回指定版本的全部卡牌技能
    public HashMap<String, CommonSkill> getSkillByEdition(EditionType et) {
        
        if(EditionType.STANDARD != et) {
            System.out.println("不支持其他版本");
        }
            
        return null;
    }
}
