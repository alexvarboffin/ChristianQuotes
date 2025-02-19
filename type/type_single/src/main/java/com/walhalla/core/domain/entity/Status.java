package com.walhalla.core.domain.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.walhalla.core.adapter.StatusAdapter;
import com.walhalla.core.adapter.StatusI;
import com.walhalla.view.adapter.ViewModel;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Status implements Serializable, ViewModel, StatusI {

    @Ignore
    public int imagesIndex = 0;
    @Ignore
    public int typeface;

    public static Status valueOf(StatusI status) {
        Status tmp = new Status();
        tmp._id = status.getId();
        tmp.text = status.getText();
        tmp.author = status.getAuthor();
        tmp.rate = status.rate();
        tmp.c_id = status.catIdLong();
        tmp.liked = status.isLiked() ? 1 : 0;
        return tmp;
    }

    public Long getId() {
        return _id;
    }

    @Override
    public String catIdString() {
        return null;
    }

    @Override
    public Long catIdLong() {
        return null;
    }

    @Override
    public int rate() {
        return 0;
    }

    @Override
    public void setTypeface(int typeface) {
        this.typeface = typeface;
    }

    @Override
    public int typeface() {
        return typeface;
    }

    /* Not set Nullable */
    @PrimaryKey
    @ColumnInfo(name = "_id")
    public Long _id;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "rate")
    public int rate = 100;

    //single id
    @ColumnInfo(name = "c_id")
    public Long c_id = 1L;


    //multi id
//    @ColumnInfo(name = "c_id")
//    public String c_id = "";

    @ColumnInfo(name = "liked")
    public int liked = 0;
    //public String icon;

    public Status(long _id, String text, long catId, String author) {
        this._id = _id;
        this.text = text;
        this.c_id = catId;
        this.author = author;
    }

    public Status() {
    }

    @Override
    public int getItemType() {
        return StatusAdapter.TYPE_STATUS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(text, status.text);
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void imageIndexIncrement() {
        imagesIndex++;
    }

    @Override
    public int imagesIndex() {
        return imagesIndex;
    }

    @Override
    public void imageIndexDecrement() {
        imagesIndex--;
    }

    @Override
    public void setImagesIndex(int i) {
        imagesIndex = i;
    }

    @Override
    public void setLiked() {
        liked = 1;
    }

    @Override
    public void setDisLiked() {
        liked = 0;
    }

    @Override
    public boolean isLiked() {
        return liked == 1;
    }

//    @Override
//    public void imagesIndex(int value) {
//        imagesIndex = value;
//    }
}
