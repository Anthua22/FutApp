package com.example.futapp.Servicios;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearServicio {

    public static ServicioApiRest crearServicioArbitros(){
        String url = "http://10.0.2.2/liga/arbitros";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit.create(ServicioApiRest.class);
    }

    public static ServicioApiRest crearServicioEquipos(){
        String url = "http://192.168.0.132/liga/equipos";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit.create(ServicioApiRest.class);
    }

}
