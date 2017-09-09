package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.config.ApiConstant;
import cn.codekong.imageclassificationsystemclient.config.Constant;
import cn.codekong.imageclassificationsystemclient.presenter.ModifyPasswordPresenter;
import cn.codekong.imageclassificationsystemclient.util.ActivityCollector;
import cn.codekong.imageclassificationsystemclient.util.SaveDataUtil;
import cn.codekong.imageclassificationsystemclient.view.IModifyPasswordView;

public class ModifyPasswordActivity extends TopBarBaseActivity implements IModifyPasswordView {

    @BindView(R.id.id_modify_old_password)
    EditText oldPassword;
    @BindView(R.id.id_modify_new_password)
    EditText newPassword;
    @BindView(R.id.id_confirm_password)
    EditText confirmPassword;

    private ModifyPasswordPresenter modifyPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("修改密码");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        modifyPasswordPresenter = new ModifyPasswordPresenter(this,this);
    }

    @Override
    public void oldPasswordInconsistent(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        oldPassword.setText("");
        oldPassword.setFocusable(true);
    }

    @Override
    public void confirmPasswordInconsistent(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        newPassword.setFocusable(true);
    }

    @Override
    public void modifyPasswordFailed(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void modifyPasswordSuccess(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        SaveDataUtil.saveToSharedPreferences(this, Constant.SHAREDPREFERENCES_DEFAULT_NAME,
                ApiConstant.OAUTH_TOKEN,"");
    }

    @Override
    public void validateError(String msg) {
        //token失效
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.id_modify_password)
    public void onClick() {
        //检查新旧密码是否一致
        boolean isOldNewPasswordConsistent = modifyPasswordPresenter.checkConfirmPassword(newPassword.getText().toString(),
                confirmPassword.getText().toString());
        if (isOldNewPasswordConsistent){
            modifyPasswordPresenter.modifyPassword(oldPassword.getText().toString(),
                    newPassword.getText().toString());
        }
    }
}
