package com.example.futapp.VistasFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futapp.Adaptadores.AdaptadorPartidosNuevos;
import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartidosNuevosTabFragment extends Fragment {

    RecyclerView recyclerView;
    AdaptadorPartidosNuevos adaptadorPartidosNuevos;
    List<Partidos> partidos;
    Arbitros actual;
    ServicioApiRestUtilidades servicioApiRestUtilidades;

    public PartidosNuevosTabFragment(Arbitros actual) {
        this.actual = actual;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partidos_nuevos_tab, container,false);
        partidos = new ArrayList<>();
        servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        recyclerView = view.findViewById(R.id.recycler_partidos_nuevos);
        adaptadorPartidosNuevos = new AdaptadorPartidosNuevos(partidos, getActivity());
        recyclerView.setAdapter(adaptadorPartidosNuevos);
        obtenerPartidos();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        return view;
    }

    private void obtenerPartidos(){
        Call<List<Partidos>> response = servicioApiRestUtilidades.servicioApiRest.getPartidos();
        response.enqueue(new Callback<List<Partidos>>() {
            @Override
            public void onResponse(Call<List<Partidos>> call, Response<List<Partidos>> response) {
                if(response.isSuccessful()){
                    for(Partidos x : response.body()){
                        if(x.getArbitroprincipal() == actual.getId() || x.getArbitrosecundario() == actual.getId() || x.getCronometrador() == actual.getId() || x.getTercer_arbitro() == actual.getId())
                        {
                            partidos.add(x);
                            adaptadorPartidosNuevos.notifyDataSetChanged();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Partidos>> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }


}
