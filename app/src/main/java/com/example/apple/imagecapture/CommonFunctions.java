package com.example.apple.imagecapture;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CommonFunctions {
    private static RelativeLayout enabledRelativeLayout;
    public static void enableRlLoader(final Activity activity, boolean rlLoaderFlag){
        if(rlLoaderFlag)
        {
            RelativeLayout relativelayout = new RelativeLayout(activity);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            relativelayout.setBackgroundColor(Color.parseColor("#cc000000"));
            relativelayout.setClickable(true);
            relativelayout.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams progressBarParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

            ProgressBar progressBar = new ProgressBar(activity,null,android.R.attr.progressBarStyleSmall);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setBackgroundColor(Color.TRANSPARENT);
            Drawable drawable = activity.getResources().getDrawable(R.drawable.progress_small);
            progressBar.setIndeterminateDrawable(drawable);
            progressBar.setId(View.generateViewId());

            TextView tv = new TextView(activity);
            textViewParams.addRule(RelativeLayout.BELOW, progressBar.getId());
            tv.setText("Kindly wait...");
            tv.setId(View.generateViewId());
            tv.setTextColor(Color.WHITE);

            relativelayout.addView(progressBar, progressBarParams);
            relativelayout.addView(tv, textViewParams);
            enabledRelativeLayout = relativelayout;
            relativelayout.setVisibility(View.VISIBLE);
            activity.addContentView(relativelayout,layoutParams);
        }
        else
        {
            enabledRelativeLayout.setVisibility(View.GONE);
        }
    }
}
