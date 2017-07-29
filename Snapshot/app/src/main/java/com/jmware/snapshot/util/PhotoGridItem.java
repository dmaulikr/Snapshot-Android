package com.jmware.snapshot.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class PhotoGridItem extends RelativeLayout {

    public PhotoGridItem(Context context) {
        super(context);
    }

    public PhotoGridItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoGridItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
