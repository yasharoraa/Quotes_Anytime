package com.Innovvapp.anytimequotes.Activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.android.android.quotes.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AboutActivity extends AppCompatActivity {

    AdView firstAdView;

    AdView secondAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MobileAds.initialize(this,getString(R.string.google_app_id));




        ActionBar actionBar = getSupportActionBar();
    try {
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }catch (Exception e)
    {
        return;
    }
    try {
        actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>About </font>"));

    }catch (NullPointerException e){
        return;
    }



       AdRequest adRequestone = new AdRequest.Builder()
                .build();

firstAdView = (AdView) findViewById(R.id.first_ad_view);



        AdRequest adRequesttwo = new AdRequest.Builder()
                .build();

 secondAdView = (AdView) findViewById(R.id.second_ad_view);

        secondAdView.loadAd(adRequesttwo);

        firstAdView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }


        });
        firstAdView.loadAd(adRequestone);

        secondAdView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }


        });
        secondAdView.loadAd(adRequesttwo);





    }
    @Override
    public void onPause() {
        if ( firstAdView!= null) {
            firstAdView.pause();
        }
        if (secondAdView != null) {
            secondAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (firstAdView != null) {
            firstAdView.resume();
        }
        if (secondAdView != null) {
            secondAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (firstAdView != null) {
            firstAdView.destroy();
        }
        if (secondAdView != null) {
            secondAdView.destroy();
        }
        super.onDestroy();
    }

}
