package com.example.ddddd.widget.view;

import java.math.BigDecimal;

import com.example.ddddd.util.NumberUtils;
import com.example.ddddd.util.ViewUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void init() {
        if (ViewUtil.isSpecialMode(getContext())) {
            int size = (int) NumberUtils.round(getTextSize()
                    / getContext().getResources().getDisplayMetrics().scaledDensity, 0, BigDecimal.ROUND_HALF_UP);
            size += 4;
            ViewUtil.setSpecialModeTextSize(getContext(), this, size);
        }
    }
}
