package com.hw.photomovie.sample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hw.photomovie.samples.databinding.MovieBottomviewBinding;


public class MovieBottomView extends ConstraintLayout {

    private MovieBottomCallback mCallback;
    private MovieBottomviewBinding binding;

    public MovieBottomView(Context context) {
        super(context);
        init(context);
    }

    public MovieBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MovieBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Inflate the layout using ViewBinding
        binding = MovieBottomviewBinding.inflate(LayoutInflater.from(context), this, true);

        // Set click listeners
        binding.movieNext.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onNextClick();
            }
        });

        binding.movieFilter.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onFilterClick();
            }
        });

        binding.movieFilterTxt.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onFilterClick();
            }
        });

        binding.movieTransfer.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onTransferClick();
            }
        });

        binding.movieTransferTxt.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onTransferClick();
            }
        });

        binding.movieMusic.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onMusicClick();
            }
        });

        binding.movieMusicTxt.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onMusicClick();
            }
        });
    }

    public void setCallback(MovieBottomCallback callback) {
        this.mCallback = callback;
    }

    public interface MovieBottomCallback {
        void onNextClick();
        void onMusicClick();
        void onTransferClick();
        void onFilterClick();
    }
}
