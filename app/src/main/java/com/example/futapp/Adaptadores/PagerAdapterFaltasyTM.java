package com.example.futapp.Adaptadores;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.VistasFragments.FaltasTMLocalesFragment;
import com.example.futapp.VistasFragments.FaltasTiempoMuertoFragment;
import com.example.futapp.VistasFragments.JugadoresLocalesFragment;

public class PagerAdapterFaltasyTM extends FragmentStatePagerAdapter {
    int numeroPestañsa;
    Partidos partidos;
    public PagerAdapterFaltasyTM(FragmentManager fm, int pestñas,Partidos partidos) {
        super(fm);
        this.numeroPestañsa = pestñas;
        this.partidos = partidos;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FaltasTMLocalesFragment faltasTMLocalesFragment = new FaltasTMLocalesFragment();
                return  faltasTMLocalesFragment;
            case 1 :
                FaltasTMLocalesFragment faltasTMLocalesFragment1 = new FaltasTMLocalesFragment();
                return  faltasTMLocalesFragment1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numeroPestañsa;
    }
}
