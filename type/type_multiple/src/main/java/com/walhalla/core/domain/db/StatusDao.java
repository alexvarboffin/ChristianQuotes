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
//    List<StatusI> queryShortList(long id);

    @Query("SELECT * FROM status")
    List<Status> selectAll();

//    @Query("SELECT status.* FROM status JOIN status_categories ON status.status_id = status_categories.status_id JOIN category ON status_categories.category_id = category.category_id WHERE status_categories.category_id = :id")
//    List<StatusI> getFullData(long id);

    @Query("SELECT status.* FROM status JOIN status_categories ON status.status_id = status_categories.status_id " +
            "WHERE status_categories.category_id = :id")
    List<Status> getFullData(long id);

//    @Query("SELECT * FROM status WHERE c_id=:id")
//    List<StatusI> getFullData(long id);

    @Query("SELECT * FROM status WHERE status_id = :id")
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
