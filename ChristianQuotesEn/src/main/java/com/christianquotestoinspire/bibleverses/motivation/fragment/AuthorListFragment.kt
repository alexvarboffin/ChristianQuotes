package com.christianquotestoinspire.bibleverses.motivation.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.christianquotestoinspire.bibleverses.motivation.Const
import com.christianquotestoinspire.bibleverses.motivation.R
import com.walhalla.core.MyIntent
import com.walhalla.core.adapter.StatusI
import com.walhalla.core.domain.LocalDataBaseInteractor
import com.walhalla.core.domain.entity.Author
import com.walhalla.core.domain.entity.Status
import com.walhalla.core.fragment.Abstract0StatusListFragment
import com.walhalla.view.databinding.ItemDescriptionBinding

/*
   Inner 2-level fragment
*/
class AuthorListFragment : Abstract0StatusListFragment<Author>(), LocalDataBaseInteractor.Callback<List<Status>> {

    override fun dbName(): String {
        return getString(R.string.abc_d_name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        AppSimpleDatabase db = LocalDatabaseRepo.getDatabase(getContext(), dbName());
//        interactor = new DataInteractorImpl(
//                ThreadExecutor.getInstance(),
//                MainThreadImpl.getInstance(), db
//        );
    }

    override fun isEnableWatermark(): Boolean {
        return Const.ENABLE_WATERMARK
    }


    override fun loadKeywords() {
        if (category!!.id > 0 && !TextUtils.isEmpty(category!!.name)) {
            interactor.getByAuthorName(category!!.name, this@AuthorListFragment)
            //Search by like category_name
        } else {
            super.loadKeywords() //Favorite data
        }

        //        if (category._id > 0) {
//            interactor.getFullData(category._id, this);
//        } else {
//            super.loadKeywords();//Favorite data
//        }
    }

    override fun prefixName(): String {
        return Const.PREFIX_NAME
    }

    override fun updateStatusClick(status: StatusI) {
        val tmp = Status.valueOf(status)
        interactor.updateStatus(tmp, object : LocalDataBaseInteractor.Callback<Int> {
            override fun onMessageRetrieved(message: Int) {
                //Toast.makeText(getContext(), "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                //We use favorite screen, so update
                if (category!!.id < 1) {
                    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                    loadKeywords()
                }
            }

            override fun onRetrievalFailed(error: String) {
                this@AuthorListFragment.onRetrievalFailed(error)
            }
        })
    }

    override fun popupMenu(binding: ItemDescriptionBinding, status: StatusI) {
        this.tmp = binding
        val llQuoteShare: ViewGroup = binding.llQuoteShare
        val tvQuotesWatermark = binding.dynamic.binding.tvQuotesWatermark
        val parent = binding.layoutQuotesParentView
        val tools: ViewGroup = binding.dynamic.binding.tools
        presenter.popup(llQuoteShare, status, tvQuotesWatermark, parent, tools)
    }


    override fun onMessageRetrieved(message: List<Status>) {
        val obj: List<StatusI> = ArrayList<StatusI>(message)
        handleMessage(obj)
    }


    companion object {
        @JvmStatic
        fun newInstance(author: Author?, title: String?): Fragment {
            val fragment: Fragment = AuthorListFragment()
            val bundle = Bundle()
            bundle.putSerializable(MyIntent.KEY_OBJ, author)
            bundle.putString(MyIntent.KEY_CAT_NAME, title)
            fragment.arguments = bundle
            return fragment
        }
    }
}
