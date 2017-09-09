package cn.codekong.imageclassificationsystemclient.bean;

/**
 * 我页面显示的用户的基本信息
 */
public class UserTaskAmount {

    private User user;
    private String task_amount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTask_amount() {
        return task_amount;
    }

    public void setTask_amount(String task_amount) {
        this.task_amount = task_amount;
    }

}
