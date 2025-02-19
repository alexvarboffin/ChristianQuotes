package com.hw.photomovie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hw.photomovie.fragment.PhotoMovieFragment;
import com.hw.photomovie.samples.databinding.ActivityDemo2Binding;
import com.hw.photomovie.util.AppResources;
import com.zhihu.matisse.ui.MatisseActivity;

import java.util.ArrayList;

public class PhotoMovieActivity extends AppCompatActivity {

    private ActivityDemo2Binding binding;
    private ArrayList<Uri> photos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppResources.getInstance().init(getResources());
        super.onCreate(savedInstanceState);
        binding = ActivityDemo2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            handleIntent(getIntent());
            Fragment home;
            if(photos != null && !photos.isEmpty()){
                home = PhotoMovieFragment.newInstance(photos);
            }else {
                home = PhotoMovieFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                    .add(binding.container.getId(), home, "123")
                    .commit();
        }
    }

    private void handleIntent(Intent data) {
        if (data != null) {
            photos = data.getParcelableArrayListExtra(MatisseActivity.EXTRA_RESULT_SELECTION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
