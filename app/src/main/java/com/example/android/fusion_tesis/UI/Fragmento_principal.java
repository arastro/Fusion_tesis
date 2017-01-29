package com.example.android.fusion_tesis.UI;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.android.fusion_tesis.Modelo.Sitio;
import com.example.android.fusion_tesis.R;




public class Fragmento_principal extends Fragment {


    public Fragmento_principal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragmento_principal, container, false);


        Adaptador_Sitios adapter = new Adaptador_Sitios(getActivity(), Sitio.COMIDAS_POPULARES);
        ListView lista = (ListView)view.findViewById(R.id.reciclador);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent =new Intent(getContext(), Actividad_Detalle_Sitio.class);
                startActivity(intent);
            }
        });

        return view;
    }

}