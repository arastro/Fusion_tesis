package us.example.android.fusion_tesis.UI;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Fragmento_principal extends Fragment {

    public static final String URL="http://ceramicapiga.com/tesis/get5sites.php";
    private ArrayList<Sitio> sitios = new ArrayList<Sitio>();
    private ArrayList<Integer> idSitios = new ArrayList<>(); // va a contener la posicion del sitio cuando sea agregado al Arraylist;
    private int userid;
    private View view;


    public Fragmento_principal() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragmento_principal, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext()); // Contiene al usuario

        userid= prefs.getInt("userid", 0);  // contiene el id del usuario
       // userid = args.getInt("userid", 0) ;

        GetFromUrl tsk = new GetFromUrl();
        tsk.execute();


       /* Adaptador_Sitios adapter = new Adaptador_Sitios(getActivity(), sitios);
        ListView lista = (ListView)view.findViewById(R.userid.reciclador);
        lista.setAdapter(adapter); */

       /* lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent =new Intent(getContext(), Actividad_Detalle_Sitio.class);
                startActivity(intent);
            }
        }); */

        return view;
    }

    private class GetFromUrl extends AsyncTask<Void, Void ,Void> {

        private ProgressDialog pDialog;

        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap imagen=null;
            HashMap<String, String> params = new HashMap<>();
            params.put("user", Integer.toString(userid));

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
                    sitios.add(new Sitio(id, name,"","", imagen,avg));
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


            Adaptador_Sitios adapter = new Adaptador_Sitios(getActivity(), sitios);

            ListView lista = (ListView)view.findViewById(R.id.reciclador);

            lista.setAdapter(adapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent =new Intent(getContext(), Actividad_Detalle_Sitio.class);
                    intent.putExtra("id_sitio",idSitios.get(position));
                    startActivity(intent);
                }
            });
            pDialog.dismiss();

        }


    }


}