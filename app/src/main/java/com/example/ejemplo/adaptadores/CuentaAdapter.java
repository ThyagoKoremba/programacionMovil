package com.example.ejemplo.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplo.R;
import com.example.ejemplo.actividades.DetalleCuentaActivity;
import com.example.ejemplo.modelos.Cuenta;

import java.util.List;

public class CuentaAdapter extends RecyclerView.Adapter<CuentaAdapter.ViewHolder> {

    private List<Cuenta> listaCuentas;
    private Context context;

    public CuentaAdapter(List<Cuenta> listaCuentas, Context context) {
        this.listaCuentas = listaCuentas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cuenta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cuenta cuenta = listaCuentas.get(position);

        holder.txtDescripcion.setText(cuenta.getDescripcion());
        holder.txtTotal.setText("Total: $" + cuenta.getTotalPrecio());
        holder.txtFaltante.setText("Restante: $" + cuenta.getMontoRestante());
        holder.txtCuotas.setText("Cuotas: " + cuenta.getCantidadPagadas() + " / " + cuenta.getCantidadCuotas());

        // 📌 Click → Abrir DetalleCuentaActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleCuentaActivity.class);

            // 🔥 Ahora enviamos también el ID real de la cuenta
            intent.putExtra("id_cuenta", cuenta.getId());
            intent.putExtra("descripcion", cuenta.getDescripcion());
            intent.putExtra("total", cuenta.getTotalPrecio());
            intent.putExtra("cuotas", cuenta.getCantidadCuotas());
            intent.putExtra("pagadas", cuenta.getCantidadPagadas());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaCuentas.size();
    }

    // --------------------------------------------------
    // 🔥 ESTE ES EL VIEWHOLDER QUE TE FALTABA
    // --------------------------------------------------
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDescripcion, txtTotal, txtCuotas, txtFaltante;

        public ViewHolder(View itemView) {
            super(itemView);

            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtCuotas = itemView.findViewById(R.id.txtCuotas);
            txtFaltante = itemView.findViewById(R.id.txtFaltante);
        }
    }
}
