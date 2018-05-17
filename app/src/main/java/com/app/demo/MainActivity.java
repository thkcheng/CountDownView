package com.app.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CountDownView tvCDView;

    private long CREATE_TIME = 1523171128000L;
    private long NOW_TIME = 1523171379000L;
    private int LIMI_TTIME = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCDView = findViewById(R.id.tvCDView);

        long CREATETIME_MINUTES = CREATE_TIME + LIMI_TTIME * 60 * 1000;
        long LASTTIME = CREATETIME_MINUTES - NOW_TIME; //剩余倒计时时间

        tvCDView.startTime(LASTTIME); //开始倒计时

        tvCDView.setOnTimeoutListener(new CountDownView.OnTimeOutListener() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁倒计时
        tvCDView.cancel();
    }
}
