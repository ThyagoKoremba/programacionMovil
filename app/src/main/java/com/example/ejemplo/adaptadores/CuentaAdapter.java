package com.example.ejemplo.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplo.modelos.Cuenta;
import com.example.ejemplo.R;


import java.util.List;

public class CuentaAdapter extends RecyclerView.Adapter<CuentaAdapter.ViewHolder> {

    private List<Cuenta> cuentas;

    public CuentaAdapter(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cuenta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cuenta cuenta = cuentas.get(position);
        holder.txtDescripcion.setText(cuenta.getDescripcion());
        holder.txtTotal.setText("Total: $" + cuenta.getTotalPrecio());
        holder.txtCuotas.setText(cuenta.getCantidadPagadas() + " de " + cuenta.getCantidadCuotas());
        holder.txtFaltante.setText("Faltan: $" + String.format("%.2f", cuenta.getMontoRestante()));
    }

    @Override
    public int getItemCount() {
        return cuentas.size();
    }

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
