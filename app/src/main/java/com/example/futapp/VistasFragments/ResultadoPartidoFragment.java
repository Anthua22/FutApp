package com.example.futapp.VistasFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultadoPartidoFragment extends Fragment {

    Partidos partidos;
    TextView nombrelocal, nombrevisitante;
    ImageView local, visitante;
    EditText golvisitante, gollocal, motivosuspension;
    Switch suspendido;
    Button enviar;
    EnviarInformacion enviarInformacion;

    public interface EnviarInformacion{
        void Enviar(String resultado, String motivosuspencion);
    }



    public ResultadoPartidoFragment(Partidos partidos) {
        this.partidos = partidos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resultado_partido,container,false);
        asignarID(view);
        ponerValores();
        agregarEventoSwitch();
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviarInformacion.Enviar(obtenerResultado(), obtenerSuspension());
            }
        });
        return  view;
    }

    void asignarID(View view){
        nombrelocal = view.findViewById(R.id.localnombre);
        nombrevisitante = view.findViewById(R.id.visistantenombre);
        local = view.findViewById(R.id.localfo);
        visitante = view.findViewById(R.id.visitantefoto);
        gollocal = view.findViewById(R.id.goleslocal);
        golvisitante = view.findViewById(R.id.golesvisitantes);
        suspendido = view.findViewById(R.id.suspendido);
        enviar = view.findViewById(R.id.confirmarresultado);
        motivosuspension = view.findViewById(R.id.motivosuspension);

    }

    String obtenerResultado(){
        String resultado = gollocal.getText().toString()+'-'+golvisitante.getText().toString();
        return  resultado;
    }

    String obtenerSuspension(){
        return motivosuspension.getText().toString();
    }




    void ponerValores(){
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<List<Equipos>> response = servicioApiRestUtilidades.servicioApiRest.getEquipos();
        response.enqueue(new Callback<List<Equipos>>() {
            @Override
            public void onResponse(Call<List<Equipos>> call, Response<List<Equipos>> response) {
                if(response.isSuccessful()){
                    for(Equipos x : response.body()){
                        if(partidos.getEquipoLocal() == x.getIdEquipo()){
                            nombrelocal.setText(x.getNombre());
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(local);
                            }else{
                                local.setImageResource(R.drawable.equipodefecto);
                            }

                        }else if(partidos.getEquipoVisitante() == x.getIdEquipo()){
                            nombrevisitante.setText(x.getNombre());
                            if(!x.getFoto().equals("/Assets/equipodefecto.png")){
                                Glide.with(getActivity()).load(x.getFoto())
                                        .into(visitante);
                            }else{
                                visitante.setImageResource(R.drawable.equipodefecto);
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
    }

    void agregarEventoSwitch(){
        suspendido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    motivosuspension.setVisibility(View.VISIBLE);
                }else{
                    motivosuspension.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            enviarInformacion = (EnviarInformacion) context;

        }catch (ClassCastException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
