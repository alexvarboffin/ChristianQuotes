package com.christianquotestoinspire.bibleverses.motivation.author

import androidx.lifecycle.Observer
import com.christianquotestoinspire.bibleverses.motivation.fragment.AuthorFragment
import com.walhalla.boilerplate.domain.executor.Executor
import com.walhalla.boilerplate.domain.executor.MainThread
import com.walhalla.boilerplate.domain.interactors.base.AbstractInteractor
import com.walhalla.core.domain.db.AuthorDao
import com.walhalla.core.domain.entity.Author
import com.walhalla.ui.DLog.handleException

class AuthorPresenter(
    threadExecutor: Executor,
    mainThread: MainThread,
    private val view: AuthorFragment,
    private val authorDao: AuthorDao
) : AbstractInteractor(threadExecutor, mainThread) {
    val allAuthors: Unit
        get() {
            try {
                authorDao.getAllAuthors().observe( //view.getLifecycleOwner(),
                    view.getViewLifecycleOwner(),

                    object : Observer<MutableList<Author>> {
                        override fun onChanged(authors: MutableList<Author>) {
                            if (view != null) {
                                mMainThread.post(Runnable { view.showAuthors(authors) })
                            }
                        }
                    })
            } catch (e: Exception) {
                handleException(e)
                mMainThread.post(Runnable { view.onRetrievalFailed("Database error: " + e.message) })
            }
            //        mThreadExecutor.execute(() -> {
//
//        });
        }

    public override fun run() {
    } //    public void addAuthor(String name) {
    //        Author author = new Author();
    //        author.setName(name);
    //        authorDao.insertAuthor(author);
    //    }
}
