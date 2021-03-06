package us.example.android.fusion_tesis.UI;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import us.example.android.fusion_tesis.Modelo.JSONParser;
import com.example.android.fusion_tesis.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActividadPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int userid;
    /*nuevo-------------------------------------------------------------------------------------------------------------------*/
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private SimpleCursorAdapter myAdapter;
    public static final String URL= Ruta.BUSCAR_SITE_URL;

    SearchView searchView = null;
    private String[] strArrData = {"No Suggestions"};
    private ArrayList<Integer> posicionSitiosBusqueda;
    /*---------------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        /*NUEVO------------------------------------------------------------------------------------*/
        final String[] from = new String[] {"Name"};
        final int[] to = new int[] {android.R.id.text1};

        // setup SimpleCursorAdapter
        myAdapter = new SimpleCursorAdapter(ActividadPrincipal.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        // Fetch data from mysql table using AsyncTask
        // new AsyncFetch().execute();

        /*------------------------------------------------------------------------------------------*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentoPrincipal fragnent = new FragmentoPrincipal();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment,fragnent);
        Intent intent = getIntent();
        //userid = intent.getIntExtra("userid", 0);
        Bundle args = new Bundle();
        //args.putInt("userid", userid);
        fragnent.setArguments(args);
        fragmentTransaction.commit();
    }


    public void listCompleteActivity(View view) {

        /* Lleva a ActividadListaCompleta
        *
        * el numero indica que lista va a seleccionar (0 todos los sitios recomendados)
        *
        * */

        Intent intent =new Intent(this, ActividadListaCompleta.class);
        intent.putExtra("numero",0);
        startActivity(intent);

    }

    public void listCategoria(View view) {
        Intent intent =new Intent(this, ActividadCategorias.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_barra_principal, menu);

        /*NUEVO------------------------------------------------------------------------------------*/
        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) ActividadPrincipal.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(ActividadPrincipal.this.getComponentName()));
            searchView.setIconified(false);
            searchView.setSuggestionsAdapter(myAdapter);
            // Getting selected (clicked) item suggestion
            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionClick(int position) {

                    // Add clicked text to search box
                    CursorAdapter ca = searchView.getSuggestionsAdapter();
                    Cursor cursor = ca.getCursor();
                    cursor.moveToPosition(position);
                    searchView.setQuery(cursor.getString(cursor.getColumnIndex("Name")),false);
                    Intent intent = new Intent(getApplicationContext(), ActividadDetalleSitio.class);
                    intent.putExtra("id_sitio",posicionSitiosBusqueda.get(position));
                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onSuggestionSelect(int position) {
                    return true;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    AsyncFetch asyncFetch = new AsyncFetch();
                    asyncFetch.execute(s);


                    return false;
                }
            });
        }
        /*-----------------------------------------------------------------------------------------*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_puntuados) {
            /* Lleva a ActividadListaCompleta
            *
            * el numero indica que lista va a seleccionar (1 sitios puntuados)
            *
            * */

            Intent intent =new Intent(this, ActividadListaCompleta.class);
            intent.putExtra("numero",1);
            startActivity(intent);

        } else if (id == R.id.nav_password) {

            Intent intent = new Intent(getApplicationContext(),ActividadCambioClave.class);
            startActivity(intent);

        }else if( id == R.id.nav_logout){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ActividadPrincipal.this);
            prefs.edit().clear().commit();
            Intent intent = new Intent(getApplicationContext(), ActividadLogin.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*NUEVO-----------------------------------------------------------------------------------------*/
    // Every time when you press search button on keypad an Activity is recreated which in turn calls this function
    @Override
    protected void onNewIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }

            // User entered text and pressed search button. Perform task ex: fetching data from database and display

        }
    }



    // Create class AsyncFetch
    private class AsyncFetch extends AsyncTask<String, String, String> {

        private ProgressDialog pDialog = new ProgressDialog(ActividadPrincipal.this);;

        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pDialog.setMessage("\tLoading...");
            pDialog.setCancelable(false);
            // pDialog.show();

        }

        @Override
        protected String doInBackground(String... nameSite) {

            if(!nameSite.toString().isEmpty()) {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", nameSite[0]);

                Log.i("Tag", "llego Aqui");

                ArrayList<String> listaSitios = new ArrayList<>();

                json = jsonParser.makeHttpRequest(URL, "POST", params);

                try {

                    JSONArray values = json.getJSONArray("sitios");
                    posicionSitiosBusqueda = new ArrayList<>();

                    int i = 0;
                    while (i < values.length()) {
                        JSONObject sitioJson = values.getJSONObject(i);
                        int id = sitioJson.getInt("id");
                        String name = sitioJson.getString("nombre");
                        listaSitios.add(name);
                        posicionSitiosBusqueda.add(id);

                        i++;
                    }

                    strArrData = listaSitios.toArray(new String[listaSitios.size()]);


                } catch (JSONException e) {
                    strArrData = listaSitios.toArray(new String[listaSitios.size()]);

                }
            }

            return nameSite[0];
        }

        @Override
        protected void onPostExecute(String result) {



            // Filter data
            final MatrixCursor mc = new MatrixCursor(new String[]{BaseColumns._ID, "Name"});
            for (int i = 0; i < strArrData.length; i++) {
                if (strArrData[i].toLowerCase().startsWith(result.toLowerCase()))
                    mc.addRow(new Object[]{i, strArrData[i]});


            }

            myAdapter.changeCursor(mc);
            searchView.setSuggestionsAdapter(myAdapter);
        }
    }


    /*----------------------------------------------------------------------------------------------*/



}
