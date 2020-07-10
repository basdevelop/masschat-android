package com.flowerbell.masschat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flowerbell.masschat.R;

/**
 * Created by TAO on 2017/7/16.
 */

public class BackgroundProgress extends LinearLayout {

    String showTxt;
    boolean isGradient;
    BackgroundProgressView backgroundProgressView;

    public BackgroundProgress(Context context) {
        this(context, null, 0);
    }

    public BackgroundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BackgroundProgress);
        try {
            showTxt = typedArray.getString(R.styleable.BackgroundProgress_showTxt);
            isGradient = typedArray.getBoolean(R.styleable.BackgroundProgress_isGradient, true);
        } catch (Exception e) {
            showTxt = null;
            isGradient = true;
        }

        typedArray.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.bp, null);

        backgroundProgressView = (BackgroundProgressView) view.findViewById(R.id.progress);

        backgroundProgressView.set_isGradient(isGradient);


        ViewGroup.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, layoutParams);
    }


    public void setText(String txt) {
       // tv.setText(txt);
    }

    public void setMax(float max) {
        backgroundProgressView.setMax(max);
    }

    public void setProgress(float progress) {
        backgroundProgressView.setProgress(progress);
    }

}
