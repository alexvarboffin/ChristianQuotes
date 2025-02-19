package com.walhalla.core.domain.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.walhalla.core.domain.entity.Author;

import java.util.List;

@Dao
public interface AuthorDao {
    @Query("SELECT * FROM author")
    LiveData<List<Author>> getAllAuthors();

    @Insert
    void insertAuthor(Author author);
}
