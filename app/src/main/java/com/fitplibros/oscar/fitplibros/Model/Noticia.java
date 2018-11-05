package com.fitplibros.oscar.fitplibros.Model;

public class Noticia {

    String titulo, fecha, descripcion, url;

    public Noticia() {
    }

    public Noticia(String titulo, String fecha, String descripcion, String url) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
