//package com.christianquotestoinspire.bibleverses.motivation.activity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.walhalla.ui.DLog;
//
//import org.apache.cordova.generated.P;
//
//import org.apache.mvp.ReferrerAdapter;
//import com.christianquotestoinspire.bibleverses.extended.MyMainPresenter;
//
//import org.jetbrains.annotations.NotNull;
//
//public abstract class BaseActivity extends AppCompatActivity
//        //implements MainView
//        //WebFragment.Lecallback
//        //, MClb
//{
//
//    protected MyMainPresenter presenter;
//
//    //Views
//    protected boolean rotated = false;
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!rotated) {
//            presenter.onResume(getIntent());
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Handler handler = new Handler(Looper.getMainLooper());
//        presenter = new MyMainPresenter(
//                new ReferrerAdapter.Callback() {
//                    @Override
//                    public void successResult(String var0) {
//                        //DLog.d("@@@xxxxxxxxxx" + var0 + "\n");
//                    }
//                }, this, handler);
//        //pref = TPreferences.getInstance(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        presenter.onDestroy();
//    }
//
//
////    @SuppressLint("SourceLockedOrientationActivity")
////    @Override
////    public void m404() {
////        makeScreen(new UIVisibleDataset(ScreenType.WEB_VIEW, NONENONE));
////        if (toolbar != null) {
////            toolbar.setSubtitle("");
////        }
////    }
//
//
//
//    @Override
//    protected void onSaveInstanceState(@NotNull Bundle outState) {
//        outState.putBoolean(P.KEY_ROTATED, this.rotated);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        this.rotated = savedInstanceState.getBoolean(P.KEY_ROTATED, false);
//    }
//}
