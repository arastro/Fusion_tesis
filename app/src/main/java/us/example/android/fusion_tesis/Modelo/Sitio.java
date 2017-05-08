package us.example.android.fusion_tesis.Modelo;

import android.graphics.Bitmap;

/**
 * Created by IVANF on 20/01/2017.
 */
public class Sitio {
    private int id ;
    private String nombre;
    private Bitmap img;
    private String municipio;
    private String departamento;
    private double avg;

    public Sitio(int id, String nombre, String departamento, String municipio, Bitmap img) {
        this.id = id;
        this.nombre = nombre;
        this.img = img;
        this.municipio = municipio;
        this.departamento = departamento;
        this.avg = 0;
    }

    public Sitio(int id, String nombre, String departamento, String municipio, Bitmap img, double avg) {
        this.id = id;
        this.nombre = nombre;
        this.img = img;
        this.municipio = municipio;
        this.departamento = departamento;
        this.avg = avg;
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

    public double getAvg(){
        return avg;
    }

    public void setAvg(double avg){
        this.avg = avg;
    }
}

