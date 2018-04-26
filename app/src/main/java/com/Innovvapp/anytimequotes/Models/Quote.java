package com.Innovvapp.anytimequotes.Models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.QuoteRealmProxy;
import io.realm.RealmObject;

/**
 * Created by Yash Arora on 22-01-2018.
 */
@Parcel( implementations= {QuoteRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = { Quote.class })
public class Quote extends RealmObject {

    @SerializedName("id")
    private int id;

    @SerializedName("quote")
    private String quote;

    @SerializedName("author")
    private String author;

    @SerializedName("photoId")
    private int photoId;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(@Nullable int photoId) {
        this.photoId = photoId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
            this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
