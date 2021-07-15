package com.kill_rear.skill.Support;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillType;

public class DyingState extends CommonSkill {

    public DyingState(GameRunner runner) {
        super(runner);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void launchMySelf(SkillRunTime myself) throws RunningException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void destory() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isNeedCheck() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public SkillType getSkillType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void execute(SkillRunTime myself) throws RunningException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void acceptInput(SkillRunTime myself, Input input) throws RunningException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setGameObjSelectable(SkillRunTime previous, int target) throws RunningException {
        // TODO Auto-generated method stub
        
    }
    
}
