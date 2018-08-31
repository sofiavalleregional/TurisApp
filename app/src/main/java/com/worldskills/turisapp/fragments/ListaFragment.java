package com.worldskills.turisapp.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.worldskills.turisapp.R;
import com.worldskills.turisapp.activities.MainActivity;
import com.worldskills.turisapp.adapters.AdapterLugares;
import com.worldskills.turisapp.interfaces.ComunicaFragment;
import com.worldskills.turisapp.modelos.ItemLugar;
import com.worldskills.turisapp.servicios.ServicioWeb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaFragment extends Fragment {


    private boolean vista;
    private String categoria;

    private GridView gridView;
    private AdapterLugares adapterLugares;

    public ListaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_lista, container, false);
        gridView=rootView.findViewById(R.id.lista_gridview);

        if (getArguments()!=null){
            vista=getArguments().getBoolean(MainActivity.VISTA);
            categoria=getArguments().getString(MainActivity.CATEGORIA);
        }

        if (getActivity()!=null && isAdded())consumeDatos();

        return rootView;
    }

    public void consumeDatos(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url_lugares))
             .addConverterFactory(GsonConverterFactory.create()).build();

        ServicioWeb servicio=retrofit.create(ServicioWeb.class);

        Call<List<ItemLugar>> res;

        if (categoria.equalsIgnoreCase("sitios")){
            res=servicio.getSitios();
        }else if (categoria.equalsIgnoreCase("hoteles")){
            res=servicio.getHoteles();
        }else{
            res=servicio.getRestaurantes();
        }

        res.enqueue(new Callback<List<ItemLugar>>() {
            @Override
            public void onResponse(Call<List<ItemLugar>> call, Response<List<ItemLugar>> response) {
                cargaLista(response.body());
            }

            @Override
            public void onFailure(Call<List<ItemLugar>> call, Throwable t) {

            }
        });
    }

    public void cargaLista(List<ItemLugar> lugares){

        try{
            if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                adapterLugares=new AdapterLugares(getActivity(),R.layout.item_lugar_list_land,lugares);
            }else if (vista){
                adapterLugares=new AdapterLugares(getActivity(),R.layout.item_lugar_list,lugares);
            }else{
                adapterLugares=new AdapterLugares(getActivity(),R.layout.item_lugar_grid,lugares);
            }
            gridView.setAdapter(adapterLugares);


        }catch (Exception e){}

        if (adapterLugares!=null) adapterLugares.notifyDataSetChanged();
        clickLugar();

    }
    public void clickLugar(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((ComunicaFragment)getActivity()).itemPrecionado(position);
            }
        });
    }

}
