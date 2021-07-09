package com.kill_rear.service.ajax;

import java.util.ArrayList;
import java.util.List;

import com.kill_rear.dao.GeneralDao;
import com.kill_rear.dao.GeneralSkillDao;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.mapper.GeneralMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GeneralService {
    
    @Autowired
    GeneralMapper generalMapper;
    
    @Autowired
    SkillService skillService;

    private static int maxGenerals = 1000;
    private static List<General> generalsCache = new ArrayList<>(maxGenerals);

    public GeneralService() {
        // 查出所有的武将
        List<GeneralDao> gens = generalMapper.queryAllGeneral();
        List<GeneralSkillDao> skills = generalMapper.queryAllSkill();
        
        for(GeneralDao genDao:gens) {
            General general = new General();
            for(GeneralSkillDao skill:skills) {
                if(genDao.getGeneralId() == skill.getGeneralId()) {
                    general.addSkill(skillService.getGeneralSkillByname(skill.getName()), skill.getAudio());
                }
            }
            general.blood = genDao.getBlood();
            general.category = genDao.getCategory();
            general.generalId = genDao.getGeneralId();
            general.gimage = genDao.getgImage();
            general.name = genDao.getName();
            generalsCache.set(genDao.getGeneralId(), general);
        }
    }

    public List<General> queryUserOwns(String account) {        
        
        List<General> res = new ArrayList<General>();
        List<Integer> ownGeneralIds = generalMapper.queryOwnGeneralId(account);
       
        for(Integer i:ownGeneralIds) {
            // 所有武将信息，均在缓存中
            General tmp = generalsCache.get(i);
            if(tmp == null) {
                System.out.println("ERROR, FROM service.ajax.GeneralService.java");
                continue;
            }
            res.add(tmp);
        }
        
        return res;
    }
    
    public General queryGeneralById(int generalId) {
        
        if(generalsCache.get(generalId) == null) {
            System.out.println("Error FROM service.ajax.GeneralService.java");
            return null;
        }

        return generalsCache.get(generalId);
    }
}
