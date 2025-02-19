package com.walhalla.core.domain.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.walhalla.core.domain.Const;
import com.walhalla.view.adapter.ViewModel;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Author implements Comparable<Author>, Serializable, ViewModel {

    @PrimaryKey(autoGenerate = true)
    private long _id;

    private String name;

    public Author(Long id, String name) {
        this._id = id;
        this.name = name;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        this._id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return _id == author._id && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name);
    }

    @Override
    public int compareTo(Author another) {
        // Сравниваем объекты по именам авторов
        return this.name.compareTo(another.name);
    }

    @Override
    public int getItemType() {
        return Const.TYPE_AUTHOR;
    }
}

