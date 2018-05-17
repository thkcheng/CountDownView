package com.app.demo;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by lishu on 2018/4/16.
 */
public class MyClockView extends RelativeLayout {
    private float dayTextSize, hourTextSize, minTextSize, secTextSize;
    private FlipClockView  hourTextView, minTextView, secTextView;
    private TextView dTextView, hTextView, mTextView, sTextView;
    private LayoutParams dayLayoutParams, hourLayoutParams, minLayoutParams, secLayoutParams;
    private DownCountTimerListener mDownCountTimerListener;
    private long totalTime = 0;
    private boolean isRunning = true;
    private int screenW;
    private CountDownViewTimer mCountdown = null;
    private long outNumber = 0;//超过的最大计数的时间（秒数）

    public MyClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyClockView(Context context) {
        this(context, null);
    }

    public MyClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        screenW = getScreenWidth(context);
        TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.MyClock);
        dayTextSize = tArray.getDimension(R.styleable.MyClock_dayTextSize, 26f);
        hourTextSize = tArray.getDimension(R.styleable.MyClock_hourTextSize, 26f);
        minTextSize = tArray.getDimension(R.styleable.MyClock_minTextSize, 26f);
        secTextSize = tArray.getDimension(R.styleable.MyClock_secTextSize, 26f);
        int dayTextColor = tArray.getColor(R.styleable.MyClock_dayTextColor, 0xffffff);
        int hourTextColor = tArray.getColor(R.styleable.MyClock_hourTextColor, 0xffffff);
        int minTextColor = tArray.getColor(R.styleable.MyClock_minTextColor, 0xffffff);
        int secTextColor = tArray.getColor(R.styleable.MyClock_secTextColor, 0xffffff);

        Drawable dayTextBg = tArray.getDrawable(R.styleable.MyClock_dayTextBackground);
        Drawable hourTextBg = tArray.getDrawable(R.styleable.MyClock_hourTextBackground);
        Drawable minTextBg = tArray.getDrawable(R.styleable.MyClock_minTextBackground);
        Drawable secTextBg = tArray.getDrawable(R.styleable.MyClock_secTextBackground);

        tArray.recycle();

//        dayTextView = new FlipClockView(context);
        hourTextView = new FlipClockView(context);
        minTextView = new FlipClockView(context);
        secTextView = new FlipClockView(context);

//        dayTextView.setId(R.id.dayTextView);
        hourTextView.setId(R.id.hourTextView);
        minTextView.setId(R.id.minTextView);
        secTextView.setId(R.id.secTextView);

        dTextView = new TextView(context);
        hTextView = new TextView(context);
        mTextView = new TextView(context);
        sTextView = new TextView(context);
        dTextView.setText("DAYS");
        hTextView.setText("HOURS");
        mTextView.setText("MINUTES");
        sTextView.setText("SECONDS");
        dTextView.setTextColor(Color.parseColor("#ffffff"));
        hTextView.setTextColor(Color.parseColor("#ffffff"));
        mTextView.setTextColor(Color.parseColor("#ffffff"));
        sTextView.setTextColor(Color.parseColor("#ffffff"));
        dTextView.setTextSize(10f);
        hTextView.setTextSize(10f);
        mTextView.setTextSize(10f);
        sTextView.setTextSize(10f);

//        dayTextView.setClockBackground(dayTextBg);
//        dayTextView.setClockTextSize(dayTextSize);
//        dayTextView.setClockTextColor(dayTextColor);
        hourTextView.setClockBackground(hourTextBg);
        hourTextView.setClockTextSize(dayTextSize);
        hourTextView.setClockTextColor(hourTextColor);
        minTextView.setClockBackground(minTextBg);
        minTextView.setClockTextSize(dayTextSize);
        minTextView.setClockTextColor(minTextColor);
        secTextView.setClockBackground(secTextBg);
        secTextView.setClockTextSize(dayTextSize);
        secTextView.setClockTextColor(secTextColor);
        //secTextView.setFlipDirection(false);

        //Log.e("---->","屏幕的宽"+screenW);
        int viewWidth = (int) (screenW * 0.14);
        int viewMargin = (int) (screenW * 0.05);
        dTextView.setWidth(viewWidth);
        dTextView.setGravity(Gravity.CENTER);
        hTextView.setWidth(viewWidth);
        hTextView.setGravity(Gravity.CENTER);
        mTextView.setWidth(viewWidth);
        mTextView.setGravity(Gravity.CENTER);
        sTextView.setWidth(viewWidth);
        sTextView.setGravity(Gravity.CENTER);

