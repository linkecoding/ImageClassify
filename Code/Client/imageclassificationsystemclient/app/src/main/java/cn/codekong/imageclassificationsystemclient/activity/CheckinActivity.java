package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.customizeview.CalendarView;
import cn.codekong.imageclassificationsystemclient.presenter.CheckinPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.view.ICheckinView;

public class CheckinActivity extends TopBarBaseActivity implements ICheckinView{

    @BindView(R.id.id_calendar_view)
    CalendarView calendarView;

    private CheckinPresenter checkinPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_checkin;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_checkin_record));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        checkinPresenter = new CheckinPresenter(this,this);
        Date date = new Date();
        Log.d("pyh", "init: "+(date.getYear()+1900)+" "+(date.getMonth()+1)+"");
        checkinPresenter.getCheckinData((date.getYear()+1900)+"",(date.getMonth()+1)+"");
        bindControlEvent();
    }

    /**
     * 为CalendarView绑定事件
     */
    private void bindControlEvent(){
        calendarView.setCalendarListener(new CalendarView.CalendarListener() {

            @Override
            public void prevMonthListener(String year, String month) {
                Log.d("pyh", "prevMonthListener: "+year +" " +month);
                checkinPresenter.getCheckinData(year,(Integer.parseInt(month)+1)+"");
            }

            @Override
            public boolean nextMonthListener(String year, String month) {
                Date date = new Date();
                if(date.getYear()+1900 == Integer.parseInt(year) && date.getMonth()+1 == Integer.parseInt(month)){
                    Toast.makeText(CheckinActivity.this,"当前已是最新打卡月",Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    checkinPresenter.getCheckinData(year,(Integer.parseInt(month)+1)+"");
                    return false;
                }
            }
        });
    }

    @Override
    public void getCheckinDataSuccess(List<String> days) {
        calendarView.setDate(days);

    }

    @Override
    public void getCheckinDataFailed(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateError(String msg) {
        //token失效
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
