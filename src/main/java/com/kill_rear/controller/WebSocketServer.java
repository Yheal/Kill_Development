package com.kill_rear.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kill_rear.controller.match.MatchController;
import com.kill_rear.service.common.SessionPools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@ServerEndpoint("/main")
@Component
public class WebSocketServer {

    // 注册websocket会话，接受前端消息，并传递给相应的下层服务
    private static SessionPools sessPools;
    @Autowired
    public static void setSessionPools(SessionPools sessPools) {
        WebSocketServer.sessPools = sessPools;
    } 

    // 下层控制类
    private static MatchController matchController;
    @Autowired
    public static void setMatchController(MatchController matchController) {
        WebSocketServer.matchController = matchController;
    } 

    private Session session;
    private HttpSession httpSession;


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 增加一个session
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String username = (String) this.httpSession.getAttribute("user");
        sessPools.addSession(session, username);
    }

    @OnClose
    public void onClose(Session session) {
        String username = (String) this.httpSession.getAttribute("user");
        sessPools.dropSession(username);
        this.session = null;
        this.httpSession = null;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误, 在controller/WebSocket.java中的onError方法中");
        throwable.printStackTrace();
    }

    @OnMessage
    public void OnMessage(String message, Session session){
        
        JSONObject jsonObj = JSON.parseObject(message);
        String username = (String) this.httpSession.getAttribute("username");

        switch(jsonObj.getString("api")) {
            case "match":
                matchController.handleMessage(username, jsonObj.getJSONObject("data"));
                break;
            case "play":
                // 这里属于打游戏的服务
        }
    }

}
