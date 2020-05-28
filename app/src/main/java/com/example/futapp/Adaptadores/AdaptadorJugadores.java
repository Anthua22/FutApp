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
import com.example.futapp.Servicios.OnDialogoEventoClickListener;
import com.example.futapp.Servicios.OnDialogoFuncionClickListener;
import com.example.futapp.Servicios.OnDialogoGolClickListener;

import java.util.List;

public class AdaptadorJugadores extends RecyclerView.Adapter implements View.OnClickListener{
    Context context;
    List<Jugadores> jugadores;
    HolderJugadores holderJugadores;
    OnDialogoFuncionClickListener clickfuncion;
    OnDialogoEventoClickListener listenerevento;
    OnDialogoGolClickListener listenergol;
    View.OnClickListener listener;



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
        holderJugadores.setClickfuncionDialgo(new OnDialogoFuncionClickListener() {
            @Override
            public void onFuncionClick(Jugadores jugadores) {
                clickfuncion.onFuncionClick(jugadores);
            }
        });
        holderJugadores.setClickeventoDialogo(new OnDialogoEventoClickListener() {
            @Override
            public void onEventoClick(Jugadores jugadores) {
                listenerevento.onEventoClick(jugadores);
            }
        });

        holderJugadores.setClickgolDialogo(new OnDialogoGolClickListener() {
            @Override
            public void onGolClick(Jugadores jugadores) {
                listenergol.onGolClick(jugadores);
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

    public void onClickListener(View.OnClickListener listener){
        if(listener!=null) this.listener = listener;
    }

    @Override
    public void onClick(View v) {

      if(listener !=null)listener.onClick(v);
    }

    public void setClickfuncionDialogo(OnDialogoFuncionClickListener listener){
        if(listener!=null) clickfuncion = listener;
    }

    public void setClickeventoDialogo(OnDialogoEventoClickListener listener){
        if(listener!=null)this.listenerevento = listener;
    }

    public void setClickgolDialogo(OnDialogoGolClickListener listener){
        if(listener!=null)this.listenergol = listener;
    }
}
