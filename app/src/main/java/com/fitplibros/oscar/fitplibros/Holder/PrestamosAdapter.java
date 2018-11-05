package com.fitplibros.oscar.fitplibros.Holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fitplibros.oscar.fitplibros.Model.Prestamo;
import com.fitplibros.oscar.fitplibros.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PrestamosAdapter extends RecyclerView.Adapter<PrestamosAdapter.PrestamosViewHolder> {
    private Context mCtx;
    private List<Prestamo> prestamoList;


    public PrestamosAdapter(Context mCtx, List<Prestamo> prestamoList) {
        this.mCtx = mCtx;
        this.prestamoList = prestamoList;
    }

    @NonNull
    @Override
    public PrestamosAdapter.PrestamosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.prestamo_list, viewGroup, false);
        return new PrestamosAdapter.PrestamosViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PrestamosAdapter.PrestamosViewHolder holder, int i) {
        Prestamo prestamo = prestamoList.get(i);
        holder.prestamo_titulo.setText(prestamo.titulo);
        holder.prestamo_autor.setText(prestamo.autor);
        holder.prestamo_fecha.setText(prestamo.fecha_devolucion);

        Picasso.get().load(prestamo.url_libro).into(holder.prestamo_imagen);
    }

    @Override
    public int getItemCount() {
        return prestamoList.size();
    }


    public class PrestamosViewHolder extends RecyclerView.ViewHolder {
        TextView prestamo_titulo, prestamo_autor, prestamo_fecha;
        ImageView prestamo_imagen;

        public PrestamosViewHolder(@NonNull View itemView) {
            super(itemView);

            prestamo_titulo = itemView.findViewById(R.id.prestamo_titulo);
            prestamo_fecha = itemView.findViewById(R.id.prestamo_fecha);
            prestamo_autor = itemView.findViewById(R.id.prestamo_autor);
            prestamo_imagen = itemView.findViewById(R.id.prestamo_imagen);

        }
    }
}
