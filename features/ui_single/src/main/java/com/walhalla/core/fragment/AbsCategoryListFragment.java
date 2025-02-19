package com.walhalla.core.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.walhalla.boilerplate.domain.executor.impl.ThreadExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;
import com.walhalla.core.CategoryListFragmentCallback;

import com.walhalla.core.adapter.StoreListAdapter;

import com.walhalla.core.domain.DataInteractorImpl;
import com.walhalla.core.domain.LocalDataBaseInteractor;
import com.walhalla.core.domain.db.AppDatabase;
import com.walhalla.core.domain.db.LocalDatabaseRepo;
import com.walhalla.core.domain.entity.Category;
import com.walhalla.ui.BuildConfig;
import com.walhalla.ui.DLog;

import com.walhalla.uisingle.R;
import com.walhalla.uisingle.databinding.FragmentSearchListBinding;
import com.walhalla.view.adapter.CategoryListAdapter;
import com.walhalla.view.adapter.EmptyViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsCategoryListFragment extends Fragment {

    protected FragmentSearchListBinding binding;
    protected CategoryListFragmentCallback callback;
    private StoreListAdapter storeListAdapter;
    protected CategoryListAdapter categoryListAdapter;
    private DataInteractorImpl interactor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (searchBar()) {
            storeListAdapter = new StoreListAdapter(getContext(), R.layout.store_item, new ArrayList<>());
        }
        final AppDatabase db = LocalDatabaseRepo.getDatabase(getContext(), dbName());
        interactor = new DataInteractorImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                db.statusDao(), db.categoryDao()
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (useAlphaSectionRes()) {
            final String[] customAlphabet = getResources().getStringArray(R.array.alphabet);
            binding.alphSectionIndex.setAlphabet(customAlphabet);
            binding.alphSectionIndex.onSectionIndexClickListener((view1, position, character) -> {
                //String info = " Position = " + position + " Char = " + character + "\t" + getPositionFromData(character);
                //Log.i("View: ", view1 + "," + info);
                //Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
                //recyclerView.smoothScrollToPosition(getPositionFromData(character));
                binding.recyclerView.scrollToPosition(categoryListAdapter.getPositionFromData(character));
            });
        } else {
            binding.alphSectionIndex.setVisibility(View.GONE);
        }
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(lm);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), lm.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        if (dividerDrawable != null) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(dividerDrawable);
            binding.recyclerView.addItemDecoration(dividerItemDecoration);
        }

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(categoryListAdapter);

        /**
         * AutoCompleteTextView
         */
        binding.autoTextView.setThreshold(2);
        if (searchBar()) {
            binding.autoTextView.setVisibility(View.VISIBLE);
            binding.autoTextView.setAdapter(storeListAdapter);
            binding.autoTextView.setOnItemClickListener((adapterView, view12, position, l) -> {
                Category item = (Category) adapterView.getItemAtPosition(position);

                if (getActivity() != null) {
                    InputMethodManager inputManager = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    View v = getActivity().getCurrentFocus();
                    if (v != null) {
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                        if (inputManager != null) {
                            inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                }
                String key = item.name;
                binding.autoTextView.setText(key);
//                    Toast.makeText(KeywordListFragment.this.getContext(),
//                            "Clicked item from auto completion list "
//                                    + adapterView.getItemAtPosition(position)
//                            , Toast.LENGTH_SHORT).show();
                selectWord(item);
            });
        }
        binding.autoTextView.clearFocus();
//        mBind.autoTextView.setThreshold(2);
//        mBind.autoTextView.setAdapter(storeListAdapter);
//        mBind.autoTextView.setOnItemClickListener((adapterView, view12, position, l) -> {
//            Category item = (Category) adapterView.getItemAtPosition(position);
//
//
//            if (getActivity() != null) {
//                InputMethodManager inputManager = (InputMethodManager) getActivity()
//                        .getSystemService(Context.INPUT_METHOD_SERVICE);
//                View v = getActivity().getCurrentFocus();
//                if (v != null) {
//                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                    if (inputManager != null) {
//                        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    }
//                }
//            }
//            String key = item.name;
//            mBind.autoTextView.setText(key);
////                    Toast.makeText(KeywordListFragment.this.getContext(),
////                            "Clicked item from auto completion list "
////                                    + adapterView.getItemAtPosition(position)
////                            , Toast.LENGTH_SHORT).show();
//            selectWord(item);
//
//        });

        loadKeywords(/*QUtils.toType(title),*/ interactor);

        ///data/data/com.walhalla.dreambook/databases/dream_book.db
        //Log.i(TAG, "###" + getDatabasePath("dream_book.db").getAbsolutePath());

//        DreamBookDatabase db = LocalDatabaseRepo.getStoreInfoDatabase(getContext());
//        LocalDataBaseInteractor interactor = new DataInteractorImpl(
//                ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), db
//        );
//        interactor.extractById(new LocalDataBaseInteractor.Callback() {
//            @Override
//            public void onMessageRetrieved(Object message) {
//                Log.i(TAG, "onCreate: " + message);
//            }
//
//            @Override
//            public void onRetrievalFailed(String error) {
//                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    protected abstract String dbName();

    protected abstract boolean useAlphaSectionRes();

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (bar != null) {
                try {
                    bar.setSubtitle(R.string.menu_all_categories);
                } catch (Resources.NotFoundException e) {
                    DLog.handleException(e);
                }
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CategoryListFragmentCallback) {
            callback = (CategoryListFragmentCallback) context;
        } else {
            throw new RuntimeException(context + " must implement Callback");
        }
    }

    private void handleMessage(List<Category> message) {
        if (message == null || message.isEmpty()) {
            this.categoryListAdapter.swap(new EmptyViewModel(getString(R.string.empty)));
        } else {
            categoryListAdapter.swap0(message);
            if (searchBar()) {
                this.storeListAdapter.swap(message);
            }
        }
    }

    private void loadKeywords(DataInteractorImpl interactor) {
        interactor.selectAllCategories(new LocalDataBaseInteractor.Callback<List<Category>>() {
            @Override
            public void onMessageRetrieved(List<Category> message) {
                handleMessage(message);
            }

            @Override
            public void onRetrievalFailed(String error) {
                if (BuildConfig.DEBUG) {
                    categoryListAdapter.swap(new EmptyViewModel(error));
                }
            }
        });
    }

    protected void selectWord(Category category) {
        if (callback != null) {
            callback.readMore(category);
        }

//        DreamBookDatabase db = LocalDatabaseRepo.getStoreInfoDatabase(getContext());
//        LocalDataBaseInteractor interactor = new DataInteractorImpl(
//                ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), db
//        );
//        interactor.selectByKeyword(category, title, new LocalDataBaseInteractor.Callback<List<Dictionary>>() {
//            @Override
//            public void onMessageRetrieved(List<Dictionary> message) {
//                MainActivity activity = ((MainActivity) getActivity());
//                if (activity != null) {
//                    activity.readMore(category, title);
//                }
//            }
//
//            @Override
//            public void onRetrievalFailed(String error) {
//
//            }
//        });
    }

    protected abstract boolean searchBar();


}
