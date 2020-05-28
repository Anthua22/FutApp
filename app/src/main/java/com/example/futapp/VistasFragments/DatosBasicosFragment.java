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
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.ClasesPojos.Staffs;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosBasicosFragment extends Fragment {

    Partidos partidos;
    TextView equipolocal, equipovisitante, fecha, hora, jornada, direccion;
    Spinner spinner;
    ImageView equilocalfoto, equipovisitantefoto;
    ArrayList<String> delegados;
    TableRow asistentes;
    TextView arbitros_principales;
    TextView arbitros_asitentes;
    ArrayAdapter<String> adapter;
    Button enviar;
    EnviarDatosdelPartido enviarDatosdelPartido;
    String delegado;
    public interface EnviarDatosdelPartido{
        void EnviarDatosBasicos(String st);
    }

    public DatosBasicosFragment(Partidos partidos)
    {
        this.partidos = partidos;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datos_partido,container,false);
        asignarID(view);
        asignarValores();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                delegado = delegados.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatosdelPartido.EnviarDatosBasicos(recogerDatos());
            }
        });
        return view;
    }

    void asignarID(View view){
        equilocalfoto = view.findViewById(R.id.equipolocalfoto);
        equipovisitante = view.findViewById(R.id.nombrevisitante);
        equipovisitantefoto = view.findViewById(R.id.equipoVisitantefoto);
        equipolocal = view.findViewById(R.id.nombrelocal);
        fecha = view.findViewById(R.id.fecha_partido);
        hora = view.findViewById(R.id.hora_encuentro);
        jornada = view.findViewById(R.id.jornada);
        spinner = view.findViewById(R.id.delegados);
        direccion = view.findViewById(R.id.direccion);
        arbitros_asitentes = view.findViewById(R.id.arbiasistentes);
        arbitros_principales = view.findViewById(R.id.arbiprinciapl);
        asistentes = view.findViewById(R.id.asistentes);
        enviar = view.findViewById(R.id.enviardatosbasicos);
        delegados = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_gallery_item, delegados);
        spinner.setAdapter(adapter);

    }

    String recogerDatos(){
        String resultado = equipolocal.getText().toString()+" vs "+equipovisitante.getText().toString()+'\n';
        resultado+="Dirección del encuentro: "+partidos.getDireccion_encuentro()+"\n";
        resultado+="Jornada: "+partidos.getJornada()+'\n';
        if(delegado!=null){
            resultado+="Delegado: "+delegado+'\n';
        }
        resultado+="Fecha Encuentro: "+fecha.getText().toString()+'\n';
        resultado+="Hora Encuentro: "+hora.getText().toString()+'\n';
        resultado+="Arbitros: "+arbitros_principales.getText().toString()+'\n';
        if(arbitros_asitentes.getText().toString().length()>0){
            resultado+="Asistentes: "+arbitros_asitentes.getText().toString()+'\n';
        }
        return  resultado;
    }

    void asignarValores(){

        if(partidos.getCronometrador()!=0) {
            asistentes.setVisibility(View.VISIBLE);
        }
        String[] fechahora = partidos.getFecha_encuentro().split(" ");
        String fecha = fechahora[0];
        String hora = fechahora[1];
        this.fecha.setText(fecha);
        this.hora.setText(hora);
        direccion.setText(partidos.getDireccion_encuentro());
        jornada.setText(partidos.getJornada()+"");
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<List<Equipos>> response = servicioApiRestUtilidades.servicioApiRest.getEquipos();

        response.enqueue(new Callback<List<Equipos>>() {
            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {
                if(response.isSuccessful()){
                    for(Equipos x : response.body()){
                        if(partidos.getEquipoLocal() == x.getIdEquipo()){
                            equipolocal.setText(x.getNombre());
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(equilocalfoto);
                            }else{
                                equilocalfoto.setImageResource(R.drawable.equipodefecto);
                            }

                        }else if(partidos.getEquipoVisitante() == x.getIdEquipo()){
                            equipovisitante.setText(x.getNombre());
                            if(!x.getFoto().equals("/Assets/equipodefecto.png") && x.getFoto()!=null){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(equipovisitantefoto);
                            }else{
                                equipovisitantefoto.setImageResource(R.drawable.equipodefecto);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Equipos>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Staffs>> responsestaffs = servicioApiRestUtilidades.servicioApiRest.getStaffs();
        responsestaffs.enqueue(new Callback<List<Staffs>>() {
            @Override
            public void onResponse(Call<List<Staffs>> call, Response<List<Staffs>> response) {
                if(response.isSuccessful()){
                    for(Staffs x : response.body()){
                        if(x.getEquipo() == partidos.getEquipoLocal()){
                            delegados.add(x.getNombre_completo());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Staffs>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Arbitros>> responseArbitros = servicioApiRestUtilidades.servicioApiRest.getArbitros();

        responseArbitros.enqueue(new Callback<List<Arbitros>>() {
            @Override
            public void onResponse(Call<List<Arbitros>> call, Response<List<Arbitros>> response) {
                if(response.isSuccessful()){
                    for(Arbitros x : response.body()){
                        if(x.getId() == partidos.getArbitroprincipal() || x.getId() == partidos.getArbitrosecundario()){
                            if(arbitros_principales.getText().toString().length() >0){
                                arbitros_principales.setText(arbitros_principales.getText().toString()+", "+x.getNombre_completo());
                            }else{
                                arbitros_principales.setText(x.getNombre_completo());
                            }
                        }else if (x.getId() == partidos.getCronometrador() || x.getId() == partidos.getTercer_arbitro()){
                            if(arbitros_asitentes.getText().toString().length()>0){
                                arbitros_asitentes.setText(arbitros_asitentes.getText().toString()+", "+x.getNombre_completo());
                            }else{
                                arbitros_asitentes.setText(x.getNombre_completo());
                            }

                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Arbitros>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            enviarDatosdelPartido = (EnviarDatosdelPartido) context;

        }catch (ClassCastException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
