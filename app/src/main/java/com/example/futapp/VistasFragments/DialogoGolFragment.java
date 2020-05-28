package com.example.futapp.VistasFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.futapp.ClasesPojos.Jugadores;
import com.example.futapp.R;

import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Types;
import java.util.ArrayList;

public class DialogoGolFragment extends DialogFragment {

    TableLayout tablagol;
    Spinner numerogoles;
    ArrayAdapter<String> adapter;
    ArrayList<String> goles;
    Jugadores jugadores;
    ArrayList<EditText> golesedit;
    ArrayList<Switch> penaltis;
    EnviarGolesInterface enviarGolesInterface;

    public interface EnviarGolesInterface{
        void EnviarGoles(String te);
    }


    public DialogoGolFragment(Jugadores jugadores) {
        this.jugadores = jugadores;
        golesedit = new ArrayList<>();
        penaltis = new ArrayList<>();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialogo_gol, null);
        agregarGoles();
        asignarID(view);

        builder.setView(view).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enviarGolesInterface.EnviarGoles(recogerGoles());
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogoGolFragment.this.getDialog().cancel();
            }
        });



        numerogoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int numgoles = Integer.parseInt(goles.get(position));
                agregarfilas(numgoles);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(),"nada seleccionado",Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();

    }

    void asignarID(View view){
        tablagol = view.findViewById(R.id.tablagoles);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,goles);
        numerogoles = view.findViewById(R.id.numerogoles);
        numerogoles.setAdapter(adapter);

    }
    void agregarGoles(){
        goles = new ArrayList<>();
        for(int i =0;i<20;i++){
            goles.add(String.valueOf(i));
        }
    }


    void agregarfilas(int numerofilas){

        if(tablagol.getChildCount()>numerofilas && tablagol.getChildCount()!=1){
            int numero = tablagol.getChildCount()-1;
            while(tablagol.getChildCount()>numerofilas){
                tablagol.removeViewAt(numero);
                golesedit.remove(numero-1);
                penaltis.remove(numero-1);
                numero--;
            }
        }
        int filas =tablagol.getChildCount();
        if(filas>=2){
            for(int i =filas;i<=numerofilas;i++){
                TableRow tableRow = new TableRow(getActivity());
                TextView t1 = new TextView(getActivity());
                t1.setText(i+"");
                t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                EditText editText = new EditText(getActivity());
                editText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                golesedit.add(editText);

                Switch switc = new Switch(getActivity());
                switc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                switc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                penaltis.add(switc);


                tableRow.addView(t1);
                tableRow.addView(editText);
                tableRow.addView(switc);
                tablagol.addView(tableRow);
            }
        }else{
            for(int i =0;i<numerofilas;i++){
                TableRow tableRow = new TableRow(getActivity());
                TextView t1 = new TextView(getActivity());
                t1.setText(i+1+"");
                t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                EditText editText = new EditText(getActivity());
                editText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                editText.setInputType(Types.NUMERIC);
                golesedit.add(editText);


                Switch switc = new Switch(getActivity());
                switc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                switc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                penaltis.add(switc);

                tableRow.addView(t1);
                tableRow.addView(editText);
                tableRow.addView(switc);
                tablagol.addView(tableRow);
            }
        }


    }

    String recogerGoles(){
        String resultado ="";
        String minuto="";
        if(golesedit.size()>0 && penaltis.size()>0)
        {
            resultado ="El jugador "+jugadores.getNombre_completo()+" ha marcado los siguientes goles:\n";
            for(int i =0;i<golesedit.size(); i++){
                if(penaltis.get(i).isChecked()){
                    resultado+=(i+1)+" en el minuto: "+golesedit.get(i).getText().toString()+" de Penalti+\n";
                }else{
                    resultado+=(1+i)+" en el minuto: "+golesedit.get(i).getText().toString()+'\n';
                }
            }
        }

        return resultado;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            enviarGolesInterface = (EnviarGolesInterface) context;

        }catch (ClassCastException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
