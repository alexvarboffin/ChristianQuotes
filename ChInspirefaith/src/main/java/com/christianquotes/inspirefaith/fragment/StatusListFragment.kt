package com.christianquotes.inspirefaith.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.christianquotes.inspirefaith.Const
import com.christianquotes.inspirefaith.R
import com.walhalla.core.MyIntent
import com.walhalla.core.domain.entity.Category
import com.walhalla.core.fragment.AbstractStatusListFragment

//import com.walhalla.core.fragment.Abstract0StatusListFragment;
/*
    Inner 2-level fragment
 */
class StatusListFragment : AbstractStatusListFragment() {
    override fun dbName(): String {
        return getString(R.string.abc_d_name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        AppDatabase db = LocalDatabaseRepo.getDatabase(getContext(), dbName());
//        interactor = new DataInteractorImpl(
//                ThreadExecutor.getInstance(),
//                MainThreadImpl.getInstance(), db
//        );
    }


    override fun isEnableWatermark(): Boolean {
        return Const.ENABLE_WATERMARK
    }


    override fun loadKeywords() {
        if ((category.id ?: 0L) > 0L) {
            interactor.getFullData(category.id ?: 0L, this)
        } else {
            super.loadKeywords() //Favorite data
        }
    }

    override fun prefixName(): String? {
        return Const.PREFIX_NAME
    } //    @Override
    //    public void popupMenu(ViewGroup ll_quote_share, StatusI status, TextView tv_quotes_watermark, ViewGroup relativeLayout, ViewGroup tools) {
    //        presenter.popup(ll_quote_share, status, tv_quotes_watermark, relativeLayout, tools);
    //    }


    companion object {
        @JvmStatic
        fun newInstance(category: Category?, title: String?): Fragment {
            val fragment: Fragment = StatusListFragment()
            val bundle = Bundle()
            bundle.putSerializable(MyIntent.KEY_OBJ, category)
            bundle.putString(MyIntent.KEY_CAT_NAME, title)
            fragment.setArguments(bundle)
            return fragment
        }
    }
}
