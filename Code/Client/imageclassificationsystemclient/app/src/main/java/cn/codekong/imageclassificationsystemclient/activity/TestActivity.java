package cn.codekong.imageclassificationsystemclient.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.codekong.imageclassificationsystemclient.R;

public class TestActivity extends TopBarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("设置");
        setTopRightButton("hh", new OnClickListener() {
            @Override
            public void onClick() {
//                Toast.makeText(TestActivity.this, "hh", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(TestActivity.this, CameraClassifierActivity.class);
//                startActivity(intent);
                shareRank();
            }
        });
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(TestActivity.this, "back", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void shareRank() {

    }
}
