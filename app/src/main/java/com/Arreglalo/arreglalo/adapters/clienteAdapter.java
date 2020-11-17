package com.Arreglalo.arreglalo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Arreglalo.arreglalo.R;
import com.Arreglalo.arreglalo.Solicitud;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class clienteAdapter extends RecyclerView.Adapter<clienteAdapter.ViewHolderclient> implements View.OnClickListener {
    private List<Solicitud> solicitudList;
    private RecyclerView recyclerView;
    private Context context;
    private  LayoutInflater inflater;
    private View.OnClickListener listener;
    public clienteAdapter(List<Solicitud> solicituds, Context context){
        this.inflater = LayoutInflater.from(context);
        this.context =context;
        this.solicitudList = solicituds;
    }
    @Override
    public int getItemCount(){
        return solicitudList.size();
    }

    @Override
    public clienteAdapter.ViewHolderclient onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.serviceclient,null);

        view.setOnClickListener(this);

        return new clienteAdapter.ViewHolderclient(view);
    }

    @Override
    public void onBindViewHolder(final clienteAdapter.ViewHolderclient holder, int position) {
        holder.bindData(solicitudList.get(position));
    }




    public void setItems(List<Solicitud> items){
        this.solicitudList= items;
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener !=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderclient extends RecyclerView.ViewHolder{
        TextView tipo,detalles;
        ImageView icono;
        public ViewHolderclient(View view){
            super(view);
            tipo = view.findViewById(R.id.txt_tipo);
            detalles = view.findViewById(R.id.txt_detalles);
            icono = view.findViewById(R.id.icono);


        }
        void bindData(final Solicitud item){
            tipo.setText(item.getService());
            detalles.setText(item.getDetails());
            if (item.isAcepted()&&!item.isComplete()){
                icono.setImageResource(R.mipmap.fixer);
            }else if(item.isComplete()) {
                icono.setImageResource(R.mipmap.comprobar);
                
            }
        }
    }
}
