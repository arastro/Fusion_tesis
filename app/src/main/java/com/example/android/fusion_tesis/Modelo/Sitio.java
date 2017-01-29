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
    private int img;

    public Sitio(int id, String nombre, int img) {
        this.id = id;
        this.nombre = nombre;
        this.img = img;
    }

    public static  ArrayList<Sitio> COMIDAS_POPULARES = new ArrayList<Sitio>();
    /*public static final List<Sitio> BEBIDAS = new ArrayList<>();
    public static final List<Sitio> POSTRES = new ArrayList<>();
    public static final List<Sitio> PLATILLOS = new ArrayList<>();*/

    static {
        COMIDAS_POPULARES.add(new Sitio(1, "Camarones Tismados", R.drawable.camarones));
        COMIDAS_POPULARES.add(new Sitio(2, "Rosca Herbárea", R.drawable.camarones));
        COMIDAS_POPULARES.add(new Sitio(3, "Sushi Extremo", R.drawable.camarones));
        COMIDAS_POPULARES.add(new Sitio(4, "Sandwich Deli", R.drawable.camarones));
        COMIDAS_POPULARES.add(new Sitio(5, "Lomo De Cerdo Austral", R.drawable.camarones));

       /* PLATILLOS.add(new Sitio(5, "Camarones Tismados", R.drawable.background));
        PLATILLOS.add(new Sitio(3.2f, "Rosca Herbárea", R.drawable.background));
        PLATILLOS.add(new Sitio(12f, "Sushi Extremo", R.drawable.background));
        PLATILLOS.add(new Sitio(9, "Sandwich Deli", R.drawable.background));
        PLATILLOS.add(new Sitio(34f, "Lomo De Cerdo Austral",R.drawable.background));

        BEBIDAS.add(new Sitio(3, "Taza de Café",R.drawable.background));
        BEBIDAS.add(new Sitio(12, "Coctel Tronchatoro",R.drawable.background));
        BEBIDAS.add(new Sitio(5, "Jugo Natural",R.drawable.background));
        BEBIDAS.add(new Sitio(24, "Coctel Jordano",R.drawable.background));
        BEBIDAS.add(new Sitio(30, "Botella Vino Tinto Darius", R.drawable.background));

        POSTRES.add(new Sitio(2, "Postre De Vainilla", R.drawable.background));
        POSTRES.add(new Sitio(3, "Flan Celestial",R.drawable.background));
        POSTRES.add(new Sitio(2.5f, "Cupcake Festival", R.drawable.background));
        POSTRES.add(new Sitio(4, "Pastel De Fresa", R.drawable.background));
        POSTRES.add(new Sitio(5, "Muffin Amoroso", R.drawable.background));*/
    }


    public String getNombre() {
        return nombre;
    }

    public float getId() {
        return id;
    }

    public int getImg() {
        return img;
    }
}
