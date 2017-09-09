package cn.codekong.imageclassificationsystemclient.view;

/**
 * Created by szh on 2017/5/24.
 */

public interface IChooseInterestedCategoryView {
    //获取分类列表成功
    void getCategoryHobbyListSuccess();
    //获取分类列表失败
    void getCategoryHobbyListFailed(String msg);

    //提交选择的兴趣成功
    void submitChosenIntterestedCategorySuccess(String msg);
    //提交选择的兴趣失败
    void submitChosenIntterestedCategoryFailed(String msg);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
