package com.christianquotestoinspire.bibleverses.motivation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.christianquotestoinspire.bibleverses.motivation.R
import com.walhalla.core.domain.entity.Category
import com.walhalla.core.fragment.AbsCategoryListFragment
import com.walhalla.view.adapter.CategoryListAdapter

//
// home screen
//
// ChristianQuotes
////AStatusListFragmentFlirtyIslm//multiple
class CategoryListFragment : AbsCategoryListFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryListAdapter = CategoryListAdapter(
            { category: Category? -> this.selectWord(category) }, ArrayList(), intArrayOf(
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
        binding.mm.setOnClickListener { v: View? ->
            if (callback != null) {
                callback.readMore(
                    Category(
                        1,
                        getString(R.string.menu_all_categories)
                    )
                ) //show all
            }
        }
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
            fragment.arguments = bundle
            return fragment
        }
    }
}