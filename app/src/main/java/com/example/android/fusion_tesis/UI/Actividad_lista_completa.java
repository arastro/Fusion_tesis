package com.example.android.fusion_tesis.UI;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.fusion_tesis.Modelo.DescargasYCargas;
import com.example.android.fusion_tesis.Modelo.JSONParser;
import com.example.android.fusion_tesis.Modelo.Sitio;
import com.example.android.fusion_tesis.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Actividad_lista_completa extends AppCompatActivity {

    public static final String URL="http://ceramicapiga.com/tesis/get15sites.php";
    private ArrayList<String> sitios = new ArrayList<String>();
    private ArrayList<Integer> idSitios = new ArrayList<>(); // va a contener la posicion del sitio cuando sea agregado al Arraylist;
    private int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_completa);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Actividad_lista_completa.this);
        userid= prefs.getInt("userid", 0);

        Intent intent = getIntent();
        int numero = intent.getIntExtra("numero",0);

        if(numero ==0) {
            GetFromUrl tsk = new GetFromUrl();
            tsk.execute();
        }else{


        }

    }

    private class GetFromUrl extends AsyncTask<Void, Void ,Void> {

        private ProgressDialog pDialog;

        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(Actividad_lista_completa.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap imagen=null;
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
                    sitios.add(name);
                    idSitios.add(id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);


            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(Actividad_lista_completa.this, android.R.layout.simple_list_item_1, sitios);
            ListView lista = (ListView) findViewById(R.id.reciclador_lista_completa);

            lista.setAdapter(itemsAdapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent =new Intent(getApplicationContext(), Actividad_Detalle_Sitio.class);
                    intent.putExtra("id_sitio",idSitios.get(position));
                    startActivity(intent);
                }
            });
            pDialog.dismiss();

        }


    }
}
