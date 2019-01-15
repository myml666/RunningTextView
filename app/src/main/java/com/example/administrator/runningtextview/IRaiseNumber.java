package com.example.administrator.runningtextview;

public interface IRaiseNumber {
    void start();

    void setFloat(float fromNum, float toNum);

    void setInteger(int fromNum, int toNum);

    void setDuration(long duration);

    void setOnEndListener(RunningTextView.EndListener callback);

}
