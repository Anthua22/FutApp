package com.example.futapp.Adaptadores;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.VistasFragments.DatosBasicosFragment;
import com.example.futapp.VistasFragments.FaltasTiempoMuertoFragment;
import com.example.futapp.VistasFragments.IncidenciasFragment;
import com.example.futapp.VistasFragments.JugadoresFragment;
import com.example.futapp.VistasFragments.ResultadoPartidoFragment;
import com.example.futapp.VistasFragments.StaffsFragment;

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
                ResultadoPartidoFragment resultadoPartidoFragment = new ResultadoPartidoFragment(partidos);
                return  resultadoPartidoFragment;
            case 2:
                JugadoresFragment jugadoresFragment = new JugadoresFragment(partidos);
                return jugadoresFragment;
            case 3:
                StaffsFragment staffsFragment = new StaffsFragment(partidos);
                return staffsFragment;
            case 4:
                FaltasTiempoMuertoFragment faltasTiempoMuertoFragment = new FaltasTiempoMuertoFragment(partidos);
                return  faltasTiempoMuertoFragment;
            case 5:
                IncidenciasFragment incidenciasFragment = new IncidenciasFragment();
                return incidenciasFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numeroPestaña;
    }
}
