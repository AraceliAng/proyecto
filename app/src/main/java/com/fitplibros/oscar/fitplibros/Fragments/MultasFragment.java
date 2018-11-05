package com.fitplibros.oscar.fitplibros.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitplibros.oscar.fitplibros.Common.Common;
import com.fitplibros.oscar.fitplibros.Holder.MultasAdapter;
import com.fitplibros.oscar.fitplibros.Model.Multa;
import com.fitplibros.oscar.fitplibros.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MultasFragment extends Fragment {

    private RecyclerView recyclerView, recyclerViewpagado;
    private MultasAdapter adapter, adapter_pagada;
    private List<Multa> multaList, multa_pagada;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View multa_vistazo=inflater.inflate(R.layout.fragment_multas, container, false);

        recyclerView = multa_vistazo.findViewById(R.id.idRecycler_multa);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewpagado = multa_vistazo.findViewById(R.id.idRecycler_multa_pagada);
        recyclerViewpagado.setHasFixedSize(true);
        recyclerViewpagado.setLayoutManager(new LinearLayoutManager(getContext()));


        multaList = new ArrayList<>();
        adapter = new MultasAdapter(getContext(),multaList);
        recyclerView.setAdapter(adapter);

        multa_pagada = new ArrayList<>();
        adapter_pagada = new MultasAdapter(getContext(),multa_pagada);
        recyclerViewpagado.setAdapter(adapter_pagada);


        Query query = FirebaseDatabase.getInstance().getReference("Multas")
                .orderByChild("num_control")
                .equalTo(Common.currentUser.getNum_control());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                multaList.clear();
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Multa multa = snapshot.getValue(Multa.class);
                        multaList.add(multa);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query1 = FirebaseDatabase.getInstance().getReference("Multas")

                .orderByChild("num_control")
                .equalTo(Common.currentUser.getNum_control()+1);

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                multa_pagada.clear();
                if (dataSnapshot.exists())
                {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Multa multa = snapshot.getValue(Multa.class);
                        multa_pagada.add(multa);
                    }
                    adapter_pagada.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return multa_vistazo;
    }

    public interface OnFragmentInteractionListener {
    }
}
