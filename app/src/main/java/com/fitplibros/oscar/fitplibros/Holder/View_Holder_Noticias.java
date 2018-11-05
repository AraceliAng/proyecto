package com.fitplibros.oscar.fitplibros.Holder;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitplibros.oscar.fitplibros.R;

public class View_Holder_Noticias extends RecyclerView.ViewHolder {

    public View mView;
    public TextView mTitle;
    public TextView mDate;
    public TextView mDetail;
    public ImageView mImage;


    public View_Holder_Noticias(View itemView) {
        super(itemView);

        mView = itemView;

        mTitle = mView.findViewById(R.id.noticia_titulo);
        mDate = mView.findViewById(R.id.noticia_fecha);
        mDetail = mView.findViewById(R.id.noticia_descripcion);
        mImage = mView.findViewById(R.id.noticia_imagen);
    }

}
