package com.codekong.tflibrary.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by 尚振鸿 on 2017/8/11. 10:27
 * mail:szh@codekong.cn
 */

public class FileUtil {
    /**
     * 获得SD卡根目录
     * @param context
     * @return
     */
    public static String getExternalStoragePath(Context context){
        //权限判断
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return null;
        }
        return Environment.getExternalStorageDirectory().getPath();
    }
}

