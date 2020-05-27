package com.example.futapp.Holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.MainActivity;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HolderPartidos extends RecyclerView.ViewHolder {

    TextView arbitros, equipolocal, equipovisitante, fechaecnuentro, arbitrosasistentes;
    ImageView equipolocalfoto, equipovisitantefoto;
    ServicioApiRestUtilidades servicioApiRest;
    LinearLayout arbitrosAsi;
    Context context;


    public HolderPartidos(@NonNull View itemView, Context context) {
        super(itemView);
        arbitros = itemView.findViewById(R.id.arbitros);
        equipolocalfoto = itemView.findViewById(R.id.fotoEquipoLocal);
        equipovisitantefoto = itemView.findViewById(R.id.fotoEquipoVisitante);
        equipolocal = itemView.findViewById(R.id.equipoLocal);
        equipovisitante = itemView.findViewById(R.id.equipoVisitante);
        fechaecnuentro = itemView.findViewById(R.id.fecha_encuentro);
        arbitrosasistentes = itemView.findViewById(R.id.arbitros_asistentes);
        arbitrosAsi = itemView.findViewById(R.id.arbitrossecund);
        servicioApiRest = new ServicioApiRestUtilidades();
        this.context = context;
    }

    public void bind(Partidos partido){

        Call<List<Equipos>> response = servicioApiRest.servicioApiRest.getEquipos();
        fechaecnuentro.setText(partido.getFecha_encuentro());

        response.enqueue(new Callback<List<Equipos>>() {
            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {
                if(response.isSuccessful()){
                    for(Equipos x : response.body()){
                        if(x.getIdEquipo() == partido.getEquipoLocal()){
                            equipolocal.setText(x.getNombre());
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(context).load(x.getFoto())
                                        .into(equipolocalfoto);
                            }else{
                                equipolocalfoto.setImageResource(R.drawable.equipodefecto);
                            }

                        }else if(x.getIdEquipo() == partido.getEquipoVisitante()){
                            equipovisitante.setText(x.getNombre());
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(context).load(x.getFoto())
                                        .into(equipovisitantefoto);
                            }else{
                                equipovisitantefoto.setImageResource(R.drawable.equipodefecto);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Equipos>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        if(partido.getCronometrador() !=0){
            arbitrosAsi.setVisibility(View.VISIBLE);
        }
        Call<List<Arbitros>> responseArbitros = servicioApiRest.servicioApiRest.getArbitros();

        responseArbitros.enqueue(new Callback<List<Arbitros>>() {
            @Override
            public void onResponse(Call<List<Arbitros>> call, Response<List<Arbitros>> response) {
                if(response.isSuccessful()){
                    for(Arbitros x : response.body()){
                        if(x.getId() == partido.getArbitroprincipal() || x.getId() == partido.getArbitrosecundario()){
                            if(arbitros.getText().toString().length() >0){
                                arbitros.setText(arbitros.getText().toString()+", "+x.getNombre_completo());
                            }else{
                                arbitros.setText(x.getNombre_completo());
                            }
                        }else if (x.getId() == partido.getCronometrador() || x.getId() == partido.getTercer_arbitro()){
                            if(arbitrosasistentes.getText().toString().length()>0){
                                arbitrosasistentes.setText(arbitrosasistentes.getText().toString()+", "+x.getNombre_completo());
                            }else{
                                arbitrosasistentes.setText(x.getNombre_completo());
                            }

                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Arbitros>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

}
