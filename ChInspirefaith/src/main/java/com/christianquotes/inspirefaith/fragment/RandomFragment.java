package com.christianquotes.inspirefaith.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import com.christianquotes.inspirefaith.Const;
import com.christianquotes.inspirefaith.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import com.walhalla.boilerplate.domain.executor.impl.ThreadExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;

import com.walhalla.core.adapter.StatusI;
import com.walhalla.core.domain.DataInteractorImpl;
import com.walhalla.core.domain.db.AppDatabase;
import com.walhalla.core.domain.db.LocalDatabaseRepo;
import com.walhalla.core.domain.entity.Status;
import com.walhalla.core.mvp.view.RandomView;
import com.walhalla.core.fragment.R_F;
import com.walhalla.core.mvp.presenter.RandomPresenter;
import com.walhalla.core.utils.MainUtils;

import java.util.List;


public class RandomFragment extends R_F implements RandomView<Status> {


    private Status status;
    private String[] titles;

    private RandomPresenter<Status> presenter;
    private Bitmap bitmap;


    @Override
    protected void tryOpenVideoMaker0(ViewGroup parent) {
        presenter.tryOpenVideoMaker0(watermark, tools, parent);
    }

    @Override
    protected void onSave29(FragmentActivity activity, ViewGroup parent) {
        presenter.onSave29(activity, parent);
    }

    @Override
    protected void updateRecyclerViewItemBackground(int newValue) {
        presenter.updateRecyclerViewItemBackground(newValue);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //titles = getResources().getStringArray(R.array.c_title);
        AppDatabase db = LocalDatabaseRepo.getDatabase(getActivity(), getString(R.string.abc_d_name));
        DataInteractorImpl interactor = new DataInteractorImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), db.statusDao(), db.categoryDao());
        Handler handler = new Handler();
        presenter = new RandomPresenter<>((AppCompatActivity) getActivity(), this, interactor, handler, fileNamePrefix(),
                requestPermissionLauncher,
                storageActivityResultLauncher,
                requestVideoMakerPermissionLauncher);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fontQuote = Typeface.createFromAsset(getActivity().getAssets(), "fonts/oswald_regular.ttf");
        this.fontQuote = ResourcesCompat.getFont(getActivity(), R.font.oswald_regular);
        this.favBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton button) {
                if (status != null) {
                    status.setLiked();
                    presenter.updateStatus(status);
                }
            }

            @Override
            public void unLiked(LikeButton button) {
                if (status != null) {
                    status.setDisLiked();
                    presenter.updateStatus(status);
                }
            }
        });
        presenter.selectOne();
    }

    @Override
    protected void onSave(FragmentActivity activity, ViewGroup parent) {
        presenter.saveQuotesLikeImage(activity, parent);
    }
    @Override
    protected void openVideoMaker(TextView watermark, ViewGroup tools, ViewGroup parent) {
        presenter.tryOpenVideoMaker0(watermark, tools, parent);
    }

    @Override
    protected void onClickHandler(View view) {
        presenter.onClickHandler(view);
    }


    @Override
    protected String fileNamePrefix() {
        return Const.PREFIX_NAME;
    }

