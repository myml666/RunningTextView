package com.example.administrator.runningtextview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DecimalFormat;

public class RunningTextView extends TextView implements IRaiseNumber {
    public RunningTextView(Context context) {
        this(context, null);
    }

    public RunningTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RunningTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextColor(context.getResources().getColor(R.color.colorAccent));
        setTextSize(30);
    }

    private static final int STOPPED = 0;
    private static final int RUNNING = 1;
    private int mPlayingState = STOPPED;
    private float toNumber;
    private float fromNumber;
    private long duration = 1500;
    private int numberType = 2;
    private DecimalFormat fnum = new DecimalFormat("##0.00");
    private EndListener mEndListener = null;

    public boolean isRunning() {
//返回当前运行状态
        return (mPlayingState == RUNNING);
    }

    private void runFloat() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(fromNumber, toNumber);
        valueAnimator.setDuration(duration);
        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        setText(fnum.format(Float.parseFloat(valueAnimator
                                .getAnimatedValue().toString())));
                        if (valueAnimator.getAnimatedFraction() >= 1) {
//大于等于1时认为动画运行结束
                            mPlayingState = STOPPED;
                            if (mEndListener != null)
                                mEndListener.onEndFinish();
                        }
                    }

                });
        valueAnimator.start();
    }

    private void runInt() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) fromNumber,
                (int) toNumber);
        valueAnimator.setDuration(duration);
        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        setText(valueAnimator.getAnimatedValue().toString());
                        if (valueAnimator.getAnimatedFraction() >= 1) {
//置标志位
                            mPlayingState = STOPPED;
                            if (mEndListener != null)
                                mEndListener.onEndFinish();
                        }
                    }
                });
        valueAnimator.start();
    }

    @Override
    public void start() {
        if (!isRunning()) {
            mPlayingState = RUNNING;
            if (numberType == 1)
                runInt();
            else
                runFloat();
        }
    }

    @Override
    public void setFloat(float fromNum, float toNum) {
        toNumber = toNum;
        numberType = 2;
        fromNumber = fromNum;
    }

    @Override
    public void setInteger(int fromNum, int toNum) {
        toNumber = toNum;
        numberType = 1;
        fromNumber = fromNum;
    }

    @Override
    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public void setOnEndListener(EndListener callback) {
        mEndListener = callback;
    }

    public interface EndListener {
        void onEndFinish();
    }

}
