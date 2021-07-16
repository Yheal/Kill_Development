package com.kill_rear.skill.util;


// 技能处理阶段， 当一个技能被加到栈顶的时候，模型按照以下步骤操作
public class SkillHandleStage {

    private int tag;
                                        // BEFOREXECUTE(启动效果前)、EXECUTE（执行效果）AFTEREXECUTE(执行效果后)、END(结束执行)
                                        // acceptInput（)接受输入，acceptResult（）接受上次技能的结果

                                        // beforexecute -> execute -> afterexecute-> end
                                        // 
    public SkillHandleStage() {
        tag = 0;              
    }
    public void reset() {tag = 0;}

    // 阶段有关
    public void setBeforeExecute() {tag = 0;}
    public void setExecute() { tag = 1; }
    public void setAfterExecute() {tag = 2;}
    public void setEnd() {tag = 3;}

    // 接受输入
    public void setAcceptInput() {tag = 4;}
    public void setAcceptResult() {tag = 5;}

    public boolean isBeforeExecute() { return tag == 0; }
    public boolean isExecute() {return tag == 1;}
    public boolean isAfterExecute() {return tag == 2;}
    public boolean isEnd() { return tag == 3; }
    public boolean isAcceptInput() {return tag == 4;}
    public boolean isAcceptResult() {return tag == 5;}


    public int getTag() { return tag;}
}

