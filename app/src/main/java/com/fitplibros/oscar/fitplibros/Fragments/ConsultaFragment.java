package com.fitplibros.oscar.fitplibros.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.fitplibros.oscar.fitplibros.Holder.LibrosAdapter;
import com.fitplibros.oscar.fitplibros.Model.Libro;
import com.fitplibros.oscar.fitplibros.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ConsultaFragment extends Fragment {

    RecyclerView recyclerView;
    LibrosAdapter adapter;
    EditText search_edit;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> libro_titulo;
    ArrayList<String> libro_autor;
    ArrayList<String> libro_edicion;
    ArrayList<String> libro_editorial;
    ArrayList<String> libro_tema;
    ArrayList<String> libro_ubicacion;
    ArrayList<String> libro_imagen;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View consulta_vista=inflater.inflate(R.layout.fragment_consulta, container, false);

        search_edit = consulta_vista.findViewById(R.id.search_edit);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = consulta_vista.findViewById(R.id.idRecycler_consulta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        libro_titulo = new ArrayList<>();
        libro_autor = new ArrayList<>();
        libro_edicion = new ArrayList<>();
        libro_editorial = new ArrayList<>();
        libro_ubicacion = new ArrayList<>();
        libro_tema = new ArrayList<>();
        libro_imagen = new ArrayList<>();

        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    setAdapter(s.toString());
                } else{
                    libro_autor.clear();
                    libro_edicion.clear();
                    libro_editorial.clear();
                    libro_imagen.clear();
                    libro_tema.clear();
                    libro_titulo.clear();
                    libro_ubicacion.clear();
                    recyclerView.removeAllViews();
                }

            }
        });

        return consulta_vista;
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("Libros").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                libro_autor.clear();
                libro_edicion.clear();
                libro_editorial.clear();
                libro_imagen.clear();
                libro_tema.clear();
                libro_titulo.clear();
                libro_ubicacion.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                //Search all users for matching searched string

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String titulo = snapshot.child("titulo").getValue(String.class);
                    String autor = snapshot.child("autor").getValue(String.class);
                    String editorial = snapshot.child("editorial").getValue(String.class);
                    String edicion = snapshot.child("edicion").getValue(String.class);
                    String tema = snapshot.child("tema").getValue(String.class);
                    String ubicacion = snapshot.child("ubicacion").getValue(String.class);
                    String portada = snapshot.child("portada").getValue(String.class);

                    if (titulo.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        libro_titulo.add(titulo);
                        libro_autor.add(autor);
                        libro_edicion.add(edicion);
                        libro_editorial.add(editorial);
                        libro_tema.add(tema);
                        libro_ubicacion.add(ubicacion);
                        libro_imagen.add(portada);
                        counter++;
                    }else if (autor.toLowerCase().contains(searchedString.toLowerCase())) {
                        libro_titulo.add(titulo);
                        libro_autor.add(autor);
                        libro_edicion.add(edicion);
                        libro_editorial.add(editorial);
                        libro_tema.add(tema);
                        libro_ubicacion.add(ubicacion);
                        libro_imagen.add(portada);
                        counter++;
                    } else if (edicion.toLowerCase().contains(searchedString.toLowerCase())) {
                        libro_titulo.add(titulo);
                        libro_autor.add(autor);
                        libro_edicion.add(edicion);
                        libro_editorial.add(editorial);
                        libro_tema.add(tema);
                        libro_ubicacion.add(ubicacion);
                        libro_imagen.add(portada);
                        counter++;
                    } else if (editorial.toLowerCase().contains(searchedString.toLowerCase())) {
                        libro_titulo.add(titulo);
                        libro_autor.add(autor);
                        libro_edicion.add(edicion);
                        libro_editorial.add(editorial);
                        libro_tema.add(tema);
                        libro_ubicacion.add(ubicacion);
                        libro_imagen.add(portada);
                        counter++;
                    }  else if (tema.toLowerCase().contains(searchedString.toLowerCase())) {
                        libro_titulo.add(titulo);
                        libro_autor.add(autor);
                        libro_edicion.add(edicion);
                        libro_editorial.add(editorial);
                        libro_tema.add(tema);
                        libro_ubicacion.add(ubicacion);
                        libro_imagen.add(portada);
                        counter++;
                    } else if (ubicacion.toLowerCase().contains(searchedString.toLowerCase())) {
                        libro_titulo.add(titulo);
                        libro_autor.add(autor);
                        libro_edicion.add(edicion);
                        libro_editorial.add(editorial);
                        libro_tema.add(tema);
                        libro_ubicacion.add(ubicacion);
                        libro_imagen.add(portada);
                        counter++;
                    }

                    if (counter == 15)
                        break;
                }

                adapter = new LibrosAdapter(getContext(), libro_titulo, libro_autor, libro_edicion,
                        libro_editorial, libro_ubicacion, libro_tema, libro_imagen);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface OnFragmentInteractionListener {
    }
}
