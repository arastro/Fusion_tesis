package us.example.android.fusion_tesis.Modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edgar on 28/01/2017.
 */

public class DescargasYCargas {
    //Clase que se encarga de Descargar imagenes y Cargarlas

    public static Bitmap descargarImagen (String imageHttpAddress){
        URL imageUrl;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return imagen;
    }


}
