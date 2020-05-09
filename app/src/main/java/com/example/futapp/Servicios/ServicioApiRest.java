package com.example.futapp.Servicios;

import com.example.futapp.ClasesPojos.Arbitro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ServicioApiRest {
    @GET("arbitros")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<List<Arbitro>> getArbitros();

    @GET("partidos")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<List<Integer>> getPartios();

}
