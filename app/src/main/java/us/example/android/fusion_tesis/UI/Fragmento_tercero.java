package us.example.android.fusion_tesis.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.fusion_tesis.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento_tercero extends Fragment {


    public Fragmento_tercero() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_tercero, container, false);
    }

}