//    @Override
//    protected void presenter_makeSaved0(Bitmap bitmap) {
//        presenter.fileSavedEvent(getActivity(), bitmap);
//    }

    @Override
    public boolean isEnableWatermark() {
        return Const.ENABLE_WATERMARK;
    }

    @Override
    public void showWatermark() {
        showWatermark(watermark, tools);
    }

    @Override
    public void hideWatermark() {
        hideWatermark(watermark, tools);
    }




    @Override
    public void bindStatus(Status status0) {
        this.status = status0;
        this.textView2.setText(String.format("%1$s", this.status.text));
        this.textView2.setTypeface(this.fontQuote);

        if (!TextUtils.isEmpty(this.status.author)) {
            String raw0 = "— " + this.status.author;
            this.author.setVisibility(View.VISIBLE);
            this.author.setText(raw0);
        }

//        try {
        //String _t = titles[Integer.parseInt("" + status.c_id) - 1];
//            //String _t = status.c_id;
//            //mBind.include.txtCategory.setText(_t);
//        } catch (Exception ignored) {
//        }
//        List<Integer> colors = new ArrayList<>();
//        colors.add(R.color.colorPrimaryDark);
//        colors.add(R.color.color_pink);
//        colors.add(R.color.color_light_blue);
//        colors.add(R.color.color_front);
//
//        GlitchTextEffect effect = new GlitchTextEffect(description.getContext(),colors,String.format("%1$s", status.text));
//        effect.setTextSize(44);
//        effect.setNoise(5);
//        effect.setSpeed(2000);
//        effect.start();
//        layout0.addView(effect);

        //this.share.setOnClickListener(v -> callback.onShareClick(status));
//        this.share_iv.setOnClickListener(v -> callback.onShareClick(status));
//        this.share_tv.setOnClickListener(v -> callback.onShareClick(status));

        llQuoteShare.setOnClickListener(v -> popup(status));
        //copy button
        llCopyQuote.setOnClickListener(v -> MainUtils.copyStatus(getActivity(), status.text));
//            this.favorite.setOnClickListener(v -> callback.onFavoriteClick(status));
//            if (status.getFavorite() > 0) {
//                this.favorite.setImageResource(R.drawable.ic_star_black_24dp);
//            }
//this.itemView.setOnClickListener(v -> callback.onWordClick(status));
        favBtn.setLiked(status.liked > 0);
        rlLike.setOnClickListener(favBtn);

//        String rr = rr();
//        showWatermark(tv_quotes_watermark);
//        Bitmap bitmap = Bitmap.createBitmap(llBackground.getWidth(), llBackground.getHeight(),
//                Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        llBackground.draw(canvas);
//        Intent sharePintrestIntent = new Intent(Intent.ACTION_SEND);
//        sharePintrestIntent.setPackage("com.pinterest");
//        sharePintrestIntent.putExtra("com.pinterest.EXTRA_DESCRIPTION", rr);
//        sharePintrestIntent.putExtra("com.pinterest.EXTRA_URL", UConst.GOOGLE_PLAY_CONSTANT + getActivity().getPackageName());
//        sharePintrestIntent.putExtra("com.pinterest.EXTRA_WEB_TITLE_STRING", UConst.GOOGLE_PLAY_CONSTANT + getActivity().getPackageName());
//
//        sharePintrestIntent.putExtra(Intent.EXTRA_STREAM, OnePresenter.getLocalBitmapUri(getActivity(), bitmap));
//        sharePintrestIntent.setType("image/*");
//        startActivityForResult(sharePintrestIntent, 443);


        getTools().setVisibility(View.GONE);
        //bitmapper();
    }

    @Override
    public void bindSelectedCategories(List<String> message) {

    }

//    private void bitmapper() {
//        bitmap = BitmapUtils.getBitmapFromImageView(getCardBackground());
//        if (bitmap == null) {
//            //Toast.makeText(getContext(), "@@@@", Toast.LENGTH_SHORT).show();
//        } else {
//            if (titles == null) {
//                titles = getResources().getStringArray(R.array.c_title);
//            }
//            //@@@ presenter.makeCatNames(this.status.c_id);
//
//            String[] cats = this.status.c_id.split(",");
//            final int catLength = cats.length;
//            String[] categoryNames = new String[catLength];
//            for (int i = 0; i < catLength; i++) {
//                int catIndex = Integer.parseInt(cats[i]) - 1;
//                if (catIndex < catLength - 1) {
//                    categoryNames[i] = titles[catIndex];
//                } else {
//                    categoryNames[i] = "@@" + catIndex;
//                }
//            }
////
////
//            //com.walhalla.promo.TelegramSender.sendStatusToTelegram(status.text, status.author, bitmap, categoryNames);
//        }
//    }
}
