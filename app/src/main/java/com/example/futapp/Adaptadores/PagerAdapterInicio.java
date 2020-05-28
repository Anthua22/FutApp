package com.example.futapp.Adaptadores;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.VistasFragments.PartidosAntiguosFragment;
import com.example.futapp.VistasFragments.PartidosNuevosTabFragment;

public class PagerAdapterInicio extends FragmentStatePagerAdapter {

    int numeroPesatañas;
    Arbitros actual;

    public PagerAdapterInicio(FragmentManager fm, int numeroPesatañas, Arbitros actual) {
        super(fm);
        this.numeroPesatañas = numeroPesatañas;
        this.actual = actual;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                PartidosNuevosTabFragment partidosNuevosTabFragment = new PartidosNuevosTabFragment(actual);
                return  partidosNuevosTabFragment;

            case 1:
                PartidosAntiguosFragment partidosAntiguosFragment = new PartidosAntiguosFragment(actual);
                return  partidosAntiguosFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numeroPesatañas;
    }
}
