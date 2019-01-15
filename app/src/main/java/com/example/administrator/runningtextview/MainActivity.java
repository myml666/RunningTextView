package com.example.administrator.runningtextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    RunningTextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.tv);
        // 设置数据
        textView.setFloat(0.00f, 2087.98f);
// 设置动画播放时间
        textView.setDuration(2000);
// 监听动画播放结束
        textView.setOnEndListener(new RunningTextView.EndListener() {
            @Override
            public void onEndFinish() {

            }
        });

    }
    public void onClick(View view){
        if(!textView.isRunning()){
            textView.start();
        }
    }
}
