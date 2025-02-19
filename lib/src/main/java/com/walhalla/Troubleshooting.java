package com.walhalla;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.walhalla.ui.DLog;

import java.io.File;
import java.util.Locale;

public class Troubleshooting {

    public static File defLocation(Context context) {
        File aa = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        DLog.d("@@@" + aa);
        return aa;
    }

    //below 29 api
    public static File withoutFileStore(Context context) {
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard, "Latest_quotes");
        boolean tmp = directory.mkdir();
        String filename = String.format(Locale.getDefault(), "%d.jpg", System.currentTimeMillis());
        return new File(directory, filename);
    }

}
