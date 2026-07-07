/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import modelos.ResultadoOrdenamiento;

/**
 * Merge Sort (ordenamiento por mezcla).
 * Complejidad: O(n log n) SIEMPRE (mejor, promedio y peor caso), a costa
 * de usar memoria extra para los arreglos temporales de la combinacion.
 * 
 * @author mariana y jorge
 */
public class MergeSort implements Algoritmo {

    private long comparaciones;
    private long intercambios;

    @Override
    public String getNombre() {
        return "Merge Sort";
    }

    @Override
    public ResultadoOrdenamiento ejecutar(int[] datosOriginales) {
        int[] datos = datosOriginales.clone();
        comparaciones = 0;
        intercambios = 0;

        long inicio = System.nanoTime();
        if (datos.length > 1) {
            mergeSort(datos, 0, datos.length - 1);
        }
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        return new ResultadoOrdenamiento(getNombre(), tiempoMs, comparaciones, intercambios);
    }

    /** Divide el arreglo en dos mitades, ordena cada una y las combina. */
    private void mergeSort(int[] datos, int izquierda, int derecha) {
        if (izquierda >= derecha) {
            return;
        }
        int medio = izquierda + (derecha - izquierda) / 2;
        mergeSort(datos, izquierda, medio);
        mergeSort(datos, medio + 1, derecha);
        combinar(datos, izquierda, medio, derecha);
    }

    /** Combina (merge) dos sub-arreglos ya ordenados en uno solo ordenado. */
    private void combinar(int[] datos, int izquierda, int medio, int derecha) {
        int[] temporal = new int[derecha - izquierda + 1];
        int i = izquierda;
        int j = medio + 1;
        int k = 0;

        while (i <= medio && j <= derecha) {
            comparaciones++;
            if (datos[i] <= datos[j]) {
                temporal[k++] = datos[i++];
            } else {
                temporal[k++] = datos[j++];
            }
            intercambios++; // cada colocacion en el arreglo temporal cuenta como un "movimiento"
        }
        while (i <= medio) {
            temporal[k++] = datos[i++];
            intercambios++;
        }
        while (j <= derecha) {
            temporal[k++] = datos[j++];
            intercambios++;
        }

        System.arraycopy(temporal, 0, datos, izquierda, temporal.length);
    }
}

