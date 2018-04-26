package com.Innovvapp.anytimequotes.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.android.android.quotes.R;


/**
 * Created by Yash Arora on 22-01-2018.
 */

public class Functions {
    public static void changeMainFragment(FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }


}
