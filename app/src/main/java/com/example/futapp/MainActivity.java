package com.example.futapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.futapp.Servicios.OnSelectedItemListener;
import com.example.futapp.VistasFragments.InformacionPartidoFragment;
import com.example.futapp.VistasFragments.LoginFragment;
import com.example.futapp.VistasFragments.ResultadoPartidoFragment;

public class MainActivity extends AppCompatActivity implements ResultadoPartidoFragment.EnviarInformacion {

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
    public void Enviar(String resultado) {
        text = resultado;
        InformacionPartidoFragment informacionPartidoFragment = (InformacionPartidoFragment) getSupportFragmentManager().findFragmentByTag("resultadofragment");
        if(informacionPartidoFragment!=null){
            informacionPartidoFragment.pasandoDatos(resultado);
        }else{
          /*  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.principal,InformacionPartidoFragment.newinsTance(text));
            ft.commit();
            ft.addToBackStack(null);*/

            DialogFragment dialogFragment = InformacionPartidoFragment.newinsTance(text);
            dialogFragment.show(getSupportFragmentManager(),"resultado");
        }


    }





}
