package com.worldskills.turisapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.maps.android.PolyUtil;
import com.worldskills.turisapp.R;
import com.worldskills.turisapp.modelos.ItemLugar;
import com.worldskills.turisapp.servicios.ServicioWeb;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int fragActivo, itemPrecionado;
    private String categoria;


    private LatLng origen, destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle datos = getIntent().getExtras();

        if (datos != null) {
            fragActivo = datos.getInt(MainActivity.FRAG_ACTIVO);
            itemPrecionado = datos.getInt(MainActivity.ITEM_PRECIONADO);
            categoria = datos.getString(MainActivity.CATEGORIA);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        consumeDatos();
    }

    public void consumeDatos() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url_lugares))
                .addConverterFactory(GsonConverterFactory.create()).build();

        ServicioWeb servicio = retrofit.create(ServicioWeb.class);

        final Call<List<ItemLugar>> res;


        if (categoria.equalsIgnoreCase("sitios")) {
            res = servicio.getSitios();
        } else if (categoria.equalsIgnoreCase("hoteles")) {
            res = servicio.getHoteles();
        } else {
            res = servicio.getRestaurantes();
        }

        res.enqueue(new Callback<List<ItemLugar>>() {
            @Override
            public void onResponse(Call<List<ItemLugar>> call, Response<List<ItemLugar>> response) {


                    if (fragActivo == 1) addMarcadores(response.body());
                    else cargaDatosRuta(response.body());

            }

            @Override
            public void onFailure(Call<List<ItemLugar>> call, Throwable t) {

            }
        });
    }

    public void addMarcadores(List<ItemLugar> lugaress) {
        LatLngBounds.Builder build=new LatLngBounds.Builder();

        if (lugaress!=null) {
            for (int i = 0; i < lugaress.size(); i++) {
                ItemLugar lugar = lugaress.get(i);

                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .title(lugar.getNombre()).position(new LatLng(lugar.getLatitud(), lugar.getLongitud())));

                build.include(new LatLng(lugar.getLatitud(), lugar.getLongitud()));

            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(build.build(),1));
        }else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }




    }

    public void cargaDatosRuta(List<ItemLugar> lugares) {
            ItemLugar lugar=lugares.get(itemPrecionado);
            LatLngBounds.Builder builders=new LatLngBounds.Builder();
            miPosicion();
            destino=new LatLng(lugar.getLatitud(),lugar.getLongitud());

            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Mi pocision")
            .position(origen));

            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(lugar.getNombre())
            .position(destino));

            //aqui

            builders.include(origen);
            builders.include(destino);

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builders.build(),0));

            creaRuta();




    }

    public void miPosicion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location miLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(miLocation!=null) origen=new LatLng(miLocation.getLatitude(),miLocation.getLongitude());
        else Toast.makeText(this, "Sin conexion para la posicion", Toast.LENGTH_SHORT).show();
    }
    public void creaRuta(){
        String oring=origen.latitude+","+origen.longitude;
        String desti=destino.latitude+","+destino.longitude;

        Retrofit retrofit=new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url_rutas))
                .addConverterFactory(GsonConverterFactory.create()).build();

        ServicioWeb servicio=retrofit.create(ServicioWeb.class);

        Call<ResponseBody> res=servicio.getRutas(oring,desti);

        res.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JsonObject json=null;

                try {
                    json=new Gson().fromJson(response.body().string(), JsonElement.class).getAsJsonObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String points=json.get("routes").getAsJsonArray().get(0).getAsJsonObject()
                        .get("overview_polyline").getAsJsonObject()
                        .get("points").getAsString();

                mMap.addPolyline(new PolylineOptions().color(Color.BLACK).addAll(PolyUtil.decode(points)));

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
