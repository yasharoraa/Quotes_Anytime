package com.Innovvapp.anytimequotes.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Innovvapp.anytimequotes.Adapters.QuotesAdapter;
import com.Innovvapp.anytimequotes.Models.Quote;
import com.Innovvapp.anytimequotes.Utils.RealmController;
import com.android.android.quotes.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Yash Arora on 22-01-2018.
 */

public class SavedFragment extends Fragment {

    @BindView(R.id.fragment_favourite_recyclerView)
    RecyclerView quotesRecyclerView;

    @BindView(R.id.fragment_favorite_notification)
    TextView notification;

    private QuotesAdapter quotesAdapter;
    private List<Quote> quotes = new ArrayList<>();
    private Unbinder unbinder;

    InterstitialAd mInterstitialAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstisial_ad_unit_id));

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);


        unbinder = ButterKnife.bind(this, view);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        quotesRecyclerView.setLayoutManager(linearLayoutManager);
        quotesAdapter = new QuotesAdapter(getActivity(),quotes);
        quotesRecyclerView.setAdapter(quotesAdapter);

        getQuotes();

        return view;
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


   private void getQuotes(){
        RealmController realmController = new RealmController();
        quotes.addAll(realmController.getQuotes());
        if (quotes.size() == 0 ){
            try {
                notification.setVisibility(View.VISIBLE);
                quotesRecyclerView.setVisibility(View.GONE);

            }catch (NullPointerException e){
                e.printStackTrace();
            }


        }else{
            quotesAdapter.notifyDataSetChanged();
            try {
                notification.setVisibility(View.GONE);
                quotesRecyclerView.setVisibility(View.VISIBLE);

            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        super.onCreateView(getLayoutInflater(), (ViewGroup) getView(),null);

        quotes.clear();

        getQuotes();

        quotesAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
