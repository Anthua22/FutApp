package com.example.futapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futapp.ClasesPojos.Jugadores;
import com.example.futapp.Holders.HolderJugadores;
import com.example.futapp.R;
import com.example.futapp.Servicios.onDialogoFuncionClickListener;

import java.util.List;

public class AdaptadorJugadores extends RecyclerView.Adapter implements View.OnClickListener{
    Context context;
    List<Jugadores> jugadores;
    HolderJugadores holderJugadores;
    View.OnClickListener listenrfuncion,listenerevento,listenergol;
    onDialogoFuncionClickListener clickfuncion;
    public AdaptadorJugadores(List<Jugadores> jugadores, Context context){
        this.jugadores  = jugadores;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantillajugador, parent,false);
        holderJugadores = new HolderJugadores(view,context);
        view.setOnClickListener(this);
        holderJugadores.setClickfuncionDialgo(new onDialogoFuncionClickListener() {
            @Override
            public void onFuncionClick(Jugadores jugadores) {
                clickfuncion.onFuncionClick(jugadores);
            }
        });
        holderJugadores.setClickeventoDialogo(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenerevento!=null)listenerevento.onClick(v);
            }
        });

        holderJugadores.setClickgolDialogo(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenergol!=null)listenergol.onClick(v);
            }
        });
        return holderJugadores;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HolderJugadores)holder).bind(jugadores.get(position));
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    @Override
    public void onClick(View v) {
      //  if(listenrfuncion!=null) listenrfuncion.onClick(v);
    }

    public void setClickfuncionDialogo(onDialogoFuncionClickListener listener){
        if(listener!=null) clickfuncion = listener;
    }

    public void setClickeventoDialogo(View.OnClickListener listener){
        if(listener!=null)this.listenerevento = listener;
    }

    public void setClickgolDialogo(View.OnClickListener listener){
        if(listener!=null)this.listenergol = listener;
    }
}
