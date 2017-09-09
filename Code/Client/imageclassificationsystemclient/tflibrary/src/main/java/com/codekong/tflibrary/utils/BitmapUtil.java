package com.codekong.tflibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Bitmap处理工具类
 * Created by 尚振鸿 on 2017/8/16. 14:20
 * mail:szh@codekong.cn
 */

public class BitmapUtil {

    /**
     * 将原始Bitmap调整为dstWidth * dstHeight
     * @param orgImgBitmap
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap orgImgBitmap, int dstWidth, int dstHeight) {
        int orgWidth = orgImgBitmap.getWidth();
        int orgHeight = orgImgBitmap.getHeight();

        float scaleWidth = ((float) dstWidth) / orgWidth;
        float scaleHeight = ((float) dstHeight) / orgHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(orgImgBitmap, 0, 0, orgWidth, orgHeight, matrix, true);
    }
}
