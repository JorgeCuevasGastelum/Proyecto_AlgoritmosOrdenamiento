/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import modelos.ResultadoOrdenamiento;

/**
 * Quick Sort (ordenamiento rapido), usando particion de Lomuto con el
 * ultimo elemento como pivote.
 * Complejidad: O(n log n) en promedio, O(n^2) en el peor caso (por
 * ejemplo, cuando el arreglo ya viene ordenado y siempre se elige el
 * ultimo elemento como pivote).
 * 
 * @author mariana y jorge
 */
public class QuickSort implements Algoritmo {

    private long comparaciones;
    private long intercambios;

    @Override
    public String getNombre() {
        return "Quick Sort";
    }

    @Override
    public ResultadoOrdenamiento ejecutar(int[] datosOriginales) {
        int[] datos = datosOriginales.clone();
        comparaciones = 0;
        intercambios = 0;

        long inicio = System.nanoTime();
        if (datos.length > 1) {
            quickSort(datos, 0, datos.length - 1);
        }
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        return new ResultadoOrdenamiento(getNombre(), tiempoMs, comparaciones, intercambios);
    }

    private void quickSort(int[] datos, int inicio, int fin) {
        if (inicio < fin) {
            int posicionPivote = particionar(datos, inicio, fin);
            quickSort(datos, inicio, posicionPivote - 1);
            quickSort(datos, posicionPivote + 1, fin);
        }
    }

    /** Particion de Lomuto: coloca el pivote en su posicion final y devuelve ese indice. */
    private int particionar(int[] datos, int inicio, int fin) {
        int pivote = datos[fin];
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            comparaciones++;
            if (datos[j] <= pivote) {
                i++;
                intercambiar(datos, i, j);
            }
        }
        intercambiar(datos, i + 1, fin);
        return i + 1;
    }

    private void intercambiar(int[] datos, int a, int b) {
        if (a == b) {
            return; // no contamos como intercambio si es la misma posicion
        }
        int temp = datos[a];
        datos[a] = datos[b];
        datos[b] = temp;
        intercambios++;
    }
}
