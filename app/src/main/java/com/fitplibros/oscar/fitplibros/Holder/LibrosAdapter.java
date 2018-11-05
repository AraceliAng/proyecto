package com.fitplibros.oscar.fitplibros.Holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitplibros.oscar.fitplibros.Model.Libro;
import com.fitplibros.oscar.fitplibros.Model.Multa;
import com.fitplibros.oscar.fitplibros.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class LibrosAdapter extends RecyclerView.Adapter<LibrosAdapter.LibrosViewHolder> {

    Context mCtx;
    ArrayList<String> libro_titulo;
    ArrayList<String> libro_autor;
    ArrayList<String> libro_edicion;
    ArrayList<String> libro_editorial;
    ArrayList<String> libro_tema;
    ArrayList<String> libro_ubicacion;
    ArrayList<String> libro_imagen;


    class LibrosViewHolder extends RecyclerView.ViewHolder {
        TextView libro_titulo, libro_autor, libro_editorial, libro_edicion, libro_tema, libro_ubicacion;
        ImageView libro_imagen;

        public LibrosViewHolder(@NonNull View itemView) {
            super(itemView);

            libro_titulo = itemView.findViewById(R.id.libro_titulo);
            libro_autor = itemView.findViewById(R.id.libro_autor);
            libro_editorial = itemView.findViewById(R.id.libro_editorial);
            libro_edicion = itemView.findViewById(R.id.libro_edicion);
            libro_tema = itemView.findViewById(R.id.libro_tema);
            libro_ubicacion = itemView.findViewById(R.id.libro_ubicacion);
            libro_imagen = itemView.findViewById(R.id.libro_imagen);

        }
    }


    public LibrosAdapter(Context mCtx, ArrayList<String> libro_titulo, ArrayList<String> libro_autor, ArrayList<String> libro_edicion,
                         ArrayList<String> libro_editorial, ArrayList<String> libro_ubicacion, ArrayList<String> libro_tema,
                         ArrayList<String> libro_imagen) {
        this.mCtx = mCtx;
        this.libro_titulo = libro_titulo;
        this.libro_autor = libro_autor;
        this.libro_edicion = libro_edicion;
        this.libro_editorial = libro_editorial;
        this.libro_ubicacion = libro_ubicacion;
        this.libro_tema = libro_tema;
        this.libro_imagen = libro_imagen;

    }

    @NonNull
    @Override
    public LibrosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.libro_list, viewGroup, false);
        return new LibrosAdapter.LibrosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibrosViewHolder holder, int i) {

        holder.libro_titulo.setText(libro_titulo.get(i));
        holder.libro_autor.setText(libro_autor.get(i));
        holder.libro_editorial.setText(libro_editorial.get(i));
        holder.libro_edicion.setText(libro_edicion.get(i));
        holder.libro_tema.setText(libro_tema.get(i));
        holder.libro_ubicacion.setText(libro_ubicacion.get(i));

        Picasso.get().load(libro_imagen.get(i)).into(holder.libro_imagen);

    }

    @Override
    public int getItemCount() {
        return libro_titulo.size();
    }

}
