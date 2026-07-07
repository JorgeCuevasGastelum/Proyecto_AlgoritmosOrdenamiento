/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 * Representa los 4 escenarios de datos que pide el proyecto:
 * Ordenados, Casi ordenados, Ordenados en orden inverso, y Totalmente aleatorios.
 *
 * Se usa como tipo del JComboBox en la pantalla de parametros: al llevar
 * su propia etiqueta legible (getEtiqueta).
 * 
 * @author mariana y jorge
 */
public enum EscenarioDatos {

    ORDENADO("Ordenados"),
    CASI_ORDENADO("Casi ordenados"),
    INVERSO("Ordenados en orden inverso"),
    ALEATORIO("Totalmente aleatorios");

    private final String etiqueta;

    EscenarioDatos(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}