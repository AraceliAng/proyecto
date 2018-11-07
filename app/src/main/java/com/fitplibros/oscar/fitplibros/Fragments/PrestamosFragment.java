package com.fitplibros.oscar.fitplibros.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitplibros.oscar.fitplibros.Common.Common;
import com.fitplibros.oscar.fitplibros.Holder.PrestamosAdapter;
import com.fitplibros.oscar.fitplibros.Model.Prestamo;
import com.fitplibros.oscar.fitplibros.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PrestamosFragment extends Fragment {

    private RecyclerView recyclerView;
    private PrestamosAdapter adapter;
    private List<Prestamo> prestamoList;
    FirebaseUser user;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View prestamo_vista=inflater.inflate(R.layout.fragment_prestamos, container, false);

        recyclerView = prestamo_vista.findViewById(R.id.idRecycler_prestamo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        prestamoList = new ArrayList<>();
        adapter = new PrestamosAdapter(getContext(),prestamoList);
        recyclerView.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();

        String userId = user.getUid();
        Query query = FirebaseDatabase.getInstance().getReference("Prestamos/"+userId)
                .orderByChild("usuario_prestado")
                .equalTo(Common.currentUser.getNum_control());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prestamoList.clear();
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Prestamo prestamo = snapshot.getValue(Prestamo.class);
                        prestamoList.add(prestamo);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return prestamo_vista;
    }

    public interface OnFragmentInteractionListener {
    }
}
