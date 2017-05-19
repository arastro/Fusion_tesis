package us.example.android.fusion_tesis.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import us.example.android.fusion_tesis.Modelo.JSONParser;
import us.example.android.fusion_tesis.Modelo.Sitio;

import com.example.android.fusion_tesis.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActividadListaCompleta extends AppCompatActivity {

    public static final String URL= Ruta.GET_15_SITES_URL; // url de los 15 sitios a recomendar
    public static final String URL2= Ruta.GET_RECOM_SITES_URL; // url de los sitios punteados
    private ArrayList<Integer> idSitios = new ArrayList<>(); // va a contener la posicion del sitio cuando sea agregado al Arraylist;
    private int userid; // id de usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_completa);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ActividadListaCompleta.this);
        userid= prefs.getInt("userid", 0);

        Intent intent = getIntent();
        int numero = intent.getIntExtra("numero",0);

        // si es  0 se cargan los 15 sitios a recomendar
        // diferente a 0, sitios punteados
        if(numero ==0) {
            GetRecomSites tsk = new GetRecomSites();
            tsk.execute();
        }else{

            GetDottesSites tsk = new GetDottesSites();
            tsk.execute();
        }

    }

    private class GetRecomSites extends AsyncTask<Void, Void ,Void> {

        private ProgressDialog pDialog;
        private ArrayList<String> sitios = new ArrayList<>();
        private JSONObject json = new JSONObject();
        private JSONParser jsonParser = new JSONParser();

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(ActividadListaCompleta.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HashMap<String, String> params = new HashMap<>();
            params.put("userid", Integer.toString(userid));

            Log.i("Tag", "llego Aqui");

            json = jsonParser.makeHttpRequest(URL, "POST", params);
            try {
                JSONArray values = json.getJSONArray("sitios");

                for (int i=0; i<values.length(); i++){
                    JSONObject sitioJson = values.getJSONObject(i);
                    int id = sitioJson.getInt("id");
                    String name = sitioJson.getString("nombre");
                    sitios.add(name); // almacena los sitios
                    idSitios.add(id); // almacena la id, en la posicion de los sitios
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);


            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(ActividadListaCompleta.this, android.R.layout.simple_list_item_1, sitios);
            ListView lista = (ListView) findViewById(R.id.reciclador_lista_completa);

            lista.setAdapter(itemsAdapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent =new Intent(getApplicationContext(), ActividadDetalleSitio.class);
                    intent.putExtra("id_sitio",idSitios.get(position));
                    startActivity(intent);
                }
            });
            pDialog.dismiss();

        }


    }

    private class GetDottesSites extends AsyncTask<Void, Void, Void>{
        private ProgressDialog pDialog;
        private ArrayList<Sitio> sitios = new ArrayList<>();
        private JSONObject json = new JSONObject();
        private JSONParser jsonParser = new JSONParser();
        private ArrayList<Integer>score = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(ActividadListaCompleta.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        protected Void doInBackground(Void... voids) {
            HashMap<String, String> params = new HashMap<>();
            params.put("userid", Integer.toString(userid));

            Log.i("Tag", "llego Aqui");

            json = jsonParser.makeHttpRequest(URL2, "POST", params);
            try {
                JSONArray values = json.getJSONArray("sitios");

                for (int i=0; i<values.length(); i++){
                    JSONObject sitioJson = values.getJSONObject(i);
                    int id = sitioJson.getInt("id");
                    String name = sitioJson.getString("nombre");
                    int sc = sitioJson.getInt("rating");
                    Sitio sitio = new Sitio(id, name, null, null, null);
                    sitios.add(sitio);
                    score.add(sc);
                    idSitios.add(id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);


            AdaptadorSitiosDot itemsAdapter = new AdaptadorSitiosDot(getApplicationContext(), sitios, score);

            ListView lista = (ListView) findViewById(R.id.reciclador_lista_completa);

            lista.setAdapter(itemsAdapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent =new Intent(getApplicationContext(), ActividadDetalleSitio.class);
                    intent.putExtra("id_sitio",idSitios.get(position));
                    startActivity(intent);
                }
            });
            pDialog.dismiss();

        }
    }

}
