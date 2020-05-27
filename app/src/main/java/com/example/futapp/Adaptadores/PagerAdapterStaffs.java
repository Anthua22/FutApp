package com.example.futapp.Adaptadores;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.VistasFragments.JugadoresLocalesFragment;
import com.example.futapp.VistasFragments.StaffsLocalesFragment;

public class PagerAdapterStaffs extends FragmentStatePagerAdapter {
    int numeropestañs;
    Partidos partidos;

    public PagerAdapterStaffs(FragmentManager fm,int pestñas,Partidos partidos) {
        super(fm);
        this.numeropestañs = pestñas;
        this.partidos = partidos;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                StaffsLocalesFragment staffsLocalesFragment = new StaffsLocalesFragment(partidos);
                return  staffsLocalesFragment;
            case 1 :
                StaffsLocalesFragment staffsLocalesFragment2 = new StaffsLocalesFragment(partidos);
                return  staffsLocalesFragment2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numeropestañs;
    }
}
