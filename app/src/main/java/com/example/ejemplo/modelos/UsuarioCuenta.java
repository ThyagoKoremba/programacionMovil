package com.example.ejemplo.modelos;

public class UsuarioCuenta {
    private int id;
    private int usuarioId;
    private int cuentaId;

    public UsuarioCuenta() {}

    public UsuarioCuenta(int id, int usuarioId, int cuentaId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cuentaId = cuentaId;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getCuentaId() { return cuentaId; }
    public void setCuentaId(int cuentaId) { this.cuentaId = cuentaId; }
}
