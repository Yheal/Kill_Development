package com.kill_rear.skill.round;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillType;


public class RoundDisCard extends CommonSkill{

    public RoundDisCard(GameRunner runner) {
        super(runner);
    }

    @Override
    public void destory() { runner = null; }

    @Override
    public String getName() { return "RoundDisCard"; }

    @Override
    public SkillType getSkillType() { return SkillType.CONTORLL; }

    @Override
    public boolean isNeedCheck() { return false; }

    @Override
    public void launchMySelf(SkillRunTime myself) {
        myself.accepters.add(myself.sender);
    }

    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {
        return false;
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) { return false;}

    @Override
    public void execute(SkillRunTime myself) throws RunningException {
        // TODO Auto-generated method stub
        
    }
    
}
