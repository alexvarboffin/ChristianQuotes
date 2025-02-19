//package com.christianquotestoinspire.bibleverses.extended;
//
//import android.content.Context;
//import android.os.Handler;
//import android.webkit.WebView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.christianquotestoinspire.bibleverses.motivation.NetUtils;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import com.walhalla.ui.DLog;
//
//import org.apache.cordova.Chipper;
//import org.apache.cordova.TPreferences;
//import org.apache.cordova.constants.TableField;
//import org.apache.cordova.repository.AbstractDatasetRepository;
//import org.apache.cordova.utils.Utils;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public abstract class AbstractFirebaseRepository extends AbstractDatasetRepository {
//    public static final String HKEY_USERS = "users";
//    private final TPreferences prefs;
//    private static final String KEY_DATA_CREATED = "data_sent";
//    private final Handler tMain;
//    protected final ExecutorService executor = Executors.newSingleThreadExecutor();
//
//    private final ValueEventListener createdListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            prefs.getPreferences().edit().putBoolean(KEY_DATA_CREATED, true).apply();
//            //Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//            //Toast.makeText(context, "err", Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    private final ValueEventListener updateListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            prefs.getPreferences().edit().putBoolean(KEY_DATA_CREATED, true).apply();
//            //Toast.makeText(context, "ook", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//            //Toast.makeText(context, "err", Toast.LENGTH_SHORT).show();
//        }
//    };
//
//
//    public AbstractFirebaseRepository(Context context, Handler handler) {
//        super(context);
//        this.tMain = handler;
//        prefs = TPreferences.getInstance(context);
//    }
//
//    //    public void updateUpdater0(Context context) {
////        try {
////            Map<String, Object> map = new HashMap<>();
////            map.put(TableField.FIELD_UPDATE_AT, Utils.makeDate());
////            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
////                    .getReference(HKEY_USERS)
////                    .child(Chipper.android_id(context));
////            handler.post(() -> {
////                try {
////                    databaseReference.addValueEventListener(updateListener);
////                    databaseReference.updateChildren(map);
////                } catch (Exception e) {
////                    DLog.handleException(e);
////                }
////            });
////        } catch (Exception e) {
////            DLog.handleException(e);
////        }
////    }
//    public void updateUpdater1(Context context) {
//        try {
//            DatabaseReference updatesRef = FirebaseDatabase.getInstance()
//                    .getReference(HKEY_USERS)
//                    .child(Chipper.android_id(context))
//                    .child(TableField.FIELD_UPDATE_AT);
//            executor.execute(() -> {
//                try {
//                    updatesRef.addValueEventListener(updateListener);
////                    String newKey = updatesRef.push().getKey();
////                    updatesRef.child(newKey).setValue(Utils.makeDate());
//                    updatesRef.push().setValue(Utils.makeDate());
//                } catch (Exception e) {
//                    DLog.handleException(e);
//                }
//            });
//        } catch (Exception e) {
//            DLog.handleException(e);
//        }
//    }
//    //device id {
//    //
//    // }
//
//    public void createUserRecord(Context context) {
//        try {
////            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
////                    .getReference(KEY_USERS).child(Chipper.android_id(context));
//            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(HKEY_USERS);
//            String userAgentString = new WebView(context).getSettings().getUserAgentString();
//
//            Map<String, Object> map = new HashMap<>();
//            //map.put("fingerprint", Chipper.makeFingerPrint(context, userAgentString));
//            map.put(Chipper.android_id(context), Chipper.makeFingerPrint00(context, prefs, userAgentString));
//
//            executor.execute(() -> {
//                try {
//                    databaseReference.addValueEventListener(createdListener);
//                    databaseReference.updateChildren(map);
//                } catch (Exception e) {
//                    DLog.handleException(e);
//                }
//            });
//        } catch (Exception e) {
//            DLog.handleException(e);
////            tMain.post(() -> {
////                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
////            });
//        }
//    }
//
////    public void event(Activity context, BodyClass body) {
////        try {
////            Thread t = new Thread(() -> {
////                try {
////                    FirebaseDatabase.getInstance()
////                            .getReference("w_s").push().setValue(body);
////                } catch (Exception e) {
////                    DLog.handleException(e);
////                }
////            });
////            t.start();
////        } catch (Exception e) {
////            DLog.handleException(e);
////        }
////    }
//
//    public void updateUpdater0Request7() {
//        //Старт при первом запуске, без проверки доставки
////        if (!pref.noneFirst()) {
////            enableActivityAutoTracking0(context);
////            pref.noneFirstEnable();
////        }
//        if(NetUtils.isOnline(context)){
//            boolean dataSent = prefs.getPreferences().getBoolean(KEY_DATA_CREATED, false);
//            if (!dataSent) {
//                createUserRecord(context);
//            } else {
//                //updateUpdater1(context);
//            }
//        }
//
//    }
//}
