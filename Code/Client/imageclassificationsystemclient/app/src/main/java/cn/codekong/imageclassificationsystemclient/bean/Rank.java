package cn.codekong.imageclassificationsystemclient.bean;

/**
 * 排行榜bean
 * Created by 尚振鸿 on 2017/6/12. 20:41
 * mail:szh@codekong.cn
 */

public class Rank {
    private String avatar_url;
    private String username;
    private String integral;
    private String accuracy;
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}
