package com.example.futapp.VistasFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.futapp.R;

public class InformacionPartidoFragment extends DialogFragment {
    TextView textoresultado;
    public static InformacionPartidoFragment newinsTance(String text, String suspension){
        InformacionPartidoFragment informacionPartidoFragment = new InformacionPartidoFragment();
        Bundle b = new Bundle();
        b.putString("resultado",text);
        b.putString("suspension",suspension);
        informacionPartidoFragment.setArguments(b);
        return informacionPartidoFragment;
    }

    public static InformacionPartidoFragment newinsTanceAlineacion(String text){
        InformacionPartidoFragment informacionPartidoFragment = new InformacionPartidoFragment();
        Bundle b = new Bundle();
        b.putString("alineacion",text);
        informacionPartidoFragment.setArguments(b);
        return informacionPartidoFragment;
    }

    public static InformacionPartidoFragment newIntanceEvento(String text){
        InformacionPartidoFragment informacionPartidoFragment = new InformacionPartidoFragment();
        Bundle b = new Bundle();
        b.putString("evento",text);
        informacionPartidoFragment.setArguments(b);
        return informacionPartidoFragment;
    }

    public static InformacionPartidoFragment newIntanceGol(String text){
        InformacionPartidoFragment informacionPartidoFragment = new InformacionPartidoFragment();
        Bundle b = new Bundle();
        b.putString("gol",text);
        informacionPartidoFragment.setArguments(b);
        return informacionPartidoFragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /*if(savedInstanceState!=null){
            String result =  savedInstanceState.getString("resultado");
            String suspension = savedInstanceState.getString("suspension");
            String alineacion = savedInstanceState.getString("alineacion");
            if(suspension!=null || result!=null){
                if(suspension.length()>0){
                    textoresultado.setText(suspension+","+result);
                }
                textoresultado.setText(result);
            }
            if(alineacion!=null){
                textoresultado.setText(alineacion);
            }

        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.enviarinformacion, null);
        textoresultado = view.findViewById(R.id.resultadoooo);
        String resulta= this.getArguments().getString("resultado");
        String suspen = this.getArguments().getString("suspension");
        String alineacion = this.getArguments().getString("alineacion");
        String goles = this.getArguments().getString("gol");
        String motivos = this.getArguments().getString("evento");
        if(suspen!=null || resulta!=null){
            if(suspen.length()>0){
                textoresultado.setText(suspen+","+resulta);
            }
            textoresultado.setText(resulta);
        }
        if(alineacion!=null){
            textoresultado.setText(alineacion);
        }

        if(motivos!=null){
            textoresultado.setText(motivos);
        }
        if(goles!=null){
            textoresultado.setText(goles);
        }


        builder.setView(view);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InformacionPartidoFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }




}
