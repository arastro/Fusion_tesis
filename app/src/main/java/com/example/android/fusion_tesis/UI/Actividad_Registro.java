package com.example.android.fusion_tesis.UI;

/**
 * Created by IVANF on 29/01/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
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


public class Actividad_Registro extends Activity {

    //String de url de la pagina
    private static final String LOGIN_URL = "http://ceramicapiga.com/tesis/registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registro);
    }

    public void onRegistrar(View view) {
        new PostAsync().execute();

    }

    public void goToMenuPrincipal(View view) {



    }

    class PostAsync extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;
        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        EditText userEditText = (EditText) findViewById(R.id.userEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        EditText EmailEditText = (EditText)findViewById(R.id.emailEdit);

        String user = userEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = EmailEditText.getText().toString();

        String salt = "";
        String largePassword = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Actividad_Registro.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            salt = Textgenerator.generateSalt();
            largePassword = Textgenerator.get_SHA_512_SecurePassword(password, salt);

            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("email", email);
                params.put("salt", salt);
                params.put("largepassword", largePassword);

                Log.d("request", "starting");

                json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
                Toast.makeText(getApplication(), "Registro Incompleto", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Registro Completo", Toast.LENGTH_LONG).show();

            }
        }

    }
}
