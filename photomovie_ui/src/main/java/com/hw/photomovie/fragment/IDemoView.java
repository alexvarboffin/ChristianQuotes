package com.hw.photomovie.fragment;

import android.app.Activity;

import com.hw.photomovie.sample.widget.FilterItem;
import com.hw.photomovie.sample.widget.TransferItem;
import com.hw.photomovie.render.GLTextureView;

import java.util.List;

/**
 * Created by huangwei on 2018/9/9.
 */
public interface IDemoView {
    public GLTextureView getGLView();
    void setFilters(List<FilterItem> filters);
    Activity getActivity();

    void setTransfers(List<TransferItem> items);
}
