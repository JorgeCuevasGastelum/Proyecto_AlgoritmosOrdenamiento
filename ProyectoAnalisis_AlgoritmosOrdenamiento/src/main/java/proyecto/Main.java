/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package proyecto;

import interfaces.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicacion.
 * Toda la interfaz Swing debe crearse y manipularse dentro del
 * "Event Dispatch Thread" (EDT); por eso se envuelve en invokeLater.
 * 
 * @author mariana y jorge
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
