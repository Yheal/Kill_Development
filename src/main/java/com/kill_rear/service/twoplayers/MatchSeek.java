package com.kill_rear.service.twoplayers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.kill_rear.service.common.SessionPools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 双人匹配模式的匹配队列

@Component
public class MatchSeek {
    // 可以自定义你想要的匹配方式, 例如按照玩家的等级、身份、武将数等因随进行匹配
    // 为了实现上面的按因素匹配，利用用户的account在数据库中查找对应的信息，然后做成不同匹配集合即可
    // 为了简单起见, 就使用一个map
    @Autowired
    SessionPools sessionPools;

    @Autowired
    MatchConfirm matchConfirm;
    
    private static ConcurrentHashMap<String, Integer> matchQueue = new ConcurrentHashMap<>();
    
    public void add(String username) {
        matchQueue.put(username, 1);
    }
    public void remove(String username) {
        matchQueue.remove(username);
    }

    public int size() {
        return matchQueue.size();
    }

    public void solveUserExit(String username) {
        matchQueue.remove(username);
    }

    public void launch(String username) {
        // 为指定玩家组织游戏，注意这里可能有同步问题，不过，为了简单起见，随便写了
        if(matchQueue.get(username) == null) {
            System.out.println("Error! 出现在MatchQueueTwo中");
        } else{
            if(matchQueue.size() >= 2) {
                ArrayList<String> players = new ArrayList<>();
                players.add(username);
                matchQueue.remove(username);
                int n = 1;

                Iterator<Entry<String, Integer>> entrys = matchQueue.entrySet().iterator();
                while(entrys.hasNext() && n > 0) {
                    String tmp = entrys.next().getKey();
                    players.add(tmp);
                    matchQueue.remove(tmp);
                    n--;
                }
                matchConfirm.prepare(players);
            }
        }
    }
}
