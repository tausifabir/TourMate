package com.example.tourmatebase03.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @GET
    Call<CurrentWeather> getCurrentWeather(@Url String url);
 }
