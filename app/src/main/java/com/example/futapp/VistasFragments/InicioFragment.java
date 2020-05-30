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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.futapp.Adaptadores.PagerAdapterInicio;
import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


public class InicioFragment extends Fragment {

    DrawerLayout drawerLayout;
    ActionBar actionBar;
    TabLayout tabLayout;
    Arbitros arbitrosactual;
    TextView categoria, nombre;
    ImageView fotoarbitro;
    ViewPager viewPager;



    public InicioFragment(Arbitros arbitrosactual) {
        this.arbitrosactual = arbitrosactual;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inicio, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar_inicio);
        drawerLayout = view.findViewById(R.id.drawer_layout_inicio);
        getActivity().setActionBar(toolbar);

        NavigationView navigationView = view.findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                FragmentManager FM ;
                FragmentTransaction FT;
                switch (menuItem.getItemId()){
                    case R.id.cerrarSesion:
                        FM = getFragmentManager();
                        FT= FM.beginTransaction();

                        Fragment fragment = new LoginFragment();
                        FT.replace(R.id.principal, fragment);
                        FT.commit();
                        break;
                    case R.id.navigationinicioConfigurarCuenta:
                        DialogFragment dialogFragment = new DialogoConfigurarContraseña(arbitrosactual);

                        dialogFragment.show(getFragmentManager(),"configurar");
                        break;
                }


                return  true;
            }
        });
        View viewheader = navigationView.getHeaderView(0);
        colocarDatosHeader(viewheader);
        tabLayout =view.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Nuevos"));
        tabLayout.addTab(tabLayout.newTab().setText("Pasados"));
        viewPager = view.findViewById(R.id.viewpager);
        PagerAdapterInicio pagerAdapterInicio = new PagerAdapterInicio(getFragmentManager(), tabLayout.getTabCount(),arbitrosactual);
        viewPager.setAdapter(pagerAdapterInicio);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        cambioTabs();


        return  view;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

        }

        return super.onOptionsItemSelected(item);
    }

     private void colocarDatosHeader(View viewheader){

        nombre = viewheader.findViewById(R.id.nombreArbitro);
        categoria  = viewheader.findViewById(R.id.categoria);
        fotoarbitro = viewheader.findViewById(R.id.fotoArbitro);

        nombre.setText(arbitrosactual.getNombre_completo());
        categoria.setText(arbitrosactual.getCategoria());

        if(!arbitrosactual.getFoto().equals("/Assets/defecto.jpg")){
            Glide.with(getActivity()).load(arbitrosactual.getFoto())
                    .into(fotoarbitro);
        }else{
            fotoarbitro.setImageResource(R.drawable.defecto);
        }

    }
}
