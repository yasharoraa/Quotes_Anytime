package com.Innovvapp.anytimequotes.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.android.quotes.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Yash Arora on 05-02-2018.
 */

public class SupportusFragment extends Fragment {

    @BindView(R.id.thanks_user)
    TextView thanksUserTextview;

    @BindView(R.id.asking_support)
    TextView askingSupportTextView;

    @BindView(R.id.support_progress_bar)
    ProgressBar supportProgressBar;

    @BindView(R.id.support_wait)
    TextView supportWait;

    private Unbinder unbinder;


    private RewardedVideoAd mRewardedVideoAd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_layout, container, false);

        unbinder = ButterKnife.bind(this, view);


        thanksUserTextview.setText(R.string.tanks_support);

        askingSupportTextView.setText(R.string.support_title);


        supportWait.setText(R.string.support_wait_title);


        MobileAds.initialize(getContext(),"ca-app-pub-6198453078855259/5274680472");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());

        mRewardedVideoAd.loadAd("ca-app-pub-6198453078855259/5274680472", new AdRequest.Builder().build());




        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {


            @Override
            public void onRewardedVideoAdLoaded() {

                try {
                    mRewardedVideoAd.show();

                }catch (Exception e){
                    return;
                }





            }

            @Override
            public void onRewardedVideoAdOpened() {


            }

            @Override
            public void onRewardedVideoStarted() {
                Toast.makeText(getContext(),R.string.watch_full, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRewardedVideoAdClosed() {
                askingSupportTextView.setVisibility(View.GONE);
                supportProgressBar.setVisibility(View.GONE);
                supportWait.setVisibility(View.GONE);

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

                askingSupportTextView.setVisibility(View.GONE);
                supportProgressBar.setVisibility(View.GONE);
                supportWait.setVisibility(View.GONE);
            }


            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(getContext(), R.string.ad_video_failed, Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }




    @Override
    public void onPause() {
        super.onPause();
        mRewardedVideoAd.pause(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.resume(getContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRewardedVideoAd.destroy(getContext());
    }
}
