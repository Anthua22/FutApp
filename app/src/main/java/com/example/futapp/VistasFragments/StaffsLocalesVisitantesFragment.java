package com.example.futapp.VistasFragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.futapp.Adaptadores.AdaptadorStaff;
import com.example.futapp.ClasesPojos.Equipos;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.ClasesPojos.Staffs;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;
import com.example.futapp.Servicios.OnAsisteStaffClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffsLocalesVisitantesFragment extends Fragment {
    RecyclerView recyclerView, recyclerViewvisitante;
    List<Staffs> staffsList, staffsListvisitantes;
    Partidos partidos;
    AdaptadorStaff adaptadorStaff, adaptadorStaffvisitante;
    TextView nombrelocal, nombrevisitante;
    ImageView local, visitante;
    FloatingActionButton enviar;
    EnviarAsistenciaStaff enviarAsistenciaStaff;


    public StaffsLocalesVisitantesFragment(Partidos partidos) {
        this.partidos = partidos;
        staffsList = new ArrayList<>();
        staffsListvisitantes = new ArrayList<>();
    }

    public interface EnviarAsistenciaStaff{
        void EnviarStaff(String str);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stafflocalesvisitantes,container,false);
        asignarID(view);
        adaptadorStaff = new AdaptadorStaff(getActivity(), staffsList);
        adaptadorStaffvisitante = new AdaptadorStaff(getActivity(),staffsListvisitantes);
        recyclerView.setAdapter(adaptadorStaff);
        recyclerViewvisitante.setAdapter(adaptadorStaffvisitante);
        obtenerStaffs();
        asignarValores();


        adaptadorStaff.setClickSwitchlistener(new OnAsisteStaffClickListener() {
            @Override
            public void onAsisteClick(int j,Staffs staffs) {

                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(j);
                Switch switc = holder.itemView.findViewById(R.id.staffswitc);
                CardView cardView = holder.itemView.findViewById(R.id.cardviewstaff);
                if(switc.isChecked()){
                    cardView.setCardBackgroundColor(Color.rgb(115,238,156));
                    staffs.setAsiste(true);
                }else{
                    cardView.setCardBackgroundColor(Color.rgb(255,255,255));
                    staffs.setAsiste(false);
                }
            }
        });
        adaptadorStaffvisitante.setClickSwitchlistener(new OnAsisteStaffClickListener() {
            @Override
            public void onAsisteClick(int p,Staffs staffs) {
                RecyclerView.ViewHolder holder = recyclerViewvisitante.findViewHolderForAdapterPosition(p);
                Switch switc = holder.itemView.findViewById(R.id.staffswitc);
                CardView cardView = holder.itemView.findViewById(R.id.cardviewstaff);
                if(switc.isChecked()){
                    cardView.setCardBackgroundColor(Color.rgb(115,238,156));
                    staffs.setAsiste(true);
                }else{
                    cardView.setCardBackgroundColor(Color.rgb(255,255,255));
                    staffs.setAsiste(false);
                }
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerViewvisitante.setHasFixedSize(true);
        recyclerViewvisitante.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarAsistenciaStaff.EnviarStaff(recuperarAsitencia());
            }
        });

        return  view;
    }

    void obtenerStaffs(){
        ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
        Call<List<Staffs>> response = servicioApiRestUtilidades.servicioApiRest.getStaffs();
        response.enqueue(new Callback<List<Staffs>>() {
            @Override
            public void onResponse(Call<List<Staffs>> call, Response<List<Staffs>> response) {
                if(response.isSuccessful()){
                    for(Staffs x : response.body()){
                        if(x.getEquipo() == partidos.getEquipoLocal()){
                            staffsList.add(x);
                            adaptadorStaff.notifyDataSetChanged();
                        }else if(x.getEquipo()== partidos.getEquipoVisitante()){
                            staffsListvisitantes.add(x);
                            adaptadorStaffvisitante.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Staffs>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void asignarID(View view){
        recyclerView = view.findViewById(R.id.recyclerstafflocales);
        recyclerViewvisitante = view.findViewById(R.id.recyclerstaffvisitantes);
        nombrelocal = view.findViewById(R.id.equipolocalnombrestaff);
        nombrevisitante = view.findViewById(R.id.equipovisitantenombrestaff);
        local = view.findViewById(R.id.equipolocalfotostaff);
        visitante = view.findViewById(R.id.equipovisitantefotostaff);
        enviar = view.findViewById(R.id.enviarasisatenciastaff);
    }

    void asignarValores(){
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


    String recuperarAsitencia(){
        String resultado="";
        if(staffsList.size()>0){
            resultado ="";
            for(Staffs x : staffsList){
                if(x.isAsiste()){
                    resultado+=x.getNombre_completo()+','+x.getCargo()+'\n';
                }
            }
        }
        if(staffsListvisitantes.size()>0){
            resultado+=":";
            for(Staffs x : staffsListvisitantes){
                if(x.isAsiste()){
                    resultado+=x.getNombre_completo()+','+x.getCargo()+'\n';
                }
            }
        }
        return resultado;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            enviarAsistenciaStaff = (EnviarAsistenciaStaff) context;

        }catch (ClassCastException e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
