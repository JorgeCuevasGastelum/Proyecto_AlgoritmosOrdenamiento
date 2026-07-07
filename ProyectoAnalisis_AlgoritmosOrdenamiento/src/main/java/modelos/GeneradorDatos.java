/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.Random;
import static modelos.EscenarioDatos.ALEATORIO;
import static modelos.EscenarioDatos.CASI_ORDENADO;
import static modelos.EscenarioDatos.INVERSO;
import static modelos.EscenarioDatos.ORDENADO;

/**
 * Genera arreglos de enteros de acuerdo al escenario de datos elegido
 * por el usuario (Ordenados, Casi ordenados, Orden inverso o Aleatorios).
 * 
 * @author mariana y jorge
 */
public class GeneradorDatos {

    private static final Random RANDOM = new Random();

    /**
     * Genera un arreglo de tamaño elementos segun el escenario indicado.
     *
     * @param tamano cantidad de elementos (entre 10 y 10,000 segun el proyecto)
     * @param escenario escenario de datos deseado
     * @return arreglo de enteros ya generado (todavia sin ordenar, salvo que el escenario sea ORDENADO)
     */
    public static int[] generar(int tamano, EscenarioDatos escenario) {
        switch (escenario) {
            case ORDENADO:
                return generarOrdenado(tamano);
            case CASI_ORDENADO:
                return generarCasiOrdenado(tamano);
            case INVERSO:
                return generarInverso(tamano);
            case ALEATORIO:
            default:
                return generarAleatorio(tamano);
        }
    }

    /** Arreglo 0,1,2,3,...,tamano-1 (perfectamente ordenado ascendente). */
    private static int[] generarOrdenado(int tamano) {
        int[] datos = new int[tamano];
        for (int i = 0; i < tamano; i++) {
            datos[i] = i;
        }
        return datos;
    }

    /** Arreglo ordenado descendente: el peor caso para varios algoritmos. */
    private static int[] generarInverso(int tamano) {
        int[] datos = new int[tamano];
        for (int i = 0; i < tamano; i++) {
            datos[i] = tamano - i;
        }
        return datos;
    }

    /** Arreglo totalmente aleatorio, con valores entre 0 y tamano*10. */
    private static int[] generarAleatorio(int tamano) {
        int[] datos = new int[tamano];
        for (int i = 0; i < tamano; i++) {
            datos[i] = RANDOM.nextInt(tamano * 10 + 1);
        }
        return datos;
    }

    /**
     * Parte de un arreglo ordenado y le aplica un pequeño porcentaje
     * (~5%) de intercambios aleatorios, simulando datos "casi ordenados".
     */
    private static int[] generarCasiOrdenado(int tamano) {
        int[] datos = generarOrdenado(tamano);
        int cantidadDeIntercambios = Math.max(1, tamano / 20);

        for (int k = 0; k < cantidadDeIntercambios; k++) {
            int posicionA = RANDOM.nextInt(tamano);
            int posicionB = RANDOM.nextInt(tamano);
            int temp = datos[posicionA];
            datos[posicionA] = datos[posicionB];
            datos[posicionB] = temp;
        }
        return datos;
    }
}
