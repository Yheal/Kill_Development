package com.kill_rear.skill.util;

import java.util.ArrayList;
import java.util.List;

import com.kill_rear.common.util.RunningException;
import com.kill_rear.skill.SkillRunTime;


// 技能处理栈
public class SkillHandleStack {

    public List<SkillRunTime> handleStack;
    public int top;

    public SkillHandleStack() {
        top = -1;             // 当前栈顶位置
        handleStack = new ArrayList<>();  // 预留10个
        for(int i=0;i<10;i++)
            handleStack.add(new SkillRunTime());
    }

    public SkillRunTime getNew() {
        top++;
        if(top>= handleStack.size()) {
            for(int i=0;i<5;i++) 
                handleStack.add(new SkillRunTime());
        }
        return handleStack.get(top).init();
    }

    public SkillRunTime getTop() throws RunningException{
        if(top < 0)
            throw new RunningException("控制错误");
        return handleStack.get(top);
    }

    public void pop() { top--; }

    public void spreadTopSkillRunTimeLower() throws RunningException{
        SkillRunTime topSkillRunTime = getTop();
        for(int i=top-1;i>=0;i--) {
            boolean res = handleStack.get(i).skill.modifyActivatedSkill(topSkillRunTime);
            if(!res)
                return;
        }
        
    }
} 
