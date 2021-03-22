package com.sametkemalozdemir.retrofitjava.service;

import com.sametkemalozdemir.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {
    //GET, POST , UPDATE, DELETE


    //URL BASE = www.website.com
    //GET = price?key=

    //https://api.nomics.com/v1/prices?key=9e8e96f71f575607ae0be67fa7a2f6ca

    @GET("prices?key=9e8e96f71f575607ae0be67fa7a2f6ca")
    Observable<List<CryptoModel>> getData();

   // Call<List<CryptoModel>> getData();



}
