package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;

public class SettingsActivity extends TopBarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_settings));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                    finish();
            }
        });
    }

    @OnClick({R.id.id_modify_user_info, R.id.id_modify_password, R.id.id_exit_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_modify_user_info:
                Intent modifyInfoIntent = new Intent(SettingsActivity.this,ModifyUserInfoActivity.class);
                startActivity(modifyInfoIntent);
                break;
            case R.id.id_modify_password:
                Intent modifyPasswordIntent = new Intent(SettingsActivity.this,ModifyPasswordActivity.class);
                startActivity(modifyPasswordIntent);
                break;
            case R.id.id_exit_login:
                //清空当前用户信息
                SaveDataUtil.saveToSharedPreferences(this, Constant.SHAREDPREFERENCES_DEFAULT_NAME, ApiConstant.OAUTH_TOKEN, "");
                Intent loginIntent = new Intent(SettingsActivity.this,LoginRegisterActivity.class);
                startActivity(loginIntent);
                finish();
                break;
        }
    }
}
