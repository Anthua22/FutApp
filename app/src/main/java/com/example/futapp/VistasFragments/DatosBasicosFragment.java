package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosBasicosFragment extends Fragment {

    Partidos partidos;
    TextView equipolocal, equipovisitante, fecha, hora, jornada, delegado, direccion;
    ImageView equilocalfoto, equipovisitantefoto;
    public DatosBasicosFragment(Partidos partidos)
    {
        this.partidos = partidos;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datos_partido,container,false);
        asignarID(view);
        asignarValores();

        return view;
    }

    void asignarID(View view){
        equilocalfoto = view.findViewById(R.id.equipolocalfoto);
        equipovisitante = view.findViewById(R.id.nombrevisitante);
        equipovisitantefoto = view.findViewById(R.id.equipoVisitantefoto);
        equipolocal = view.findViewById(R.id.nombrelocal);
        fecha = view.findViewById(R.id.fecha_partido);
        hora = view.findViewById(R.id.hora_encuentro);
        jornada = view.findViewById(R.id.jornada);
        direccion = view.findViewById(R.id.direccion);
        delegado = view.findViewById(R.id.delegado_campo);
    }

    void asignarValores(){

        String[] fechahora = partidos.getFecha_encuentro().split(" ");
        String fecha = fechahora[0];
        String hora = fechahora[1];
        this.fecha.setText(fecha);
        this.hora.setText(hora);
        direccion.setText(partidos.getDireccion_encuentro());
        jornada.setText(partidos.getJornada()+"");
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<List<Equipos>> response = servicioApiRestUtilidades.servicioApiRest.getEquipos();
        response.enqueue(new Callback<List<Equipos>>() {
            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {
                if(response.isSuccessful()){
                    for(Equipos x : response.body()){
                        if(partidos.getEquipoLocal() == x.getIdEquipo()){
                            equipolocal.setText(x.getNombre());
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(equilocalfoto);
                            }else{
                                equilocalfoto.setImageResource(R.drawable.equipodefecto);
                            }

                        }else if(partidos.getEquipoVisitante() == x.getIdEquipo()){
                            equipovisitante.setText(x.getNombre());
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
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
                Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
