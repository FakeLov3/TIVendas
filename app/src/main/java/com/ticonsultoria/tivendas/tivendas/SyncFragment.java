package com.ticonsultoria.tivendas.tivendas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ticonsultoria.tivendas.tivendas.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SyncFragment extends Fragment {


    public SyncFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sync, container, false);

        Button btnEnviar = view.findViewById(R.id.btn_enviar);
        Button btnBaixar = view.findViewById(R.id.btn_baixar);

        btnBaixar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });



        return view;
    }

}
