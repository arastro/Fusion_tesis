package us.example.android.fusion_tesis.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import us.example.android.fusion_tesis.Modelo.Calculador;
import us.example.android.fusion_tesis.Modelo.DescargasYCargas;
import us.example.android.fusion_tesis.Modelo.JSONParser;
import us.example.android.fusion_tesis.Modelo.Sitio;

import com.example.android.fusion_tesis.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Edgar on 6/05/2017.
 */

public class Actividad_Categoria_Seleccionada extends AppCompatActivity{

    public static final String URL="http://ceramicapiga.com/tesis/get_by_tag.php";
    private ArrayList<Sitio> sitios = new ArrayList<Sitio>();
    private ArrayList<Integer> idSitios = new ArrayList<>(); // va a contener la posicion del sitio cuando sea agregado al Arraylist;
    ListView lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_categorias);

        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");
        lista =(ListView)findViewById(R.id.listViewCategorias);

        GetFromUrl tsk = new GetFromUrl();
        tsk.execute(tag);
    }



    private class GetFromUrl extends AsyncTask<String , Void, Void> {

        private ProgressDialog pDialog;

        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(Actividad_Categoria_Seleccionada.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... tag) {

            Bitmap imagen=null;
            HashMap<String, String> params = new HashMap<>();
            params.put("texto", tag[0]);
            Log.i("Tag", "llego Aqui");

            json = jsonParser.makeHttpRequest(URL, "POST", params);
            try {
                JSONArray values = json.getJSONArray("sitios");

                for (int i=0; i<values.length(); i++){
                    JSONObject sitioJson = values.getJSONObject(i);
                    int id = sitioJson.getInt("id");
                    String name = sitioJson.getString("nombre");
                    double avg = sitioJson.getDouble("avg");
                    avg = Calculador.Redondear(avg);
                    imagen = DescargasYCargas.descargarImagen(sitioJson.getString("url"));
                    sitios.add(new Sitio(id, name,"","", imagen, avg));
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


            Adaptador_Sitios adapter = new Adaptador_Sitios(getApplicationContext(), sitios);

            lista.setAdapter(adapter);
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
