package cn.codekong.imageclassificationsystemclient.view;

import java.util.List;

import cn.codekong.imageclassificationsystemclient.bean.ContributeImg;

/**
 * Created by 尚振鸿 on 2017/8/21. 19:40
 * mail:szh@codekong.cn
 */

public interface IContributeImgView {
    //获取贡献图片的列表成功
    void getContributeImgListSuccess(List<ContributeImg> contributeImgList);
    //获取贡献图片的列表失败
    void getContributeImgListFailed(String msg);
    //加载更多列表数据成功
    void loadMoreContributeImgSuccess(List<ContributeImg> contributeImgList);
    //上传贡献的图片成功
    void uploadContributeImgSuccess(String msg);
    //上传贡献的图片失败
    void uploadContributeImgFailed(String msg);
    //更新压缩包上传进度
    void updataUploadZipProgress(long total, long progress);
    //鉴权失败(token存在问题)
    void validateError(String msg);
}
