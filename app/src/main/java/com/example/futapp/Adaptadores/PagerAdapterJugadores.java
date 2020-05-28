package com.example.futapp.Adaptadores;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.VistasFragments.JugadoresLocalesVisitantesFragment;

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
                JugadoresLocalesVisitantesFragment jugadoresLocalesVisitantesFragment = new JugadoresLocalesVisitantesFragment(partidos);
                return jugadoresLocalesVisitantesFragment;
            case 1 :
                JugadoresLocalesVisitantesFragment jugadoresLocalesFragment1 = new JugadoresLocalesVisitantesFragment(partidos);
                return  jugadoresLocalesFragment1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numeroPestañsa;
    }


}
