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
    public List<GeneralDao> queryAllGeneral();
    public List<GeneralSkillDao> queryAllSkill();
    public List<GeneralSkillDao> querySkillById(int generalId);
    public GeneralDao queryGeneralById(int generalId);

}
