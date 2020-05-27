package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.futapp.Adaptadores.PagerAdapterFaltasyTM;
import com.example.futapp.Adaptadores.PagerAdapterJugadores;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.google.android.material.tabs.TabLayout;

public class FaltasTiempoMuertoFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    Partidos partidos;

    public FaltasTiempoMuertoFragment(Partidos partidos) {
        this.partidos = partidos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.faltastm,container,false);
        asignarId(view);
        tabLayout.addTab(tabLayout.newTab().setText("Locales"));
        tabLayout.addTab(tabLayout.newTab().setText("Visitantes"));
        PagerAdapterFaltasyTM pagerAdapterFaltasyTM = new PagerAdapterFaltasyTM(getFragmentManager(),tabLayout.getTabCount(),partidos);
        viewPager.setAdapter(pagerAdapterFaltasyTM);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        cambioTabs();
        return view;
    }

    void asignarId(View view){
        viewPager = view.findViewById(R.id.viewpager_faltastm);
        tabLayout = view.findViewById(R.id.tablayout_faltastm);
    }

    void cambioTabs(){
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
