package us.example.android.fusion_tesis.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import us.example.android.fusion_tesis.Modelo.Sitio;

import com.example.android.fusion_tesis.R;

import java.util.ArrayList;

/**
 * Created by IVANF on 29/01/2017.
 */

public class AdaptadorSitios extends ArrayAdapter<Sitio> {

    public AdaptadorSitios(Context context, ArrayList<Sitio> sitios) {
        super(context, 0, sitios);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_lista_sitios, parent, false);
        }


        Sitio sitioActual = getItem(position);

        TextView nombre = (TextView) listItemView.findViewById(R.id.nombre_sitio);

        nombre.setText(sitioActual.getNombre());

        TextView avg = (TextView) listItemView.findViewById(R.id.municipio);

        avg.setText((String.valueOf(sitioActual.getAvg())));

        ImageView imagen = (ImageView) listItemView.findViewById(R.id.miniatura_sitio);

        imagen.setImageBitmap(sitioActual.getImg());

        return listItemView;
    }

}
