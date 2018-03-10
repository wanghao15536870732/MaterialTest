package com.example.lab.android.nuc.materialtest.Activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lab.android.nuc.materialtest.Downalod.AutoInstall;
import com.example.lab.android.nuc.materialtest.R;

public class FriendActivity extends AppCompatActivity implements View.OnClickListener{

    private Button change_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        change_view = (Button) findViewById(R.id.diaplay_apk);
        //点击切换头像按钮调用相册选图片进行显示
        change_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diaplay_apk:
                String fileName = AutoInstall.getdoanloadUrl().substring(AutoInstall.getdoanloadUrl().lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                AutoInstall.setUrl(directory + fileName);
                AutoInstall.install(FriendActivity.this);
        }
    }
}
