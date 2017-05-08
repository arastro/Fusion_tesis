package us.example.android.fusion_tesis.UI;

/**
 * Created by IVANF on 29/01/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import us.example.android.fusion_tesis.Modelo.JSONParser;
import us.example.android.fusion_tesis.Modelo.Textgenerator;
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
        setContentView(com.example.android.fusion_tesis.R.layout.actividad_registro);
    }

    public void onRegistrar(View view) {


    }



    public void onLogin(View view) {

        // Here is the code to execute when te user pick in the button login
        new PostAsync().execute();
    }

    class PostAsync extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;
        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        EditText userEditText = (EditText) findViewById(R.id.userEditText);

        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        EditText passwordEditText2 = (EditText)findViewById(R.id.passwordEditText2);

        EditText EmailEditText = (EditText)findViewById(R.id.emailEdit);
        EditText EmailEditText2 = (EditText)findViewById(R.id.emailEdit2);

        String user = userEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String password2 = passwordEditText2.getText().toString();
        String email = EmailEditText.getText().toString();
        String email2= EmailEditText2.getText().toString();
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


            if ((password.equals(password2) && email.equals(email2)) && !user.isEmpty()) {

                salt = Textgenerator.generateSalt(); // genera un String al azar
                largePassword = Textgenerator.get_SHA_512_SecurePassword(password, salt); // se genera una contraseña encriptada

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

            }else{
                // contraseña o email vacios o que no encajen. usuario Vacio
                cancel(true);
            }

            return null;
            }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            pDialog.dismiss();
            Toast.makeText(getApplication(), "Contraseña o Email diferentes", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void a) {

            pDialog.dismiss();

                try {
                    if (json.getInt("success") != 0) {
                        Toast.makeText(getApplication(), json.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication(), json.getString("message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplication(), "Algo Salio mal", Toast.LENGTH_LONG).show();
                }
        }

    }


}
