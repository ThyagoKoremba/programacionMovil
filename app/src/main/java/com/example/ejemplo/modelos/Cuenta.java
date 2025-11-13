package com.example.ejemplo.modelos;

public class Cuenta {
    private int id;
    private String descripcion;
    private double totalPrecio;
    private int cantidadCuotas;
    private int cantidadPagadas;

    public Cuenta() {}

    public Cuenta(int id, String descripcion, double totalPrecio, int cantidadCuotas, int cantidadPagadas) {
        this.id = id;
        this.descripcion = descripcion;
        this.totalPrecio = totalPrecio;
        this.cantidadCuotas = cantidadCuotas;
        this.cantidadPagadas = cantidadPagadas;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getTotalPrecio() { return totalPrecio; }
    public void setTotalPrecio(double totalPrecio) { this.totalPrecio = totalPrecio; }

    public int getCantidadCuotas() { return cantidadCuotas; }
    public void setCantidadCuotas(int cantidadCuotas) { this.cantidadCuotas = cantidadCuotas; }

    public int getCantidadPagadas() { return cantidadPagadas; }
    public void setCantidadPagadas(int cantidadPagadas) { this.cantidadPagadas = cantidadPagadas; }


    public double getMontoRestante() {
        double cuota = totalPrecio / cantidadCuotas;
        return totalPrecio - (cuota * cantidadPagadas);
    }
}
