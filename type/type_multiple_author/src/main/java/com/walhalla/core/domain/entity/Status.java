package com.walhalla.core.domain.entity;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.walhalla.core.adapter.StatusAdapter;
import com.walhalla.core.adapter.StatusI;

import java.io.Serializable;

@Keep
@Entity(tableName = "status")
public class Status implements Serializable, StatusI {
    @Ignore
    public int imagesIndex = 0;

    @Ignore
    public int typeface;

    /* Not set Nullable */
    @PrimaryKey
    @ColumnInfo(name = "status_id")
    public Long _id;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "c_id")
    public String c_id = "";

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "rate")
    public int rate = 100;

//    @ColumnInfo(name = "c_id")
//    public Long c_id = 1L;

    @ColumnInfo(name = "liked")
    public int liked = 0;

    //public String icon;

    public Status(String text, String author) {
        this.text = text;
        this.author = author;
    }

    @Ignore
    public Status() {
    }//keep

    public static Status valueOf(StatusI status) {
        Status tmp = new Status();
        tmp._id = status.getId();
        tmp.text = status.getText();
        tmp.author = status.getAuthor();
        tmp.rate = status.rate();
        tmp.c_id = status.catIdString();
        tmp.liked = status.isLiked() ? 1 : 0;
        return tmp;
    }


    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public boolean isLiked() {
        return liked > 0;
    }

//    @Override
//    public void imagesIndex(int value) {
//        imagesIndex = value;
//    }

    @Override
    public Long getId() {
        return _id;
    }


    @Override
    public String catIdString() {
        return c_id;//yes
    }

    @Override
    public Long catIdLong() {
        return 0L;
    }

    @Override
    public int rate() {
        return rate;
    }

    @Override
    public void setTypeface(int typeface) {
        this.typeface = typeface;
    }

    @Override
    public int typeface() {
        return typeface;
    }

    public void setLiked() {
        liked = 1;
    }

    public void setDisLiked() {
        liked = 0;
    }

    public void imageIndexIncrement() {
        imagesIndex++;
    }

    public void imageIndexDecrement() {
        imagesIndex--;
    }

    public void setImagesIndex(int value) {
        imagesIndex = value;
    }

    public int imagesIndex() {
        return imagesIndex;
    }

    @Override
    public int getItemType() {
        return StatusAdapter.TYPE_STATUS;
    }
}
