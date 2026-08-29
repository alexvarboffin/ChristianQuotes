package com.walhalla.core.domain.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.walhalla.core.domain.entity.Status

@Dao
interface StatusDao {
    //    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    //    @Query("SELECT * FROM status WHERE _id=:id")
    //    List<Status> queryShortList(long id);
    @Query("SELECT * FROM status WHERE c_id=:id")
    fun getFullData(id: Long): MutableList<Status>?

    @Query("SELECT * FROM status WHERE _id = :id")
    fun getById(id: Long): Status?

    //    @Insert
    //    void insert(Question question);
    @Update
    fun update(question: Status): Int


    @get:Query("SELECT * FROM status WHERE liked>0")
    val favorite: MutableList<Status>

    @Query("SELECT * FROM status WHERE text LIKE :word")
    fun getLike(word: String): MutableList<Status>
//    @Delete
//    void delete(Question question);
}
