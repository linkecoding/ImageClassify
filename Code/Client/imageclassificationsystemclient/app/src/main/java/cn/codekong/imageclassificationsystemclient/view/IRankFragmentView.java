package cn.codekong.imageclassificationsystemclient.view;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.Rank;

/**
 * Created by 尚振鸿 on 2017/6/12. 19:20
 * mail:szh@codekong.cn
 */

public interface IRankFragmentView {
    //获取排行榜信息成功
    void getRankingSuccess(List<Rank> rankList);
    //加载更多排行榜信息成功
    void loadMoreRankingSuccess(List<Rank> rankList);
    //获取排行榜信息失败
    void getRankingFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
