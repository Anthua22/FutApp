package com.example.futapp.VistasFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.futapp.R;

public class IncidenciasFragment extends Fragment {

    Switch locales, visitantes;
    EditText incidenciaslcales, incidenciasvisitantes;
    Button enviar;
    EnviarIncidencias enviarIncidencias;
    public interface EnviarIncidencias{
        void EnvioIncidencias(String inc);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incidencias, container,false);
        asignarId(view);
        locales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(locales.isChecked()){
                    incidenciaslcales.setVisibility(View.VISIBLE);
                }else{
                    incidenciaslcales.setVisibility(View.INVISIBLE);
                }
            }
        });

        visitantes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(visitantes.isChecked()){
                    incidenciasvisitantes.setVisibility(View.VISIBLE);
                }else{
                    incidenciasvisitantes.setVisibility(View.INVISIBLE);
                }
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarIncidencias.EnvioIncidencias(recuperarIncidenciad());
            }
        });
        return view;
    }

    void asignarId(View view){
        locales = view.findViewById(R.id.incidenciaslocalesswitch);
        visitantes = view.findViewById(R.id.incidenciasvisitantesswitch);
        incidenciaslcales = view.findViewById(R.id.incidenciaslocales);
        incidenciasvisitantes = view.findViewById(R.id.incidenciasvisitantes);
        enviar = view.findViewById(R.id.enviarincidendias);
    }

    String recuperarIncidenciad(){
        String resultado ="";
        if(locales.isChecked() && incidenciaslcales.getText().toString().length()>0){
            resultado +="EquipoLocal: "+incidenciaslcales.getText().toString();
        }
        if(visitantes.isChecked() && incidenciasvisitantes.getText().toString().length()>0){
            resultado+="EquipoVisitante: "+incidenciasvisitantes.getText().toString();
        }
        return resultado;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            enviarIncidencias = (EnviarIncidencias) context;

        }catch (ClassCastException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