//        dayLayoutParams = new LayoutParams(viewWidth, viewWidth);
//        dayLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
//        dayLayoutParams.setMargins(0, 60, 0, 0);
//        addView(dayTextView, dayLayoutParams);
//        LayoutParams dLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dLayoutParams.addRule(RelativeLayout.BELOW, R.id.dayTextView);
//        dLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.dayTextView);
//        dLayoutParams.setMargins(0, 5, 0, 60);
//        addView(dTextView, dLayoutParams);

        hourLayoutParams = new LayoutParams(viewWidth,viewWidth);
        hourLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.dayTextView);
        hourLayoutParams.setMargins(0, 0, 20, 0);
        addView(hourTextView, hourLayoutParams);
        LayoutParams hLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        hLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.hourTextView);
        hLayoutParams.addRule(RelativeLayout.BELOW, R.id.hourTextView);
        addView(hTextView, hLayoutParams);

        minLayoutParams = new LayoutParams(viewWidth,viewWidth);
        minLayoutParams.setMargins(0, 0, 20, 0);
        minLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.hourTextView);
        addView(minTextView, minLayoutParams);
        LayoutParams mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.minTextView);
        mLayoutParams.addRule(RelativeLayout.BELOW, R.id.minTextView);
        addView(mTextView, mLayoutParams);

        secLayoutParams = new LayoutParams(viewWidth,viewWidth);
        secLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.minTextView);
        addView(secTextView, secLayoutParams);
        LayoutParams sLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        sLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.secTextView);
        sLayoutParams.addRule(RelativeLayout.BELOW, R.id.secTextView);
        addView(sTextView, sLayoutParams);

//        dayTextView.setClockTime("00");
        hourTextView.setClockTime("00");
        minTextView.setClockTime("00");
        secTextView.setClockTime("00");
    }

    public interface DownCountTimerListener {

        void stopDownCountTimer();

    }

    /**
     * 结束倒计时
     */
    public void pauseDownCountTimer() {
        if (mCountdown!=null){
            mCountdown.cancel();
            hourTextView.setClockTime("00");
            minTextView.setClockTime("00");
            secTextView.setClockTime("00");
            mCountdown.onFinish();
        }
    }

    public void setDownCountTimerListener(DownCountTimerListener listener) {
        this.mDownCountTimerListener = listener;
    }

    /**
     * 获取设置倒计时的总时间
     *
     * @return
     */
    public long getDownCountTime() {
        return totalTime;
    }

    /**
     * 设定需要倒计时的总共时间
     *
     * @param totalDownCountTimes
     */
    public void setDownCountTime(long totalDownCountTimes) {
        this.totalTime = totalDownCountTimes;
        pauseDownCountTimer();
    }

    /**
     * 设定倒计时的时间开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     */
    public void setDownCountTime(long startTime, long endTime) {
        this.totalTime = endTime - startTime;
        pauseDownCountTimer();
    }

    public class CountDownViewTimer extends CountDownTimer {

        public CountDownViewTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCountDownTime(millisUntilFinished+ 1000L);
        }

        @Override
        public void onFinish() {
            isRunning = false;
            if (null != mDownCountTimerListener) {
                mDownCountTimerListener.stopDownCountTimer();
            }
        }
    }

    public void getCountDownTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");// 初始化formatter的转换格式
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(time);
        String[] str = hms.split(":");
        hourTextView.setClockTime(str[0]);
        minTextView.setClockTime(str[1]);
        secTextView.setClockTime(str[2]);
        int second = getClockSecValue();
        int min = getClockMinValue();
        int hour = getClockHourValue();
        if (second > 0) {//秒
            secTextView.smoothFlip();
        } else {
            if (min > 0 && second == 0) {//分鐘
                minTextView.smoothFlip();
                secTextView.setClockTime("60");
                secTextView.smoothFlip();
            } else {//小時
                if (hour > 0 && min == 0 && second == 0) {
                    hourTextView.smoothFlip();
                    minTextView.setClockTime("60");
                    minTextView.smoothFlip();
                    secTextView.setClockTime("60");
                    secTextView.smoothFlip();
                }
            }
        }
//            }
    }

    /**
     * 开始倒计时
     */
    public void startTime(long LASTTIME) {
        this.totalTime = LASTTIME;
        if (null == mCountdown) {
            mCountdown = new CountDownViewTimer(totalTime, 1000);
            mCountdown.start();
            isRunning = true;
        }else {
            mCountdown.cancel();
            mCountdown.start();
        }
    }

    /**
     * 关闭倒计时
     */
    public void cancel() {
        if (null != mCountdown) {
            mCountdown.cancel();
            mCountdown = null;
            isRunning = false;
        }
    }

    public int getScreenWidth(Context mContext) {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public int getClockHourValue() {
        return hourTextView.getCurrentValue();
    }

    public int getClockMinValue() {
        return minTextView.getCurrentValue();
    }

    public int getClockSecValue() {
        return secTextView.getCurrentValue();
    }
}