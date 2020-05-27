package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futapp.Adaptadores.AdaptadorJugadores;
import com.example.futapp.ClasesPojos.Jugadores;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;
import com.example.futapp.Servicios.onDialogoEventoClickListener;
import com.example.futapp.Servicios.onDialogoFuncionClickListener;
import com.example.futapp.Servicios.onDialogoGolClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JugadoresLocalesFragment extends Fragment {

    RecyclerView recyclerView;
    TextView titulares, suplentes, porteros, capitan;
    List<Jugadores> jugadoresList;
    Partidos partidos;
    AdaptadorJugadores adaptadorJugadores;
    int posicion;

    public JugadoresLocalesFragment(Partidos partidos) {
        this.partidos = partidos;
        jugadoresList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jugadores_locales, container,false);
        asignarID(view);
        adaptadorJugadores = new AdaptadorJugadores(jugadoresList,getActivity());
        recyclerView.setAdapter(adaptadorJugadores);
        obtenerJugadores();
        adaptadorJugadores.onClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion = recyclerView.getChildAdapterPosition(v);
            }
        });
        adaptadorJugadores.setClickeventoDialogo(new onDialogoEventoClickListener() {
            @Override
            public void onEventoClick(Jugadores jugadores) {
                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(posicion);
                DialogFragment dialogFragment = new DialogoEventoFragment(jugadores, holder);
                dialogFragment.show(getFragmentManager(),"evento");
            }
        });
        adaptadorJugadores.setClickfuncionDialogo(new onDialogoFuncionClickListener() {
            @Override
            public void onFuncionClick(Jugadores jugadores) {

                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(posicion);
                DialogFragment dialogFragment = new DialogoFuncionFragment(jugadores, pasarArraylistCabecera(), holder);

                dialogFragment.show(getFragmentManager(),"funcion");
            }
        });
        adaptadorJugadores.setClickgolDialogo(new onDialogoGolClickListener() {
            @Override
            public void onGolClick(Jugadores jugadores) {
                DialogFragment dialogFragment = new DialogoGolFragment(jugadores);
                dialogFragment.show(getFragmentManager(),"gol");
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        return  view;
    }

    void obtenerJugadores(){
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<List<Jugadores>> response = servicioApiRestUtilidades.servicioApiRest.getJugadores();
        response.enqueue(new Callback<List<Jugadores>>() {
            @Override
            public void onResponse(Call<List<Jugadores>> call, Response<List<Jugadores>> response) {
                if(response.isSuccessful()){
                    for(Jugadores x : response.body()){
                        if(x.getEquipo() == partidos.getEquipoLocal()){
                            jugadoresList.add(x);
                            adaptadorJugadores.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Jugadores>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void asignarID(View view){
        recyclerView = view.findViewById(R.id.recyclerlocales);
        titulares = view.findViewById(R.id.numerotitulares);
        suplentes = view.findViewById(R.id.suplentes);
        porteros = view.findViewById(R.id.porteros);
        capitan = view.findViewById(R.id.capitan);
    }

    private ArrayList<TextView> pasarArraylistCabecera(){
        ArrayList<TextView> cabera = new ArrayList<>();
        cabera.add(titulares);
        cabera.add(suplentes);
        cabera.add(porteros);
        cabera.add(capitan);
        return cabera;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       // outState.put
    }
}
