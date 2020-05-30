package com.example.futapp.VistasFragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.futapp.Adaptadores.PagerAdapterPartido;
import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.MainActivity;
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
    TabLayout tabLayout;
    Partidos partidoactual;
    Toolbar toolbar;
    ViewPager viewPager;
    TextView categoria, nombre;
    ImageView fotoarbitro;
    Arbitros arbitros;
    String local, visitante, resultado;

    public PartidoFragment(Partidos partidos, Arbitros arbitros){
        partidoactual =partidos;
        this.arbitros = arbitros;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partido, container, false);
        toolbar = view.findViewById(R.id.toolbar_partido);
        ponerNombrerToolbar();
        drawerLayout = view.findViewById(R.id.drawer_layout_partido);

        NavigationView navigationView = view.findViewById(R.id.navView_partido);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                FragmentManager FM  = getFragmentManager();
                FragmentTransaction FT = FM.beginTransaction();
                try{
                    switch (menuItem.getItemId()){
                        case R.id.cerrarSesion_partido:
                            Fragment fragment = new LoginFragment();
                            FT.replace(R.id.principal, fragment);
                            FT.commit();
                            break;
                        case R.id.configurarcuenta:
                            DialogFragment dialogFragment = new DialogoConfigurarContraseña(arbitros);
                            dialogFragment.show(getFragmentManager(),"configurarpar");

                            break;

                        case R.id.generarpdf:
                            int respuesta = MainActivity.generaArchivo(getActivity(), partidoactual);
                            switch (respuesta){
                                case 0:
                                    Toast.makeText(getActivity(), "Algo ha fallado",Toast.LENGTH_SHORT).show();
                                    break;
                                case -1:
                                    Toast.makeText(getActivity(), "Faltán datos necesarios para generar el acta",Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    Toast.makeText(getActivity(), "Acta generada correctamente",Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            break;
                        case R.id.cerrarpartido:
                            if(MainActivity.cerrarPartido(partidoactual,getActivity())){
                                Toast.makeText(getActivity(),"Partido Finalizado",Toast.LENGTH_SHORT).show();
                                InicioFragment inicioFragment = new InicioFragment(arbitros);
                                FT.replace(R.id.principal, inicioFragment);
                                FT.commit();
                            }else{
                                Toast.makeText(getActivity(),"No se ha generado él acta para poder cerrar el partido",Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());

                    }
                }catch (Exception ex){
                    Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                }



                return  true;
            }
        });

        View viewheader = navigationView.getHeaderView(0);
        colocarDatosHeader(viewheader);
        tabLayout = view.findViewById(R.id.tablayout_partido);
        tabLayout.addTab(tabLayout.newTab().setText("Datos Básicos"));
        tabLayout.addTab(tabLayout.newTab().setText("Resultado"));
        tabLayout.addTab(tabLayout.newTab().setText("Jugadores"));
        tabLayout.addTab(tabLayout.newTab().setText("Staff"));
        tabLayout.addTab(tabLayout.newTab().setText("Faltas/Tiempo"));
        tabLayout.addTab(tabLayout.newTab().setText("Incidencias"));

        viewPager = view.findViewById(R.id.viewpager_partido);
        PagerAdapterPartido pagerAdapterPartido = new PagerAdapterPartido(getFragmentManager(), tabLayout.getTabCount(),partidoactual);
        viewPager.setAdapter(pagerAdapterPartido);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        cambioTabs();

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

    private void colocarDatosHeader(View viewheader){

        nombre = viewheader.findViewById(R.id.nombreArbitro);
        categoria  = viewheader.findViewById(R.id.categoria);
        fotoarbitro = viewheader.findViewById(R.id.fotoArbitro);

        nombre.setText(arbitros.getNombre_completo());
        categoria.setText(arbitros.getCategoria());

        if(!arbitros.getFoto().equals("/Assets/defecto.jpg")){
            Glide.with(getActivity()).load(arbitros.getFoto())
                    .into(fotoarbitro);
        }else{
            fotoarbitro.setImageResource(R.drawable.defecto);
        }

    }
}
