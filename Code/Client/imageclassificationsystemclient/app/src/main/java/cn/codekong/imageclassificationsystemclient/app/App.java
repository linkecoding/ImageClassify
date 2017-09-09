package cn.codekong.imageclassificationsystemclient.app;

import android.app.Application;
/**
 * Created by szh on 2017/5/6.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //SaveDataUtil.saveToSharedPreferences(getApplicationContext(), Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN,"");
    }
}
