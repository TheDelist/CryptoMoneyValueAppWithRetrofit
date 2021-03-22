package com.sametkemalozdemir.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sametkemalozdemir.retrofitjava.R;
import com.sametkemalozdemir.retrofitjava.adapter.RecyclerAdapter;
import com.sametkemalozdemir.retrofitjava.model.CryptoModel;
import com.sametkemalozdemir.retrofitjava.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL="https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.nomics.com/v1/prices?key=9e8e96f71f575607ae0be67fa7a2f6ca

        recyclerView=findViewById(R.id.recyclerView);

        //Retrofit & gson
        Gson gson=new GsonBuilder().setLenient().create();

         retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).build();
        loadData();


    }
    private void loadData(){
        CryptoAPI cryptoAPI=retrofit.create(CryptoAPI.class);

        compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::handlerResponse));




        /*
        Call<List<CryptoModel>> call=cryptoAPI.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()){
                    List<CryptoModel> cryptosFromURL=response.body();
                    cryptoModels=new ArrayList<>(cryptosFromURL);

                    //Recycler Adapter
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerAdapter=new RecyclerAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


         */
    }

    private void handlerResponse(List<CryptoModel> cryptoModelsFromUrl){
        cryptoModels=new ArrayList<>(cryptoModelsFromUrl);

        //Recycler Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerAdapter=new RecyclerAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}