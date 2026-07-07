/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import modelos.ResultadoOrdenamiento;

/**
 * Selection Sort (ordenamiento por seleccion).
 * Complejidad: O(n^2) siempre (incluso si el arreglo ya esta ordenado),
 * porque siempre recorre el resto del arreglo buscando el minimo.
 * A diferencia de Bubble Sort, hace muy pocos intercambios (a lo mas n-1).
 */
public class SelectionSort implements Algoritmo {

    @Override
    public String getNombre() {
        return "Selection Sort";
    }

    @Override
    public ResultadoOrdenamiento ejecutar(int[] datosOriginales) {
        int[] datos = datosOriginales.clone();
        long comparaciones = 0;
        long intercambios = 0;
        int n = datos.length;

        long inicio = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                comparaciones++;
                if (datos[j] < datos[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }
            // Solo se intercambia si de verdad se encontro un menor
            if (indiceMinimo != i) {
                int temp = datos[i];
                datos[i] = datos[indiceMinimo];
                datos[indiceMinimo] = temp;
                intercambios++;
            }
        }

        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        return new ResultadoOrdenamiento(getNombre(), tiempoMs, comparaciones, intercambios);
    }
}
