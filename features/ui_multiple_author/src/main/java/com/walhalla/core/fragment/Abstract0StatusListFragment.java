package com.walhalla.core.fragment;

import static com.walhalla.core.CoreUtil.loadCategory;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.walhalla.boilerplate.domain.executor.impl.ThreadExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;


import com.walhalla.core.adapter.OnRvStatusItemClickListener;
import com.walhalla.core.adapter.StatusAdapter;
import com.walhalla.core.adapter.StatusI;
import com.walhalla.core.adapter.StatusViewHolder;
import com.walhalla.core.domain.DataInteractorImpl;
import com.walhalla.core.domain.LocalDataBaseInteractor;
import com.walhalla.core.domain.db.LocalDatabaseRepo;
import com.walhalla.core.domain.db.AppDatabase;

import com.walhalla.core.domain.entity.Status;
import com.walhalla.ui.BuildConfig;
import com.walhalla.ui.DLog;

import com.walhalla.view.adapter.EmptyViewModel;
import com.walhalla.view.databinding.ItemDescriptionBinding;
import com.walhalla.view.dialogs.RanFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//  @ ChristianQuotesEn


public abstract class Abstract0StatusListFragment<T extends Serializable> extends BaseListFragment<T>
        implements OnRvStatusItemClickListener {
    //
    protected LocalDataBaseInteractor<Status> interactor;
    private static final String __BASE = "quotes";
    private static final char CHAR_10 = (char) 10;


    private int imIndex;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.makeMimeBitmap();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AppDatabase db = LocalDatabaseRepo.getDatabase(getContext(), dbName());
        interactor = new DataInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), db.statusDao(), db.categoryDao()
        );
//        TextView textView = view.findViewById(R.id.title);
//        if (keyword != null) {
//            textView.setText("@@@");//getString(R.string.what_is_dreaming, keyword)
//        }

//        int rawValue = Math.toIntExact(category._id);
        int[] images = loadCategory(getContext());
//        imIndex = rawValue % images.length;
        adapter = new StatusAdapter(this, new ArrayList<>(), getActivity(), images);
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract String dbName();


    @Override
    public void loadKeywords() {
        //Toast.makeText(getContext(), "" + category.toString(), Toast.LENGTH_LONG).show();
        favoriteData0();
    }

    //Favorite data
    private void favoriteData0() {
        interactor.getFavorite(new LocalDataBaseInteractor.Callback<>() {
            @Override
            public void onMessageRetrieved(List<Status> message) {
                List<StatusI> obj = new ArrayList<>(message);
                handleMessage(obj);
            }

            @Override
            public void onRetrievalFailed(String error) {
                DLog.d(error);
            }
        });
    }


    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


//            public void onShareClick(Status dictionary) {
//                if (getContext() != null) {
//                    String description = dictionary.text
//                            //+ (char) 10
//                            + System.getProperty("line.separator")
//                            + dictionary.text
//                            //+ (char) 10
//                            //+ (char) 10
//                            + System.getProperty("line.separator")
//                            + System.getProperty("line.separator");
////                            "Сонник - Большая Книга Снов\n" +
////                            "https://play.google.com/store/apps/details?id=" + getContext().getPackageName();
//                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                    sharingIntent.setType("text/plain");
//                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
//                    sharingIntent.putExtra(Intent.EXTRA_TEXT, description);
//                    startActivity(Intent.createChooser(sharingIntent, "Share text via"));
//                }
//            }


    @Override
    public void onSaveClick(ItemDescriptionBinding binding) {
        this.tmp = binding;
        presenter.saveQuotesLikeImage(getActivity(), tmp);
    }

    //AUTHORITY

    //private Toast short0;

//        @Override
//        public void copyStatus(StatusI status) {
////            if (short0 != null) {
////                short0.cancel();
////            }
////            short0 = Toast.makeText(getActivity(), R.string.copy_to_buffer, Toast.LENGTH_SHORT);
////            short0.show();
//            MainUtils.copyStatus0(getActivity(), status);
//        }

    @Override
    public void viewChanged(int imagesIndex) {
        presenter.viewChanged();
    }

    @Override
    public void openFontsDialog(int adapterposition) {
        this.adapterPosition = adapterposition;
        RanFragment.openFontsDialog(this);
    }

    @Override
    public void openGalleryDialog(int adapterPosition0) {
        this.adapterPosition = adapterPosition0;
        RanFragment.openGalleryDialog(Abstract0StatusListFragment.this);
    }




//Share image tool
//    private Uri getLocalBitmapUri(Bitmap bitmap) {
//        Uri bmpUri = null;
//        try {
//            File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//                    __BASE + System.currentTimeMillis() + ".png");
//            FileOutputStream out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.close();
//            bmpUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", file);
//        } catch (FileNotFoundException e) {
//            DLog.handleException(e);
//        } catch (IOException e) {
//            DLog.handleException(e);
//        }
//        return bmpUri;
//    }


    public void onRetrievalFailed(String error) {
        if (BuildConfig.DEBUG) {
            //DLog.d("@L->false: " + status.getId());
            adapter.swap(new EmptyViewModel(error));
        }
    }
}
