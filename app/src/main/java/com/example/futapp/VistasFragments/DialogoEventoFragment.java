package com.example.futapp.VistasFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

public class DialogoEventoFragment extends DialogFragment {

    Jugadores jugadores;
    EditText motivoamarilla, motivoroja, minutoamarilla,minutoroja, motivolesion, motivosegundamarilla, minutosegundaamrilla;
    LinearLayout lesionlinear;
    Switch lesion;
    RecyclerView.ViewHolder holderJugadores;
    TextView tarjetas;
    EnviarSanciones enviarSanciones;
    public interface EnviarSanciones{
        void EnvioSanciones(String string);
    }

    public DialogoEventoFragment(Jugadores jugadores, RecyclerView.ViewHolder holderJugadores){
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
                enviarSanciones.EnvioSanciones(recogerInformacion());

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogoEventoFragment.this.getDialog().cancel();
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

    String recogerInformacion(){
        String resultado="";
        if(motivoamarilla.getText().toString().length()>0 && minutoamarilla.getText().toString().length()>0){
            resultado = "Amarilla al jugador "+jugadores.getNombre_completo()+" con dorsal "+jugadores.getDorsal()+" por "+motivoamarilla.getText().toString()+ " en el minuto "+minutoamarilla.getText().toString()+'\n';
        }else if(motivosegundamarilla.getText().toString().length()>0 && minutosegundaamrilla.getText().toString().length()>0){
            resultado +="Segunda amarilla al jugador "+jugadores.getNombre_completo()+" con dorsal " +jugadores.getDorsal()+" por "+motivoamarilla.getText().toString()+ " en el minuto "+minutoamarilla.getText().toString()+'\n';
        }else if(motivoroja.getText().toString().length()>0 && minutoroja.getText().toString().length()>0){
            resultado+="Roja al jugador "+jugadores.getNombre_completo()+" con dorsal"+jugadores.getDorsal()+" por"+motivoamarilla.getText().toString()+ " en el minuto "+minutoamarilla.getText().toString()+'\n';
        }
        else if(lesion.isChecked() && motivolesion.getText().toString().length()>0){
            resultado+="Jugador: "+jugadores.getNombre_completo()+" lesionado: "+motivolesion.getText().toString()+'\n';
        }
        return  resultado;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            enviarSanciones = (EnviarSanciones) context;

        }catch (ClassCastException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
