package com.christianquotestoinspire.bibleverses.motivation.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.christianquotestoinspire.bibleverses.motivation.BitmapUtils
import com.christianquotestoinspire.bibleverses.motivation.Const
import com.christianquotestoinspire.bibleverses.motivation.R
import com.like.LikeButton
import com.like.OnLikeListener
import com.walhalla.boilerplate.domain.executor.impl.ThreadExecutor
import com.walhalla.boilerplate.threading.MainThreadImpl
import com.walhalla.core.domain.DataInteractorImpl
import com.walhalla.core.domain.db.LocalDatabaseRepo
import com.walhalla.core.domain.entity.Status
import com.walhalla.core.fragment.R_F
import com.walhalla.core.mvp.presenter.RandomPresenter
import com.walhalla.core.mvp.view.RandomView
import com.walhalla.core.utils.MainUtils
import com.walhalla.core.utils.QTextUtils

class RandomFragment : R_F(), RandomView<Status>, OnLikeListener {

    //    MyProject myProject = MyProjectsHandler.christQuotesEn;
    //    TelegramClient[] client = MyProjectsHandler.christianQuotesIdeas_telegramClient;

    private var status0: Status? = null
    private var titles: Array<String>? = null

    private var presenter: RandomPresenter<Status>? = null
    private var bitmap: Bitmap? = null


    override fun tryOpenVideoMaker0(parent: ViewGroup) {
        presenter!!.tryOpenVideoMaker0(watermark, tools, parent)
    }

    override fun onSave29(activity: FragmentActivity, parent: ViewGroup) {
        presenter!!.onSave29(activity, parent)
    }

    override fun updateRecyclerViewItemBackground(newValue: Int) {
        presenter!!.updateRecyclerViewItemBackground(newValue)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //titles = getResources().getStringArray(R.array.c_title);
        val db = LocalDatabaseRepo.getDatabase(
            activity, getString(R.string.abc_d_name)
        )
        val interactor = DataInteractorImpl(
            ThreadExecutor.getInstance(),
            MainThreadImpl.getInstance(),
            db.statusDao(),
            db.categoryDao()
        )
        val handler = Handler()
        presenter = RandomPresenter(
            activity as AppCompatActivity?,
            this, interactor, handler, fileNamePrefix(),
            requestPermissionLauncher,
            storageActivityResultLauncher,
            requestVideoMakerPermissionLauncher
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //fontQuote = Typeface.createFromAsset(getActivity().getAssets(), "fonts/oswald_regular.ttf");
        this.fontQuote = ResourcesCompat.getFont(requireActivity(), R.font.oswald_regular)
        favBtn.setOnLikeListener(this)
        //if (!BuildConfig.DEBUG) {
        presenter!!.selectOne()
        //}
    }

    override fun onSave(activity: FragmentActivity, parent: ViewGroup) {
        presenter!!.saveQuotesLikeImage(activity, parent)
    }

    override fun onResume() {
        super.onResume()
        //        if (BuildConfig.DEBUG) {
//            presenter.selectNewRandom();
//        }
    }

    override fun openVideoMaker(watermark: TextView, tools: ViewGroup, parent: ViewGroup) {
        presenter!!.tryOpenVideoMaker0(watermark, tools, parent)
    }


    override fun onClickHandler(view: View) {
        presenter!!.onClickHandler(view)
    }

    override fun fileNamePrefix(): String {
        return Const.PREFIX_NAME
    }

    override fun isEnableWatermark(): Boolean {
        return Const.ENABLE_WATERMARK
    }

    //    @Override
    //    protected void presenter_makeSaved0(Bitmap bitmap) {
    //        presenter.fileSavedEvent(getActivity(), bitmap);
    //    }
    override fun showWatermark() {
        showWatermark(watermark, tools)
    }

    override fun hideWatermark() {
        hideWatermark(watermark, tools)
    }


    override fun bindStatus(status0: Status) {
        this.status0 = status0
        textView2.text = String.format(
            "%1\$s",
            this.status0!!.text
        )
        textView2.typeface = this.fontQuote

        if (!TextUtils.isEmpty(this.status0!!.author)) {
            val raw0 = "— " + this.status0!!.author
            author.visibility = View.VISIBLE
            author.text = raw0
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
        llQuoteShare.setOnClickListener { v: View? -> popup(this.status0) }
        //copy button
        llCopyQuote.setOnClickListener { v: View? ->
            MainUtils.copyStatus(
                activity,
                this.status0!!.text
            )
        }
        //            this.favorite.setOnClickListener(v -> callback.onFavoriteClick(status));
//            if (status.getFavorite() > 0) {
//                this.favorite.setImageResource(R.drawable.ic_star_black_24dp);
//            }
//this.itemView.setOnClickListener(v -> callback.onWordClick(status));
        favBtn.isLiked = this.status0!!.liked > 0
        rlLike.setOnClickListener(favBtn)


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
        getTools().visibility = View.GONE
        //bitmapper();
    }

    override fun bindSelectedCategories(categoryNames: List<String>) {
        var smile = ""
        val appName = getString(R.string.app_name)
        val packageName = requireContext().packageName

        //        appName = myProject.getNames()[0];//getString(R.string.app_name);
//        com.walhalla.promo.TelegramSender.sendStatusToTelegram(client, myProject, status0.text, status0.author, bitmap, categoryNames);
//        //===================================
//        smile = TelegramSender.getSmiles0();
        smile = smile + "" +
                "\n" // +"Категория: "


        val sb = StringBuilder()
        for (categoryName in categoryNames) {
            sb.append("" + "#").append(categoryName.replace(" ", "")).append("\n")
        }
        val statusAuthor = status0!!.getAuthor()
        if (!TextUtils.isEmpty(statusAuthor)) {
            val formated = statusAuthor.replace(" ", "")
            sb.append("" + "#").append(formated).append("\n")
        }

        var quotes = status0!!.text
        if (QTextUtils.isAuthorNotEmpty(status0!!.getAuthor())) {
            quotes = ("""$quotes
— ${status0!!.getAuthor()}
 ($appName — $packageName)""")
        }


        val msg =  //"<strong>" + result.get("title") + "</strong>\n" +
            ("""
                $quotes
                
                $smile$sb
                
                """.trimIndent())
        //like dislike
        //+ "<pre>" + l_count + " " + "\uD83D\uDC4D" + "\t\t" + " " + d_count + " \uD83D\uDC4E" + "</pre>"

        //===================================
        shareQuotesLikeImage(status0, msg)
    }

    private fun bitmapper() {
        bitmap = BitmapUtils.getBitmapFromImageView(
            cardBackground
        )
        if (bitmap == null) {
            //Toast.makeText(getContext(), "@@@@", Toast.LENGTH_SHORT).show();
        } else {
            if (titles == null) {
                titles = resources.getStringArray(R.array.c_title)
            }
            presenter!!.makeCatNames(status0!!.c_id)


            //            String[] cats = this.status0.c_id.split(",");
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
//
//
            //com.walhalla.promo.TelegramSender.sendStatusToTelegram(status.text, status.author, bitmap, categoryNames);
        }
    }


    override fun liked(button: LikeButton) {
        if (status0 != null) {
            status0!!.setLiked()
            presenter!!.updateStatus(status0)
        }
    }

    override fun unLiked(button: LikeButton) {
        if (status0 != null) {
            status0!!.setDisLiked()
            presenter!!.updateStatus(status0)
        }
    }
}
