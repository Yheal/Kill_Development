package com.kill_rear.service.ajax;

import java.util.ArrayList;
import java.util.List;

import com.kill_rear.dao.GeneralDao;
import com.kill_rear.gamebo.game.general.General;
import com.kill_rear.mapper.GeneralMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GeneralService {
    
    @Autowired
    GeneralMapper generalMapper;

    private static int maxGenerals = 1000;
    private static List<General> generalsCache = new ArrayList<>(maxGenerals);


    public List<General> queryUserOwns(String account) {        
        
        /*
        List<General> res = new ArrayList<General>();
        List<Integer> ownGeneralIds = generalMapper.queryOwnGeneralId(account);
        
        for(Integer i:ownGeneralIds) {
            if(generalsCache.get(i) == null) {
                General generalDao = generalMapper.queryGeneralByid(i);
                generalsCache.set(generalDao.getGeneralId(), generalDao);
            } 
            res.add(generalsCache.get(i));
        }
        */
        return res;
    }

    public GeneralDao queryGeneralById(int id) {
        
        if(id < 0)
            return null;

        if(generalsCache.get(id) == null) {
            GeneralDao generalDao = generalMapper.queryGeneralByid(id);
            if(generalDao != null) {
                generalsCache.set(id, generalDao);
            }
        }
        return generalsCache.get(id);
    }
    
}
