package com.christianquotes.inspirefaith.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.christianquotes.inspirefaith.R
import com.walhalla.core.domain.entity.Category
import com.walhalla.core.fragment.AbsCategoryListFragment
import com.walhalla.view.adapter.CategoryListAdapter
import com.walhalla.view.adapter.CategoryListCallback
import com.walhalla.view.adapter.ViewModel

//
// home screen
//
// ChristianQuotes
//
class CategoryListFragment : AbsCategoryListFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryListAdapter = CategoryListAdapter(
            CategoryListCallback { category: Category? -> this.selectWord(category) },
            ArrayList<ViewModel?>(),
            intArrayOf(
                R.drawable.ic_statuses
            )
        )
    }


    override fun dbName(): String {
        return getString(R.string.abc_d_name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (Constants.D_ALL == c_id) {
//            mBind.mm.setVisibility(View.GONE);
//        } else {
        //mm.setVisibility(View.GONE);
        binding.mm.setOnClickListener(View.OnClickListener { v: View? ->
            if (callback != null) {
                callback.readMore(Category(1, getString(R.string.menu_all_categories))) //show all
            }
        })
        //        }
    }


    override fun useAlphaSectionRes(): Boolean {
        return true
    }


    override fun searchBar(): Boolean {
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            val fragment: Fragment = CategoryListFragment()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }
}
