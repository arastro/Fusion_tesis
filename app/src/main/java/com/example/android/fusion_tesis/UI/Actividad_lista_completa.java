package com.example.android.fusion_tesis.UI;

import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.fusion_tesis.Modelo.Sitio;
import com.example.android.fusion_tesis.R;

import java.util.ArrayList;

public class Actividad_lista_completa extends AppCompatActivity {

    private ArrayList<String> sitios = new ArrayList<String>();
    private ArrayList<Integer> idSitios = new ArrayList<>(); // va a contener la posicion del sitio cuando sea agregado al Arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_completa);

        sitios.add("ivan");
        sitios.add("ivan");
        sitios.add("ivan");
        sitios.add("ivan");

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sitios);

        ListView listView = (ListView) findViewById(R.id.reciclador_lista_completa);
        listView.setAdapter(itemsAdapter);



    }
}
