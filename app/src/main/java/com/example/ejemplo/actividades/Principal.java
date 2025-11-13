package com.example.ejemplo.actividades;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        txtUsuario = findViewById(R.id.txtUsuario);
        txtTotalDeuda = findViewById(R.id.txtTotalDeuda);
        recyclerCuentas = findViewById(R.id.recyclerCuentas);
        recyclerCuentas.setLayoutManager(new LinearLayoutManager(this));
        btnAgregarCuenta = findViewById(R.id.btnAgregarCuenta);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        usuarioId = intent.getIntExtra("usuario_id", -1);
        usuarioNombre = intent.getStringExtra("usuario_nombre");

        txtUsuario.setText("Hola, " + usuarioNombre);

        cargarCuentas();

        btnAgregarCuenta.setOnClickListener(v -> mostrarDialogNuevaCuenta());
    }

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

    private void mostrarDialogNuevaCuenta() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_nueva_cuenta);

        EditText inputDescripcion = dialog.findViewById(R.id.inputDescripcion);
        EditText inputTotal = dialog.findViewById(R.id.inputTotal);
        EditText inputCuotas = dialog.findViewById(R.id.inputCuotas);
        Button btnGuardar = dialog.findViewById(R.id.btnGuardarCuenta);

        btnGuardar.setOnClickListener(v -> {
            String descripcion = inputDescripcion.getText().toString().trim();
            String totalStr = inputTotal.getText().toString().trim();
            String cuotasStr = inputCuotas.getText().toString().trim();

            if (descripcion.isEmpty() || totalStr.isEmpty() || cuotasStr.isEmpty()) {
                inputDescripcion.setError("Completa todos los campos");
                return;
            }

            double total = Double.parseDouble(totalStr);
            int cuotas = Integer.parseInt(cuotasStr);

            Cuenta nuevaCuenta = new Cuenta(0, descripcion, total, cuotas, 0);
            dbHelper.addCuenta(nuevaCuenta, usuarioId);

            dialog.dismiss();
            cargarCuentas();
        });

        dialog.show();
    }
}
