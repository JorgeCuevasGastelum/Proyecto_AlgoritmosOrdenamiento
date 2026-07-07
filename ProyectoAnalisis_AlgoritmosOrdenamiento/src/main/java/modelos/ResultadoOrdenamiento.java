/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 * Objeto de solo lectura (inmutable) que representa el resultado de haber
 * ejecutado UN algoritmo de ordenamiento sobre UN conjunto de datos.
 *
 * Cada algoritmo (BubbleSort, QuickSort, etc.) crea uno de estos objetos
 * al terminar de ordenar, y la interfaz grafica los usa para llenar la
 * tabla de resultados y la grafica comparativa.
 * 
 * @author mariana y jorge
 */
public class ResultadoOrdenamiento {

    private final String nombreAlgoritmo;
    private final double tiempoMs;
    private final long comparaciones;
    private final long intercambios;

    public ResultadoOrdenamiento(String nombreAlgoritmo, double tiempoMs,
                                  long comparaciones, long intercambios) {
        this.nombreAlgoritmo = nombreAlgoritmo;
        this.tiempoMs = tiempoMs;
        this.comparaciones = comparaciones;
        this.intercambios = intercambios;
    }

    public String getNombreAlgoritmo() {
        return nombreAlgoritmo;
    }

    /** Tiempo de ejecucion en milisegundos (con decimales, medido con System.nanoTime). */
    public double getTiempoMs() {
        return tiempoMs;
    }

    public long getComparaciones() {
        return comparaciones;
    }

    public long getIntercambios() {
        return intercambios;
    }

    @Override
    public String toString() {
        return String.format("%s -> tiempo=%.4f ms, comparaciones=%d, intercambios=%d",
                nombreAlgoritmo, tiempoMs, comparaciones, intercambios);
    }
}

