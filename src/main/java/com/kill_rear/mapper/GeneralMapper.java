package com.kill_rear.mapper;

import java.util.List;

import com.kill_rear.dao.GeneralDao;
import com.kill_rear.dao.GeneralSkillDao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface GeneralMapper {

    public List<Integer> queryOwnGeneralId(String account);
    public GeneralDao queryGeneralByid(int generalId);
    public List<GeneralSkillDao> querySkill(int generalId);
}
