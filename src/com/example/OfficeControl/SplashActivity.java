package com.example.OfficeControl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.OfficeControl.fragment.R;

/**
 * Created by gm on 2016/5/29.
 */
public class SplashActivity extends Activity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        startMainAvtivity();
    }

    private void startMainAvtivity() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();//结束本Activity
            }
        }, 1000);//设置执行时间
    }
}
