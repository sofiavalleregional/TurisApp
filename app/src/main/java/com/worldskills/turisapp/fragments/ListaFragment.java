package com.worldskills.turisapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.worldskills.turisapp.R;
import com.worldskills.turisapp.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaFragment extends Fragment {


    private boolean vista;
    private String categoria;

    public ListaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_lista, container, false);

        if (getArguments()!=null){
            vista=getArguments().getBoolean(MainActivity.VISTA);
            categoria=getArguments().getString(MainActivity.CATEGORIA);
        }
        return rootView;
    }

    public void consumeDatos(){

    }

}
