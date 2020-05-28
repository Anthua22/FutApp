package com.example.futapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.futapp.VistasFragments.DialogoEventoFragment;
import com.example.futapp.VistasFragments.DialogoGolFragment;
import com.example.futapp.VistasFragments.FaltasTMLocalesVisitantesFragment;
import com.example.futapp.VistasFragments.IncidenciasFragment;
import com.example.futapp.VistasFragments.InformacionPartidoFragment;
import com.example.futapp.VistasFragments.JugadoresLocalesVisitantesFragment;
import com.example.futapp.VistasFragments.LoginFragment;
import com.example.futapp.VistasFragments.ResultadoPartidoFragment;
import com.example.futapp.VistasFragments.StaffsLocalesVisitantesFragment;

public class MainActivity extends AppCompatActivity implements ResultadoPartidoFragment.EnviarInformacion, JugadoresLocalesVisitantesFragment.EnviarAlineaciones, DialogoEventoFragment.EnviarSanciones, DialogoGolFragment.EnviarGolesInterface, StaffsLocalesVisitantesFragment.EnviarAsistenciaStaff, FaltasTMLocalesVisitantesFragment.EnviarFaltasYTM, IncidenciasFragment.EnviarIncidencias {

    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT= FM.beginTransaction();

        Fragment fragment = new LoginFragment();
        FT.add(R.id.principal, fragment);
        FT.commit();
    }


    @Override
    public void Enviar(String resultado, String d) {
        text = resultado;
        InformacionPartidoFragment informacionPartidoFragment = (InformacionPartidoFragment) getSupportFragmentManager().findFragmentByTag("resultadofragment");

        DialogFragment dialogFragment = InformacionPartidoFragment.newinsTance(text,d);
        dialogFragment.show(getSupportFragmentManager(),"resultado");



    }


    @Override
    public void Enviar(String alineacion) {
        DialogFragment dialogFragment = InformacionPartidoFragment.newinsTanceAlineacion(alineacion);
        dialogFragment.show(getSupportFragmentManager(),"resultado");
    }

    @Override
    public void EnvioSanciones(String string) {
        DialogFragment dialogFragment = InformacionPartidoFragment.newIntanceEvento(string);
        dialogFragment.show(getSupportFragmentManager(),"resultado");
    }

    @Override
    public void EnviarGoles(String te) {
        DialogFragment dialogFragment = InformacionPartidoFragment.newIntanceGol(te);
        dialogFragment.show(getSupportFragmentManager(),"resultado");
    }

    @Override
    public void EnviarStaff(String str) {
        DialogFragment dialogFragment = InformacionPartidoFragment.newIntanceGol(str);
        dialogFragment.show(getSupportFragmentManager(),"resultado");
    }

    @Override
    public void EnvioFaltasyTiempos(String st) {
        DialogFragment dialogFragment = InformacionPartidoFragment.newIntanceGol(st);
        dialogFragment.show(getSupportFragmentManager(),"resultado");
    }

    @Override
    public void EnvioIncidencias(String inc) {
        DialogFragment dialogFragment = InformacionPartidoFragment.newIntanceGol(inc);
        dialogFragment.show(getSupportFragmentManager(),"resultado");
    }
}
