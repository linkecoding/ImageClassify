package cn.codekong.imageclassificationsystemclient.bean;

/**
 * Created by szh on 2017/5/13.
 */

public class SMSVerifyResponse {
    private int code;
    private String msg;
    private String obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
