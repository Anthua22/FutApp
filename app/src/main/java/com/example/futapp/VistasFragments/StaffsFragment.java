package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.futapp.Adaptadores.PagerAdapterStaffs;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.google.android.material.tabs.TabLayout;

public class StaffsFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    Partidos partidos;

    public StaffsFragment(Partidos partidos) {
        this.partidos = partidos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.staffs,container,false);
        asignarId(view);
        tabLayout.addTab(tabLayout.newTab().setText("Locales"));
        tabLayout.addTab(tabLayout.newTab().setText("Visitantes"));
        PagerAdapterStaffs pagerAdapterStaffs = new PagerAdapterStaffs(getFragmentManager(),tabLayout.getTabCount(),partidos);
        viewPager.setAdapter(pagerAdapterStaffs);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        cambioTabs();
        return view;
    }

    void asignarId(View view){
        viewPager = view.findViewById(R.id.viewpager_staffs);
        tabLayout = view.findViewById(R.id.tablayout_staffs);
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
