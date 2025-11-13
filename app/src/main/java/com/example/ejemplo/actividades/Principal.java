package com.example.ejemplo.actividades;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplo.R;
import com.example.ejemplo.adaptadores.CuentaAdapter;
import com.example.ejemplo.database.DBHelper;
import com.example.ejemplo.modelos.Cuenta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Principal extends AppCompatActivity {

    private TextView txtUsuario, txtTotalDeuda;
    private RecyclerView recyclerCuentas;
    private CuentaAdapter cuentaAdapter;
    private DBHelper dbHelper;
    private int usuarioId;
    private String usuarioNombre;
    private FloatingActionButton btnAgregarCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Referencias UI
        txtUsuario = findViewById(R.id.txtUsuario);
        txtTotalDeuda = findViewById(R.id.txtTotalDeuda);
        recyclerCuentas = findViewById(R.id.recyclerCuentas);
        recyclerCuentas.setLayoutManager(new LinearLayoutManager(this));
        btnAgregarCuenta = findViewById(R.id.btnAgregarCuenta);

        dbHelper = new DBHelper(this);

        // Recuperar datos enviados desde MainActivity
        Intent intent = getIntent();
        usuarioId = intent.getIntExtra("usuario_id", -1);
        usuarioNombre = intent.getStringExtra("usuario_nombre");

        // Validación: si algo falló, evitar crash
        if (usuarioId == -1) {
            Toast.makeText(this, "Error al obtener usuario", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (usuarioNombre == null || usuarioNombre.isEmpty()) {
            usuarioNombre = "Usuario";
        }

        txtUsuario.setText("Hola, " + usuarioNombre);

        cargarCuentas();

        btnAgregarCuenta.setOnClickListener(v -> mostrarDialogNuevaCuenta());
    }

    /**
     * Carga las cuentas del usuario desde la base de datos y actualiza la lista
     */
    private void cargarCuentas() {
        List<Cuenta> cuentas = dbHelper.getCuentasByUsuario(usuarioId);
        double totalDeuda = 0;

        for (Cuenta cuenta : cuentas) {
            totalDeuda += cuenta.getMontoRestante();
        }

        txtTotalDeuda.setText("Total pendiente: $" + String.format("%.2f", totalDeuda));
        cuentaAdapter = new CuentaAdapter(cuentas);
        recyclerCuentas.setAdapter(cuentaAdapter);
    }

    /**
     * Muestra un cuadro de diálogo para agregar una nueva cuenta
     */
    private void mostrarDialogNuevaCuenta() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_nueva_cuenta);
        dialog.setCancelable(true);

        EditText inputDescripcion = dialog.findViewById(R.id.inputDescripcion);
        EditText inputTotal = dialog.findViewById(R.id.inputTotal);
        EditText inputCuotas = dialog.findViewById(R.id.inputCuotas);
        Button btnGuardar = dialog.findViewById(R.id.btnGuardarCuenta);

        btnGuardar.setOnClickListener(v -> {
            String descripcion = inputDescripcion.getText().toString().trim();
            String totalStr = inputTotal.getText().toString().trim();
            String cuotasStr = inputCuotas.getText().toString().trim();

            // Validaciones básicas
            if (descripcion.isEmpty() || totalStr.isEmpty() || cuotasStr.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            double total;
            int cuotas;
            try {
                total = Double.parseDouble(totalStr);
                cuotas = Integer.parseInt(cuotasStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Verifica los valores numéricos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cuotas <= 0 || total <= 0) {
                Toast.makeText(this, "Los valores deben ser mayores que cero", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear cuenta nueva y guardar
            Cuenta nuevaCuenta = new Cuenta(0, descripcion, total, cuotas, 0);
            long idCuenta = dbHelper.addCuenta(nuevaCuenta, usuarioId);

            if (idCuenta != -1) {
                Toast.makeText(this, "Cuenta agregada correctamente", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                cargarCuentas();
            } else {
                Toast.makeText(this, "Error al guardar la cuenta", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
