package com.example.futapp.VistasFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Jugadores;
import com.example.futapp.R;

public class DialogoFuncion extends DialogFragment {

    TextView nombre, categoria;
    ImageView foto;
    EditText dorsal;
    Switch titular, suplente, capitan, portero;
    Jugadores jugadores;

    public DialogoFuncion(Jugadores jugadores) {
        this.jugadores = jugadores;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialogo_funcion, null);
        asignarID(view);
        asignarValores();
        builder.setView(view).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 if(titular.isActivated()){

                 }
             }
             }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     DialogoFuncion.this.getDialog().cancel();
                 }
             });
        return builder.create();
    }

    void asignarID(View view){
        nombre = view.findViewById(R.id.nombre);
        foto = view.findViewById(R.id.jugador);
        categoria = view.findViewById(R.id.categoriaju);
        dorsal = view.findViewById(R.id.dorsal);
        titular = view.findViewById(R.id.titular);
        suplente = view.findViewById(R.id.suplente);
        capitan = view.findViewById(R.id.capitan);
        portero = view.findViewById(R.id.portero);
    }

    void asignarValores(){
        if(!jugadores.getFoto().equals("/Assets/defecto.jpg")){
            Glide.with(getActivity()).load(jugadores.getFoto()).into(foto);
        }else{
            foto.setImageResource(R.drawable.defecto);
        }
        nombre.setText(jugadores.getNombre_completo());
        categoria.setText(jugadores.getCategoria());

    }
}
