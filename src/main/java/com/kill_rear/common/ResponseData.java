package com.kill_rear.common;

/**
 * @ Author     ：Negen
 * @ Date       ：Created in 22:47 2020/3/5
 * @ Description：统一返回类
 * @ Modified By：
 * @Version: 1.0
 */
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ResponseData {
    Integer code;       //响应状态码
    String message;			//响应信息
    Object data;		//响应数据

    public static  ResponseData getInstance() {
        return new ResponseData();
    }

    public  ResponseData code(Integer code){
        this.code = code;
        return (ResponseData) this;
    }

    public int getCode() { return code;}

    public Object getData() { return this.data;}
    
    public String getMessage() { return this.message;}
    
    public  ResponseData message(String message){
        this.message = message;
        return (ResponseData) this;
    }

    public  ResponseData data(Object data){
        this.data = data;
        return (ResponseData) this;
    }

    public  ResponseData responseEnum(ResponseEnum responseEnum){
        this.code = responseEnum.code;
        this.message = responseEnum.message;
        return (ResponseData) this;
    }
    
    @Override
    public String toString() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", this.code);
        resultJson.put("message", this.message);
        resultJson.put("data", this.data);
        System.out.println(resultJson.toJSONString());
        return resultJson.toJSONString();
    }
    
    //测试
    public static void main(String[] args) {
        System.out.println(ResponseData.getInstance().responseEnum(ResponseEnum.LOGIN_SUCCESS));
    }
}

