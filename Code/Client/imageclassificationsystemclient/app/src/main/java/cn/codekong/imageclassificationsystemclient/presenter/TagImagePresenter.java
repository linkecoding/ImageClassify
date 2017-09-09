package cn.codekong.imageclassificationsystemclient.presenter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import cn.codekong.imageclassificationsystemclient.bean.Images;
import cn.codekong.imageclassificationsystemclient.bean.ImageTask;
import cn.codekong.imageclassificationsystemclient.bean.ImgLabel;
import cn.codekong.imageclassificationsystemclient.bean.Label;
import cn.codekong.imageclassificationsystemclient.bean.TagResult;
import cn.codekong.imageclassificationsystemclient.config.ApiConfig;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.net.RtHttp;
import cn.codekong.imageclassificationsystemclient.service.ImageTaskService;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.ITagImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by szh on 2017/5/26.
 */

//TODO 图片任务完成情况显示有误
public class TagImagePresenter implements ITagImagePresenter {
    private static final String TAG = "xxhh";

    //获得的图片任务
    private ImageTask mImageTask;
    //完成进度TextView
    private TextView mFinishProgressTv;
    private GridView mTagResultGridView;
    //每个人物的图片数量
    private int mTaskAmount;
    //备选标签选中View情况
    private List<ArrayList<Label>> selectedOptionTagViewList = new ArrayList<>();
    //手动输入的标签
    private List<List<Label>> inputedManualTagList = new ArrayList<>();
    private Context mContext;
    private ITagImageView iTagImageView;

    public TagImagePresenter(Context context, ITagImageView iTagImageView) {
        this.mContext = context;
        this.iTagImageView = iTagImageView;
    }

