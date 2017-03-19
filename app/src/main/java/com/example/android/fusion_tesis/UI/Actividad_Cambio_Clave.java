package com.example.android.fusion_tesis.UI;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.fusion_tesis.Modelo.JSONParser;
import com.example.android.fusion_tesis.Modelo.Textgenerator;
import com.example.android.fusion_tesis.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Edgar on 15/03/2017.
 */

public class Actividad_Cambio_Clave extends AppCompatActivity{


    //String de url de la pagina
    private static final String CHANGE_URL="http://ceramicapiga.com/tesis/changePassword.php";


    EditText oldPasswordEditText;
    EditText newPasswordEditText;
    EditText newPassword2EditText;
    int userid; //id del usuario
    String salt;
    String password = "";
    String password2 ="";
    String rareString = "";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_cambio_clave);
        oldPasswordEditText = (EditText)findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = (EditText)findViewById(R.id.passwordEditText);
        newPassword2EditText = (EditText)findViewById(R.id.passwordEditText2);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Actividad_Cambio_Clave.this);
        userid= prefs.getInt("userid", 0);
        salt = prefs.getString("salt", "0");



    }

    public void changepassword(View view) {

        String oldPassword = oldPasswordEditText.getText().toString();
        rareString = Textgenerator.get_SHA_512_SecurePassword(oldPassword,salt);
        password = newPasswordEditText.getText().toString();
        password2 = newPassword2EditText.getText().toString();


        if(password.equals(password2) && !password.equals("")){

            new changePasswordTask().execute();



        }else{
            Toast.makeText(getApplication(), "Contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }


    }

    class changePasswordTask extends AsyncTask<Void, Void, Void>{

        ProgressDialog pDialog;
        JSONObject json = new JSONObject(); // Contendra la respuesta del servidor en json
        JSONParser jsonParser = new JSONParser(); // hara la peticion de servicio con el php
        private static final String TAG_SUCCESS = "success"; // mensaje de exito
        String nuevaSalt = Textgenerator.generateSalt();
        String largepassword = Textgenerator.get_SHA_512_SecurePassword(password,nuevaSalt);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Actividad_Cambio_Clave.this);
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... a) {

            try {

                HashMap<String, String> params = new HashMap<>(); // contendra todas las variables que seran mandaras al servidor
                params.put("userid", Integer.toString(userid)); //
                params.put("rarestring", rareString);
                params.put("largepassword",largepassword);
                params.put("nuevasalt",nuevaSalt);

                Log.d("request", "starting");

                json = jsonParser.makeHttpRequest(CHANGE_URL, "POST", params); // ejecuta una peticion y recibe la respuesta en Json
            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            try {
                if (json.getInt(TAG_SUCCESS)== 1){
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Actividad_Cambio_Clave.this);
                    prefs.edit().putString("salt",salt).commit();

                    Toast.makeText(getApplication(), "Contraseña Cambiada Correctamente", Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(getApplication(), "Algo salio mal, Revisa bien los datos", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {

                Toast.makeText(getApplication(), "!!UPS, Algo salio mal Intentalo de nuevo", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


        }

    }
}
