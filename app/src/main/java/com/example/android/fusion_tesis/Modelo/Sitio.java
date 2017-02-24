package com.example.android.fusion_tesis.Modelo;

import android.graphics.Bitmap;

import com.example.android.fusion_tesis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IVANF on 20/01/2017.
 */
public class Sitio {
    private int id ;
    private String nombre;
    private Bitmap img;
    private String municipio;
    private String departamento;

    public Sitio(int id, String nombre, String departamento, String municipio, Bitmap img) {
        this.id = id;
        this.nombre = nombre;
        this.img = img;
        this.municipio = municipio;
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getDepartamento() {
        return departamento;
    }


    public String getNombre() {
        return nombre;
    }

    public float getId() {
        return id;
    }

    public Bitmap getImg() {
        return img;
    }
}

