package com.pxtin.android.ui;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * https://github.com/paulzeng/PullToRefreshStaggeredGridView
 * Created by Mostafa Gazar on 11/2/13.
 */
public class CircleImageView extends BaseImageView {

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static Bitmap getBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawOval(new RectF(0.0f, 0.0f, width, height), paint);

        return bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return getBitmap(getWidth(), getHeight());
    }
}