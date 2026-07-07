/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import modelos.ResultadoOrdenamiento;

/**
 * Bubble Sort (ordenamiento burbuja).
 * Complejidad: O(n^2) en el peor y promedio caso, O(n) si ya esta ordenado
 * (gracias a la bandera huboIntercambio que corta el algoritmo antes).
 * 
 * @author mariana y jorge
 */
public class BubbleSort implements Algoritmo {

    @Override
    public String getNombre() {
        return "Bubble Sort";
    }

    @Override
    public ResultadoOrdenamiento ejecutar(int[] datosOriginales) {
        // Trabajamos sobre una copia para no alterar el arreglo original
        // (asi todos los algoritmos comparan sobre los mismos datos de entrada).
        int[] datos = datosOriginales.clone();
        long comparaciones = 0;
        long intercambios = 0;
        int n = datos.length;

        long inicio = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;
            for (int j = 0; j < n - 1 - i; j++) {
                comparaciones++;
                if (datos[j] > datos[j + 1]) {
                    int temp = datos[j];
                    datos[j] = datos[j + 1];
                    datos[j + 1] = temp;
                    intercambios++;
                    huboIntercambio = true;
                }
            }
            // Si en una pasada completa no hubo intercambios, el arreglo
            // ya esta ordenado y no tiene caso seguir iterando.
            if (!huboIntercambio) {
                break;
            }
        }

        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        return new ResultadoOrdenamiento(getNombre(), tiempoMs, comparaciones, intercambios);
    }
}
