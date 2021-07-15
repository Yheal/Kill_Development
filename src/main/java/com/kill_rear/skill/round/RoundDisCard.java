package com.kill_rear.skill.round;

import java.util.ArrayList;
import java.util.List;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.gamebo.game.card.Card;
import com.kill_rear.gamebo.game.operate.Input;
import com.kill_rear.gamebo.game.operate.OperationPanel;
import com.kill_rear.service.twoplayers.GameRunner;
import com.kill_rear.skill.CommonSkill;
import com.kill_rear.skill.SkillRunTime;
import com.kill_rear.skill.util.SkillType;


public class RoundDisCard extends CommonSkill{


    private int step = 0;
    public List<Card> disCards;

    public RoundDisCard(GameRunner runner) {
        super(runner);
        step = 0;
        disCards = new ArrayList<>();
    }

    
    @Override
    public void acceptInput(SkillRunTime myself, Input input) throws RunningException {
        
        if(step == 0) {
            runner.setiThAck(input.player);
        } else {
            switch(input.type) {
                case HANDCARD:
                    int num = Integer.parseInt(input.value);
                    OperationPanel op = runner.ops[myself.sender];
                    disCards.add(op.setHandCardChoose(num));
                    if(op.handCards.size() - disCards.size() == op.blood) {
                        
                    }
                case BUTTON:

                default:
                    throw new RunningException("控制错误");
            }
        }
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
        
        step = 0;
        disCards.clear();

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
        step = 1;
        int player = myself.sender;
        OperationPanel op = runner.ops[player];
        if(op.handCards.size() > op.blood) {
            // 执行弃牌操作
            op.setOnlyHandCardSelectable();
            runner.sendInteractionData(player);
            myself.skillHandleStage.setAccept();
        }else {
            myself.skillHandleStage.setAfterExecute();
        }
    }


    @Override
    public void setGameObjSelectable(SkillRunTime previous, int target) throws RunningException {
        
    }
}
