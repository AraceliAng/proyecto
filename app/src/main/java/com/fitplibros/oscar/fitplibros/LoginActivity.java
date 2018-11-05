package com.fitplibros.oscar.fitplibros;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fitplibros.oscar.fitplibros.Common.Common;
import com.fitplibros.oscar.fitplibros.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn;
    RelativeLayout rootLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Antes del setContentView
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_login);

        //Inicia Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference(Common.informacionEstudiantes_tbl);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        sharedPreferences = getSharedPreferences("loginref",Context.MODE_PRIVATE);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                  //  Toast.makeText(LoginActivity.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();
                  //  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  //  startActivity(intent);
                  //  finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    private void showLoginDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("INGRESAR ");
        dialog.setMessage("Usa un email para ingresar");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login,null);

        final MaterialEditText edtEmail = login_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword = login_layout.findViewById(R.id.edtPassword);
        final CheckBox check_pass = login_layout.findViewById(R.id.check_pass);

        final CheckBox check_recuerda = login_layout.findViewById(R.id.check_recuerda);

        editor = sharedPreferences.edit();

        savelogin=sharedPreferences.getBoolean("savelogin",true);

        if(savelogin==true){
            check_recuerda.setChecked(true);
            edtEmail.setText(sharedPreferences.getString("username",null));
            edtPassword.setText(sharedPreferences.getString("password",null));

            //   iniciarSesion();
        }


        check_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    check_pass.setButtonDrawable(R.drawable.ic_visibility_black_24dp);

                } else {
                    // hide password
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    check_pass.setButtonDrawable(R.drawable.ic_no_visible);
                }
            }
        });


        dialog.setView(login_layout);

        //Ponemos al boton
        dialog.setPositiveButton("INGRESAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                //Checamos validacion

                if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                    Snackbar.make(rootLayout, "Por favor, ingresa un email", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    Snackbar.make(rootLayout, "Ingresa una contraseña", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                final SpotsDialog waitingDialog = (SpotsDialog) new SpotsDialog.Builder()
                        .setContext(LoginActivity.this)
                        .setCancelable(false)
                        .setMessage("Cargando")
                        .build();
                waitingDialog.show();


                //Login

                auth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();

                                FirebaseDatabase.getInstance().getReference(Common.informacionEstudiantes_tbl)
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Common.currentUser = dataSnapshot.getValue(User.class);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                if(check_recuerda.isChecked()) {
                                    editor.putBoolean("savelogin", true);
                                    editor.putString("username", edtEmail.getText().toString());
                                    editor.putString("password", edtPassword.getText().toString());
                                    editor.commit();
                                    check_recuerda.setChecked(true);
                                }

                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitingDialog.dismiss();
                       Snackbar.make(rootLayout, "No se pudo obtener los datos del alumno", Snackbar.LENGTH_SHORT).show();

                    }
                });
            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
