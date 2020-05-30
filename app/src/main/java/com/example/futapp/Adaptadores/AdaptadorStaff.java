package com.example.futapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futapp.ClasesPojos.Staffs;
import com.example.futapp.Holders.HolderStaffs;
import com.example.futapp.R;
import com.example.futapp.Servicios.OnAsisteStaffClickListener;

import java.util.List;

public class AdaptadorStaff extends RecyclerView.Adapter implements View.OnClickListener {
    Context context;
    List<Staffs> staffs;
    View.OnClickListener listener;
    HolderStaffs holderStaffs;
    OnAsisteStaffClickListener onAsisteStaffClickListener;

    public AdaptadorStaff(Context context, List<Staffs> staffs) {
        this.context = context;
        this.staffs = staffs;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null)listener.onClick(v);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staffplantilla, parent,false);
        holderStaffs = new HolderStaffs(view, context);
        view.setOnClickListener(this);
        holderStaffs.setOnAsisteStaffClickListener(new OnAsisteStaffClickListener() {
            @Override
            public void onAsisteClick(int j,Staffs staffs) {
                onAsisteStaffClickListener.onAsisteClick(j,staffs);
            }
        });
        return holderStaffs;
    }

    public void setClickSwitchlistener(OnAsisteStaffClickListener listener){
        if(listener != null) onAsisteStaffClickListener =listener;
    }

    public void onClickListener(View.OnClickListener listener){
        if(listener!=null)this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HolderStaffs)holder).bind(staffs.get(position));
    }

    @Override
    public int getItemCount() {
        return staffs.size();
    }
}
