package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.google.android.material.tabs.TabLayout;

public class JugadoresFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    Partidos partidos;

    public JugadoresFragment(Partidos partidos) {
        this.partidos = partidos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jugadores,container,false);
        asignarId(view);
        tabLayout.addTab(tabLayout.newTab().setText("Locales"));
        tabLayout.addTab(tabLayout.newTab().setText("Visitantes"));
        return view;
    }

    void asignarId(View view){
        viewPager = view.findViewById(R.id.viewpager_jugadores);
        tabLayout = view.findViewById(R.id.tablayout_jugadores);
    }
}
