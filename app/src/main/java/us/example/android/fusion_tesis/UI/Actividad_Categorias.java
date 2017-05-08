package us.example.android.fusion_tesis.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.fusion_tesis.R;


/**
 * Created by Edgar on 6/05/2017.
 */

public class Actividad_Categorias extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_categorias);

        String[] categorias = {"Acuario",
                "Area de Descanso",
                "Area de Juego",
                "Artesanias",
                "Bahia",
                "Basilica",
                "Biblioteca",
                "Botanica",
                "Campamento Al Aire",
                "Canto",
                "Capilla",
                "Cascada",
                "Castillo",
                "Catedral",
                "Cementerio",
                "Centro Cultural",
                "Centro de Ocio Urbano",
                "Complejo Hotelero",
                "Compras",
                "Convento",
                "Cultura",
                "Danza",
                "Deportes Nauticos",
                "Edificio Con Valor Arquitectonico",
                "Ejercicio Fisico",
                "Escultura",
                "Espectaculo",
                "Excursion",
                "Expresion Teatral",
                "Gastronomia",
                "Iglesia",
                "Isla",
                "Monumento Conmemorativo",
                "Monumentos",
                "Muelle",
                "Museo Cultural",
                "Museo de Arte",
                "Museo de Historia",
                "Museo Militar",
                "Musica",
                "Parque Acuatico",
                "Parque de Ocio",
                "Parque Zoologico",
                "Paseo",
                "Patrimonio Natural",
                "Playa",
                "Pueblo",
                "Salidas a Restaurante",
                "Sendero De Bicicleta",
                "Sendero de Largo Recorrido",
                "Sendero Litoral",
                "Sendero Marcha a Pie",
                "Turismo Cultural",
                "Turismo Etnico",
                "Turismo Historico",
                "Turismo Naturaleza",
                "Turismo Rural",
                "Turismo Urbano",};

        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                categorias);

        ListView theListView = (ListView) findViewById(R.id.listViewCategorias);
        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                   Intent intent =new Intent(getApplicationContext(), Actividad_Categoria_Seleccionada.class);
                   intent.putExtra("tag",String.valueOf(parent.getItemAtPosition((position))));
                   startActivity(intent);
               }
           }
        );
    }
}
