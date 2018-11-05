package com.fitplibros.oscar.fitplibros.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.fitplibros.oscar.fitplibros.Holder.View_Holder_Noticias;
import com.fitplibros.oscar.fitplibros.Model.Noticia;
import com.fitplibros.oscar.fitplibros.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    RecyclerView mRecyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Noticia> options;
    private FirebaseRecyclerAdapter<Noticia, View_Holder_Noticias> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistoso=inflater.inflate(R.layout.fragment_home, container, false);

        //RecyclerView

        mRecyclerView = vistoso.findViewById(R.id.idRecycler_noti);
        mRecyclerView.setHasFixedSize(true);

        //set Layout as LinearLayout
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Noticia");

        options = new FirebaseRecyclerOptions.Builder<Noticia>()
                .setQuery(databaseReference, Noticia.class).build();

        adapter = new FirebaseRecyclerAdapter<Noticia, View_Holder_Noticias>(options) {
            @Override
            protected void onBindViewHolder(@NonNull View_Holder_Noticias holder, int position, @NonNull Noticia model) {

                Picasso.get().load(model.getUrl()).into(holder.mImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                holder.mTitle.setText(model.getTitulo());
                holder.mDate.setText(model.getFecha());
                holder.mDetail.setText(model.getDescripcion());

              /*  Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Arkhip_font.ttf");
                holder.mTitle.setTypeface(face);
                holder.mDate.setTypeface(face);
                holder.mDetail.setTypeface(face);
                */
            }

            @NonNull
            @Override
            public View_Holder_Noticias onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.noticia_list,viewGroup,false);
                return new View_Holder_Noticias(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);

        return vistoso;
    }

    public interface OnFragmentInteractionListener {
    }
}
