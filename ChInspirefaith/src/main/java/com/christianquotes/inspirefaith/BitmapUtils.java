package com.christianquotes.inspirefaith;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.RelativeLayout;

public class BitmapUtils {
    public static Bitmap getBitmapFromImageView(RelativeLayout parent) {
        int height = parent.getHeight();
        int width = parent.getWidth();
        Bitmap bitmap = null;
        if (height > 0) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            parent.draw(canvas);
        }
        return bitmap;
    }
}
