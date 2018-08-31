package com.worldskills.turisapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.worldskills.turisapp.R;
import com.worldskills.turisapp.modelos.ItemLugar;

import java.util.List;

public class AdapterLugares extends BaseAdapter {

    private Context context;
    private int layoutItem;
    private List<ItemLugar> lugares;

    public AdapterLugares(Context context, int layoutItem, List<ItemLugar> lugares) {
        this.context = context;
        this.layoutItem = layoutItem;
        this.lugares = lugares;
    }

    @Override
    public int getCount() {
        return lugares.size();
    }

    @Override
    public Object getItem(int position) {
        return lugares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        Holder holder=new Holder();


        // Se verifica que la vista sea nula y luego se infla pasandole como parametro la plantilla que va a visualizar el el adapter.
        // Con la vista obtenida se buscan por medio de la clase holder y se instancian los elemtos, luego se instancia el objeto y se le envian los
        // parametros a mostrar.
        if (row==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layoutItem,null);

            holder.imagen=row.findViewById(R.id.item_lugar_imagen);
            holder.nombre=row.findViewById(R.id.item_lugar_nombre);
            holder.descripcion=row.findViewById(R.id.item_lugar_descripcion);
            holder.ubicacion=row.findViewById(R.id.item_lugar_ubicacion);

            row.setTag(holder);

        }else{
            holder=(Holder)row.getTag();
        }

        ItemLugar lugar=lugares.get(position);


        Picasso.get().load(lugar.getUrlImagen()).fit().centerCrop().error(R.drawable.logoapp).into(holder.imagen);
       // Glide.with(context).load(lugar.getUrlImagen()).into(holder.imagen);
        holder.nombre.setText(lugar.getNombre());
        holder.ubicacion.setText(lugar.getUbicacion());
        holder.descripcion.setText(lugar.getDescripcionCorta());

        return row;
    }


    // La creacion de la clase que va a tener los componentes del item para enviarlos e instanciarlos en el adapter.
    class Holder {
        ImageView imagen;
        TextView nombre, ubicacion, descripcion;
    }
}
