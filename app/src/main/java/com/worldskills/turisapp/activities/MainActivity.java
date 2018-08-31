package com.worldskills.turisapp.activities;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.worldskills.turisapp.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int fragActivo, itemPresionado;
    private boolean vista;
    private String categoria;

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menuItem=menu.getItem(R.id.acction_vista);
        actualizaPantalla();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }else if(id==R.id.acction_vista){

            if (vista){
                menuItem.setIcon(R.drawable.icon_lista);
                vista=false;
            }else{
                menuItem.setIcon(R.drawable.icon_grid);
                vista=true;
            }
        }
        iniciaFragment(fragActivo,categoria);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            iniciaFragment(0,"inicio");
        } else if (id == R.id.nav_sitios) {
            iniciaFragment(1,"sitios");

        } else if (id == R.id.nav_hoteles) {
            iniciaFragment(1,"hoteles");

        } else if (id == R.id.nav_restaurantes) {
            iniciaFragment(1,"restaurantes");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*Este metodo recibe dos parametro, el primero sirve para ver que fragment se activo, y el segundo para ver en que categoria se encuentra*/
    public void iniciaFragment(int fragAct, String cate){
        fragActivo=fragAct;
        categoria=cate;

        FragmentManager manager=getFragmentManager();
        FragmentTransaction transicion=manager.beginTransaction();

        Fragment frag=getFrament(fragActivo);

        Bundle datos=new Bundle();
        datos.putInt(FRAG_ACTIVO,fragActivo);
        datos.putString(CATEGORIA,categoria);
        datos.putBoolean(VISTA,vista);

        frag.setArguments(datos);

        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            LinearLayout layoutFrag1=findViewById(R.id.contenedor_fragment_1);

            switch (fragActivo){
                case 0:
                    layoutFrag1.getLayoutParams().width=getResources().getDisplayMetrics().widthPixels;
                    transicion.replace(R.id.contenedor_fragment_1,frag);

                    break;
                case 1:
                    layoutFrag1.getLayoutParams().width=(int)(getResources().getDisplayMetrics().widthPixels/2.5);
                    transicion.replace(R.id.contenedor_fragment_1,frag);
                    itemPresionado=0;
                    iniciaFragment(2,categoria);
                    break;
                case 2:
                    transicion.replace(R.id.contenedor_fragment_2,frag);
                    break;
            }

        }else{
            transicion.replace(R.id.contenedor_fragment_1, frag);
        }




        transicion.commit();


    }
    public void actualizaPantalla(){

    }
    public Fragment getFrament(int frag){
        switch (frag){
            case 0: return new Fragment();
            case 1: return new Fragment();
            default: return new Fragment();
        }
    }
    public void onResume(){
        super.onResume();
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);

        //deeee
        vista=datos.getBoolean(VISTA,false);
        itemPresionado=datos.getInt(ITEM_PRECIONADO,0);
        fragActivo=datos.getInt(FRAG_ACTIVO,0);
        categoria=datos.getString(CATEGORIA,"sitios");

        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE && fragActivo==2){
            iniciaFragment(1,categoria);
        }else{
            iniciaFragment(fragActivo,categoria);
        }


    }

    public static final String VISTA="VISTA";
    public static final String FRAG_ACTIVO="FRAG_ACTI";
    public static final String ITEM_PRECIONADO="ITEM_P";
    public static final String CATEGORIA="CATEGORIA";


    public void onPause(){
        super.onPause();
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor guarda=datos.edit();

        guarda.putBoolean(VISTA,vista);
        guarda.putString(CATEGORIA, categoria);
        guarda.putInt(FRAG_ACTIVO, fragActivo);
        guarda.putInt(ITEM_PRECIONADO, itemPresionado);

        guarda.apply();

    }
}
