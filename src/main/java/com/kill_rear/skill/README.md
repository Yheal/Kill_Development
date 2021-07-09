本文件存放各种技能的处理逻辑，该类也是服务类，但是特殊，用一个单独的文件存放

skill 处理阶段
0是BEFOREEFFECT
1是INEFFECT
2是AFTEREFFECT

skill发送的数据:
button的值有
confrim、cancel、end

技能处理流程：
beforeEffect状态，检查是否有，M检查是否有其他依赖技能可以在之前发动，技能需要设置可以被响应的技能的为可操作，如果没有调用beforeEffect函数
ineffect     -- 接受用户输入
aftereffect  --  效果被处理之后，M检查是否有其他依赖技能可以发动，如果没有，那么调用affecteffct函数
accept       -- 接受前一技能的结果

每当发送消息的时候，需要做同步。
M每次处理的时候，都会检查是否需要等待用户，如果不需要按照上面的流程走。
否则，停止执行，等待下次的用户输入。

round（回合）
{
    action: 'Round'
    source: curPlayer,
    target: curplayer,
    data: 'jump' |'start' | 'judge' | 'getcard' | 'action' |
          'discard' | 'end' | 'next'
}

RoundPrepare
{

}

RoundJudge
{
    action: 'RoundJudge',
    source: %playernumber%
    target: curPlayer
    data: skill.getName()
}

RoundGetCard
{
    action: 'RoundGetCard',
    source: 'target'
    targte:　'All'
    data: target：卡牌数据
          other：2
    不同的人物发送的数据不同
}

RoundAction
{

}

RoundDisCard
{

}

RoundEnd
{

}