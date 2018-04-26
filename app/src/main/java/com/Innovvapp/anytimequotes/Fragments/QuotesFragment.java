package com.Innovvapp.anytimequotes.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Innovvapp.anytimequotes.Adapters.QuotesAdapter;
import com.Innovvapp.anytimequotes.Models.Quote;
import com.Innovvapp.anytimequotes.Utils.EndlessRecyclerOnScrollListener;
import com.Innovvapp.anytimequotes.Webservices.ApiInterface;
import com.Innovvapp.anytimequotes.Webservices.ServiceGenerator;
import com.android.android.quotes.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yash Arora on 22-01-2018.
 */

public class QuotesFragment extends Fragment {
    private final static String TAG = QuotesFragment.class.getSimpleName();

    private RewardedVideoAd mAd;

    @BindView(R.id.fragment_quotes_recycler_view)
    RecyclerView quotesRecyclerView;

    @BindView(R.id.fragment_quotes_progress)
    ProgressBar progressBar;

    @BindView(R.id.try_again_image_view)
    ImageView tryAgainImage;

    @BindView(R.id.try_again_text_view)
    TextView tryAgainText;

    private QuotesAdapter quotesAdapter;
    private ArrayList<Quote>quotes = new ArrayList<>();

    private Unbinder unbinder;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    InterstitialAd interstitialAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        unbinder = ButterKnife.bind(this,view);

        interstitialAd = new InterstitialAd(getContext());

        interstitialAd.setAdUnitId(getString(R.string.interstisial_ad_unit_id));

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        quotesRecyclerView.setLayoutManager(linearLayoutManager);

        quotesRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager){
            @Override
            public void onLoadMore(int current_page) {

                AdRequest adRequest = new AdRequest.Builder()
                        .build();

                interstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });

                interstitialAd.loadAd(adRequest);

                getQuotes();
            }
        });

        tryAgainText.setText(R.string.try_again_title);


        quotesAdapter = new QuotesAdapter(getActivity(), quotes);
        quotesRecyclerView.setAdapter(quotesAdapter);

        showProgressBar(true);

        getQuotes();

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        try {

                            tryAgainImage.setVisibility(View.GONE);
                            tryAgainText.setVisibility(View.GONE);

                        }catch (NullPointerException e){

                        }


                        refreshAction();


                    }
                }
        );


        return view;
    }

    private void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    public List<Quote> getRecyclerViewItems(){
        return quotes;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey("key"))

                quotes = Parcels.unwrap(savedInstanceState.getParcelable("key"));

                quotesAdapter = new QuotesAdapter(getActivity(), quotes);

            quotesRecyclerView.setAdapter(quotesAdapter);

            progressBar.setVisibility(View.GONE);
            quotesRecyclerView.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("key", Parcels.wrap(quotes));

    }

    public void refreshAction(){

        Context context = getContext();

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isAvailable()) {
                LoadAgain();

            } else {
                Toast.makeText(context, "Check Network Connectivity And Try Again", Toast.LENGTH_SHORT).show();
                try {
                    swipeRefreshLayout.setRefreshing(false);

                } catch (NullPointerException e) {

                }
            }
        }catch (Exception e){
            LoadAgain();
        }
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

        interstitialAd.loadAd(adRequest);

    }

    public void LoadAgain(){

        super.onCreateView(getLayoutInflater(), (ViewGroup) getView(),null);



        quotes.clear();



        getQuotes();

        showProgressBar(true);

        quotesAdapter.notifyDataSetChanged();

    }

    private void  getQuotes() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);

        Call<List<Quote>> call = apiInterface.getQuotes();

        call.enqueue(new Callback<List<Quote>>() {

            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful()) {

                    quotes.addAll(response.body());

                    quotesAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Fail" + response.message());


                        try {
                            if (quotes.size()<5) {


                                tryAgainImage.setVisibility(View.VISIBLE);

                            tryAgainText.setVisibility(View.VISIBLE);

                        }

                        }catch (NullPointerException e) {
                    }



                }
                showProgressBar(false);

                try {
                    swipeRefreshLayout.setRefreshing(false);

                }catch (NullPointerException e){


                }

            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                Log.d(TAG, "Fail: " + t.getMessage());

                try {

                    tryAgainImage.setVisibility(View.VISIBLE);

                    tryAgainText.setVisibility(View.VISIBLE);

                }catch (NullPointerException e)
                {
                    return;

                }

                showProgressBar(false);

            }
        });
    }


    private void showProgressBar(boolean isShow){
        if(isShow){
            try {
                progressBar.setVisibility(View.VISIBLE);

            }catch (NullPointerException e){
                e.printStackTrace();
            }

            try {
                quotesRecyclerView.setVisibility(View.GONE);

            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }else{
            try {
                progressBar.setVisibility(View.GONE);

            }catch (NullPointerException e){
                e.printStackTrace();
            }
            try {
                quotesRecyclerView.setVisibility(View.VISIBLE);

            }catch (NullPointerException e){
                e.printStackTrace();

            }


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        unbinder.unbind();
    }
}

