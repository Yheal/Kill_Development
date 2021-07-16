package com.kill_rear.skill.card.basic;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillType;


// sha的功能, 要求目标玩家发动一个闪的技能
public class Sha extends CommonSkill {


    public Sha(GameRunner runner) {
        super(runner);
    }

    @Override
    public void launchMySelf(SkillRunTime myself) throws RunningException {
        // nothing
    }

    @Override
    public void destory() { runner = null; }

    @Override
    public String getName() { return "Sha";}

    @Override
    public boolean isNeedCheck() { return true; }

    @Override
    public SkillType getSkillType() { return SkillType.BASICCARD;}


    @Override
    public boolean acceptResult(SkillRunTime myself, SkillRunTime previous) {
        


        return true;
    }

    
    @Override
    public void acceptInput(SkillRunTime myself, Input input) throws RunningException {
        // myself的目标对象

        int target = myself.accepters.get(0);
        OperationPanel op = runner.ops[target];

        // 杀到目标对象, 触发另一个技能的使用或者不使用等于NON
        switch(input.type) {
            
            case GENERALSKILL:
            case HANDCARD:
            case EQUIPMENT:
                op.switchSkillToLaunch(input);
                runner.sendInteractionData(target);
                break;
            case BUTTON:
                
                // 只有两种可能的按钮，出现第三种，直接报错。
                // 如果是confrim，那么启动当前保存在op里面的技能
                // 如果是cancel，
                // 那么先退出当前技能，然后重新计算杀的可响应对象
                // 那么先重新刷新整个op，然后启动新的造成伤害, 调用op的重置所有恢复状态
                // 然后我们new一个新的技能damage，造成伤害，这样杀就算完成了。

                if(input.value.equals("confrim")) {
                    op.launchSkill();
                }else {
                    if(!op.cancelLaunchSkill(myself)) {
                        // 如果不能撤销技能，那么sha将会启动伤害技能

                    }
                }

                break;
            case NON:
                if(!op.cancelLaunchSkill(myself)) {
                    // 启动伤害技能
                }
                break;
            default:
                throw new RunningException("不正确的输入");
                
        }
    }


    @Override
    public boolean modifyActivatedSkill(SkillRunTime skillRunTime) {
        return true;
    }

    @Override
    public void execute(SkillRunTime myself) throws RunningException {
        
        if(myself.accepters.size() != 1)
            throw new RunningException("错误的玩家人数");
        
        OperationPanel op = runner.ops[myself.accepters.get(0)];
        
        op.setActivableGameObjects(myself);
        op.setPlayerChoosen(myself.sender);
        op.setPlayerUnselectable();
        op.setUsuallyButtons();

        runner.sendInteractionData(myself.accepters.get(0));
    }


    @Override
    public CommonSkill init() {
        return this;
    }

    // 设置previous的sender可以操作的对象
    @Override
    public void setGameObjSelectable(SkillRunTime previous, int target) throws RunningException {
        
        // previous想要启动杀，那么杀启动的条件是一个发动者和一个接收者
        // 所以可以选择的只有玩家对象，其余的一概不可以选中

        OperationPanel op = runner.ops[target];
        int playerChoose = op.getChoosenOtherPlayers();
        
        if(playerChoose == 0) {
            // 现在没有一个被选中
            op.setAttackablePlayerSelectable();
        } else {
            op.remainLastPlayerChoosen();
        }
    }

    @Override
    public void end(SkillRunTime myself) throws RunningException {
        
    }
    
}
