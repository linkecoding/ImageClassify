package cn.codekong.imageclassificationsystemclient.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ListView;

import com.zhy.adapter.abslistview.CommonAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.codekong.imageclassificationsystemclient.R;

/**
 * Created by 尚振鸿 on 2017/8/11. 19:53
 * mail:szh@codekong.cn
 */

public class ShareUtil {
    private enum SHARE_TYPE {SHARE_TEXT, SHARE_IMAGE};

    /**
     * 分享排行榜截图
     *
     * @param context
     * @param listView
     * @param handler
     */
    public static void shareRankShot(final Context context, final ListView listView, final Handler handler) {
        if (context != null && listView != null) {
            CommonAdapter adapter = (CommonAdapter) listView.getAdapter();
            int itemsCount = adapter.getCount();
            int allItemsHeight = 0;
            int itemWidth = 0;
            final List<Bitmap> bitmapList = new ArrayList<Bitmap>();

            //获取ListView的Bitmap集合
            for (int i = 0; i < itemsCount; i++) {
                View childView = adapter.getView(i, null, listView);
                Drawable defaultDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
                childView.measure(MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                itemWidth = childView.getMeasuredWidth();
                childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
                childView.setDrawingCacheEnabled(true);
                childView.buildDrawingCache();
                bitmapList.add(childView.getDrawingCache());
                allItemsHeight += childView.getMeasuredHeight();
            }

            final int finalItemWidth = itemWidth;
            final int finalAllItemsHeight = allItemsHeight;
            //开启子线程合并图片并分享
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bigBitmap = Bitmap.createBitmap(finalItemWidth, finalAllItemsHeight, Bitmap.Config.ARGB_8888);
                    Canvas bigCanvas = new Canvas(bigBitmap);
                    Paint paint = new Paint();
                    int iHeight = 0;

                    for (int i = 0; i < bitmapList.size(); i++) {
                        Bitmap bmp = bitmapList.get(i);
                        bigCanvas.drawBitmap(bmp, 0, iHeight, paint);
                        iHeight += bmp.getHeight();
                        bmp.recycle();
                        bmp = null;
                    }

                    String screenShotPathName = "";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);

                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        //以当前时间作为图片前缀
                        final String screenShotName = Environment.getExternalStorageDirectory().getPath()
                                + File.separator
                                + simpleDateFormat.format(new Date())
                                + "screenshot.png";
                        try {
                            FileOutputStream out = new FileOutputStream(screenShotName);
                            boolean isSaved = bigBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            if (isSaved) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        share(context, SHARE_TYPE.SHARE_IMAGE, "我的排名",
                                                "分享排名", screenShotName);
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }
    }

    /**
     * 分享功能
     *
     * @param context
     * @param shareType
     * @param shareContent
     * @param shareTitle
     * @param imageUriString
     */
    private static void share(Context context, SHARE_TYPE shareType, String shareContent,
                              String shareTitle, String imageUriString) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        switch (shareType) {
            case SHARE_TEXT:
                intent.setType("text/plain");
                break;
            case SHARE_IMAGE:
                intent.setType("image/*");
                break;
            default:
                intent.setType("text/plain");
                break;
        }
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageUriString)));
        intent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, shareTitle));
    }
}