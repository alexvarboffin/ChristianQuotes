package com.christianquotes.inspirefaith.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.christianquotes.inspirefaith.R;
import com.walhalla.core.domain.entity.Category;

import com.walhalla.core.fragment.AbsCategoryListFragment;
import com.walhalla.ui.DLog;
import com.walhalla.view.adapter.CategoryListAdapter;

import java.util.ArrayList;
import java.util.List;

//
// home screen
//
// ChristianQuotes
//

public class CategoryListFragment extends AbsCategoryListFragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryListAdapter = new CategoryListAdapter(this::selectWord, new ArrayList<>(), new int[]{R.drawable.ic_statuses});
    }


    protected String dbName() {
        return getString(R.string.abc_d_name);
    }

    public static Fragment newInstance() {
        Fragment fragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (Constants.D_ALL == c_id) {
//            mBind.mm.setVisibility(View.GONE);
//        } else {
        //mm.setVisibility(View.GONE);
        binding.mm.setOnClickListener(v -> {
            if (callback != null) {
                callback.readMore(new Category(1, getString(R.string.menu_all_categories)));//show all
            }
        });
//        }
    }

    
    protected boolean useAlphaSectionRes() {
        return true;
    }


    protected boolean searchBar() {
        return false;
    }
}
