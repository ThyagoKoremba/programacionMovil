package com.example.ejemplo.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ejemplo.R;
import com.example.ejemplo.database.DBHelper;
import com.example.ejemplo.modelos.User;

public class MainActivity extends AppCompatActivity {

    private EditText input_usuario, input_contrasena;
    private Button btn_ingresar;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        input_usuario = findViewById(R.id.main__input_usuario);
        input_contrasena = findViewById(R.id.main__input_contrasena);
        btn_ingresar = findViewById(R.id.main__button_ingresar);
        dbHelper = new DBHelper(MainActivity.this);

        // Crear usuario admin sólo si no existe (para pruebas)
        User admin = dbHelper.comprobarUsuarioLocal("admin", "admin");
        if (admin == null) {
            dbHelper.addUser(new User(0, "admin", "admin"));
        }

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = input_usuario.getText().toString().trim();
                String contrasena = input_contrasena.getText().toString().trim();

                if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                User usuarioIngresado = dbHelper.comprobarUsuarioLocal(nombreUsuario, contrasena);

                if (usuarioIngresado != null) {
                    Intent intent = new Intent(MainActivity.this, Principal.class);
                    intent.putExtra("usuario_id", (int) usuarioIngresado.getId());
                    intent.putExtra("usuario_nombre", usuarioIngresado.getNombreUsuario());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
