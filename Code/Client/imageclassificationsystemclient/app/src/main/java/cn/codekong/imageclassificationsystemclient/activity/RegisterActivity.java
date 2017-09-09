package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;
import cn.codekong.imageclassificationsystemclient.presenter.RegisterPresenter;
import cn.codekong.imageclassificationsystemclient.view.IRegisterView;

public class RegisterActivity extends TopBarBaseActivity  implements IRegisterView{
    private static final String TAG = "RegisterActivity";
    private RegisterPresenter mRegisterPresenter;

    @BindView(R.id.id_register_username)
    EditText mUsername;
    @BindView(R.id.id_register_phone_number)
    EditText mPhoneNumber;
    @BindView(R.id.id_register_identify_code)
    EditText mIdentifyCode;
    @BindView(R.id.id_register_get_identify_code)
    TextView mGetIdentifyCode;
    @BindView(R.id.id_register_input_password_edit)
    EditText mInputPasswordEdit;
    @BindView(R.id.id_register_btn)
    Button mRegisterBtn;

    //验证码倒计时
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mGetIdentifyCode.setText(millisUntilFinished/1000 + "秒");
        }

        @Override
        public void onFinish() {
            mGetIdentifyCode.setClickable(true);
            mGetIdentifyCode.setText("获取验证码");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_register));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mRegisterPresenter = new RegisterPresenter(this, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @OnClick({R.id.id_register_get_identify_code, R.id.id_register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_register_get_identify_code:
                mRegisterPresenter.getVerificationCode(mPhoneNumber.getText().toString(), mGetIdentifyCode, timer);
                break;
            case R.id.id_register_btn:
                mRegisterPresenter.register(mUsername.getText().toString(), mPhoneNumber.getText().toString(),
                        mInputPasswordEdit.getText().toString(), mIdentifyCode.getText().toString());
                break;
        }
    }

    @Override
    public void registerSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //跳转
        finish();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void registerFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //清空输入框
        mUsername.setText("");
        mPhoneNumber.setText("");
        mIdentifyCode.setText("");
        mInputPasswordEdit.setText("");
    }

    @Override
    public void validateError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
