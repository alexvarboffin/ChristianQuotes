package com.hw.photomovie.sample.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;

public class IntentUtils {

    public static void shareVideo(Activity activity, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        Uri uri = makeURI(activity, file);
        intent.setDataAndType(uri, "video/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(intent);
    }

    public static Uri makeURI(Context context, File file) {
        String APPLICATION_ID = context.getPackageName();
        if (file.isDirectory()) {
            return Uri.fromFile(file);//Not use FileProvider is Directory
        }
        return FileProvider.getUriForFile(context, APPLICATION_ID + ".provider", file);
    }
}
