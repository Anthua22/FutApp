package com.example.futapp.VistasFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogoConfigurarContraseña extends DialogFragment {
    Arbitros arbitros;
    Switch cambiocontra;
    TableLayout tabla;
    ImageView foto;
    TextView nombre, categoria;
    EditText numero, correo, contraseñaactual, contraseñanueva, contraseñaconfirmar;

    public DialogoConfigurarContraseña(Arbitros arbitros){
        this.arbitros = arbitros;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.configurarcuenta, null);
        asignarID(view);
        asignarValores();
        cambiocontra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cambiocontra.isChecked()){
                    tabla.setVisibility(View.VISIBLE);
                }else{
                    tabla.setVisibility(View.INVISIBLE);
                }
            }
        });
        builder.setView(view).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Arbitros aux = arbitros;
                if(!numero.getText().toString().equals(arbitros.getTelefono())){
                    arbitros.setTelefono(numero.getText().toString());
                }else if(!correo.getText().toString().equals(arbitros.getEmail())){
                    arbitros.setEmail(correo.getText().toString());
                }else if (cambiocontra.isChecked()){
                    if(Encriptar(contraseñaactual.getText().toString()).equals(arbitros.getPass())){
                        if(contraseñanueva.getText() == contraseñaconfirmar.getText()){
                            if(comprobarContraseña(contraseñanueva.getText().toString())){
                                arbitros.setPass(Encriptar(contraseñanueva.getText().toString()));

                            }else{
                                Toast.makeText(getActivity(),"La contraseña tiene que tener al menos una minúscula, mayúscula, un número y un caracter especial",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(),"La contraseñas nuevas introducidas no coinciden",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getActivity(),"La contraseña introducida no coincide con la actual",Toast.LENGTH_SHORT).show();
                    }
                }

                update();
                Toast.makeText(getActivity(),"Árbitro: "+arbitros.getNombre_completo()+" actualizado",Toast.LENGTH_SHORT).show();



            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogoConfigurarContraseña.this.getDialog().cancel();
            }
        });


        return  builder.create();
    }

    private void asignarValores() {
        numero.setText(arbitros.getTelefono());
        correo.setText(arbitros.getEmail());
        if(!arbitros.getFoto().equals("/Assets/defecto.jpg")){
            Glide.with(getActivity()).load(arbitros.getFoto())
                    .into(foto);
        }else{
            foto.setImageResource(R.drawable.defecto);
        }
        nombre.setText(arbitros.getNombre_completo());
        categoria.setText(arbitros.getCategoria());

    }

    private boolean comprobarContraseña(String contraseña){
        String regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        return Pattern.matches(regexp,contraseña);

    }

    private String Encriptar(String contraseña){

        String passencriptada = DigestUtils.sha1Hex(contraseña);
        return  passencriptada;
    }
    private void asignarID(View view) {
        numero = view.findViewById(R.id.telefonoarbitro);
        correo = view.findViewById(R.id.correoarbirto);
        contraseñaactual = view.findViewById(R.id.contraseñaactual);
        contraseñanueva = view.findViewById(R.id.contraseñanueva);
        contraseñaconfirmar = view.findViewById(R.id.contraseñaconfirmar);
        cambiocontra = view.findViewById(R.id.cambiarcontraseña);
        tabla = view.findViewById(R.id.tablacontraseña);
        foto = view.findViewById(R.id.arbifoto);
        nombre = view.findViewById(R.id.nombrearbi);
        categoria = view.findViewById(R.id.categoriaabi);
    }

    private  void update(){
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<Arbitros> response =servicioApiRestUtilidades.servicioApiRest.updateArbirtro(arbitros.getId(), arbitros);
        response.enqueue(new Callback<Arbitros>() {
            @Override
            public void onResponse(Call<Arbitros> call, Response<Arbitros> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(),"code: "+response.code(),Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<Arbitros> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
