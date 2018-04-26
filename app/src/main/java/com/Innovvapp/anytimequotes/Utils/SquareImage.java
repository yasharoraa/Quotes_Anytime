package com.Innovvapp.anytimequotes.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Yash Arora on 22-01-2018.
 */

public class SquareImage extends android.support.v7.widget.AppCompatImageView {
    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setScaleType(ScaleType.FIT_XY);


    }
}