    @Override
    public void getImageTaskByAmount(String taskImgAccount) {
        if (TextUtils.isEmpty(taskImgAccount)) {
            //为空
            iTagImageView.getImageTaskByAmountFailed(mContext.getString(R.string.str_unknown_error));
            return;
        }
        //网络请求图片数量
        final ImageTaskService imageTaskService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, ImageTaskService.class);
        //获取token
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)){
            imageTaskService.getImageTaskByAmount(token, taskImgAccount).enqueue(new Callback<HttpResult<ImageTask>>() {
                @Override
                public void onResponse(Call<HttpResult<ImageTask>> call, Response<HttpResult<ImageTask>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        String code = response.body().getCode();
                        String msg = response.body().getMsg();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            //任务获取成功
                            mImageTask = response.body().getData();
                            iTagImageView.getImageTaskByAmountSuccess(mImageTask);
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iTagImageView.validateError(response.body().getMsg());
                        } else if (code.equals(Constant.REQUEST_ERROR)) {
                            //任务获取失败
                            iTagImageView.getImageTaskByAmountFailed(msg);
                        } else {
                            //未知错误
                            iTagImageView.getImageTaskByAmountFailed(mContext.getString(R.string.str_unknown_error));
                        }
                    }else{
                        //服务器错误
                        iTagImageView.validateError(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<ImageTask>> call, Throwable t) {
                    //模拟数据
                    moniData();
                    //网络错误
                    iTagImageView.getImageTaskByAmountFailed(mContext.getString(R.string.str_network_error));
                }
            });
        }else{
            //token失效
            iTagImageView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }

    @Override
    public void getImageTaskById(String taskId) {
        if (TextUtils.isEmpty(taskId)) {
            //为空
            iTagImageView.getImageTaskByAmountFailed(mContext.getString(R.string.str_unknown_error));
            return;
        }
        final ImageTaskService imageTaskService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, ImageTaskService.class);
        //获取token
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)){
            imageTaskService.getImageTaskById(token, taskId).enqueue(new Callback<HttpResult<ImageTask>>() {
                @Override
                public void onResponse(Call<HttpResult<ImageTask>> call, Response<HttpResult<ImageTask>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        String code = response.body().getCode();
                        String msg = response.body().getMsg();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            //任务获取成功
                            mImageTask = response.body().getData();
                            iTagImageView.getImageTaskByIdSuccess(mImageTask);
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iTagImageView.validateError(response.body().getMsg());
                        } else if (code.equals(Constant.REQUEST_ERROR)) {
                            //任务获取失败
                            iTagImageView.getImageTaskByIdFailed(msg);
                        } else {
                            //未知错误
                            iTagImageView.getImageTaskByIdFailed(mContext.getString(R.string.str_unknown_error));
                        }
                    }else{
                        //服务器错误
                        iTagImageView.validateError(mContext.getResources().getString(R.string.str_server_error));
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<ImageTask>> call, Throwable t) {
                    //模拟数据
                    moniData();
                    //网络错误
                    iTagImageView.getImageTaskByIdFailed(mContext.getString(R.string.str_network_error));
                }
            });
        }else{
            //token失效
            iTagImageView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }

    }

    /**
     * 模拟数据
     */
    private void moniData() {
        //TODO 模拟数据
        //模拟数据进行测试
        ImageTask imageTask = new ImageTask();
        imageTask.setTask_id("1");
        List<Images> imagesList = new ArrayList<>();
        Images images = new Images();
        images.setImg_id("1");
        images.setImg_path("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        List<Label> labelList = new ArrayList<>();
        Label label1 = new Label();
        label1.setLabel_name("百度1");
        Label label2 = new Label();
        label2.setLabel_name("手写体1");
        labelList.add(label1);
        labelList.add(label2);
        images.setMachine_labels(labelList);
        images.setOption_labels(labelList);
        images.setManual_labels(labelList);
        imagesList.add(images);

        Images images2 = new Images();
        images2.setImg_id("1");
        images2.setImg_path("http://img.blog.csdn.net/20170113191612007?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvUGFuZGFfUHJvZ3JhbQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast");
        List<Label> labelList2 = new ArrayList<>();
        Label label3 = new Label();
        label3.setLabel_name("百度2");
        Label label4 = new Label();
        label4.setLabel_name("手写体2");
        labelList2.add(label3);
        labelList2.add(label4);
        images2.setMachine_labels(labelList2);
        images2.setManual_labels(labelList2);
        images2.setOption_labels(labelList2);
        imagesList.add(images2);

        Images images3 = new Images();
        images3.setImg_id("1");
        images3.setImg_path("http://img.blog.csdn.net/20170113191612007?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvUGFuZGFfUHJvZ3JhbQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast");
        List<Label> labelList3 = new ArrayList<>();
        Label label5 = new Label();
        label5.setLabel_name("百度3");
        Label label6 = new Label();
        label6.setLabel_name("手写体3");
        labelList3.add(label5);
        labelList3.add(label6);
        images3.setMachine_labels(labelList3);
        images3.setManual_labels(labelList3);
        List<Label> labelList4 = new ArrayList<>();
        Label label7 = new Label();
        label7.setLabel_name("百度3");
        labelList4.add(label7);
        images3.setOption_labels(labelList4);
        imagesList.add(images3);

        imageTask.setImages(imagesList);
        mImageTask = imageTask;
        iTagImageView.getImageTaskByAmountSuccess(mImageTask);
        //模拟数据进行测试
    }

    @Override
    public void setImageTask(ImageTask imageTask, ViewFlipper viewFlipper) {
        if (imageTask == null) {
            //为空
            iTagImageView.setImageTaskFailed(mContext.getString(R.string.str_unknown_error));
            return;
        }

        //获得该任务中所有的图片,然后开始遍历添加每一页的图片和相关数据
        List<Images> imagesList = imageTask.getImages();
        mTaskAmount = imagesList.size();
        for (int index = 0; index < mTaskAmount; index++) {
            //添加每一页的备选标签的List
            selectedOptionTagViewList.add(index, new ArrayList<>(imagesList.get(index).getOption_labels()));
            //用户输入的Tag的List,设置用户输入的标签(当用户修改结果的时候会有数据,初次获取任务不会有数据)
            int ii = 1;
            if ((imagesList.get(index).getManual_labels().size() == 1)
                    && (TextUtils.isEmpty(imagesList.get(index).getManual_labels().get(0).getLabel_name())
                    || imagesList.get(index).getManual_labels().get(0).getLabel_name().equals("null"))){
                inputedManualTagList.add(index, new ArrayList<Label>());
            }else {
                inputedManualTagList.add(index, new ArrayList<>(imagesList.get(index).getManual_labels()));
            }
            //添加item到viewFlipper
            viewFlipper.addView(addTask(viewFlipper, imagesList.get(index), index + 1, mTaskAmount));
        }
        //添加打标签结果页
        viewFlipper.addView(addTagResultView(viewFlipper));
    }


    /**
     * 添加任务到视图
     *
     * @param viewFlipper
     * @param img
     * @param index
     * @param taskAmount
     * @return
     */
    private View addTask(final ViewFlipper viewFlipper, Images img, final int index, int taskAmount) {
        //添任务视图
        final View view = View.inflate(mContext, R.layout.tag_image_item, null);
        final TextView tagProgressTv = (TextView) view.findViewById(R.id.id_tag_progress_tv);
        ImageView tagingImageView = (ImageView) view.findViewById(R.id.id_taging_imageview);
        final GridView optionTagGridView = (GridView) view.findViewById(R.id.id_option_tag_gridview);
        final GridView manualTagGridView = (GridView) view.findViewById(R.id.id_manual_tag_gridview);
        final EditText inputTagEd = (EditText) view.findViewById(R.id.id_input_tag_ed);
        Button confirmTagBtn = (Button) view.findViewById(R.id.id_confirm_tag_btn);
        //设置任务进度
        tagProgressTv.setText(String.format(mContext.getString(R.string.str_tag_progress_format), index, taskAmount));
        //加载需要打标签的图片
        Glide.with(mContext)
                .load(img.getImg_path())
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(tagingImageView);
        //设置备选标签
        optionTagGridView.setAdapter(new CommonAdapter<Label>(mContext, R.layout.tag_textview_item, img.getMachine_labels()) {
            @Override
            protected void convert(ViewHolder viewHolder, final Label item, final int position) {
                //防止数组越界(因为有最后一页结果页面)
                if (viewFlipper.getDisplayedChild() == mTaskAmount) {
                    return;
                }
                TextView textView = viewHolder.getView(R.id.id_tag_label_name_tv);
                viewHolder.setText(R.id.id_tag_label_name_tv, item.getLabel_name());
                //遍历List将选中状态恢复出来
                if (selectedOptionTagViewList.get(viewFlipper.getDisplayedChild()).contains(item)){
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.label_choose_selected_shape));
                    textView.setTextColor(Color.WHITE);
                }else{
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.label_choose_unselected_shape));
                    textView.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
                }
                viewHolder.setOnClickListener(R.id.id_tag_label_name_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView) v;
                        Label la = new Label();
                        la.setLabel_name(textView.getText().toString());
                        if (selectedOptionTagViewList.get(viewFlipper.getDisplayedChild()).contains(la)) {
                            //已经包含就移除
                            selectedOptionTagViewList.get(viewFlipper.getDisplayedChild()).remove(la);
                            textView.setBackground(mContext.getResources().getDrawable(R.drawable.circle_corner_rectangle_unselected_shape));
                            textView.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
                        }else{
                            //不包含就添加
                            selectedOptionTagViewList.get(viewFlipper.getDisplayedChild()).add(la);
                            textView.setBackground(mContext.getResources().getDrawable(R.drawable.circle_corner_rectangle_selected_shape));
                            textView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                        }
                    }
                });
            }
        });

        //更新标签setAdapter
        updateInputedTags(viewFlipper, manualTagGridView, index - 1);
        //为添加标签按钮设置点击事件
        confirmTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(inputTagEd.getText())) {
                    if (inputedManualTagList.get(viewFlipper.getDisplayedChild()).size() < 4) {
                        if (!duplicateTag(inputedManualTagList.get(viewFlipper.getDisplayedChild()), inputTagEd.getText().toString())){
                            //只能添加四个标签
                            Label label = new Label();
                            label.setLabel_name(inputTagEd.getText().toString());
                            inputedManualTagList.get(viewFlipper.getDisplayedChild()).add(label);
                            //清空输入框
                            inputTagEd.setText("");
                            updateInputedTags(viewFlipper, manualTagGridView, viewFlipper.getDisplayedChild());
                        }else{
                            //不能添加重复的标签
                            Toast.makeText(mContext, R.string.str_donot_add_duplicate_tag, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(mContext, R.string.str_only_add_four_tag, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    /**
     * 更新用户设置的标签
     *
     * @param viewFlipper
     * @param manualTagGridView
     */
    private void updateInputedTags(final ViewFlipper viewFlipper, final GridView manualTagGridView, int index) {
        Log.d(TAG, "updateInputedTags: ");
        for (List<Label> li : inputedManualTagList) {
            Log.d(TAG, "updateInputedTags: li " + li.size());
        }

        List<Label> labelList = new ArrayList<>(inputedManualTagList.get(index));
        final CommonAdapter<Label> adapter = new CommonAdapter<Label>(mContext, R.layout.category_textview_item, labelList) {
            @Override
            protected void convert(ViewHolder viewHolder, final Label item, int position) {
                if (viewFlipper.getDisplayedChild() == mTaskAmount) {
                    return;
                }
                if (!TextUtils.isEmpty(item.getLabel_name())){
                    viewHolder.setText(R.id.id_label_name_tv, item.getLabel_name());
                    viewHolder.setOnLongClickListener(R.id.id_label_name_tv, new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            inputedManualTagList.get(viewFlipper.getDisplayedChild()).remove(item);
                            updateInputedTags(viewFlipper, manualTagGridView, viewFlipper.getDisplayedChild());
                            //只执行长按动作
                            return true;
                        }
                    });
                }

            }
        };
        //设置用户输入的标签
        manualTagGridView.setAdapter(adapter);
    }

    /**
     * 添加打标签结果页面
     */
    private View addTagResultView(final ViewFlipper viewFlipper) {
        final View view = View.inflate(mContext, R.layout.tag_image_result_item, null);
        mTagResultGridView = (GridView) view.findViewById(R.id.id_tag_result_gridview);
        mFinishProgressTv = (TextView) view.findViewById(R.id.id_tag_finish_progress_textview);
        updateTagResult(viewFlipper, mTagResultGridView, mFinishProgressTv, getFinishedAmount());
        return view;
    }

    /**
     * 更新打标签结果页面
     *
     * @param tagResultGridView
     */
    private void updateTagResult(final ViewFlipper viewFlipper, GridView tagResultGridView, final TextView finishProgressTv, final int finishAmount) {
        //创建一个序列数组
        List<String> serialNumList = new ArrayList<>();
        for (int i = 0; i < mTaskAmount; i++) {
            serialNumList.add(i, i + 1 + "");
        }

        //设置结果页的GridView
        tagResultGridView.setAdapter(new CommonAdapter<String>(mContext, R.layout.circle_textview_item, serialNumList) {
            @Override
            protected void convert(final ViewHolder viewHolder, final String item, int position) {
                viewHolder.setText(R.id.id_tag_result_tv, item);
                //重置状态
                viewHolder.setBackgroundRes(R.id.id_tag_result_tv, R.drawable.circle_unselected_shape);
                viewHolder.setTextColor(R.id.id_tag_result_tv, Color.BLACK);

                //设置答过题目的item的背景色
                if (selectedOptionTagViewList.get(Integer.parseInt(item) - 1).size() != 0
                        || inputedManualTagList.get(Integer.parseInt(item) - 1).size() != 0) {
                    viewHolder.setBackgroundRes(R.id.id_tag_result_tv, R.drawable.circle_selected_shape);
                    viewHolder.setTextColor(R.id.id_tag_result_tv, Color.WHITE);
                }

                //设置任务完成情况
                finishProgressTv.setText(String.format(mContext.getResources().getString(R.string.str_tag_progress_format), finishAmount, mTaskAmount));
                viewHolder.setOnClickListener(R.id.id_tag_result_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView) v;
                        viewFlipper.setDisplayedChild(Integer.parseInt(textView.getText().toString()) - 1);
                        iTagImageView.backToModifyRes();
                    }
                });
            }
        });
    }

    /**
     * 获得已完成任务的数量
     *
     * @return
     */
    private int getFinishedAmount() {
        int amount = 0;
        for (int i = 0; i < mTaskAmount; i++) {
            if (selectedOptionTagViewList.get(i).size() != 0
                    || inputedManualTagList.get(i).size() != 0) {
                amount++;
            }
        }
        return amount;
    }

    @Override
    public void prevView(ViewFlipper taskViewFlipper, TextView prevImgBtn, TextView nextImgBtn) {
        taskViewFlipper.setInAnimation(mContext, R.anim.left_in);
        taskViewFlipper.setOutAnimation(mContext, R.anim.left_out);
        if (taskViewFlipper.getDisplayedChild() != 0) {
            //上一张图
            taskViewFlipper.showPrevious();
            if (taskViewFlipper.getDisplayedChild() == 0) {
                prevImgBtn.setText(R.string.str_no);
                nextImgBtn.setText(R.string.str_next_ques);
            } else {
                prevImgBtn.setText(R.string.str_previous_ques);
                nextImgBtn.setText(R.string.str_next_ques);
            }
        } else {
            prevImgBtn.setText(R.string.str_no);
            nextImgBtn.setText(R.string.str_next_ques);
        }
    }

    @Override
    public void nextView(ViewFlipper taskViewFlipper, TextView prevImgBtn, TextView nextImgBtn) {
        taskViewFlipper.setInAnimation(mContext, R.anim.right_in);
        taskViewFlipper.setOutAnimation(mContext, R.anim.right_out);
        if (taskViewFlipper.getDisplayedChild() != mTaskAmount) {
            taskViewFlipper.showNext();
            if (taskViewFlipper.getDisplayedChild() == mTaskAmount) {
                //最后一页结果页
                updateTagResult(taskViewFlipper, mTagResultGridView, mFinishProgressTv, getFinishedAmount());
            } else {
                prevImgBtn.setText(R.string.str_previous_ques);
                nextImgBtn.setText(R.string.str_next_ques);
            }
        } else {
            Log.d(TAG, "nextView: 结果页 " + getFinishedAmount());
            //最后一页结果页
            updateTagResult(taskViewFlipper, mTagResultGridView, mFinishProgressTv, getFinishedAmount());
        }
    }


    @Override
    public void submitImageTask(String isCommit) {
        TagResult tagResult = new TagResult();
        //设置任务id
        tagResult.setTask_id(mImageTask.getTask_id());
        List<ImgLabel> imgLabelList = new ArrayList<>();
        //首先获取用户选择和填写的结果
        for (int i = 0; i < mTaskAmount; i++) {
            ImgLabel imgLabel = new ImgLabel();
            //设置图片id
            imgLabel.setImg_id(mImageTask.getImages().get(i).getImg_id());
            //备选标签
            StringBuilder optionLabel = new StringBuilder();
            //手动输入的标签
            StringBuilder manualLabel = new StringBuilder();
            if (selectedOptionTagViewList.get(i).size() != 0) {
                for (Label label : selectedOptionTagViewList.get(i)) {
                    optionLabel.append(label.getLabel_name())
                            .append(";");
                }
            }
            if (inputedManualTagList.get(i).size() != 0) {
                for (Label la : inputedManualTagList.get(i)) {
                    manualLabel.append(la.getLabel_name())
                            .append(";");
                }
            }
            //设置该图片对应的labels
            imgLabel.setOption_label(optionLabel.toString());
            imgLabel.setManual_label(manualLabel.toString());
            imgLabelList.add(imgLabel);
        }
        //组装数据完成
        tagResult.setImg_label(imgLabelList);
        Gson gson = new Gson();
        String tagRes = gson.toJson(tagResult);
        //提交任务
        ImageTaskService imageTaskService = RtHttp.getHttpService(ApiConfig.SERVER_BASE_URL, ImageTaskService.class);
        //获得token
        String token = SaveDataUtil.getValueFromSharedPreferences(mContext, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN);
        if (!TextUtils.isEmpty(token)){
            imageTaskService.commitTask(token, tagRes, isCommit).enqueue(new Callback<HttpResult<String>>() {
                @Override
                public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        String code = response.body().getCode();
                        String msg = response.body().getMsg();
                        if (code.equals(Constant.REQUEST_SUCCESS)) {
                            //提交任务成功
                            iTagImageView.submitImageTaskSuccess(msg);
                        } else if (response.body().getCode().equals(Constant.OAUTH_FAILED)) {
                            //账户被冻结
                            iTagImageView.validateError(response.body().getMsg());
                        } else {
                            //提交任务失败
                            iTagImageView.submitImageTaskFailed(msg);
                        }
                    }else {
                        //服务器错误
                        iTagImageView.validateError(mContext.getResources().getString(R.string.str_server_error));
                    }
                }
                @Override
                public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                    iTagImageView.submitImageTaskFailed(mContext.getResources().getString(R.string.str_network_error));
                }
            });
        }else{
            //登录状态错误
            iTagImageView.validateError(mContext.getResources().getString(R.string.str_login_status_error));
        }
    }

    /**
     * 判断是否是重复标签
     * @return
     */
    private boolean duplicateTag(List<Label> labelList, String labelName) {
        boolean duplicate = false;
        for (Label label : labelList) {
            if (label.getLabel_name().equals(labelName)){
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

}

interface ITagImagePresenter {
    //通过图片数量获取图片任务
    void getImageTaskByAmount(String taskImgAmount);
    //通过任务id获取图片任务
    void getImageTaskById(String taskId);
    //设置图片任务(添加任务视图并设置数据)
    void setImageTask(ImageTask imageTask, ViewFlipper viewFlipper);

    //提交图片任务
    void submitImageTask(String isCommit);

    //向前翻页
    void prevView(ViewFlipper taskViewFlipper, TextView prevImgBtn, TextView nextImgBtn);

    //向后翻页
    void nextView(ViewFlipper taskViewFlipper, TextView prevImgBtn, TextView nextImgBtn);
}
