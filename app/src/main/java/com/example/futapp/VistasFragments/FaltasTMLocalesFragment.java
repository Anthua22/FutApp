package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.futapp.R;

import java.util.ArrayList;

public class FaltasTMLocalesFragment extends Fragment {

    Switch primeraparte, segundaparte;

    Spinner faltasprimeraparte, faltassegudnaparte;
    ArrayAdapter<String> adapter;
    ArrayList<String> numerofaltas;

    public FaltasTMLocalesFragment() {
        numerofaltas = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.faltastmplantilla, container,false);
        colocarFaltas();
        asignarID(view);


        return view;
    }

    void asignarID(View view){
        primeraparte = view.findViewById(R.id.tiempoprimero);
        segundaparte = view.findViewById(R.id.tiemposegundo);
        faltasprimeraparte = view.findViewById(R.id.faltasprimeras);
        faltassegudnaparte = view.findViewById(R.id.faltassegundas);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,numerofaltas);
        faltasprimeraparte.setAdapter(adapter);
        faltassegudnaparte.setAdapter(adapter);

    }

    void colocarFaltas(){
        for(int i =0 ;i<21;i++){
            numerofaltas.add(String.valueOf(i));
        }
    }
}
