package com.example.futapp.Holders;

import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HolderPartidos extends RecyclerView.ViewHolder {

    TextView arbitros, equipolocal, equipovisitante, fechaecnuentro;
    ImageView equipolocalfoto, equipovisitantefoto;
    ServicioApiRestUtilidades servicioApiRest;


    public HolderPartidos(@NonNull View itemView) {
        super(itemView);
        arbitros = itemView.findViewById(R.id.arbitros);
        equipolocalfoto = itemView.findViewById(R.id.fotoEquipoLocal);
        equipovisitantefoto = itemView.findViewById(R.id.fotoEquipoVisitante);
        equipolocal = itemView.findViewById(R.id.equipoLocal);
        equipovisitante = itemView.findViewById(R.id.equipoVisitante);
        fechaecnuentro = itemView.findViewById(R.id.fecha_encuentro);
        servicioApiRest = new ServicioApiRestUtilidades();
    }

    public void bindPrimeraSegunda(Partidos partido){

        //Http
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy();



        //equipolocal.setText(local.getNombre());
        //equipovisitante.setText(visitante.getNombre());
        fechaecnuentro.setText(partido.getFecha_encuentro());
        //arbitros.setText("Árbitro Principal: "+arbitroprincipal.getNombre_completo()+'\n'+"Árbitro Secundario: "+arbitrosecundario.getNombre_completo()+'\n'+ "Cronometrador: "+cronometrador.getNombre_completo()+'\n'+"Tercer Árbitro: "+tercer_arbitro.getNombre_completo());
        Call<List<Partidos>> respose = servicioApiRest.servicioApiRest.getPartidos();
        respose.enqueue(new Callback<List<Partidos>>() {
            @Override
            public void onResponse(Call<List<Partidos>> call, Response<List<Partidos>> response) {
                if(response.isSuccessful()){
                    for(Partidos x : response.body()){
                        Call<List<Arbitros>> arbitro = servicioApiRest.servicioApiRest.getArbitro(x.getArbitroprincipal());
                        arbitro.enqueue(new Callback<List<Arbitros>>() {
                            @Override
                            public void onResponse(Call<List<Arbitros>> call, Response<List<Arbitros>> response) {
                                if(response.isSuccessful()){
                                    arbitros.setText("Árbitro Principal: "+response.body().get(0));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Arbitros>> call, Throwable t) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Partidos>> call, Throwable t) {

            }
        });

    }
}
