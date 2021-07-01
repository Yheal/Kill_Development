package com.kill_rear.service.common;

import java.util.ArrayList;

import com.kill_rear.gamebo.room.RoomDataTwo;

import org.springframework.stereotype.Component;



@Component
public class RoomManager {
    // 房间管理
    
    private int maxNumbers = 10000; // 最大房间数（简单化操作）


    // 注意，这里有问题！不同模式下的数据可能不一样，但是为了简单起见，暂时这样
    private ArrayList<RoomDataTwo> rooms = new ArrayList<RoomDataTwo>(maxNumbers);
    
    
    // 得到可用房间号
    public int getNextNumber() {
        return 0;
    }
    

    // 释放一个房间号
    public void release(int i) {
        rooms.set(i, null);
    }
    
    // 请注意，这里的名字不应该交RoomDataTwo，而是应该叫RoomData
    // 因为，实际中可能的模式不只有一个，但是这里简单起见叫这个名字
    public void setRoomData(int roomId, RoomDataTwo roomDataTwo) {
        rooms.set(roomId, roomDataTwo);
    }
    // 获得指定的房间内的数据
    public Object getSpecifiedRoom(int num) {
        Object obj = rooms.get(num);
        if(obj == null) {
            System.out.println("Error");
            return null;
        }
        return obj;
    }


}
