package com.worldskills.turisapp.servicios;

//https://maps.googleapis.com/maps/api/directions/json?origin=parametroLatitud,parametroLongitud&destination=parametroLatitud,parametroLongitud


import com.worldskills.turisapp.modelos.ItemLugar;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Esta interfaz se encarga de hacer los diferentes pedidos a los servicios web, ya se para los lugares o las rutas
public interface ServicioWeb {

    @GET("webserviceturisappsitios.php")
    Call<List<ItemLugar>> getSitios();

    @GET("webserviceturisapphoteles.php")
    Call<List<ItemLugar>> getHoteles();

    @GET("webserviceturisappsres.php")
    Call<List<ItemLugar>> getRestaurantes();

    @GET("json")
    Call<ResponseBody> getRutas(@Query("origin") String origen, @Query("destination") String destination);

}
