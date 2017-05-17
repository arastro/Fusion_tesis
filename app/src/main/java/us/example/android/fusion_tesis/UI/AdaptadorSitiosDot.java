package us.example.android.fusion_tesis.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import us.example.android.fusion_tesis.Modelo.Sitio;
import com.example.android.fusion_tesis.R;

import java.util.ArrayList;

/**
 * Created by Edgar on 11/04/2017.
 */

public class AdaptadorSitiosDot extends ArrayAdapter<Sitio> {

    private ArrayList<Integer> sc; // guardara la puntuacion del sitio
    public AdaptadorSitiosDot(Context context, ArrayList<Sitio> sitios, ArrayList<Integer> score) {
        super(context, 0, sitios);
        sc = score;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list_sitio_2, parent, false);
        }

        Sitio sitioActual = getItem(position);

        TextView nombre = (TextView) listItemView.findViewById(R.id.nameTextView);

        nombre.setText(sitioActual.getNombre());

        TextView score = (TextView) listItemView.findViewById(R.id.scoreTextView);

        score.setText(Integer.toString(sc.get(position)));

        return listItemView;
    }
}
