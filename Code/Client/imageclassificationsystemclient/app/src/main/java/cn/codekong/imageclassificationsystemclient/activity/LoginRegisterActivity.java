package cn.codekong.imageclassificationsystemclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.codekong.imageclassificationsystemclient.R;

public class LoginRegisterActivity extends AppCompatActivity {

    @BindView(R.id.id_phone_login)
    Button phoneLogin;
    @BindView(R.id.id_register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.id_phone_login, R.id.id_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_phone_login:
                Log.d("szhszh", "onClick: 1");
                Intent login = new Intent(LoginRegisterActivity.this, LoginActivity.class);
                startActivity(login);
                break;
            case R.id.id_register:
                Log.d("szhszh", "onClick: 2");
                Intent register = new Intent(LoginRegisterActivity.this, RegisterActivity.class);
                startActivity(register);
                break;
        }
    }

}
