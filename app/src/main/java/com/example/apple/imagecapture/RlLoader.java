package com.example.apple.imagecapture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class RlLoader extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout relativelayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        relativelayout.setBackgroundColor(Color.parseColor("#cc000000"));
        relativelayout.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params1.addRule(RelativeLayout.CENTER_IN_PARENT);
        params2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        ProgressBar progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setBackgroundColor(Color.TRANSPARENT);
        progressBar.setId(View.generateViewId());

        TextView tv = new TextView(this);
        params2.addRule(RelativeLayout.BELOW, progressBar.getId());
        tv.setText("Kindly wait...");
        tv.setTextColor(Color.WHITE);

        relativelayout.addView(progressBar, params1);
        relativelayout.addView(tv, params2);
        setContentView(relativelayout);
    }
}