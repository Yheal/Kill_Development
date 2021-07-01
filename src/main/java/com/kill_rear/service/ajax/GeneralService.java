package com.kill_rear.service.ajax;

import java.util.ArrayList;
import java.util.List;

import com.kill_rear.dao.GeneralDao;
import com.kill_rear.mapper.GeneralMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GeneralService {
    
    @Autowired
    GeneralMapper generalMapper;

    private static int maxGenerals = 1000;
    private static List<GeneralDao> generalsCache = new ArrayList<>(maxGenerals);


    public List<GeneralDao> queryUserOwns(String account) {        
        
        List<GeneralDao> res = new ArrayList<GeneralDao>();
        List<Integer> ownGeneralIds = generalMapper.queryOwnGeneralId(account);
        
        for(Integer i:ownGeneralIds) {
            if(generalsCache.get(i) == null) {
                GeneralDao generalDao = generalMapper.queryGeneralByid(i);
                generalsCache.set(generalDao.getGeneralId(), generalDao);
            } 
            res.add(generalsCache.get(i));
        }
        
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
