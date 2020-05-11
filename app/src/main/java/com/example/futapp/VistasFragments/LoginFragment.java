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

import com.example.futapp.ClasesPojos.Arbitro;
import com.example.futapp.R;

import com.example.futapp.Servicios.CrearServicio;
import com.example.futapp.Servicios.ServicioApiRest;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {


    Button entrar;
    TextInputLayout usuario;
    TextInputLayout contraseña;
    ServicioApiRest servicioApiRest;
    Arbitro arbitroIniciar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container,false);
        asignarID(view);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarUsuario();
                Inicio();
            }
        });
        return view;


    }

    private void Inicio(){

        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT= FM.beginTransaction();

        Fragment fragment = new InicioFragment();
        FT.add(R.id.principal, fragment);
        FT.commit();
    }


    private void comprobarUsuario(){
        arbitroIniciar = new Arbitro(usuario.getEditText().getText().toString().toUpperCase(),contraseña.getEditText().getText().toString());
        servicioApiRest = CrearServicio.crearServicioArbitros();
        Map<String, String> map = new HashMap<>();
        map.put("dni",arbitroIniciar.getDni());
        map.put("pass",Encriptar());
        Call<Arbitro> responseCall = servicioApiRest.getArbitro(map);
        responseCall.enqueue(new Callback<Arbitro>() {
            @Override
            public void onResponse(Call<Arbitro> call, Response<Arbitro> response) {
                if(response.body()!=null){
                    arbitroIniciar = response.body();
                }else{
                    Toast.makeText(getActivity(),"Error: "+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Arbitro> call, Throwable t) {
                Log.e("Error", t.toString());
                Toast.makeText(getActivity(), "Error" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }


    private String Encriptar(){

        String passencriptada = DigestUtils.sha1Hex(arbitroIniciar.getPass());
        return  passencriptada;
    }

    private void asignarID(View view){
        entrar = view.findViewById(R.id.entrarbutton);
        usuario = view.findViewById(R.id.username);
        contraseña = view.findViewById(R.id.password);
    }
}
