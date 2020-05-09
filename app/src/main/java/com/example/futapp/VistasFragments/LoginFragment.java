package com.example.futapp.VistasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.futapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {


    Button entrar;
    TextInputEditText usuario;
    TextInputEditText contraseña;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container,false);
        asignarID(view);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;


    }

    private void asignarID(View view){
        entrar = view.findViewById(R.id.entrarbutton);
        usuario = view.findViewById(R.id.username);
        contraseña = view.findViewById(R.id.password);
    }
}
