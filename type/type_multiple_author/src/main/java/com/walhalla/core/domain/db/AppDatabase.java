package com.walhalla.core.domain.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.walhalla.core.domain.CategoryDao;
import com.walhalla.core.domain.Const;
import com.walhalla.core.domain.db.StatusDao;
import com.walhalla.core.domain.entity.Author;
import com.walhalla.core.domain.entity.Category;
import com.walhalla.core.domain.entity.Status;
import com.walhalla.core.domain.entity.StatusCategoryContract;


@Database(entities = {Author.class, Category.class, Status.class, StatusCategoryContract.class}, version = Const.version, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract StatusDao statusDao();

    public abstract AuthorDao authorDao();
}