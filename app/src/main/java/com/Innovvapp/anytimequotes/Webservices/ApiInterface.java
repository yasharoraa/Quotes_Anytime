package com.Innovvapp.anytimequotes.Webservices;

import com.Innovvapp.anytimequotes.Models.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Yash Arora on 22-01-2018.
 */

public interface ApiInterface {

    @GET("quotes")
    Call<List<Quote>> getQuotes();

}
