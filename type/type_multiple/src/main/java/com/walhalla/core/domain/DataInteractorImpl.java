package com.walhalla.core.domain;

import com.walhalla.boilerplate.domain.executor.Executor;
import com.walhalla.boilerplate.domain.executor.MainThread;
import com.walhalla.boilerplate.domain.interactors.base.AbstractInteractor;

import com.walhalla.core.domain.db.StatusDao;
import com.walhalla.core.domain.entity.Category;
import com.walhalla.core.domain.entity.Status;
import com.walhalla.ui.DLog;


import java.util.List;


public class DataInteractorImpl extends AbstractInteractor implements LocalDataBaseInteractor<Status> {


    //private final AppDatabase db;
    private final StatusDao statusDao;
    private final CategoryDao categoryDao;

    public DataInteractorImpl(Executor threadExecutor, MainThread mainThread, StatusDao statusDao, CategoryDao categoryDao) {
        super(threadExecutor, mainThread);
        //this.db = db;
        this.statusDao = statusDao;
        this.categoryDao = categoryDao;
    }


    @Override
    public void extractById(Callback<String> callback) {
//        mThreadExecutor.execute(() -> {
//
//
//            HashSet<String> map = new HashSet<>();
//
//            List<Dictionary> list = null;
//            try {
////                final FreudDictionaryDao dao = db.getFreudDictionaryDao();
////                list = dao.selectAll();
//
//                final DictionaryDao dao = db.getDictionaryDao();
//                list = dao.selectAll(Const.FREUD);
//
//            } catch (Exception e) {
//                callback.onMessageRetrieved(e.getMessage());
//            }
//            if (list == null) {
//                notifyError();
//                return;
//            }
//
//
//            for (Dictionary dictionary : list) {
//                map.add(dictionary.getWord());
//            }
//
//            callback.onMessageRetrieved(list.size() + "|" + list.toString());
//
//
//            List<Dictionary> list2 = null;
//            try {
////                final HasseDictionaryDao dao2 = db.getHasseDictionaryDao();
////                list2 = dao2.selectAll();
//
//                final DictionaryDao dao = db.getDictionaryDao();
//                list2 = dao.selectAll(Const.HASSE);
//
//            } catch (Exception e) {
//                callback.onMessageRetrieved(e.getMessage());
//            }
//            if (list2 == null) {
//                notifyError();
//                return;
//            }
//
//
//            for (Dictionary dictionary : list2) {
//                map.add(dictionary.getWord());
//            }
//
//            callback.onMessageRetrieved(list2.size() + "|" + list2.toString());
//
//
//            List<Dictionary> list3 = null;
//            try {
//                final MillerDictionaryDao dao3 = db.getMillerDictionaryDao();
//                list3 = dao3.selectAll();
//            } catch (Exception e) {
//                callback.onMessageRetrieved(e.getMessage());
//            }
//            if (list3 == null) {
//                notifyError();
//                return;
//            }
//
//
//            for (Dictionary dictionary : list3) {
//                map.add(dictionary.getWord());
//            }
//
//            callback.onMessageRetrieved(list3.size() + "|" + list3.toString());
//
//
//            //Insert ...
////                KeywordDao keywordDao = db.getKeywordsDao();
////
////                Log.i(TAG, "run: " + map.size());
////                for (String key : map) {
////
////                    try {
////                        Log.i(TAG, "run: " + keywordDao.insert(new Keyword(key)));
////                    } catch (Exception e) {
////                        Log.i(TAG, "run: " + e.getLocalizedMessage());
////                    }
////                    //Log.i(TAG, "xxx " + key);
////                }
//
//        });
    }

    @Override
    public void selectAllCategories(Callback<List<Category>> callback) {
        mThreadExecutor.execute(() -> {
            try {
                final List<Category> list = categoryDao.selectAllCategories();
                if (callback != null) {
                    mMainThread.post(() -> callback.onMessageRetrieved(list));
                }
            } catch (Exception e) {
                DLog.handleException(e);
                mMainThread.post(() -> callback.onRetrievalFailed("Database error: " + e.getMessage()));
            }
        });
    }


    private void notifyError() {
        mMainThread.post(() -> {
            //callback.onRetrievalFailed("Nothing to welcome you with :(");
        });
    }

    @Override
    public void run() {

    }

    @Override
    public void getFullData(long _id, Callback<List<Status>> callback) {
        mThreadExecutor.execute(() -> {
            try {
                final List<Status> list = statusDao.getFullData(_id);
                if (callback != null) {
                    mMainThread.post(() -> callback.onMessageRetrieved(list));
                }
            } catch (Exception e) {
                DLog.handleException(e);
                mMainThread.post(() -> callback.onRetrievalFailed("Database error: " + e.getMessage()));
            }
        });
    }

    @Override
    public void getLikeData(String like, Callback<List<Status>> callback) {
        mThreadExecutor.execute(() -> {
            try {
                final List<Status> list = statusDao.getLike(like);
                if (callback != null) {
                    if (!list.isEmpty()) {
                        mMainThread.post(() -> callback.onMessageRetrieved(list));
                    } else {
                        mMainThread.post(() -> callback.onRetrievalFailed("Database is empty, reinstall the application"));
                    }
                }
            } catch (Exception e) {
                DLog.handleException(e);
                mMainThread.post(() -> callback.onRetrievalFailed("Database error: " + e.getMessage()));
            }
        });
    }

    @Override
    public void updateStatus(Status status, Callback<Integer> callback) {
        mThreadExecutor.execute(() -> {
            try {
                int result = statusDao.update(status);
                if (callback != null) {
                    if (result > 0) {
                        mMainThread.post(() -> callback.onMessageRetrieved(result));
                    } else {
                        mMainThread.post(() -> callback.onRetrievalFailed("Database is empty, reinstall the application"));
                    }
                }
            } catch (Exception e) {
                DLog.handleException(e);
                mMainThread.post(() -> {
                    if (callback != null) {
                        callback.onRetrievalFailed("Database error: " + e.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void getFavorite(Callback<List<Status>> callback) {
        mThreadExecutor.execute(() -> {
            try {
                final List<Status> list = statusDao.getFavorite();
                if (callback != null) {
                    mMainThread.post(() -> callback.onMessageRetrieved(list));
                }
            } catch (Exception e) {
                DLog.handleException(e);
                if (callback != null) {
                    mMainThread.post(() -> callback.onRetrievalFailed("Database error: " + e.getMessage()));
                }
            }
        });
    }

    public void randomStatus(int _id, Callback<Status> callback) {
        mThreadExecutor.execute(() -> {
            try {

                final Status status = statusDao.getById(_id);
                if (callback != null) {
                    mMainThread.post(() -> callback.onMessageRetrieved(status));
                }
            } catch (Exception e) {
                DLog.handleException(e);
                if (callback != null) {
                    mMainThread.post(() -> callback.onRetrievalFailed("Database error: " + e.getMessage()));
                }
            }
        });
    }

    @Override
    public void getByAuthorName(String name, Callback<List<Status>> callback) {

    }

    @Override
    public void getCategoryNamesByIds(List<Integer> cIdList, Callback<List<String>> callback) {

    }
}
