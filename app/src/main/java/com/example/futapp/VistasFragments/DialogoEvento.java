package com.example.futapp.VistasFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futapp.ClasesPojos.Jugadores;
import com.example.futapp.R;

public class DialogoEvento extends DialogFragment {

    Jugadores jugadores;
    EditText motivoamarilla, motivoroja, minutoamarilla,minutoroja, motivolesion, motivosegundamarilla, minutosegundaamrilla;
    LinearLayout lesionlinear;
    Switch lesion;
    RecyclerView.ViewHolder holderJugadores;
    TextView tarjetas;

    public DialogoEvento(Jugadores jugadores, RecyclerView.ViewHolder holderJugadores){
        this.jugadores = jugadores;
        this.holderJugadores = holderJugadores;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_evento, null);
        asignarID(view);
        asignarValoresHolder();
        builder.setView(view).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(minutosegundaamrilla.getText().length()>0 && minutoamarilla.getText().length()<=0){
                    Toast.makeText(getActivity(), "Falta por poner el minuto de la primera tarjeta amarilla", Toast.LENGTH_SHORT).show();
                }
                if(minutosegundaamrilla.getText().length()>0 && minutoamarilla.getText().length()>0){
                    tarjetas.setText("2A");
                }else if (minutoamarilla.getText().length()>0 && minutosegundaamrilla.getText().length()<=0){
                    tarjetas.setText("1A");
                }
                else if(minutoroja.getText().length()>0 && minutoamarilla.getText().length()>0){
                    tarjetas.setText("1A+1R");
                }else if(minutoroja.getText().length()>0 && minutoamarilla.getText().length()<=0){
                    tarjetas.setText("1R");
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogoEvento.this.getDialog().cancel();
            }
        });
        lesion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(lesion.isChecked()){
                    lesionlinear.setVisibility(View.VISIBLE);
                }else{
                    lesionlinear.setVisibility(View.INVISIBLE);
                }
            }
        });

        return builder.create();
    }

    void asignarID(View view){
        motivoamarilla = view.findViewById(R.id.motivo);
        motivoroja = view.findViewById(R.id.motivoroja);
        minutoamarilla = view.findViewById(R.id.minutoamarilla);
        minutoroja = view.findViewById(R.id.minutoroja);
        motivolesion = view.findViewById(R.id.mtoivolesion);
        lesion = view.findViewById(R.id.lesionswitch);
        motivosegundamarilla = view.findViewById(R.id.motivosegundamarilla);
        minutosegundaamrilla = view.findViewById(R.id.minutosegundaamarilla);
        lesionlinear = view.findViewById(R.id.lesionlinear);
    }
    void asignarValoresHolder(){
        tarjetas = holderJugadores.itemView.findViewById(R.id.dialogoevento);
    }
}
