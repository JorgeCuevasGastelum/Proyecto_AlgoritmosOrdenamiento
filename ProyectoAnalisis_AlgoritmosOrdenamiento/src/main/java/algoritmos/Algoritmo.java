/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package algoritmos;

import modelos.ResultadoOrdenamiento;

/**
 * Contrato comun que deben cumplir todos los algoritmos de ordenamiento
 * del proyecto (Bubble, Selection, Insertion, Merge, Quick y Heap Sort).
 *
 * Gracias a esta interfaz, la interfaz grafica (VentanaPrincipal) puede
 * tratar a todos los algoritmos de la misma forma sin importar cual sea:
 * los guarda en una lista de tipo Algoritmo y llama a ejecutar(...) sobre
 * cada uno. Si en el futuro se agrega un algoritmo nuevo (por ejemplo,
 * Shell Sort), solo se necesita crear una clase que implemente esta
 * interfaz; no hay que tocar la interfaz grafica.
 */
public interface Algoritmo {

    /**
     * Ejecuta el algoritmo de ordenamiento sobre una COPIA del arreglo
     * recibido (el arreglo original no debe modificarse, para que el
     * mismo conjunto de datos se pueda usar de forma justa con todos los
     * algoritmos que el usuario seleccione).
     *
     * @param datosOriginales arreglo de datos a ordenar (no se modifica)
     * @return un ResultadoOrdenamiento con el tiempo de ejecucion,
     *         el numero de comparaciones y el numero de intercambios
     */
    ResultadoOrdenamiento ejecutar(int[] datosOriginales);

    /**
     * @return el nombre legible del algoritmo (por ejemplo "Bubble Sort"),
     *         usado para mostrarlo en la tabla y en la grafica de resultados.
     */
    String getNombre();
}
