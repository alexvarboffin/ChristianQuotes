package com.christianquotes.inspirefaith.fragment;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.christianquotes.inspirefaith.Const;
import com.christianquotes.inspirefaith.R;
import com.walhalla.core.MyIntent;
import com.walhalla.core.adapter.StatusI;
import com.walhalla.core.domain.entity.Category;
//import com.walhalla.core.fragment.Abstract0StatusListFragment;
import com.walhalla.core.fragment.AbstractStatusListFragment;
import com.walhalla.view.databinding.ItemDescriptionBinding;


/*
    Inner 2-level fragment
 */
public class StatusListFragment extends AbstractStatusListFragment {
    @Override
    protected String dbName() {
        return getString(R.string.abc_d_name);
    }

    public static Fragment newInstance(Category category, String title) {
        Fragment fragment = new StatusListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyIntent.KEY_OBJ, category);
        bundle.putString(MyIntent.KEY_CAT_NAME, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppDatabase db = LocalDatabaseRepo.getDatabase(getContext(), dbName());
//        interactor = new DataInteractorImpl(
//                ThreadExecutor.getInstance(),
//                MainThreadImpl.getInstance(), db
//        );
    }


    @Override
    public boolean isEnableWatermark() {
        return Const.ENABLE_WATERMARK;
    }


    @Override
    public void loadKeywords() {
        if (category._id > 0) {
            interactor.getFullData(category._id, this);
        } else {
            super.loadKeywords();//Favorite data
        }
    }

    @Override
    protected String prefixName() {
        return Const.PREFIX_NAME;
    }

//    @Override
//    public void popupMenu(ViewGroup ll_quote_share, StatusI status, TextView tv_quotes_watermark, ViewGroup relativeLayout, ViewGroup tools) {
//        presenter.popup(ll_quote_share, status, tv_quotes_watermark, relativeLayout, tools);
//    }


}
