package com.example.futapp.VistasFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.futapp.Adaptadores.AdaptadorJugadores;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Jugadores;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;
import com.example.futapp.Servicios.OnDialogoEventoClickListener;
import com.example.futapp.Servicios.OnDialogoFuncionClickListener;
import com.example.futapp.Servicios.OnDialogoGolClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JugadoresLocalesVisitantesFragment extends Fragment {

    RecyclerView recyclerView, recyclerViewvisitantes;
    TextView titulares, suplentes, porteros, capitan, titularesvisitantes, suplentesvisitantes, porterosvisitantes, capitanvisitante;
    List<Jugadores> jugadoreslocalesList;
    List<Jugadores> jugadoresvisitanteList;
    Partidos partidos;
    AdaptadorJugadores adaptadorJugadores, adaptadorjugadoresvisitantes;

    TextView nombreequipolocal, nommbreequipovisitante;
    ImageView local, visitante;
    FloatingActionButton enviar;

    View view;

    EnviarAlineaciones enviarAlineaciones;

    public interface EnviarAlineaciones{
        void Enviar(String alineacion);
    }

    public JugadoresLocalesVisitantesFragment(Partidos partidos) {
        this.partidos = partidos;
        jugadoreslocalesList = new ArrayList<>();
        jugadoresvisitanteList = new ArrayList<>();
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.jugadores_locales_visitantes, container,false);
        asignarID(view);
        adaptadorJugadores = new AdaptadorJugadores(jugadoreslocalesList,getActivity());
        adaptadorjugadoresvisitantes = new AdaptadorJugadores(jugadoresvisitanteList,getActivity());
        recyclerView.setAdapter(adaptadorJugadores);
        recyclerViewvisitantes.setAdapter(adaptadorjugadoresvisitantes);
        obtenerJugadores();
        ponernombreyFoto();
        clicksItemsRecycler();
        recyclerViewvisitantes.setHasFixedSize(true);
        recyclerViewvisitantes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarQuinteto()){
                    enviarAlineaciones.Enviar(obtenerAlicneaciones());
                }else{
                    Toast.makeText(getActivity(), "Para manadar la alineación hacer falta que los equipos cuenten con al menos 5 jugadores",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return  view;
    }

    void clicksItemsRecycler(){


        adaptadorJugadores.setClickeventoDialogo(new OnDialogoEventoClickListener() {
            @Override
            public void onEventoClick(int l,Jugadores jugadores) {
                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(l);
                DialogFragment dialogFragment = new DialogoEventoFragment(jugadores, holder);
                dialogFragment.show(getFragmentManager(),"evento");
            }
        });
        adaptadorJugadores.setClickfuncionDialogo(new OnDialogoFuncionClickListener() {
            @Override
            public void onFuncionClick(int n,Jugadores jugadores) {

                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(n);

                DialogFragment dialogFragment = new DialogoFuncionFragment(jugadores, pasarArraylistCabeceraLocales(), holder);

                dialogFragment.show(getFragmentManager(),"funcion");
            }
        });
        adaptadorJugadores.setClickgolDialogo(new OnDialogoGolClickListener() {
            @Override
            public void onGolClick(int j ,Jugadores jugadores) {

                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(j);
                DialogFragment dialogFragment = new DialogoGolFragment(jugadores,holder);
                dialogFragment.show(getFragmentManager(),"gol");
            }
        });


        //Visitantes



        adaptadorjugadoresvisitantes.setClickeventoDialogo(new OnDialogoEventoClickListener() {
            @Override
            public void onEventoClick(int p,Jugadores jugadores) {
                RecyclerView.ViewHolder holder = recyclerViewvisitantes.findViewHolderForAdapterPosition(p);
                DialogFragment dialogFragment = new DialogoEventoFragment(jugadores, holder);
                dialogFragment.show(getFragmentManager(),"eventovisitante");
            }
        });
        adaptadorjugadoresvisitantes.setClickfuncionDialogo(new OnDialogoFuncionClickListener() {
            @Override
            public void onFuncionClick(int n,Jugadores jugadores) {
                RecyclerView.ViewHolder holder = recyclerViewvisitantes.findViewHolderForAdapterPosition(n);

                DialogFragment dialogFragment = new DialogoFuncionFragment(jugadores, pasarArraylistCabeceraVisitantes(), holder);

                dialogFragment.show(getFragmentManager(),"funcionvisitante");
            }
        });
        adaptadorjugadoresvisitantes.setClickgolDialogo(new OnDialogoGolClickListener() {
            @Override
            public void onGolClick(int n,Jugadores jugadores) {
                RecyclerView.ViewHolder holder = recyclerViewvisitantes.findViewHolderForAdapterPosition(n);

                DialogFragment dialogFragment = new DialogoGolFragment(jugadores,holder);
                dialogFragment.show(getFragmentManager(),"gol");
            }
        });



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
                            jugadoreslocalesList.add(x);
                            adaptadorJugadores.notifyDataSetChanged();
                        }else if(x.getEquipo() == partidos.getEquipoVisitante()){
                            jugadoresvisitanteList.add(x);
                            adaptadorjugadoresvisitantes.notifyDataSetChanged();
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

        nombreequipolocal = view.findViewById(R.id.equipolocalnombre);
        nommbreequipovisitante = view.findViewById(R.id.equipovisitantenombre);
        local = view.findViewById(R.id.equipolocalfotojugadores);
        visitante = view.findViewById(R.id.equipovisitantefotojugadores);

        titularesvisitantes = view.findViewById(R.id.numerotitularesvisitantes);
        suplentesvisitantes = view.findViewById(R.id.suplentesvisitantes);
        porterosvisitantes = view.findViewById(R.id.porterosvisitantes);
        capitanvisitante = view.findViewById(R.id.capitanvisitantes);

        enviar = view.findViewById(R.id.enviaralineacion);

        recyclerViewvisitantes = view.findViewById(R.id.recyclervisitantes);
    }

    void ponernombreyFoto(){
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<List<Equipos>> response = servicioApiRestUtilidades.servicioApiRest.getEquipos();

        response.enqueue(new Callback<List<Equipos>>() {
            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {
                if(response.isSuccessful()){
                    for(Equipos x : response.body()){
                        if(x.getIdEquipo() == partidos.getEquipoLocal()){
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(local);
                            }else{
                                local.setImageResource(R.drawable.equipodefecto);
                            }

                            nombreequipolocal.setText(x.getNombre());
                        }
                        else if (x.getIdEquipo() == partidos.getEquipoVisitante()){
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(visitante);
                            }else{
                                visitante.setImageResource(R.drawable.equipodefecto);
                            }
                            nommbreequipovisitante.setText(x.getNombre());
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Equipos>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<TextView> pasarArraylistCabeceraLocales(){
        ArrayList<TextView> cabera = new ArrayList<>();
        cabera.add(titulares);
        cabera.add(suplentes);
        cabera.add(porteros);
        cabera.add(capitan);
        return cabera;
    }

    private ArrayList<TextView> pasarArraylistCabeceraVisitantes(){
        ArrayList<TextView> cabecera = new ArrayList<>();
        cabecera.add(titularesvisitantes);
        cabecera.add(suplentesvisitantes);
        cabecera.add(porterosvisitantes);
        cabecera.add(capitanvisitante);
        return cabecera;
    }

    private String obtenerAlicneaciones(){
        String resultado ="Equipo Local:\n";
        for(Jugadores x: jugadoreslocalesList ){
            if(x.isTitular() || x.isSuplente()){
                resultado += x.getNombre_completo()+" con el dorsal "+x.getDorsal();
                if(x.isTitular()){
                    resultado+=" juega como titular";
                }else{
                    resultado+=" juega como suplente";
                }
                if(x.isPortero()){
                    resultado+= " como portero";
                }if(x.isCapitan()){
                    resultado+=", y es el capitán del equipo local";
                }
                resultado+='\n';
            }
        }

        resultado+="Equipo Visitante:\n";
        for (Jugadores x: jugadoresvisitanteList){
            if(x.isTitular() || x.isSuplente()){
                resultado += x.getNombre_completo()+" con el dorsal "+x.getDorsal();
                if(x.isTitular()){
                    resultado+=" juega como titular";
                }else{
                    resultado+=" juega como suplente";
                }
                if(x.isPortero()){
                    resultado+= " como portero";
                }if(x.isCapitan()){
                    resultado+=", y es el capitán del equipo visitante";
                }
                resultado+='\n';
            }
        }
        return resultado;
    }

    private boolean comprobarQuinteto(){
        int contadorlocales =0;
        int contadorvisitantes =0;
        for(Jugadores x: jugadoreslocalesList ){
            if(x.isTitular() || x.isSuplente()){
                contadorlocales++;
            }
        }
        for (Jugadores x: jugadoresvisitanteList){
            if(x.isTitular() || x.isSuplente()){
               contadorvisitantes++;

            }
        }

        return (contadorlocales>=3 && contadorvisitantes>=3);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            enviarAlineaciones = (EnviarAlineaciones) context;

        }catch (ClassCastException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
