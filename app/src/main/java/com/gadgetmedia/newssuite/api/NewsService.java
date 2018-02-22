package com.gadgetmedia.newssuite.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * REST API access points
 */

public interface NewsService {
    @GET("facts.json")
    Call<NewsResponse> getNews();

}
