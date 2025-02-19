package com.walhalla.core.domain.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.walhalla.core.domain.entity.Status;

import java.util.List;

@Dao
public interface StatusDao {

//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("SELECT * FROM status WHERE _id=:id")
//    List<Status> queryShortList(long id);

    @Query("SELECT * FROM status WHERE c_id=:id")
    List<Status> getFullData(long id);

    @Query("SELECT * FROM status WHERE _id = :id")
    Status getById(long id);

//    @Insert
//    void insert(Question question);

    @Update
    int update(Status question);

    @Query("SELECT * FROM status WHERE liked>0")
    List<Status> getFavorite();

    @Query("SELECT * FROM status WHERE text LIKE :word")
    List<Status> getLike(String word);
//    @Delete
//    void delete(Question question);
}
