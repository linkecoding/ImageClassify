package cn.codekong.imageclassificationsystemclient.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import cn.codekong.imageclassificationsystemclient.R;

/**
 * Created by Administrator on 2017/5/26.
 */

public class CalendarView extends LinearLayout {

    private ImageView nextMonth, prevMonth;
    private TextView txtDate;
    private GridView calendarGridView;
    private Calendar curDate;
    private String displayFormat;
    private Date selectedDate;
    private CalendarListener mCalendarListener;
    private ArrayList<Date> cells;
    private HashSet<String> daySet = new HashSet<>();
    private CalendarAdapter calendarAdapter;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * 初始化控件
     *
     * @param context
     * @param attrs
     */
    private void initControl(Context context, AttributeSet attrs) {
        curDate = Calendar.getInstance();
        bindControl(context);
        bindControlEvent();

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);
        try {
            displayFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (displayFormat == null) {
                displayFormat = "yyyy MMM";
            }
        } finally {
            ta.recycle();
        }
        //渲染效果
        renderCalendar();
    }

    /**
     * 绑定控件事件
     */
    private void bindControlEvent() {
        prevMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, -1);
                mCalendarListener.prevMonthListener(curDate.get(Calendar.YEAR) + "", curDate.get(Calendar.MONTH) + "");
                renderCalendar();
            }
        });
        nextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, 1);
                boolean isCurMonth = mCalendarListener.nextMonthListener(curDate.get(Calendar.YEAR) + "", curDate.get(Calendar.MONTH) + "");
                if (!isCurMonth)
                    renderCalendar();
                else {
                    curDate.add(Calendar.MONTH, -1);
                }
            }
        });
    }

    /**
     * 界面渲染
     */
    private void renderCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat(displayFormat);
        txtDate.setText(sdf.format(curDate.getTime()));
        selectedDate = curDate.getTime();

        if (cells == null) {
            cells = new ArrayList<>();
        } else {
            cells.clear();
        }
        Calendar calendar = (Calendar) curDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -prevDays);

        int maxCellCount;
        int maxDayNum = calendar.getMaximum(Calendar.DAY_OF_MONTH) + prevDays;
        //确定cells的数量
        if (maxDayNum > 5 * 7) {
            maxCellCount = 6 * 7;
        } else if (maxDayNum > 4 * 7) {
            maxCellCount = 5 * 7;
        } else {
            maxCellCount = 4 * 7;
        }
        while (cells.size() < maxCellCount) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendarAdapter = new CalendarAdapter(getContext(), cells);
        calendarGridView.setAdapter(calendarAdapter);
    }

    /**
     * 绑定控件
     *
     * @param context
     */
    private void bindControl(Context context) {
        LayoutInflater inflate = LayoutInflater.from(context);
        inflate.inflate(R.layout.calendar_view, this);
        prevMonth = (ImageView) findViewById(R.id.id_prev_month);
        nextMonth = (ImageView) findViewById(R.id.id_next_month);
        calendarGridView = (GridView) findViewById(R.id.grid_date);
        txtDate = (TextView) findViewById(R.id.id_txt_date);
    }

    public void setCalendarListener(CalendarListener calendarListener) {
        this.mCalendarListener = calendarListener;
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {

        LayoutInflater layoutInflate;

        public CalendarAdapter(@NonNull Context context, ArrayList<Date> days) {
            super(context, R.layout.calendar_day_item, days);
            layoutInflate = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Date date = getItem(position);
            if (convertView == null) {
                convertView = layoutInflate.inflate(R.layout.calendar_day_item, parent, false);
            }
            int day = date.getDate();
            ((TextView) convertView).setText(String.valueOf(day));
            boolean isTheSameMonth = false;
            if (date.getMonth() == selectedDate.getMonth()) {
                isTheSameMonth = true;
            }
            if (isTheSameMonth) {
                ((CalendarDayTextView) convertView).setTextColor(Color.parseColor("#000000"));
            } else {
                ((CalendarDayTextView) convertView).setTextColor(Color.parseColor("#666666"));
            }
            //判断当前日期是否是已签到的日期
            if (daySet.size()>0  && daySet.contains(((TextView) convertView).getText()) && isTheSameMonth) {
                ((CalendarDayTextView) convertView).isSelected = true;
            }
            return convertView;
        }
    }

    /**
     * 设置每个月打卡日期
     * @param days
     */
    public void setDate(List<String> days) {
        this.daySet = new HashSet<>();
        if (days !=null && days.size()>0){
            daySet.addAll(days);
        }
        calendarAdapter.notifyDataSetChanged();
        calendarGridView.setAdapter(calendarAdapter);
    }

    public interface CalendarListener {
        void prevMonthListener(String year, String month);

        boolean nextMonthListener(String year, String month);
    }
}
