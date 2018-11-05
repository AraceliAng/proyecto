package com.fitplibros.oscar.fitplibros.Holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitplibros.oscar.fitplibros.Model.Multa;
import com.fitplibros.oscar.fitplibros.R;

import java.util.List;

public class MultasAdapter extends RecyclerView.Adapter<MultasAdapter.CustomViewHolder> {

    private Context mCtx;
    private List<Multa> multaList;

    public MultasAdapter(Context mCtx, List<Multa> multaList) {
        this.mCtx = mCtx;
        this.multaList = multaList;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.multa_list, viewGroup, false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int i) {
        Multa multa = multaList.get(i);
        holder.txt_titulo_multa.setText(multa.id_libros);
        holder.txt_fecha_multa.setText(multa.fecha);
        holder.txt_monto_multa.setText(multa.monto);
    }


    @Override
    public int getItemCount() {
        return multaList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txt_titulo_multa, txt_fecha_multa, txt_monto_multa;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_titulo_multa = itemView.findViewById(R.id.multa_titulo);
            txt_fecha_multa = itemView.findViewById(R.id.multa_fecha);
            txt_monto_multa = itemView.findViewById(R.id.multa_monto);
        }
    }
}
