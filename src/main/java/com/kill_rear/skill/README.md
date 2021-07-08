本文件存放各种技能的处理逻辑，该类也是服务类，但是特殊，用一个单独的文件存放

skill 处理阶段
0是BEFOREEFFECT
1是INEFFECT
2是AFTEREFFECT

skill发送的数据:

round（回合）
{
    action: 'round'
    target: curplayer
    data: 'jump' |'start' | 'judge' | 'getcard' | 'action' |
          'discard' | 'end' | 'next'
}

RoundPrepare
{

}

RoundJudge
{

}

RoundGetCard
{

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