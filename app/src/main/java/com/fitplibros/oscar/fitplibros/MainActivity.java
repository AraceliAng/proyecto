package com.fitplibros.oscar.fitplibros;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitplibros.oscar.fitplibros.Fragments.BibliotecaFragment;
import com.fitplibros.oscar.fitplibros.Fragments.ConsultaFragment;
import com.fitplibros.oscar.fitplibros.Fragments.HomeFragment;
import com.fitplibros.oscar.fitplibros.Fragments.MultasFragment;
import com.fitplibros.oscar.fitplibros.Fragments.PrestamosFragment;
import com.fitplibros.oscar.fitplibros.Holder.MultasAdapter;
import com.fitplibros.oscar.fitplibros.Model.Multa;
import com.fitplibros.oscar.fitplibros.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        BibliotecaFragment.OnFragmentInteractionListener,
        ConsultaFragment.OnFragmentInteractionListener,
        MultasFragment.OnFragmentInteractionListener,
        PrestamosFragment.OnFragmentInteractionListener{

    TextView txt_nombre_nav, txtnum_control_nav, txtcarrera_nav;
    ImageView usu_foto_head;

    FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int CAMERA_REQUEST_CODE = 0;
    private ProgressDialog progressDialog;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private static final String TAG = "MainActivity";


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
        Toast.makeText(MainActivity.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //------ SE EJECUTA LA FUNCION INITFCM AGREGADA POR ARA
        initFCM();
        //---------------------------------------------------------------------------------
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Fragment_container, fragment).commit();
        //---------------------------------------------------------------------------------


        //  getSupportActionBar().setTitle("ITPresta Libros");

      /*  android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.escenario, new HomeFragment()).commit();
        getSupportActionBar().setTitle("Noticias");*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        final ImageView usu_foto_head = header.findViewById(R.id.usu_foto_head);

        txt_nombre_nav = header.findViewById(R.id.txtnombre_nav);
        txtnum_control_nav = header.findViewById(R.id.txtnum_control_nav);
        txtcarrera_nav = header.findViewById(R.id.txtcarrera_nav);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Arkhip_font.ttf");
        txtcarrera_nav.setTypeface(face);
        txtnum_control_nav.setTypeface(face);
        txt_nombre_nav.setTypeface(face);

    /*    usu_foto_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo2 = new AlertDialog.Builder(MainActivity.this);
                dialogo2.setMessage("¿Elegir otra imagen de perfil?");
                dialogo2.setCancelable(false);
                dialogo2.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogo1, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(Intent.createChooser(intent, "Elige una imagen de perfil"), CAMERA_REQUEST_CODE);
                        }
                    }
                });
                dialogo2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo2.show();
            }
        });
*/
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    mStorage = FirebaseStorage.getInstance().getReference();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("InformacionEstudiantes");
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            txt_nombre_nav.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                            txtnum_control_nav.setText(String.valueOf(dataSnapshot.child("num_control").getValue()));
                            txtcarrera_nav.setText(String.valueOf(dataSnapshot.child("carrera").getValue()));

                            String imageUrl = String.valueOf(dataSnapshot.child("foto").getValue());
                            if (URLUtil.isValidUrl(imageUrl))
                             Picasso.get().load(Uri.parse(imageUrl)).into(usu_foto_head);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }
    //PARA GENERAL EL TOKEN DEL PRESTAMO PARA SABER A QUIEB LE LLEGARA UNA NOTIFICACION
    private void initFCM(){
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "initFCM: token: " + token);
        sendRegistrationToServer(token);

    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "sendRegistrationToServer: sending token to server: " + token);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(getString(R.string.dbnode_users))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(getString(R.string.field_prestamos_token))
                .setValue(token);
    }

    // INITFCM & SENDREGISTRATIONTOSERVER SON FUNCIONES AGREGADAS POR ARA





    public String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, "uCrop error", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == UCrop.REQUEST_CROP) {
            final Uri imgUri = UCrop.getOutput(data);
            Toast.makeText(this, imgUri.getPath(), Toast.LENGTH_SHORT).show();
            uploadImage(imgUri);
            return;
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            final Uri sourceUri = data.getData();
            if (sourceUri == null) {
                progressDialog.dismiss();
                return;
            } else {
                File tempCropped = new File(getCacheDir(), "tempImgCropped.png");
                Uri destinationUri = Uri.fromFile(tempCropped);
                UCrop.of(sourceUri, destinationUri)
                        //.withAspectRatio(3, 2)
                        //.withMaxResultSize(MAX_WIDTH, MAX_HEIGHT)
                        .start(this);
            }
        }
    }


    public void uploadImage(final Uri fileUri) {
        if (auth.getCurrentUser() == null)
            return;

        if (mStorage == null)
            mStorage = FirebaseStorage.getInstance().getReference();
        if (mDatabase == null)
            mDatabase = FirebaseDatabase.getInstance().getReference().child("InformacionEstudiantes");

        final StorageReference filepath = mStorage.child("Photos").child(getRandomString());/*uri.getLastPathSegment()*/
        final DatabaseReference currentUserDB = mDatabase.child(auth.getCurrentUser().getUid());

        progressDialog.setMessage("Subiendo imagen");
        progressDialog.show();


       currentUserDB.child("foto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();

               if (!image.equals("default") && !image.isEmpty()) {
                    Task<Void> task = FirebaseStorage.getInstance().getReferenceFromUrl(image).delete();
                    task.addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Toast.makeText(MainActivity.this, "Imagen antigua eliminada", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this, "Fallo al eliminar imagen", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                currentUserDB.child("foto").removeEventListener(this);
                progressDialog.dismiss();


               filepath.putFile(fileUri).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                                progressDialog.dismiss();
                               //Uri downloadUri = taskSnapshot.getDownloadUrl();
                                Toast.makeText(MainActivity.this, "Finalizado", Toast.LENGTH_SHORT).show();
                                Picasso.get().load(fileUri).fit().centerCrop().into(usu_foto_head);
                                DatabaseReference currentUserDB = mDatabase.child(auth.getCurrentUser().getUid());
                                currentUserDB.child("foto").setValue(downloadUri.toString());
                            }
                        });
                    }
                }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuSearch) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = null;

       // android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        if (id == R.id.nav_camera) {
            //fm.beginTransaction().replace(R.id.escenario,new HomeFragment()).commit();
            fragmentClass = HomeFragment.class;
            getSupportActionBar().setTitle("Noticias");


        } else if (id == R.id.nav_gallery) {
            //fm.beginTransaction().replace(R.id.escenario,new PrestamosFragment()).commit();
            fragmentClass = PrestamosFragment.class;
            getSupportActionBar().setTitle("Préstamos");

        } else if (id == R.id.nav_slideshow) {
           // fm.beginTransaction().replace(R.id.escenario,new MultasFragment()).commit();
            fragmentClass = MultasFragment.class;
            getSupportActionBar().setTitle("Multas");

        } else if (id == R.id.nav_manage) {
          //  fm.beginTransaction().replace(R.id.escenario,new ConsultaFragment()).commit();
            fragmentClass = ConsultaFragment.class;
            getSupportActionBar().setTitle("Consulta de Libros");

        } else if (id == R.id.nav_biblioteca_libre) {
           // fm.beginTransaction().replace(R.id.escenario,new BibliotecaFragment()).commit();
            fragmentClass = BibliotecaFragment.class;
            getSupportActionBar().setTitle("Biblioteca Libre");

        }else if (id == R.id.cerrasesion) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setMessage("¿Deseas cerrar tu sesión?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogo1, int id) {
                    if (auth.getCurrentUser() != null)
                        auth.signOut();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    dialogo1.dismiss();
                }
            });
            dialogo1.show();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Fragment_container, fragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
