package com.example.futapp.Servicios;

import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Partidos;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServicioApiRest {
    @GET("Arbitros")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<List<Arbitros>> getArbitros();

    @GET("Equipos")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<List<Equipos>> getEquipos();

    @GET("Partidos")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<List<Partidos>> getPartios();

    @PUT("Partidos/{idPartido}")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<Partidos> updatePartido(@Path("idPartido") int idPartido, @Body Partidos partido);



}
