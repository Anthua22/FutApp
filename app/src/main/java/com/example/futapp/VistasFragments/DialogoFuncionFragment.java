package com.example.futapp.VistasFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Jugadores;
import com.example.futapp.R;

import java.util.ArrayList;

public class DialogoFuncionFragment extends DialogFragment {

    TextView nombre, categoria;
    ImageView foto;
    EditText dorsal;
    Switch titular, suplente, capitan, portero;
    Jugadores jugadores;
    ArrayList<TextView> cabecera;
    ArrayList<Integer> dorsales;
    RecyclerView.ViewHolder holderJugadores;
    CardView cardView;
    TextView dorsaltext;

    public DialogoFuncionFragment(Jugadores jugadores, ArrayList<TextView> cabecera, RecyclerView.ViewHolder holderJugadores) {
        this.jugadores = jugadores;
        this.cabecera = cabecera;
        this.holderJugadores = holderJugadores;
        dorsales = new ArrayList<>();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialogo_funcion, null);
        asignarID(view);
        asignarValores();
        recuperarElementoHolder();
        builder.setView(view).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 if(dorsal.getText().toString().length()>0){
                     if(!dorsales.contains(Integer.parseInt(dorsal.getText().toString()))){
                         jugadores.setDorsal(Integer.parseInt(dorsal.getText().toString()));
                         if(titular.isChecked()){
                             int numerotitulares = Integer.parseInt(cabecera.get(0).getText().toString());
                             if(comproQuinteto(numerotitulares)){
                                 numerotitulares++;
                                 cabecera.get(0).setText(numerotitulares+"");
                                 if(jugadores.isSuplente()){
                                     int numerosuplentes = Integer.parseInt(cabecera.get(1).getText().toString());
                                     numerosuplentes--;

                                     cabecera.get(1).setText(numerosuplentes+"");
                                     jugadores.setSuplente(false);
                                 }
                                 jugadores.setTitular(true);
                                 cardView.setCardBackgroundColor(Color.rgb(115,238,156));
                             }else{
                                 Toast.makeText(getActivity(), "Ya están puesto los 5 jugadores titulares",Toast.LENGTH_LONG).show();
                             }

                         }
                         if(suplente.isChecked()){
                             int suplentes = Integer.parseInt(cabecera.get(1).getText().toString());
                             if(jugadores.isTitular()){
                                 int numerotitulares = Integer.parseInt(cabecera.get(0).getText().toString());
                                 numerotitulares--;
                                 cabecera.get(0).setText(numerotitulares+"");
                                 jugadores.setTitular(false);
                             }
                             suplentes++;
                             cabecera.get(1).setText(suplentes+"");
                             jugadores.setSuplente(true);
                             cardView.setCardBackgroundColor(Color.rgb(166,182,171));
                         }
                         if(portero.isChecked()){
                             int numeroporteros = Integer.parseInt(cabecera.get(2).getText().toString());
                             numeroporteros++;
                             cabecera.get(2).setText(numeroporteros+"");
                             jugadores.setPortero(true);
                         }
                         if(capitan.isChecked()){
                             int numerocapitan = Integer.parseInt(cabecera.get(3).getText().toString());
                             if(numerocapitan<=1){
                                 numerocapitan++;
                                 cabecera.get(3).setText(numerocapitan+"");
                                 jugadores.setCapitan(true);
                             }else{
                                 Toast.makeText(getActivity(), "Ya existe un capitán",Toast.LENGTH_LONG).show();
                             }
                         }
                         if(!portero.isChecked() && jugadores.isPortero()){
                             int numeroporteros = Integer.parseInt(cabecera.get(2).getText().toString());
                             numeroporteros--;
                             cabecera.get(2).setText(numeroporteros+"");
                             jugadores.setPortero(false);
                         }
                         if(!capitan.isChecked() && jugadores.isCapitan()){
                             int numerocapitan = Integer.parseInt(cabecera.get(3).getText().toString());
                             numerocapitan--;
                             cabecera.get(3).setText(numerocapitan+"");
                             jugadores.setCapitan(false);
                         }
                         dorsaltext.setText(jugadores.getDorsal()+"");

                     }

                     else{
                         Toast.makeText(getActivity(), "El dorsal "+dorsal.getText()+" ya está asignado",Toast.LENGTH_SHORT).show();
                     }


                 }else{
                     Toast.makeText(getActivity(),"Falta por poner el dorsal del jugador/a",Toast.LENGTH_LONG).show();
                 }



             }
             }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     DialogoFuncionFragment.this.getDialog().cancel();
                 }
             });

        titular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(suplente.isChecked()){
                    suplente.setChecked(false);

                }
            }
        });
        suplente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(titular.isChecked()){
                    titular.setChecked(false);
                }
            }
        });
        return builder.create();
    }

    void asignarID(View view){
        nombre = view.findViewById(R.id.nombre);
        foto = view.findViewById(R.id.jugador);
        categoria = view.findViewById(R.id.categoriaju);
        dorsal = view.findViewById(R.id.dorsal);
        titular = view.findViewById(R.id.titular);
        suplente = view.findViewById(R.id.suplente);
        capitan = view.findViewById(R.id.capitan);
        portero = view.findViewById(R.id.portero);
    }

    void asignarValores(){
        if(!jugadores.getFoto().equals("/Assets/defecto.jpg")){
            Glide.with(getActivity()).load(jugadores.getFoto()).into(foto);
        }else{
            foto.setImageResource(R.drawable.defecto);
        }
        nombre.setText(jugadores.getNombre_completo());
        categoria.setText(jugadores.getCategoria());
        titular.setChecked(jugadores.isTitular());
        suplente.setChecked(jugadores.isSuplente());
        portero.setChecked(jugadores.isPortero());
        capitan.setChecked(jugadores.isCapitan());
        if(jugadores.getDorsal()>0){
            dorsal.setText(String.valueOf(jugadores.getDorsal()));
        }
    }

    boolean comproQuinteto(int numerotitulares){
        return  numerotitulares<=5;
    }

    void recuperarElementoHolder(){
        cardView = holderJugadores.itemView.findViewById(R.id.cadjugador);
        dorsaltext = holderJugadores.itemView.findViewById(R.id.dialogotitular);
    }
}
