package com.example.futapp.Holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Jugadores;
import com.example.futapp.R;
import com.example.futapp.Servicios.onDialogoEventoClickListener;
import com.example.futapp.Servicios.onDialogoFuncionClickListener;
import com.example.futapp.Servicios.onDialogoGolClickListener;

public class HolderJugadores extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView foto;
    TextView dialgofuncion, nombre,categoria, dialogo_evento, dialogo_gol;
    Context context;
    onDialogoFuncionClickListener listenerfuncion;
    onDialogoEventoClickListener listenerevento;
    onDialogoGolClickListener listenergol;
    Jugadores jugadores;

    public HolderJugadores(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        foto = itemView.findViewById(R.id.fotojugador);
        dialgofuncion = itemView.findViewById(R.id.dialogotitular);
        nombre = itemView.findViewById(R.id.nombrejugador);
        categoria = itemView.findViewById(R.id.categoriajugador);
        dialogo_evento = itemView.findViewById(R.id.dialogoevento);
        dialogo_gol = itemView.findViewById(R.id.dialogogol);

        dialogo_evento.setOnClickListener(this);
        dialogo_gol.setOnClickListener(this);dialgofuncion.setOnClickListener(this);
    }

    public void bind(Jugadores jugador){
        if(!jugador.getFoto().equals("/Assets/defecto.jpg")){
            Glide.with(context).load(jugador.getFoto()).into(foto);
        }
        else{
            foto.setImageResource(R.drawable.defecto);
        }
        nombre.setText(jugador.getNombre_completo());
        categoria.setText(jugador.getCategoria());
        jugadores = jugador;
    }

    public void setClickfuncionDialgo(onDialogoFuncionClickListener listener){
        if(listener!=null) this.listenerfuncion = listener;
    }

    public void setClickeventoDialogo(onDialogoEventoClickListener listener){
        if(listener!=null)this.listenerevento = listener;
    }

    public void setClickgolDialogo(onDialogoGolClickListener listener){
        if(listener!=null)this.listenergol = listener;
    }
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.dialogotitular:
            listenerfuncion.onFuncionClick(jugadores);
            break;
           case R.id.dialogogol:
               listenergol.onGolClick(jugadores);
               break;
           case R.id.dialogoevento:
               listenerevento.onEventoClick(jugadores);
               break;
       }
    }
}
