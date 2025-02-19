package com.walhalla.dreambook;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import com.walhalla.core.R;
import com.walhalla.ui.DLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CoreUtil {


    //Share image tool
    public static Uri getLocalBitmapUri(final Context o, Bitmap bitmap) {
        String APPLICATION_ID = o.getPackageName();
        Uri bmpUri = null;
        try {
            File file = new File(o.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "dreambook_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(o, APPLICATION_ID + ".provider", file);
        } catch (FileNotFoundException e) {
            DLog.handleException(e);
        } catch (IOException e) {
            DLog.handleException(e);
        } catch (SecurityException e) {
            DLog.handleException(e);
        }
        return bmpUri;
    }



//    public static String extraText(
//            Context context, List<List<String>> data,
//            Category dictionary, String watermark) {
//
//        StringBuilder sb = new StringBuilder();
//
//        String app_name = context.getString(R.string.app_name);
//        String packageName = context.getPackageName();
//
//
////        //
////        //
////        for (List<String> o : data) {
////            Collections.shuffle(o);
////
////            for (int i = 0; i < o.size(); i++) {
////                sb.append(o.get(i));
////                if (i < o.size() - 1) {
////                    sb.append(", ");
////                } else {
////                    sb.append(".\n");
////                }
////            }
////        }
////
////        sb.append(app_name).append("\n");
////        //sb.append(GOOGLE_PLAY_CONSTANT);
////        sb.append(packageName).append("\n");
////        sb.append(app_name).append("\n");
//
//        sb.append(watermark).append("\n").append(dictionary.description);
//        return sb.toString().trim();
//    }

//    public static List<Category> readFile(Context context) {
//        List<Category> list = new ArrayList<>();
//        try {
//            InputStream is = context.getAssets().open("w.txt");
//            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//            String str;
//            while ((str = br.readLine()) != null) {
//                list.add(new Category(str, Constants.D_ALL));
//            }
//            br.close();
//        } catch (Exception ignored) {
//        }
//        return list;
//    }

    public static List<String> readFile(Context context, String fileName) {
        List<String> data = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                mLine = mLine.trim();
                if (!mLine.isEmpty()) {
                    data.add(mLine);
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return data;
    }


    public static String packageName(@NonNull ResolveInfo info) {
        if (info.activityInfo != null) {
            return info.activityInfo.packageName;
        } else if (info.serviceInfo != null) {
            return info.serviceInfo.packageName;
        }
        return "";
    }



}
