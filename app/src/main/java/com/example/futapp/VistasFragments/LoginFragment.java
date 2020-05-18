package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.R;

import com.example.futapp.Servicios.ServicioApiRestUtilidades;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {


    Button entrar;
    TextInputLayout usuario;
    TextInputLayout contraseña;

    Arbitros arbitrosIniciar;
    List<Arbitros> arbitros;
    ServicioApiRestUtilidades servicioApiRestUtilidades;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container,false);
        asignarID(view);
        arbitros = new ArrayList<>();
        arbitrosIniciar = new Arbitros();
        servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarUsuario();
            }
        });
        return view;


    }

    private void Inicio(){

        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT= FM.beginTransaction();

        Fragment fragment = new InicioFragment(arbitrosIniciar);
        FT.replace(R.id.principal, fragment);
        FT.commit();
    }

    private void comprobarUsuario(){
        arbitrosIniciar = new Arbitros(usuario.getEditText().getText().toString().toUpperCase(),Encriptar());


        Call<List<Arbitros>> respose = servicioApiRestUtilidades.servicioApiRest.getArbitros();
        respose.enqueue(new Callback<List<Arbitros>>() {
            @Override
            public void onResponse(Call<List<Arbitros>> call, Response<List<Arbitros>> response) {
                if(response.isSuccessful()){
                    List<Arbitros> arbi = response.body();

                    for(Arbitros x : arbi){
                        if(arbitrosIniciar.getDni().equals(x.getDni()) && arbitrosIniciar.getPass().equals(x.getPass())){
                            arbitrosIniciar = x;
                        }
                    }

                    if(arbitrosIniciar.getId()!=0){
                        Inicio();
                    }
                }else{
                    Log.e("Error", response.message());
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<List<Arbitros>> call, Throwable t) {
                Log.e("Error", t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG);
            }
        });




    }

    private String Encriptar(){

        String passencriptada = DigestUtils.sha1Hex(contraseña.getEditText().getText().toString());
        return  passencriptada;
    }

    private void asignarID(View view){
        entrar = view.findViewById(R.id.entrarbutton);
        usuario = view.findViewById(R.id.username);
        contraseña = view.findViewById(R.id.password);
    }
}
