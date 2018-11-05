package com.fitplibros.oscar.fitplibros.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitplibros.oscar.fitplibros.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BibliotecaFragment extends Fragment {
    PDFView myPdf;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View biblioteca_vista=inflater.inflate(R.layout.fragment_biblioteca, container, false);

       myPdf = biblioteca_vista.findViewById(R.id.pdfView);

        new RetrievePDFStream().execute("https://firebasestorage.googleapis.com/v0/b/fitplibros.appspot.com/o/Biblioteca%2FMi-taller-de-creacion-de-videojuegos-estudiante.pdf?alt=media&token=d8837fab-4a40-4466-bcd5-fb7b42dab1e0");


        return biblioteca_vista;
    }

    public interface OnFragmentInteractionListener {
    }

    class RetrievePDFStream extends AsyncTask<String,Void,InputStream>
    {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if (urlConnection.getResponseCode() == 200)
                {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return  inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            myPdf.fromStream(inputStream).load();
        }
    }
}
