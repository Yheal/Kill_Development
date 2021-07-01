package com.kill_rear.service;

import com.alibaba.fastjson.JSONObject;

// 接口，所有服务类必须实现这个接口
public interface MyService {
    public abstract void handleMessage(String username, JSONObject dataObj);
}
