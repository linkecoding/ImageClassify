package cn.codekong.imageclassificationsystemclient.customizeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by 尚振鸿 on 2017/6/11. 22:19
 * mail:szh@codekong.cn
 */

public class NoTouchScrollView extends ScrollView {
    public NoTouchScrollView(Context context) {
        this(context, null);
    }

    public NoTouchScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoTouchScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return false;
    }
}
