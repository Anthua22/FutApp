package com.example.futapp.VistasFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futapp.Adaptadores.AdaptadorStaff;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.ClasesPojos.Staffs;
import com.example.futapp.R;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;
import com.example.futapp.Servicios.onAsisteStaffClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffsLocalesFragment extends Fragment {
    RecyclerView recyclerView;
    List<Staffs> staffsList;
    Partidos partidos;
    AdaptadorStaff adaptadorStaff;
    int posicion;

    public StaffsLocalesFragment(Partidos partidos) {
        this.partidos = partidos;
        staffsList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stafflocales,container,false);
        asignarID(view);
        adaptadorStaff = new AdaptadorStaff(getActivity(), staffsList);
        recyclerView.setAdapter(adaptadorStaff);
        obtenerStaffs();
        adaptadorStaff.onClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion = recyclerView.getChildAdapterPosition(v);
            }
        });
        adaptadorStaff.setClickSwitchlistener(new onAsisteStaffClickListener() {
            @Override
            public void onAsisteClick(Staffs staffs) {

                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(posicion);
                Switch switc = holder.itemView.findViewById(R.id.staffswitc);
                CardView cardView = holder.itemView.findViewById(R.id.cardviewstaff);
                if(switc.isChecked()){
                    cardView.setCardBackgroundColor(Color.rgb(115,238,156));
                }else{
                    cardView.setCardBackgroundColor(Color.rgb(255,255,255));
                }
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
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

    }
}
