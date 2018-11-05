package com.fitplibros.oscar.fitplibros.Model;

import java.io.Serializable;

public class User {

    //Datos extraidos del usuario
    private String email, password, name, carrera, foto;
    int num_control;

    public User() {
    }



    public User(String email, String password, String name, int num_control, String carrera, String foto) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.num_control = num_control;
        this.carrera = carrera;
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_control() {
        return num_control;
    }

    public void setNum_control(int num_control) {
        this.num_control = num_control;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

