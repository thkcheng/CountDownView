package com.app.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by thkcheng on 2017/10/8.
 */

public class CountDownView extends LinearLayout {

    //时、分、秒、毫秒View
    protected TextView tvHours, tvMinutes, tvSeconds, tvMilliSecond;
    //间隔符:
    protected TextView spaceOne, spaceTwo, spaceThree;
    private int mTextColor = Color.WHITE;
    private int mSpaceColor = Color.BLACK;
    private int mBackgroundColor = Color.BLACK;
    private int mTextSize = 20;
    private int mRadius = 5;
    private int mPaddingHorizontal = 4;
    private int mPaddingVertical = 0;
    private int mBackgroundImg = R.drawable.ic_launcher_background;
    private CountDownViewTimer mCountdown = null;
    private OnTimeOutListener mListener = null;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        //设置单位字体sp 圆角dp ...
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, dm);
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mRadius, dm);
        mPaddingHorizontal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPaddingHorizontal, dm);
        mPaddingVertical= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPaddingVertical, dm);

        //设置动态属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mTextColor = array.getColor(R.styleable.CountDownView_cdv_textColor, mTextColor);
        mSpaceColor = array.getColor(R.styleable.CountDownView_cdv_spaceColor, mSpaceColor);
        mBackgroundColor = array.getColor(R.styleable.CountDownView_cdv_backgroundColor, mBackgroundColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.CountDownView_cdv_textSize, mTextSize);
        mRadius = array.getDimensionPixelOffset(R.styleable.CountDownView_cdv_radius, mRadius);
        mPaddingHorizontal = array.getDimensionPixelOffset(R.styleable.CountDownView_cdv_textPaddingHorizantal, mPaddingHorizontal);
        mPaddingVertical = array.getDimensionPixelOffset(R.styleable.CountDownView_cdv_textPaddingVertical, mPaddingVertical);
        mBackgroundImg = array.getResourceId(R.styleable.CountDownView_cdv_backgroundImg, mBackgroundImg);
        array.recycle(); //记得回收

        //设置父布局
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(mBackgroundColor);
        drawable.setCornerRadius(mRadius);

        //设置时
        tvHours = new TextView(context);
        tvHours.setLayoutParams(layoutParams);
        tvHours.setTextColor(mTextColor);
        tvHours.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        tvHours.setText("00");
        tvHours.setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical);
        tvHours.setBackground(drawable);
        //设置分
        tvMinutes = new TextView(context);
        tvMinutes.setLayoutParams(layoutParams);
        tvMinutes.setTextColor(mTextColor);
        tvMinutes.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        tvMinutes.setText("00");
        tvMinutes.setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical);
        tvMinutes.setBackground(drawable);
        //设置秒
        tvSeconds = new TextView(context);
        tvSeconds.setLayoutParams(layoutParams);
        tvSeconds.setTextColor(mTextColor);
        tvSeconds.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        tvSeconds.setText("00");
        tvSeconds.setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical);
        tvSeconds.setBackground(drawable);
        //设置毫秒
        tvMilliSecond = new TextView(context);
        tvMilliSecond.setLayoutParams(layoutParams);
        tvMilliSecond.setTextColor(mTextColor);
        tvMilliSecond.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        tvMilliSecond.setText("00");
        tvMilliSecond.setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical);
        tvMilliSecond.setBackground(drawable);
        //设置时-间隔符
        spaceOne = new TextView(context);
        spaceOne.setLayoutParams(layoutParams);
        spaceOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        spaceOne.setTextColor(mSpaceColor);
        spaceOne.setText(":");
        //设置分-间隔符
        spaceTwo = new TextView(context);
        spaceTwo.setLayoutParams(layoutParams);
        spaceTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        spaceTwo.setTextColor(mSpaceColor);
        spaceTwo.setText(":");
        //设置秒-间隔符
        spaceThree = new TextView(context);
        spaceThree.setLayoutParams(layoutParams);
        spaceThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        spaceThree.setTextColor(mSpaceColor);
        spaceThree.setText(":");

        //添加到父布局LinearLayout中
        addView(tvHours);
        addView(spaceOne);
        addView(tvMinutes);
        addView(spaceTwo);
        addView(tvSeconds);
        addView(spaceThree);
        addView(tvMilliSecond);
    }

    /**
     *
     * @param LASTTIME 剩余时间
     */
    public void startTime(long LASTTIME) {
        if (null == mCountdown) {
            mCountdown = new CountDownViewTimer(LASTTIME, 10);
            mCountdown.start();
        } else {
            //...
        }
    }

    public class CountDownViewTimer extends CountDownTimer {

        public CountDownViewTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCountDownTime(millisUntilFinished);
        }

        @Override
        public void onFinish() {
            if (null != mListener) {
                mListener.onFinish();
            }
        }
    }

    public void getCountDownTime(long time) {
        int hour = (int) (time / 1000 / 60 / 60);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SS");// 初始化formatter的转换格式
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(time);
        String[] str = hms.split(":");
        tvHours.setText(str[0]);
        tvMinutes.setText(str[1]);
        tvSeconds.setText(str[2]);
        tvMilliSecond.setText(str[3]);
        /*
        if (hour > 0) {
            tvHours.setVisibility(VISIBLE);
            spaceOne.setVisibility(VISIBLE);
        } else {
            tvHours.setVisibility(GONE);
            spaceOne.setVisibility(GONE);
        }
        */
    }

    /**
     * 关闭倒计时
     */
    public void cancel() {
        if (null != mCountdown) {
            mCountdown.cancel();
            mCountdown = null;
        }
    }

    public void setOnTimeoutListener(OnTimeOutListener listener){
        mListener = listener;
    }

    public interface OnTimeOutListener {
        void onFinish();
    }

}
