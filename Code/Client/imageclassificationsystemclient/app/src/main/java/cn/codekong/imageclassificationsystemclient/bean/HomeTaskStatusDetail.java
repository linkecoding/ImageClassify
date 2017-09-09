package cn.codekong.imageclassificationsystemclient.bean;

/**
 * 首页任务状态Bean
 * Created by 尚振鸿 on 2017/8/11. 16:47
 * mail:szh@codekong.cn
 */

public class HomeTaskStatusDetail {
    private int amount_of_task_yesterday;
    private int amount_of_task_today;
    private int amount_of_task_unfinished;

    public int getAmount_of_task_yesterday() {
        return amount_of_task_yesterday;
    }

    public void setAmount_of_task_yesterday(int amount_of_task_yesterday) {
        this.amount_of_task_yesterday = amount_of_task_yesterday;
    }

    public int getAmount_of_task_today() {
        return amount_of_task_today;
    }

    public void setAmount_of_task_today(int amount_of_task_today) {
        this.amount_of_task_today = amount_of_task_today;
    }

    public int getAmount_of_task_unfinished() {
        return amount_of_task_unfinished;
    }

    public void setAmount_of_task_unfinished(int amount_of_task_unfinished) {
        this.amount_of_task_unfinished = amount_of_task_unfinished;
    }
}

