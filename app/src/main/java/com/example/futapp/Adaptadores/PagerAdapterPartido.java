package com.example.futapp.Adaptadores;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.VistasFragments.DatosBasicosFragment;

public class PagerAdapterPartido extends FragmentStatePagerAdapter {
    int numeroPestaña;
    Partidos partidos;
    public PagerAdapterPartido(FragmentManager fm, int numeroPestaña, Partidos partidos) {
        super(fm);
        this.partidos = partidos;
        this.numeroPestaña = numeroPestaña;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DatosBasicosFragment datosBasicosFragment = new DatosBasicosFragment(partidos);
                return datosBasicosFragment;
            case 1:
                DatosBasicosFragment datosBasicosFragment1 = new DatosBasicosFragment(partidos);
                return  datosBasicosFragment1;
            case 2:
                DatosBasicosFragment datosBasicosFragment2 = new DatosBasicosFragment(partidos);
                return datosBasicosFragment2;
            case 3:
                DatosBasicosFragment datosBasicosFragment3 = new DatosBasicosFragment(partidos);
                return datosBasicosFragment3;
            case 4:
                DatosBasicosFragment datosBasicosFragment4 = new DatosBasicosFragment(partidos);
                return  datosBasicosFragment4;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numeroPestaña;
    }
}
