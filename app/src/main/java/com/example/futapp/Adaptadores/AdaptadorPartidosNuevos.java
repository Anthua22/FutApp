package com.example.futapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.Holders.HolderPartidos;
import com.example.futapp.R;

import java.util.ArrayList;

public class AdaptadorPartidosNuevos extends RecyclerView.Adapter {
    ArrayList<Partidos> partidosArrayList;
    ArrayList<Arbitros> arbitros;
    Context context;
    HolderPartidos holderPartidos;

    public AdaptadorPartidosNuevos(ArrayList<Partidos> partidos, Context context){
        partidosArrayList = partidos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partidos, parent,false);
        holderPartidos = new HolderPartidos(v);
        return holderPartidos;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HolderPartidos)holder).bindPrimeraSegunda(partidosArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return partidosArrayList.size();
    }


}
