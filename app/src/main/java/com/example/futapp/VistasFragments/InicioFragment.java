package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.R;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    TextView arbitros;

    public InicioFragment(List<Arbitros> arbitros) {
        for(Arbitros x : arbitros){
            this.arbitros.setText(x.getNombre_completo()+'\n');
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inicio, container, false);
        arbitros = view.findViewById(R.id.arbirtros);
        return  view;
    }
}
