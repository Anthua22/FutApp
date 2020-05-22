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

import com.bumptech.glide.Glide;
import com.example.futapp.Adaptadores.PagerAdapterPartido;
import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartidoFragment extends Fragment {

    DrawerLayout drawerLayout;
    ActionBar actionBar;
    TabLayout tabLayout;
    Partidos partidoactual;
    Toolbar toolbar;
    String local, visitante, resultado;
    public PartidoFragment(Partidos partidos){
        partidoactual =partidos;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partido, container, false);
        toolbar = view.findViewById(R.id.toolbar_partido);
        ponerNombrerToolbar();
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
        tabLayout.addTab(tabLayout.newTab().setText("Datos Básicos"));
        tabLayout.addTab(tabLayout.newTab().setText("Resultado"));
        tabLayout.addTab(tabLayout.newTab().setText("Jugadores"));
        tabLayout.addTab(tabLayout.newTab().setText("Staff"));
        tabLayout.addTab(tabLayout.newTab().setText("Incidencias"));

        ViewPager viewPager = view.findViewById(R.id.viewpager_partido);
        PagerAdapterPartido pagerAdapterPartido = new PagerAdapterPartido(getFragmentManager(), tabLayout.getTabCount(),partidoactual);
        viewPager.setAdapter(pagerAdapterPartido);

        return view;
    }
    void ponerNombrerToolbar()
    {
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<List<Equipos>> response = servicioApiRestUtilidades.servicioApiRest.getEquipos();
        response.enqueue(new Callback<List<Equipos>>() {
            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {
                if(response.isSuccessful()){
                    for(Equipos x : response.body()){
                        if(partidoactual.getEquipoLocal() == x.getIdEquipo()){
                            local = x.getNombre();


                        }else if(partidoactual.getEquipoVisitante() == x.getIdEquipo()){
                            visitante=x.getNombre();

                        }
                    }
                    resultado = local+" vs "+visitante;
                    toolbar.setTitle(resultado);
                }
            }

            @Override
            public void onFailure(Call<List<Equipos>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
