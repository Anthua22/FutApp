package com.example.futapp.Servicios;

import com.example.futapp.ClasesPojos.Arbitro;
import com.example.futapp.ClasesPojos.Equipo;
import com.example.futapp.ClasesPojos.Partido;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ServicioApiRest {
    @GET("arbitros")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<List<Arbitro>> getArbitros();

    @GET("arbitros")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<Arbitro> getArbitro(@QueryMap Map<String, String> map);

    @GET("equipos")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<List<Equipo>> getEquipos();

    @GET("partidos")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<List<Integer>> getPartios();

    @PUT("partidos/{idPartido}")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<Partido> updatePartido(@Path("idPartido") int idPartido, @Body Partido partido);



}
