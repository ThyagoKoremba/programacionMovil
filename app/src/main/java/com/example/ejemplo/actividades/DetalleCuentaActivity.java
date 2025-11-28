package com.example.ejemplo.actividades;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ejemplo.R;
import com.example.ejemplo.database.DBHelper;

public class DetalleCuentaActivity extends AppCompatActivity {

    private TextView txtDescripcion, txtTotal, txtCuotas, txtPagadas, txtRestante;
    private Button btnEliminar;
    private int idCuenta; // 🔥 ID DE LA CUENTA QUE VIENE DEL ADAPTER

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cuenta);

        // Referencias
        txtDescripcion = findViewById(R.id.detalleDescripcion);
        txtTotal = findViewById(R.id.detalleTotal);
        txtCuotas = findViewById(R.id.detalleCuotas);
        txtPagadas = findViewById(R.id.detallePagadas);
        txtRestante = findViewById(R.id.detalleRestante);
        btnEliminar = findViewById(R.id.btnEliminarCuenta);

        // Obtener datos enviados desde el Adapter
        idCuenta = getIntent().getIntExtra("id_cuenta", -1);
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

        // 🔥 Acción eliminar
        btnEliminar.setOnClickListener(v -> eliminarCuenta());
    }

    // -------------------------------
    //  MÉTODO PARA ELIMINAR LA CUENTA
    // -------------------------------
    private void eliminarCuenta() {

        if (idCuenta == -1) {
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Eliminar cuenta")
                .setMessage("¿Seguro que querés eliminar esta cuenta?")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> {

                    DBHelper db = new DBHelper(this);
                    db.eliminarCuenta(idCuenta);

                    finish(); // volver a pantalla anterior
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
