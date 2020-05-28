package com.example.futapp.Holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.futapp.ClasesPojos.Staffs;
import com.example.futapp.R;
import com.example.futapp.Servicios.OnAsisteStaffClickListener;

public class HolderStaffs extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView foto;
    TextView nombre, funcion;
    Switch asiste;
    Context context;
    OnAsisteStaffClickListener onAsisteStaffClickListener;
    Staffs staffs;
    public HolderStaffs(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        foto = itemView.findViewById(R.id.fotostaff);
        nombre = itemView.findViewById(R.id.nombrestaff);
        funcion = itemView.findViewById(R.id.funcionstaff);
        asiste = itemView.findViewById(R.id.staffswitc);
        asiste.setOnClickListener(this);
    }

    public void bind(Staffs staffs){
        if(!staffs.getFoto().equals("/Assets/defecto.jpg")){
            Glide.with(context).load(staffs.getFoto()).into(foto);
        }else{
            foto.setImageResource(R.drawable.defecto);
        }

        nombre.setText(staffs.getNombre_completo());
        funcion.setText(staffs.getCargo());
        this.staffs = staffs;
    }

    public void setOnAsisteStaffClickListener(OnAsisteStaffClickListener onAsisteStaffClickListener){
        if(onAsisteStaffClickListener!=null) this.onAsisteStaffClickListener = onAsisteStaffClickListener;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.staffswitc:
                onAsisteStaffClickListener.onAsisteClick(staffs);
        }
    }
}
