package com.kill_rear.service.common;

import java.util.ArrayList;

import com.kill_rear.gamebo.room.RoomDataTwo;

import org.springframework.stereotype.Component;



@Component
public class RoomManager {
    // 房间管理
    
    private int maxNumbers = 10000; // 最大房间数（简单化操作）


    // 注意，这里有问题！不同模式下的数据可能不一样，但是为了简单起见，暂时这样
    private int[] roomNumbers = new int[maxNumbers];
    private int nextAvailable = 0;
    

    // 得到可用房间号
    public int getNextNumber() {
        if(nextAvailable == -1){
            System.out.println("没有房间号了");
            return -1;
        }
        
        int res = nextAvailable, j;
        nextAvailable = -1;

        // 冗余代码，设计的不好，不过无所谓
        for(j=res+1;j<maxNumbers;j++) {
            if(roomNumbers[res] == -1) {
                nextAvailable = j;
                break;
            }
        }

        if(j == maxNumbers) {
            for(j=0;j<res;j++) {
                if(roomNumbers[j] == -1) {
                    nextAvailable = j;
                    break;
                }
            }
        }
        return res;
    }
    

    // 释放一个房间号
    public void release(int i) {
        if(i >= maxNumbers || i < 0)
            return;
        roomNumbers[i] = -1;
    }
    
    // 请注意，这里的名字不应该交RoomDataTwo，而是应该叫RoomData
    
    // 获得指定的房间内的数据, 注意房间应该是一个父类，不同模式的房间各不相同
    // 这里为了简化，就使用了一种

}
