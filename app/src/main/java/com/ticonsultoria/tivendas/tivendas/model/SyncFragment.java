package com.ticonsultoria.tivendas.tivendas.model;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ticonsultoria.tivendas.tivendas.R;

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
        return inflater.inflate(R.layout.fragment_sync, container, false);
    }

}
