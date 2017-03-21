package com.example.android.fusion_tesis.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.fusion_tesis.Modelo.DescargasYCargas;
import com.example.android.fusion_tesis.Modelo.JSONParser;
import com.example.android.fusion_tesis.Modelo.Sitio;
import com.example.android.fusion_tesis.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by IVANF on 29/01/2017.
 */

public class Actividad_Detalle_Sitio extends AppCompatActivity {
    private Sitio sitio;
    private TextView nombreEditText;
    private TextView departamentoEditText;
    private ImageView imagenArriba;
    private int id_sitio;
    private int id_user;
    private RatingBar ratingBar;
    private int rate; // va a tener la puntucion realizada por el usuario;


    public static final String URL="http://ceramicapiga.com/tesis/get_site_info.php";
    public static final String URLRECOMENDACION = "http://ceramicapiga.com/tesis/makeRating.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalle_sitio);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Actividad_Detalle_Sitio.this);
        id_user = prefs.getInt("userid", 0);

        Intent intent = getIntent();

        id_sitio = intent.getIntExtra("id_sitio", 0);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        setTheme(R.style.AppTheme);



        GetFromUrl tsk = new GetFromUrl();
        tsk.execute();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = (int) rating;
                MakeRecomendation tarea = new MakeRecomendation();
                tarea.execute();


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private class MakeRecomendation extends  AsyncTask<Void, Void,Integer>{

        int success;
        @Override
        protected Integer doInBackground(Void... param) {
            JSONObject json = new JSONObject();
            JSONParser jsonParser = new JSONParser();


            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("id_user", Integer.toString(id_user));
                params.put("id_place", Integer.toString(id_sitio));
                params.put("rating", Integer.toString(rate));

               Log.d("request", "starting");

                json = jsonParser.makeHttpRequest(URLRECOMENDACION, "POST", params);

                success =json.getInt("success");


            }catch (Exception e){
                e.printStackTrace();

            }
            return success;
        }

        @Override
        protected void onPostExecute(Integer success) {
            super.onPostExecute(success);

            if(success==1){
                Toast.makeText(getApplication(), "Puntuacion Exitosa", Toast.LENGTH_SHORT).show();
                ratingBar.setRating(rate);

            }else{
                Toast.makeText(getApplication(), "Error, algo raro ocurrio", Toast.LENGTH_SHORT).show();
                ratingBar.setRating(rate);
            }

        }
    }

    private class GetFromUrl extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;

        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(Actividad_Detalle_Sitio.this);
            pDialog.setMessage("Cargando...Espere");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... param) {


            HashMap<String, String> params = new HashMap<>();
            params.put("id_place", Integer.toString(id_sitio));

            Log.i("Tag", "llego Aqui");

            json = jsonParser.makeHttpRequest(URL, "POST", params);
            try {
                int id_sitio =json.getInt("id_place");
                String nombre_sitio = json.getString("nombre");
                String municipio_sitio = json.getString("municipio");
                String departamento_sitio = json.getString("departamento");
                Bitmap imagen = DescargasYCargas.descargarImagen(json.getString("imagenUrl"));
                sitio = new Sitio(id_sitio, nombre_sitio, departamento_sitio, municipio_sitio, imagen);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            TextView nombre = (TextView)findViewById(R.id.nombre_detalle);
            nombre.setText(sitio.getNombre());

            TextView municipio = (TextView)findViewById(R.id.municipio_detalle);
            municipio.setText(sitio.getMunicipio());

            TextView departamento = (TextView)findViewById(R.id.departamento_detalle);
            departamento.setText(sitio.getDepartamento());

            ImageView imagen = (ImageView)findViewById(R.id.image_toolbars);
            imagen.setImageBitmap(sitio.getImg());
            pDialog.dismiss();

        }
    }
}