package com.notenow.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.notenow.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView app_id = (TextView) findViewById(R.id.app_id);
        TextView app_ver = (TextView) findViewById(R.id.app_ver);
        TextView app_dev = (TextView) findViewById(R.id.dev_name);


        app_id.setText(String.format("%s", getResources().getString(R.string.app_name)));
        app_ver.setText(String.format("v%s", "1.0"));
        app_dev.setText(String.format("%s", getResources().getString(R.string.devloper_name)));
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
