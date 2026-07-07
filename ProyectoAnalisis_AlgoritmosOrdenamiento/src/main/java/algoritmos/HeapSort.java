/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import modelos.ResultadoOrdenamiento;

/**
 * Heap Sort (ordenamiento por monticulo).
 * Complejidad: O(n log n) siempre (mejor, promedio y peor caso).
 * Construye un "max-heap" y luego va extrayendo repetidamente el
 * elemento mas grande, colocandolo al final del arreglo.
 * 
 * @author mariana y jorge
 */
public class HeapSort implements Algoritmo {

    private long comparaciones;
    private long intercambios;

    @Override
    public String getNombre() {
        return "Heap Sort";
    }

    @Override
    public ResultadoOrdenamiento ejecutar(int[] datosOriginales) {
        int[] datos = datosOriginales.clone();
        comparaciones = 0;
        intercambios = 0;
        int n = datos.length;

        long inicio = System.nanoTime();

        // 1. Construir el max-heap inicial
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(datos, n, i);
        }
        // 2. Extraer elementos del heap de uno en uno
        for (int i = n - 1; i > 0; i--) {
            intercambiar(datos, 0, i);   // el mayor (raiz) va al final
            heapify(datos, i, 0);        // se restaura la propiedad de heap
        }

        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        return new ResultadoOrdenamiento(getNombre(), tiempoMs, comparaciones, intercambios);
    }

    /** Garantiza que el subarbol con raiz en "raiz" cumpla la propiedad de max-heap. */
    private void heapify(int[] datos, int n, int raiz) {
        int mayor = raiz;
        int izquierdo = 2 * raiz + 1;
        int derecho = 2 * raiz + 2;

        if (izquierdo < n) {
            comparaciones++;
            if (datos[izquierdo] > datos[mayor]) {
                mayor = izquierdo;
            }
        }
        if (derecho < n) {
            comparaciones++;
            if (datos[derecho] > datos[mayor]) {
                mayor = derecho;
            }
        }
        if (mayor != raiz) {
            intercambiar(datos, raiz, mayor);
            heapify(datos, n, mayor);
        }
    }

    private void intercambiar(int[] datos, int a, int b) {
        int temp = datos[a];
        datos[a] = datos[b];
        datos[b] = temp;
        intercambios++;
    }
}

