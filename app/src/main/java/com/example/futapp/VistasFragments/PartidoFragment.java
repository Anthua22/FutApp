package com.example.futapp.VistasFragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.futapp.Adaptadores.PagerAdapterPartido;
import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class PartidoFragment extends Fragment {

    DrawerLayout drawerLayout;
    ActionBar actionBar;
    TabLayout tabLayout;
    Partidos partidoactual;
    public PartidoFragment(Partidos partidos){
        partidoactual =partidos;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partido, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar_partido);
        drawerLayout = view.findViewById(R.id.drawer_layout_partido);
        getActivity().setActionBar(toolbar);
        actionBar = getActivity().getActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu_button);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = view.findViewById(R.id.navView_partido);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(getActivity(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        tabLayout = view.findViewById(R.id.tablayout_partido);
        tabLayout.addTab(tabLayout.newTab().setText("Datos del Partido"));
        tabLayout.addTab(tabLayout.newTab().setText("Resultado"));
        tabLayout.addTab(tabLayout.newTab().setText("Jugadores"));
        tabLayout.addTab(tabLayout.newTab().setText("Staff"));
        tabLayout.addTab(tabLayout.newTab().setText("Incidencias"));

        ViewPager viewPager = view.findViewById(R.id.viewpager_partido);
        PagerAdapterPartido pagerAdapterPartido = new PagerAdapterPartido(getFragmentManager(), tabLayout.getTabCount(),partidoactual);
        viewPager.setAdapter(pagerAdapterPartido);

        return view;
    }
}
