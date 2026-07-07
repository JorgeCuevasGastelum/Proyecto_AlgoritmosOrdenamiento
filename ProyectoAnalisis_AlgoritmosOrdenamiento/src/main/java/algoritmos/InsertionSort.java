/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import modelos.ResultadoOrdenamiento;

/**
 * Insertion Sort (ordenamiento por insercion).
 * Complejidad: O(n^2) en el peor caso, pero O(n) si los datos ya vienen
 * "casi ordenados" (por eso suele lucir muy bien en ese escenario).
 */
public class InsertionSort implements Algoritmo {

    @Override
    public String getNombre() {
        return "Insertion Sort";
    }

    @Override
    public ResultadoOrdenamiento ejecutar(int[] datosOriginales) {
        int[] datos = datosOriginales.clone();
        long comparaciones = 0;
        long intercambios = 0;
        int n = datos.length;

        long inicio = System.nanoTime();

        for (int i = 1; i < n; i++) {
            int actual = datos[i];
            int j = i - 1;

            // Recorremos hacia atras "corriendo" los elementos mayores
            // que "actual" una posicion a la derecha, hasta encontrar
            // el lugar donde debe insertarse.
            while (j >= 0) {
                comparaciones++;
                if (datos[j] > actual) {
                    datos[j + 1] = datos[j];
                    intercambios++; // se cuenta como movimiento/desplazamiento
                    j--;
                } else {
                    break;
                }
            }
            datos[j + 1] = actual;
        }

        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        return new ResultadoOrdenamiento(getNombre(), tiempoMs, comparaciones, intercambios);
    }
}
