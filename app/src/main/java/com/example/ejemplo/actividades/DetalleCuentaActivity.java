package com.example.ejemplo.actividades;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejemplo.R;

public class DetalleCuentaActivity extends AppCompatActivity {

    private TextView txtDescripcion, txtTotal, txtCuotas, txtPagadas, txtRestante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cuenta);

        txtDescripcion = findViewById(R.id.detalleDescripcion);
        txtTotal = findViewById(R.id.detalleTotal);
        txtCuotas = findViewById(R.id.detalleCuotas);
        txtPagadas = findViewById(R.id.detallePagadas);
        txtRestante = findViewById(R.id.detalleRestante);

        // Obtener datos enviados desde Principal
        String descripcion = getIntent().getStringExtra("descripcion");
        double total = getIntent().getDoubleExtra("total", 0);
        int cuotas = getIntent().getIntExtra("cuotas", 0);
        int pagadas = getIntent().getIntExtra("pagadas", 0);

        double cuota = (cuotas > 0) ? total / cuotas : 0;
        double restante = total - cuota * pagadas;

        // Mostrar datos
        txtDescripcion.setText(descripcion);
        txtTotal.setText("Total: $" + total);
        txtCuotas.setText("Cuotas: " + cuotas);
        txtPagadas.setText("Pagadas: " + pagadas);
        txtRestante.setText("Restante: $" + String.format("%.2f", restante));
    }
}
