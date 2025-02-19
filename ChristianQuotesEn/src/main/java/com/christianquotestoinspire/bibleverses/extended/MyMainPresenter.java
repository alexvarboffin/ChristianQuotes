//package com.christianquotestoinspire.bibleverses.extended;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Handler;
//import android.util.Log;
//
//import com.walhalla.ui.DLog;
//
//import org.apache.cordova.TPreferences;
//
//import org.apache.cordova.domen.UIVisibleDataset;
//import org.apache.cordova.repository.DatasetRepository;
//
//import org.apache.mvp.ReferrerAdapter;
//import org.apache.mvp.presenter.BasePresenter;
//
//import java.util.Map;
//import java.util.Set;
//
//public class MyMainPresenter extends BasePresenter
//        implements SharedPreferences.OnSharedPreferenceChangeListener,
//        DatasetRepository.RepoCallback {
//
//
//    private final AbstractFirebaseRepository repository;
//    private final SharedPreferences preferences;
//
//
//    public static final String LIST_DIVIDER = "\\|";
//    public static final String NONENONE = "";
//    private static final String TAG = "@@@";
//
//    private final Activity context;
//    private final ReferrerAdapter.Callback mView;
//    ReferrerAdapter adapter;
//
//
//    private boolean onlyOnetime = false;//resume method
//
//    public MyMainPresenter(ReferrerAdapter.Callback mainView, Activity context, Handler handler) {
//        super(handler);
//
//        this.mView = mainView;
//        this.context = context;
//        TPreferences pref = TPreferences.getInstance(this.context);
//        this.repository = new AbstractFirebaseRepository(context, handler) {
//            @Override
//            public void getConfig(Context context) {
//
//            }
//        };
//        this.repository.setCallback(this);
//        preferences = pref.getPreferences();
//        preferences.registerOnSharedPreferenceChangeListener(this);
//        adapter = new ReferrerAdapter(context, pref, mView);
//    }
//
//
//
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        final Map<String, ?> keys = preferences.getAll();
//        final Set<? extends Map.Entry<String, ?>> bbb = keys.entrySet();
//        for (Map.Entry<String, ?> entry : bbb) {
//            if (TPreferences.CheckKey.CH_UTM_MEDIUM.equals(key)) {
//                //value contains Cst.KEY_ORGANIC
//                //Log.d(TAG, "ORGANIC_TRIGGER");
//                //wrapContent0Request();
//                return;
//            } else if (TPreferences.CheckKey.CH_DEEPLINK.equals(key)) {
//                Log.d(TAG, "=========>>>>" + key + "::" + entry.getValue());
//                return;
//            }
//        }
//    }
//
//    public void onResume(Intent intent) {
//        if (!onlyOnetime) {
//            repository.updateUpdater0Request7();
//            onlyOnetime = true;
//        }
//    }
//
//
//    public void onDestroy() {
//        preferences.unregisterOnSharedPreferenceChangeListener(null);
//    }
//
//
//    //Remote Repo Callback...
//    @Override
//    public void successResponse(UIVisibleDataset value) {
//        //DLog.d("@@d@" + value.getUrl() + " " + value.getEnabled());
//    }
//
//    @Override
//    public void handleError(String m) {
//        //Toast.makeText(this, "ERROR: " + m, Toast.LENGTH_LONG).show();
//    }
//
//}
