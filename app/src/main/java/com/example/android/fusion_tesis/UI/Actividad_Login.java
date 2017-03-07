package com.example.android.fusion_tesis.UI;

import android.app.ProgressDialog;
import android.content.Intent;
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

import static android.R.attr.value;

/**
 * Created by IVANF on 29/01/2017.
 */

public class Actividad_Login extends AppCompatActivity {


    //String de url de la pagina
    private static final String LOGIN_URL="http://ceramicapiga.com/tesis/login.php";

    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Actividad_Login.this);

        userid= prefs.getInt("userid", 0);

        // se va a comprobar si el usuario ya se ha hecho log anteriormente
        if(userid>0){

            Intent intent = new Intent(getApplicationContext(), Actividad_Principal.class);
            intent.putExtra("userid", userid); // manda la userid del usuario al siguiente intent

            startActivity(intent);
            finish();
        }
    }


    public void onGotoRegisterActivity(View view) {

        // we change to RegisterActivity
        Intent intent =new Intent(this, Actividad_Registro.class);
        startActivity(intent);

    }


    public void goToMenuPrincipal(View view) {

        // llama a la actividad principal de la app
        Intent intent =new Intent(this, Actividad_Principal.class);
        intent.putExtra("userid",10);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Actividad_Login.this); // guarda usuario

        prefs.edit().putInt("userid",10).commit();

        startActivity(intent);


        //new AsyncMenuPrincipal().execute();
      /*  prefs = PreferenceManager.getDefaultSharedPreferences(MyAccountActivity.this);
        prefs.edit().clear().commit(); */

    }


    class AsyncMenuPrincipal extends AsyncTask<Void, Void, Void> {

        // Comprueba que el usurio.

        private ProgressDialog pDialog;
        JSONObject json = new JSONObject(); // Contendra la respuesta del servidor en json
        JSONParser jsonParser = new JSONParser(); // hara la peticion de servicio con el php
        private static final String TAG_SUCCESS = "success"; // mensaje de exito


        EditText userEditText = (EditText)findViewById(R.id.userEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        String user=userEditText.getText().toString();
        String password= passwordEditText.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Actividad_Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                HashMap<String, String> params = new HashMap<>(); // contendra todas las variables que seran mandaras al servidor
                params.put("user", user); //

                Log.d("request", "starting");

                json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params); // ejecuta una peticion y recibe la respuesta en Json

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {

                pDialog.dismiss();


            try {
                if(json.getInt(TAG_SUCCESS)==1) {  // Verifica que la respuesta sea correcta
                    String salt = json.getString("salt"); // almacena la salt del servidor
                    String largepassword = json.getString("password"); // almacena la password mandada del seervidor
                    String rareString = Textgenerator.get_SHA_512_SecurePassword(password, salt);
                    userid = json.getInt("userid"); // almacena la userid del usuario que intenta hacer login


                    if (rareString.equals(largepassword)) {
                        /*
                        Si las contraseñas son iguales abre el la actividad_Principal de la app
                         */
                        Toast.makeText(getApplication(), "Bienvenido", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Actividad_Principal.class);
                        intent.putExtra("userid", userid); // manda la userid del usuario al siguiente intent

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Actividad_Login.this); // guarda usuario

                        prefs.edit().putInt("userid",userid).commit();

                        startActivity(intent);
                        finish(); // destruye esta actividad para que no se pueda volver una vez hecho loguin

                    } else {

                        // en caso de no ser iguales manda este mensaje y no sigue
                        Toast.makeText(getApplication(), "Usuario o Contraseña incorrecta", Toast.LENGTH_LONG).show();
                    }
                }else{
                    // en caso de respuesta erronea por parte del servidor
                    Toast.makeText(getApplication(), "Ups, algo fallo !intentalo de nuevo!", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}

