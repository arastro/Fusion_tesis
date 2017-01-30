package com.example.android.fusion_tesis.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * Created by IVANF on 29/01/2017.
 */

public class Actividad_Login extends AppCompatActivity {


    //String de url de la pagina
    private static final String LOGIN_URL="http://ceramicapiga.com/tesis/login.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);
    }


    protected boolean isEmpty(EditText user, EditText password){

        return(user.getText().toString().equals("") && password.getText().toString().equals(""));


    }

    public void onGotoRegisterActivity(View view) {

        // we change to RegisterActivity
        Intent intent =new Intent(this, Actividad_Registro.class);
        startActivity(intent);

    }

    public void menuPrincipal(View view) {

        // we change to RegisterActivity
        Intent intent =new Intent(this, Actividad_Principal.class);
        startActivity(intent);

    }

    public void goToMenuPrincipal(View view) {
        Intent intent =new Intent(this, Actividad_Principal.class);
        intent.putExtra("id",10);
        startActivity(intent);


        //new AsyncMenuPrincipal().execute();


    }


    class AsyncMenuPrincipal extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;
        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


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
                HashMap<String, String> params = new HashMap<>();
                params.put("user", user);

                Log.d("request", "starting");

                json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }


            try {
                String salt = json.getString("salt");
                String largepassword = json.getString("password");
                String rareString = Textgenerator.get_SHA_512_SecurePassword(password, salt);
                int id = json.getInt("id");

                if(rareString.equals(largepassword)) {
                    Toast.makeText(getApplication(),"Bienvenido", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(), Actividad_Principal.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplication(),"Usuario o Contraseña incorrecta", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}

