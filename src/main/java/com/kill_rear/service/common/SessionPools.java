package com.kill_rear.service.common;

import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;


@Component
@Data
@NoArgsConstructor
public class SessionPools {
    
    // 保存全部会话映射
    private ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    public void addSession(Session session, String username) {
        sessionPools.put(username, session);
    }

    public Session getSession(String username) {
        Session session =  sessionPools.get(username);
        if(session == null)
            System.out.println("Error！获取Session失败，在SessionPools.java中出现问题");
        return session;
    }

    public void dropSession(String username) {
        sessionPools.remove(username);
    }

    public void sendMessage(String username, JSONObject data) {
        // 先对象发送数据，注意WebSocket本来就是白板数据
        Session session = getSession(username);
        if(session != null) {
            try {
                session.getBasicRemote().sendObject(data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String username, String message) {
        Session session = getSession(username);
        if(session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
