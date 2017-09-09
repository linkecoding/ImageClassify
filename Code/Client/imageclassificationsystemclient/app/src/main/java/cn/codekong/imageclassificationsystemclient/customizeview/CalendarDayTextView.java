package cn.codekong.imageclassificationsystemclient.customizeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class CalendarDayTextView extends AppCompatTextView {

    public boolean isSelected = false;
    private Paint paint;
    private Context mContext;

    public CalendarDayTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    public CalendarDayTextView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CalendarDayTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected) {
            canvas.translate(getWidth() / 2, getHeight() / 2);
            paint.setColor(Color.parseColor("#01B090"));
            //绘制背景圆
            canvas.drawCircle(0, 0, getWidth() / 2, paint);
            //绘制文字
            paint.setColor(Color.WHITE);
            paint.setTextSize(sp2px(17));
            String text = getText().toString();
            canvas.drawText(text, -text.length() * sp2px(5), sp2px(6), paint);
        }
    }

    private void initControl(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#01B090"));
        paint.setAntiAlias(true);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
