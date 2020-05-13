package com.example.futapp.VistasFragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    TextView arbitros;
    DrawerLayout drawerLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inicio, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar_inicio);
        drawerLayout = view.findViewById(R.id.drawer_layout_inicio);
        getActivity().setActionBar(toolbar);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu_button);
        actionBar.setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = view.findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Toast.makeText(getActivity(), menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return  true;
            }
        });

        return  view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return true;
        }
    }

    //  @Override
   /* public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(;
                return true;
            case R.id.action

        }
        return super.onOptionsItemSelected(item);
    }*/
}
