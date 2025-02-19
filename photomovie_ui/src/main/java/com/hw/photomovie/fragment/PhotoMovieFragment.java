package com.hw.photomovie.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.hw.photomovie.BuildConfig;
import com.hw.photomovie.render.GLTextureView;

import com.hw.photomovie.sample.widget.FilterItem;
import com.hw.photomovie.sample.widget.MovieBottomView;
import com.hw.photomovie.sample.widget.MovieFilterView;
import com.hw.photomovie.sample.widget.MovieTransferView;
import com.hw.photomovie.sample.widget.TransferItem;

import com.hw.photomovie.samples.databinding.FragmentPhotomovieBinding;
import com.hw.photomovie.util.AppResources;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;

import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.zhihu.matisse.ui.MatisseActivity;

//import me.iwf.photopicker.PhotoPicker;

import java.util.ArrayList;
import java.util.List;


public class PhotoMovieFragment extends Fragment
        implements IDemoView, MovieBottomView.MovieBottomCallback, View.OnTouchListener {

    private List<Uri> photos;


    public static Fragment newInstance() {
        PhotoMovieFragment fragment = new PhotoMovieFragment();
        if (BuildConfig.DEBUG) {
            Bundle bundle = new Bundle();
            ArrayList<Uri> photos0 = new ArrayList<>();
            photos0.add(Uri.parse("file:///storage/emulated/0/Latest_quotes/1718642900423.png"));
            bundle.putParcelableArrayList(MatisseActivity.EXTRA_RESULT_SELECTION, photos0);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public static Fragment newInstance(ArrayList<Uri> photos) {
        PhotoMovieFragment fragment = new PhotoMovieFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MatisseActivity.EXTRA_RESULT_SELECTION, photos);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static final int REQUEST_CODE_CHOOSE = 1443;


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final int REQUEST_MUSIC = 234;
    private DemoPresenter mDemoPresenter;

    private MovieFilterView mFilterView;
    private com.hw.photomovie.sample.widget.MovieTransferView mTransferView;

    private List<FilterItem> mFilters;
    private List<TransferItem> mTransfers;

    private FragmentPhotomovieBinding binding;


    //private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppResources.getInstance().init(getResources());
        super.onCreate(savedInstanceState);
        mDemoPresenter = new DemoPresenter(getActivity(), this);
        handleInstance(savedInstanceState);

//        resultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        if (data != null) {
//                            List<Uri> photos = Matisse.obtainResult(data);
//                            mDemoPresenter.onPhotoPick(photos);
//                            binding.mFloatAddView.setVisibility(View.VISIBLE);
//                            binding.mSelectView.setVisibility(View.GONE);
//                        }
//                    }
//                }
//        );
    }

    private void handleInstance(Bundle data) {
        if (data != null && data.containsKey(MatisseActivity.EXTRA_RESULT_SELECTION)) {
            photos = data.getParcelableArrayList(MatisseActivity.EXTRA_RESULT_SELECTION);
        } else if (getArguments() != null && getArguments().containsKey(MatisseActivity.EXTRA_RESULT_SELECTION)) {
            photos = getArguments().getParcelableArrayList(MatisseActivity.EXTRA_RESULT_SELECTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotomovieBinding.inflate(getLayoutInflater());
        binding.getRoot().setOnTouchListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDemoPresenter.attachView();
        binding.mBottomView.setCallback(this);
        View.OnClickListener onClickListener = v -> requestPhotos0(getActivity());
        binding.mSelectView.setOnClickListener(onClickListener);
        binding.mFloatAddView.setOnClickListener(onClickListener);
        recreateUI();
    }


    private void requestPhotos0(Activity activity) {
        //import me.iwf.photopicker.PhotoPicker;
//        PhotoPicker.builder()
//                .setPhotoCount(9)
//                .setShowCamera(false)
//                .setShowGif(false)
//                .setPreviewEnabled(true)
//                .start(getContext(), this, PhotoPicker.REQUEST_CODE);

    }

//    private void requestPhotos1(FragmentActivity activity) {
//        Matisse.from(this)
//                .choose(
//                        com.zhihu.matisse.MimeType.ofImage()
//                        //com.zhihu.matisse.MimeType.ofImage(), true
//                        //MimeType.of(MimeType.GIF), false
//                )
//                .countable(true)
//                .maxSelectable(9)
//                .showSingleMediaType(true)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f)
//                .imageEngine(new GlideEngine())
//                .showPreview(true) // Default is `true`
//                .forResult(REQUEST_CODE_CHOOSE);
//
//        //someActivityResultLauncher.launch(intent);
//    }

    @Override
    public GLTextureView getGLView() {
        return binding.glTexture;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDemoPresenter.detachView();
    }

    private boolean checkInit() {
        if (binding.mSelectView.getVisibility() == View.VISIBLE) {
            Toast.makeText(getActivity(), "please select photos", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    @Override
    public void onNextClick() {
        if (checkInit()) {
            return;
        }

        Dexter.withContext(getActivity()).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
                .withListener(new MultiplePermissionsListener() {
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            mDemoPresenter.saveVideo();
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            PrmsnDialog.showSettingDialog(getActivity());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }


                }).withErrorListener(dexterError -> Toast.makeText(getActivity().getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show()).onSameThread().check();

    }

    @Override
    public void onMusicClick() {
        if (checkInit()) {
            return;
        }
        Intent i = new Intent();
        i.setType("audio/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, REQUEST_MUSIC);
    }

    @Override
    public void onTransferClick() {
        if (checkInit()) {
            return;
        }
        if (mTransferView == null) {
            mTransferView = (MovieTransferView) binding.viewStub.inflate();
            mTransferView.setVisibility(View.GONE);
            mTransferView.setItemList(mTransfers);
            mTransferView.setTransferCallback(mDemoPresenter);
        }
        binding.mBottomView.setVisibility(View.GONE);
        mTransferView.show();
    }

    @Override
    public void onFilterClick() {
        if (checkInit()) {
            return;
        }
        if (mFilterView == null) {
            mFilterView = (MovieFilterView) binding.movieMenuFilterStub.inflate();
            mFilterView.setVisibility(View.GONE);
            mFilterView.setItemList(mFilters);
            mFilterView.setFilterCallback(mDemoPresenter);
        }
        binding.mBottomView.setVisibility(View.GONE);
        mFilterView.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_MUSIC) {
            Uri uri = data.getData();
            mDemoPresenter.setMusic(uri);
        }
//        else if (resultCode == Activity.RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
//            if (data != null) {
//                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                mDemoPresenter.onPhotoPick0(photos);
//                binding.mFloatAddView.setVisibility(View.VISIBLE);
//                binding.mSelectView.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), "@0@", Toast.LENGTH_SHORT).show();
//            }
//        }
        else if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            handleIntent(data);
            recreateUI();
        }
    }

    private void recreateUI() {
        if (photos != null && !photos.isEmpty()) {
            mDemoPresenter.onPhotoPick(photos);
            binding.mFloatAddView.setVisibility(View.VISIBLE);
            binding.mSelectView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "@@", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleIntent(Intent data) {
        if (data != null) {
            //matisse
            photos = data.getParcelableArrayListExtra(MatisseActivity.EXTRA_RESULT_SELECTION);
        }
    }

//    public static final String EXTRA_RESULT_SELECTION = "extra_result_selection";

    public static List<Uri> obtainResult(Intent data) {
        return data.getParcelableArrayListExtra(MatisseActivity.EXTRA_RESULT_SELECTION);
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (mFilterView != null && mFilterView.getVisibility() == View.VISIBLE
//                    && !checkInArea(mFilterView, ev)) {
//                mFilterView.hide();
//                binding.mBottomView.getRoot().setVisibility(View.VISIBLE);
//                return true;
//            } else if (mTransferView != null && mTransferView.getVisibility() == View.VISIBLE
//                    && !checkInArea(mTransferView, ev)) {
//                mTransferView.hide();
//                binding.mBottomView.getRoot().setVisibility(View.VISIBLE);
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    private boolean checkInArea(View view, MotionEvent event) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        return event.getRawY() > loc[1];
    }

    @Override
    public void setFilters(List<FilterItem> filters) {
        mFilters = filters;
    }


    @Override
    public void setTransfers(List<TransferItem> items) {
        mTransfers = items;
    }

    @Override
    public void onPause() {
        super.onPause();
        mDemoPresenter.onPause();
        binding.glTexture.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDemoPresenter.onResume0();
        binding.glTexture.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mFilterView != null && mFilterView.getVisibility() == View.VISIBLE
                    && !checkInArea(mFilterView, ev)) {
                mFilterView.hide();
                binding.mBottomView.setVisibility(View.VISIBLE);
                return true;
            } else if (mTransferView != null && mTransferView.getVisibility() == View.VISIBLE
                    && !checkInArea(mTransferView, ev)) {
                mTransferView.hide();
                binding.mBottomView.setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }
}
