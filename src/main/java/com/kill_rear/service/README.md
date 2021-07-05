服务类
ajax是基于url形式的服务类
twoplayers是双人模式下的服务类，包括请求匹配、处理回应、游戏初始化、游戏进行时
common保存了和游戏有关的房间数据结构类、以及WebSession。前者的数据是全部给到前端，后者主要提供向指定客户端发送消息的接口


双人模式下：
选取武将：后 -> 前
{
    roomId：int-roomId
    api：select
    generals: []  可选武将列表
}

message的data字段
前->后
{
    roomId,
    pattern:
    chooseId;
}
