package com.example.tourmatebase03;



import com.example.tourmatebase03.Nearby.NearbyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NearbyService {

    @GET
    Call<NearbyResponse>getNearbyPlaces(@Url String endUrl);
}
