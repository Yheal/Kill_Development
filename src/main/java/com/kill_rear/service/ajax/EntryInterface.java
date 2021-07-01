package com.kill_rear.service.ajax;

import com.kill_rear.common.ResponseData;


public interface EntryInterface {
    public ResponseData userAuthentic(String username, String password);
    public boolean addNewUser(String username, String password);
}
