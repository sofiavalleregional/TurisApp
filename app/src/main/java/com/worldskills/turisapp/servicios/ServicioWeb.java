package com.worldskills.turisapp.servicios;

//https://maps.googleapis.com/maps/api/directions/json?origin=parametroLatitud,parametroLongitud&destination=parametroLatitud,parametroLongitud


import com.worldskills.turisapp.modelos.ItemLugar;

import retrofit2.Call;
import retrofit2.http.GET;

//Esta interfaz se encarga de hacer los diferentes pedidos a los servicios web, ya se para los lugares o las rutas
public interface ServicioWeb {

    @GET("webserviceturisappsitios.php")
    Call<ItemLugar> getSitios();


}
