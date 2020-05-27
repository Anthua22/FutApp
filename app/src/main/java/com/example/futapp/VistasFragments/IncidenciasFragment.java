package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.futapp.R;

public class IncidenciasFragment extends Fragment {

    Switch locales, visitantes;
    EditText incidenciaslcales, incidenciasvisitantes;

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
        return view;
    }

    void asignarId(View view){
        locales = view.findViewById(R.id.incidenciaslocalesswitch);
        visitantes = view.findViewById(R.id.incidenciasvisitantesswitch);
        incidenciaslcales = view.findViewById(R.id.incidenciaslocales);
        incidenciasvisitantes = view.findViewById(R.id.incidenciasvisitantes);
    }
}
