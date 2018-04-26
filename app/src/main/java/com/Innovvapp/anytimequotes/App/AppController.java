package com.Innovvapp.anytimequotes.App;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Yash Arora on 25-01-2018.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("quotes.realm")
                .build();


        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
