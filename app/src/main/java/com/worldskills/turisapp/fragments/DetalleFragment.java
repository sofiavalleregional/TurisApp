package com.worldskills.turisapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.worldskills.turisapp.R;
import com.worldskills.turisapp.activities.MainActivity;
import com.worldskills.turisapp.modelos.ItemLugar;
import com.worldskills.turisapp.servicios.ServicioWeb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class DetalleFragment extends Fragment {


    private int itempresionado;
    private String categoria;
    private Activity thisActivity;
    private ImageView foto;
    private TextView descripcionlarga, titulo;
    public DetalleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflo la vista del fragment para mas adelante buscar los componentes y poder mandarles acciones.
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);


        foto = view.findViewById(R.id.detalle_foto);
        descripcionlarga = view.findViewById(R.id.detalle_descripcion);
        titulo = view.findViewById(R.id.detalle_title);

        itempresionado = 0;
        categoria = "";


        // Se toma la actividad en la que esta la vista
        thisActivity= getActivity();

        // se comprueba de que los arguemtnos no lleguen vacios, y entran a definirse por unas keys constantes en la clase MAIN ACTIVITY
        if(getArguments()!=null){
            itempresionado=getArguments().getInt(MainActivity.ITEM_PRECIONADO);
            categoria= getArguments().getString(MainActivity.CATEGORIA);
        }

        // Se comprueba de que la actividad haya sido asignada y no este vacia para poder consumir los recursoos
        if(thisActivity!=null && isAdded())consumeDatos();

        return view;
    }

    // Se crea el objeto de retrofit para construirlo con la base de url que se tiene para consumir el servicio, se convierte a un gson y se crea
    // Luego se instancia la clase de servicios y se crea con el retrofit mandandole la clase.
    // En la llamada de lista de objeto lugar se uso condicional para saber cual fue la categoria que se mando y asi cargar el servicio correspondiente-
    // se hace el el call back a la respuesta y se inicia el metodo de organizar
    public void consumeDatos(){
        Retrofit retrofit= new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url_lugares)).addConverterFactory(GsonConverterFactory
        .create()).build();

        ServicioWeb servicio = retrofit.create(ServicioWeb.class);

        Call<List<ItemLugar>> res;
        if(categoria.equalsIgnoreCase("sitios")){
           res= servicio.getSitios();
        } else if(categoria.equalsIgnoreCase("hoteles")){
           res= servicio.getHoteles();
        } else{
             res = servicio.getRestaurantes();
        }

        res.enqueue(new Callback<List<ItemLugar>>() {
            @Override
            public void onResponse(Call<List<ItemLugar>> call, Response<List<ItemLugar>> response) {
                organizarInfo(response.body());
            }

            @Override
            public void onFailure(Call<List<ItemLugar>> call, Throwable t) {

            }
        });
    }

    private void organizarInfo(List<ItemLugar> lugares) {
        ItemLugar itemLugar= lugares.get(itempresionado);

        Glide.with(thisActivity).load(itemLugar.getUrlImagen()).into(foto);
        descripcionlarga.setText(itemLugar.getDescripcion());
        titulo.setText(itemLugar.getNombre());

    }

}
