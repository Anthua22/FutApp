package com.example.futapp.VistasFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaltasTMLocalesVisitantesFragment extends Fragment {

    Switch primeraparte, segundaparte, primerapartevisitante, segundopartevisitante;

    Spinner faltasprimeraparte, faltassegudnaparte, faltasprimeravisitante, faltassegundasvisitante;
    ArrayAdapter<String> adapter, adaptervisitante;
    ArrayList<String> numerofaltas, numerofaltasvisitnates;
    TextView nombrelocal, nombrevisitante;
    ImageView local, visitante;
    Button enviar;
    Partidos partidos;
    EnviarFaltasYTM enviarFaltasYTM;
    String faltaslocalpri, faltasvisitantespri, faltaslocalessegu,faltasvisitantessegu;

    public interface EnviarFaltasYTM{
        void EnvioFaltasyTiempos(String st);
    }

    public FaltasTMLocalesVisitantesFragment(Partidos partidos) {
        numerofaltas = new ArrayList<>();
        numerofaltasvisitnates = new ArrayList<>();
        this.partidos =partidos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.faltastmplantilla, container,false);
        colocarFaltas();
        asignarID(view);
        asignarAdaptadores();
        asignarFotoyNombreEquipos();
        elegirFaltas();
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarFaltasYTM.EnvioFaltasyTiempos(recogerFaltasyTiempoMuertos());
            }
        });
        return view;
    }

    void asignarID(View view){
        primeraparte = view.findViewById(R.id.tiempoprimero);
        segundaparte = view.findViewById(R.id.tiemposegundo);
        faltasprimeraparte = view.findViewById(R.id.faltasprimeras);
        faltassegudnaparte = view.findViewById(R.id.faltassegundas);
        faltasprimeravisitante = view.findViewById(R.id.faltasprimerasvisitantes);
        faltassegundasvisitante =view.findViewById(R.id.faltassegundasvisitantes);
        nombrelocal = view.findViewById(R.id.equipolocalnombrefaltas);
        local = view.findViewById(R.id.equipolocalfotofaltas);
        visitante =view.findViewById(R.id.equipovisitantefotofaltas);
        nombrevisitante = view.findViewById(R.id.equipovisitantenombrefaltas);
        primerapartevisitante = view.findViewById(R.id.tiempoprimerovisitante);
        segundopartevisitante = view.findViewById(R.id.tiemposegundovisitante);
        enviar = view.findViewById(R.id.enviarfaltastm);
    }



    void asignarAdaptadores()
    {
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,numerofaltas);
        adaptervisitante = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numerofaltasvisitnates);
        faltasprimeraparte.setAdapter(adapter);
        faltassegudnaparte.setAdapter(adapter);
        faltasprimeravisitante.setAdapter(adaptervisitante);
        faltassegundasvisitante.setAdapter(adaptervisitante);

    }

    void asignarFotoyNombreEquipos(){
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<List<Equipos>> response = servicioApiRestUtilidades.servicioApiRest.getEquipos();

        response.enqueue(new Callback<List<Equipos>>() {
            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {
                if(response.isSuccessful()){
                    for(Equipos x : response.body()){
                        if(x.getIdEquipo() == partidos.getEquipoLocal()){
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(local);
                            }else{
                                local.setImageResource(R.drawable.equipodefecto);
                            }

                            nombrelocal.setText(x.getNombre());
                        }
                        else if (x.getIdEquipo() == partidos.getEquipoVisitante()){
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(visitante);
                            }else{
                                visitante.setImageResource(R.drawable.equipodefecto);
                            }
                            nombrevisitante.setText(x.getNombre());
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Equipos>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void colocarFaltas(){
        for(int i =0 ;i<21;i++){
            numerofaltas.add(String.valueOf(i));
            numerofaltasvisitnates.add(String.valueOf(i));
        }
    }

    void elegirFaltas(){
        faltasprimeraparte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faltaslocalpri = numerofaltas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        faltasprimeravisitante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faltasvisitantespri = numerofaltasvisitnates.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        faltassegudnaparte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faltaslocalessegu = numerofaltas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        faltassegundasvisitante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faltasvisitantessegu = numerofaltasvisitnates.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    String recogerFaltasyTiempoMuertos(){
        String resultado="";
        if(faltaslocalpri!=null){
            resultado += "Faltas EquipoLocal Primera Parte:"+faltaslocalpri;
            if(primeraparte.isChecked()){
                resultado+=" mas tiempo muerto";
            }

        } if(faltasvisitantespri!=null){

            resultado+="Faltas EquipoVisitante Primera Parte:"+faltasvisitantespri;
            if(primerapartevisitante.isChecked()){
                resultado+=" mas tiempo muerto";
            }
        } if (faltaslocalessegu!=null){
            resultado+="Faltas EquipoLocal Segunda Parte:"+faltaslocalessegu;
            if(segundaparte.isChecked()){
                resultado+=" mas tiempo muerto";
            }
        } if(faltasvisitantessegu!=null){
            resultado+="Faltas EquipoVisitante Segunda Parte::"+faltasvisitantessegu;
            if(segundopartevisitante.isChecked()){
                resultado+=" mas tiempo muerto";
            }
        }


        return resultado;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            enviarFaltasYTM = (EnviarFaltasYTM) context;

        }catch (ClassCastException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
