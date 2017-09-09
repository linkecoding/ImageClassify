package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.Category;
import cn.codekong.imageclassificationsystemclient.bean.CategoryHobby;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.CategoryService;
import cn.codekong.imageclassificationsystemclient.util.FormatDateUtil;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IChooseInterestedCategoryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by szh on 2017/5/24.
 */

public class ChooseInterestedCategoryPresenter implements IChooseInterestedCategoryPresenter {
    private static final String TAG = "xxhhh";
    private Context mContext;
    private IChooseInterestedCategoryView iChooseInterestedCategoryView;
    private Set<String> selectedItemIdSet;

    public ChooseInterestedCategoryPresenter(Context context, IChooseInterestedCategoryView iChooseInterestedCategoryView) {
        this.mContext = context;
        this.iChooseInterestedCategoryView = iChooseInterestedCategoryView;
        selectedItemIdSet = new HashSet<>();
    }

    /**
     * 网络请求获得分类列表并设置
     */
    @Override
    public void getAndShowCategoryHobbyList(final GridView categoryGridView) {
        CategoryService categoryService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, CategoryService.class);
        //获得token
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME,
                ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            categoryService.getCategoryHobbyList(token).enqueue(new Callback<HttpResult<CategoryHobby>>() {
                @Override
                public void onResponse(Call<HttpResult<CategoryHobby>> call, Response<HttpResult<CategoryHobby>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String code = response.body().getCode();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            //请求数据成功
                            CategoryHobby categortHobby = response.body().getData();
                            //设置数据到GridView
                            setCategoryHobbyListData(categortHobby, categoryGridView);
                            iChooseInterestedCategoryView.getCategoryHobbyListSuccess();
                        } else if (code.equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iChooseInterestedCategoryView.validateError(response.body().getMsg());
                        } else {
                            //获取兴趣列表失败
                            iChooseInterestedCategoryView.getCategoryHobbyListFailed(mContext.getString(R.string.str_getcategory_list_error));
                        }
                    } else {
                        //服务器错误
                        iChooseInterestedCategoryView.getCategoryHobbyListFailed(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<CategoryHobby>> call, Throwable t) {
                    //模拟数据
                    //moniData(categoryGridView);
                    iChooseInterestedCategoryView.getCategoryHobbyListFailed(mContext.getString(R.string.str_network_error));
                }
            });
        } else {
            //token失效,登录状态异常
            iChooseInterestedCategoryView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }

    }

    /**
     * 模拟数据
     *
     * @param
     *//*
    private void moniData(GridView categoryGridView) {
        //请求数据成功
        List<CategoryHobby> categortList = new ArrayList<>();
        //模拟数据
        CategoryHobby categoryHobby = new CategoryHobby();
        Category category1 = new Category();
        category1.setCategory_name("水果");
        category1.setCategory_id("1");
        Category category2 = new Category();
        category2.setCategory_name("风景");
        category2.setCategory_id("2");
        Category category3= new Category();
        category3.setCategory_id("3");
        category3.setCategory_name("动物");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
        List<Category> hobbyList = new ArrayList<>();
        Category hobby = new Category();
        hobby.setCategory_id("1");
        hobby.setCategory_name("水果");
        hobbyList.add(hobby);
        categoryHobby.setCategoryList(categoryList);
        categoryHobby.setHobbyList(hobbyList);
        //设置数据到GridView
        setCategoryHobbyListData(categoryHobby, categoryGridView);
    }*/
    @Override
    public void submitChosenInterestedCategory() {
        if (selectedItemIdSet.size() != 0) {
            //用户选择了才会去提交网络请求
            //首先获得token
            String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME,
                    ApiConstant.OAUTH_TOKEN);

            if (!TextUtils.isEmpty(token)) {
                //发送网络请求
                String categories = FormatDateUtil.convertListToString(new ArrayList<String>(selectedItemIdSet));
                CategoryService categoryService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, CategoryService.class);
                categoryService.modifyHobby(token, categories).enqueue(new Callback<HttpResult<String>>() {
                    @Override
                    public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getCode().equals(Constant.REQUEST_SUCCESS)) {
                                //添加兴趣成功
                                iChooseInterestedCategoryView.submitChosenIntterestedCategorySuccess(response.body().getMsg());
                            } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                                //账户被冻结
                                iChooseInterestedCategoryView.validateError(response.body().getMsg());
                            } else {
                                //添加兴趣失败
                                iChooseInterestedCategoryView.submitChosenIntterestedCategoryFailed(mContext.getString(R.string.str_network_error) + response.body().getMsg());
                            }
                        } else {
                            Log.d(TAG, "onResponse: " + response);
                            //服务器错误
                            iChooseInterestedCategoryView.submitChosenIntterestedCategoryFailed(mContext.getResources().getString(R.string.str_server_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                        //网络错误
                        iChooseInterestedCategoryView.submitChosenIntterestedCategoryFailed(mContext.getString(R.string.str_network_error));
                    }
                });

            } else {
                iChooseInterestedCategoryView.validateError(mContext.getString(R.string.str_login_status_error));
            }
        } else {
            //提示至少选择一项兴趣
            Toast.makeText(mContext, R.string.str_select_at_least_one_item, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 设置数据到GridView
     *
     * @param categoryHobby
     * @param categoryGridView
     */
    private void setCategoryHobbyListData(CategoryHobby categoryHobby, GridView categoryGridView) {
        final List<Category> hobbyList = categoryHobby.getHobbies();
        for (Category ca : hobbyList) {
            //现将所有的兴趣加入
            selectedItemIdSet.add(ca.getCategory_id());
        }
        //设置数据显示在GridView中
        categoryGridView.setAdapter(new CommonAdapter<Category>(mContext, R.layout.category_textview_item, categoryHobby.getCategories()) {
            @Override
            protected void convert(ViewHolder viewHolder, final Category item, int position) {
                viewHolder.setText(R.id.id_label_name_tv, item.getCategory_name());
                TextView textView = viewHolder.getView(R.id.id_label_name_tv);
                //用户选择的兴趣
                if (selectedItemIdSet.contains(item.getCategory_id())) {
                    selectedItemIdSet.add(item.getCategory_id());
                    textView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.label_choose_selected_shape));
                }

                viewHolder.setOnClickListener(R.id.id_label_name_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView) v;
                        if (selectedItemIdSet.contains(item.getCategory_id())) {
                            //已经包含了再次点击就移除
                            textView.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
                            textView.setBackground(mContext.getResources().getDrawable(R.drawable.label_choose_unselected_shape));
                            selectedItemIdSet.remove(item.getCategory_id());
                        } else {
                            textView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                            textView.setBackground(mContext.getResources().getDrawable(R.drawable.label_choose_selected_shape));
                            selectedItemIdSet.add(item.getCategory_id());
                        }
                    }
                });
            }
        });
    }
}

interface IChooseInterestedCategoryPresenter {
    //获取类别列表
    void getAndShowCategoryHobbyList(GridView categoryGridView);

    //提交用户选择的兴趣
    void submitChosenInterestedCategory();
}