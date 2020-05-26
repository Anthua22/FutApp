package com.example.futapp.Adaptadores;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.VistasFragments.JugadoresLocalesFragment;

public class PagerAdapterJugadores extends FragmentStatePagerAdapter {
    int numeroPestañsa;
    Partidos partidos;
    public PagerAdapterJugadores(FragmentManager fm, int pestñas,Partidos partidos) {
        super(fm);
        this.numeroPestañsa = pestñas;
        this.partidos = partidos;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                JugadoresLocalesFragment jugadoresLocalesFragment = new JugadoresLocalesFragment(partidos);
                return  jugadoresLocalesFragment;
            case 1 :
                JugadoresLocalesFragment jugadoresLocalesFragment1 = new JugadoresLocalesFragment(partidos);
                return  jugadoresLocalesFragment1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numeroPestañsa;
    }
}
