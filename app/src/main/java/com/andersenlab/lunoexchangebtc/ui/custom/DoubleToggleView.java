package com.andersenlab.lunoexchangebtc.ui.custom;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.andersenlab.lunoexchangebtc.R;

public class DoubleToggleView extends LinearLayout {
    private LinearLayout layout = null;
    private Button leftToggle = null;
    private Button rightToggle = null;

    public DoubleToggleView(Context context) {
        super(context);
    }

    public DoubleToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DoubleToggleView);

        String leftText = a.getString(R.styleable.DoubleToggleView_leftToggleText);
        String rightText = a.getString(R.styleable.DoubleToggleView_rightToggleText);
        int buttonsTextColor = a.getColor(R.styleable.DoubleToggleView_buttonsTextColor,
                ContextCompat.getColor(context, android.R.color.black));

        Drawable drawable = a.getDrawable(R.styleable.DoubleToggleView_containerBackground);
        int backgroundColor = a.getColor(R.styleable.DoubleToggleView_containerBackgroundColor,
                ContextCompat.getColor(context, android.R.color.transparent));

        leftText = leftText == null ? "" : leftText;
        rightText = rightText == null ? "" : rightText;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (LinearLayout) li.inflate(R.layout.double_toggle_layout, this, true);

        leftToggle = layout.findViewById(R.id.leftButton);
        rightToggle = layout.findViewById(R.id.rightButton);
        if (drawable != null)
            layout.setBackground(drawable);
        else {
            layout.setBackgroundColor(backgroundColor);
        }


        rightToggle.setTextColor(buttonsTextColor);
        leftToggle.setTextColor(buttonsTextColor);
        leftToggle.setText(leftText);
        rightToggle.setText(rightText);
        a.recycle();
    }

    @SuppressWarnings("unused")
    public void setLeftText(String text) {
        leftToggle.setText(text);
    }

    @SuppressWarnings("unused")
    public void setRightText(String text) {
        rightToggle.setText(text);
    }

    @SuppressWarnings("unused")
    public String getLeftText() {
        return leftToggle.getText().toString();
    }

    @SuppressWarnings("unused")
    public String getRightText() {
        return rightToggle.getText().toString();
    }

    public void setLeftToggleOnClickListener(@Nullable OnClickListener onClickListener) {
        leftToggle.setOnClickListener(onClickListener);
    }

    public void setRightToggleOnClickListener(@Nullable OnClickListener onClickListener) {
        rightToggle.setOnClickListener(onClickListener);
    }

    public Button getLeftToggle() {
        return leftToggle;
    }

    public Button getRightToggle() {
        return rightToggle;
    }


}
