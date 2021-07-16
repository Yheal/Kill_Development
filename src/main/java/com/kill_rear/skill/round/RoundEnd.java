package com.kill_rear.skill.round;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillType;


public class RoundEnd extends CommonSkill{

    public RoundEnd(GameRunner runner) {
        super(runner);
    }

    @Override
    public void acceptInput(SkillRunTime myself, Input input) throws RunningException {
        runner.setiThAck(input.player);
    }
    
    @Override
    public void destory() { runner = null; }

    @Override
    public String getName() { return "RoundEnd"; }

    @Override
    public SkillType getSkillType() { return SkillType.CONTORLL;}

    @Override
    public boolean isNeedCheck() { return false; }

    @Override
    public void launchMySelf(SkillRunTime myself) {
        myself.accepters.add(myself.sender);
        runner.broadcast(myself, "start");
    }

    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {
        return false;
    }

    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) { return false;}

    @Override
    public void execute(SkillRunTime myself) throws RunningException {
        myself.skillHandleStage.setAfterExecute();
    }

    @Override
    public void setGameObjSelectable(SkillRunTime previous, int target) throws RunningException {
        
    }

    @Override
    public void end(SkillRunTime myself) throws RunningException {
    }



        
}
