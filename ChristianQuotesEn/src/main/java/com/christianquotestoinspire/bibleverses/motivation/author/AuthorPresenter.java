package com.christianquotestoinspire.bibleverses.motivation.author;

import androidx.lifecycle.Observer;

import com.christianquotestoinspire.bibleverses.motivation.fragment.AuthorFragment;
import com.walhalla.boilerplate.domain.executor.Executor;
import com.walhalla.boilerplate.domain.executor.MainThread;
import com.walhalla.boilerplate.domain.interactors.base.AbstractInteractor;
import com.walhalla.core.domain.entity.Author;
import com.walhalla.core.domain.db.AuthorDao;

import com.walhalla.ui.DLog;

import java.util.List;

public class AuthorPresenter extends AbstractInteractor {
    private final AuthorFragment view;
    private final AuthorDao authorDao;

    public AuthorPresenter(Executor threadExecutor, MainThread mainThread, AuthorFragment view, AuthorDao authorDao) {
        super(threadExecutor, mainThread);
        this.view = view;
        this.authorDao = authorDao;
    }

    public void getAllAuthors() {
        try {
            authorDao.getAllAuthors().observe(
                    //view.getLifecycleOwner(),
                    view.getViewLifecycleOwner(),

                    new Observer<List<Author>>() {
                        @Override
                        public void onChanged(List<Author> authors) {
                            if (view != null) {
                                mMainThread.post(() -> view.showAuthors(authors));
                            }
                        }
                    });
        } catch (Exception e) {
            DLog.handleException(e);
            mMainThread.post(() -> view.onRetrievalFailed("Database error: " + e.getMessage()));
        }
//        mThreadExecutor.execute(() -> {
//
//        });
    }

    @Override
    public void run() {

    }

//    public void addAuthor(String name) {
//        Author author = new Author();
//        author.setName(name);
//        authorDao.insertAuthor(author);
//    }

}
