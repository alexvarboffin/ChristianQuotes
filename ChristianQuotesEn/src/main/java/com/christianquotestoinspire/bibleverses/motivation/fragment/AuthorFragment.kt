package com.christianquotestoinspire.bibleverses.motivation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.christianquotestoinspire.bibleverses.motivation.R
import com.christianquotestoinspire.bibleverses.motivation.author.AuthorPresenter
import com.christianquotestoinspire.bibleverses.motivation.author.AuthorView
import com.christianquotestoinspire.bibleverses.motivation.databinding.FragmentAuthorBinding
import com.walhalla.boilerplate.domain.executor.impl.ThreadExecutor
import com.walhalla.boilerplate.threading.MainThreadImpl
import com.walhalla.core.CategoryListFragmentCallback
import com.walhalla.core.adapter.AuthorListAdapter
import com.walhalla.core.adapter.AuthorListAdapter.AuthorListCallback
import com.walhalla.core.domain.db.LocalDatabaseRepo
import com.walhalla.core.domain.entity.Author
import com.walhalla.ui.BuildConfig
import com.walhalla.view.adapter.EmptyViewModel

class AuthorFragment : Fragment(), AuthorView, AuthorListCallback {

    private var callback: AuthorListFragmentCallback? = null
    private var binding: FragmentAuthorBinding? = null


    interface AuthorListFragmentCallback {
        fun readMoreAuthor(author: Author?)
    }

    private var authorListAdapter: AuthorListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorBinding.inflate(inflater, container, false)
        authorListAdapter = AuthorListAdapter(this)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerView.adapter = authorListAdapter

        val db = LocalDatabaseRepo.getDatabase(
            context, dbName()
        )
        val presenter = AuthorPresenter(
            ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
            this, db.authorDao()
        )
        presenter.getAllAuthors()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alphabetInj(true)
    }

    protected fun dbName(): String {
        return getString(R.string.abc_d_name)
    }

    override fun showAuthors(authors: List<Author>) {
        handleMessage(authors)
    }

    private fun handleMessage(message: List<Author>?) {
        if (message == null || message.isEmpty()) {
            authorListAdapter!!.swap(EmptyViewModel("Author list is empty!"))
        } else {
            //this.adapter.swap(new EmptyViewModel("@"+message.size()));
            authorListAdapter!!.setList(message)
        }
    }

    override fun onRetrievalFailed(error: String?) {
        if (BuildConfig.DEBUG) {
            authorListAdapter!!.swap(EmptyViewModel(error))
        }
    }

    override fun onItemClick(author: Author) {
        if (callback != null) {
            callback!!.readMoreAuthor(author)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CategoryListFragmentCallback) {
            callback = context as AuthorListFragmentCallback
        } else {
            throw RuntimeException(context.toString() + " must implement " + AuthorListFragmentCallback::class.java.simpleName)
        }
    }

    private fun alphabetInj(alphabet: Boolean) {
        if (alphabet) {
            val customAlphabet = resources.getStringArray(R.array.alphabet)
            binding!!.alphSectionIndex.setAlphabet(customAlphabet)
            binding!!.alphSectionIndex.onSectionIndexClickListener { view1: View?, position: Int, character: String? ->
                //String info = " Position = " + position + " Char = " + character + "\t" + getPositionFromData(character);
                //Log.i("View: ", view1 + "," + info);
                //Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
                //recyclerView.smoothScrollToPosition(getPositionFromData(character));
                binding!!.recyclerView.scrollToPosition(
                    authorListAdapter!!.getPositionFromData(
                        character
                    )
                )
            }
        } else {
            binding!!.alphSectionIndex.visibility = View.GONE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return AuthorFragment()
        }
    }
}
