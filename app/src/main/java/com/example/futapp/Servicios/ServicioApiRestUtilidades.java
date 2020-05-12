package com.example.futapp.Servicios;

import com.example.futapp.ClasesPojos.Arbitros;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioApiRestUtilidades {

    public ServicioApiRest servicioApiRest;

    public ServicioApiRestUtilidades(){
        servicioApiRest = crearServicioArbitros();

    }
    private ServicioApiRest crearServicioArbitros(){
        String url = "http://pdam13b.iesdoctorbalmis.info/liga/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit.create(ServicioApiRest.class);

    }





}
