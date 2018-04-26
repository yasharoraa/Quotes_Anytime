package com.Innovvapp.anytimequotes.Utils;

import com.Innovvapp.anytimequotes.Models.Quote;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Yash Arora on 25-01-2018.
 */

public class RealmController {

    private final Realm realm;

    public RealmController() {

        realm = Realm.getDefaultInstance();

    }


    public List<Quote> getQuotes(){
        return realm.where(Quote.class).findAll();
    }
}
