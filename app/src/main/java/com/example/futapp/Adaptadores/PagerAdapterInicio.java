package com.example.futapp.Adaptadores;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.futapp.VistasFragments.PartidosNuevosTabFragment;

public class PagerAdapterInicio extends FragmentStatePagerAdapter {

    int numeroPesatañas;

    public PagerAdapterInicio(FragmentManager fm, int numeroPesatañas) {
        super(fm);
        this.numeroPesatañas = numeroPesatañas;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                PartidosNuevosTabFragment partidosNuevosTabFragment = new PartidosNuevosTabFragment();
                return  partidosNuevosTabFragment;

            case 1:
                PartidosNuevosTabFragment partidosNuevosTabFragment1 = new PartidosNuevosTabFragment();
                return  partidosNuevosTabFragment1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numeroPesatañas;
    }
}
