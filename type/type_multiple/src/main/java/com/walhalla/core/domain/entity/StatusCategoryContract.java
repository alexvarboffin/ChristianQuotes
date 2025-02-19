package com.walhalla.core.domain.entity;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.walhalla.core.domain.entity.Category;
import com.walhalla.core.domain.entity.Status;

@Keep
@Entity(tableName = "status_categories",
        primaryKeys = {
                "status_id", "category_id"
        },
        foreignKeys = {
                @ForeignKey(entity = Category.class,
                        parentColumns = "_id",
                        childColumns = "category_id",
                        onUpdate = ForeignKey.NO_ACTION,
                        onDelete = ForeignKey.NO_ACTION),

                @ForeignKey(entity = Status.class,
                        parentColumns = "status_id",
                        childColumns = "status_id",
                        onUpdate = ForeignKey.NO_ACTION, onDelete = ForeignKey.NO_ACTION)

        })
public class StatusCategoryContract {

    @ColumnInfo(name = "category_id")//, defaultValue = "0"
    public int categoryId;

    @ColumnInfo(name = "status_id")//, defaultValue = "0"
    public int statusId;

//    public StatusCategoryContract(int categoryId, int statusId) {
//        this.categoryId = categoryId;
//        this.statusId = statusId;
//    }

    public StatusCategoryContract(int categoryId, int statusId) {
        this.statusId = statusId;
        this.categoryId = categoryId;
    }
}