/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import algoritmos.SelectionSort;
import algoritmos.MergeSort;
import algoritmos.InsertionSort;
import algoritmos.HeapSort;
import algoritmos.BubbleSort;
import algoritmos.QuickSort;
import algoritmos.Algoritmo;
import modelos.EscenarioDatos;
import modelos.GeneradorDatos;
import modelos.ResultadoOrdenamiento;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Pantalla principal de la aplicación: aquí el usuario configura el
 * tamaño del arreglo, el escenario de datos y que algoritmos quiere
 * comparar. 
 * 
 * @author mariana y jorge
 */
public class VentanaPrincipal extends JFrame {

    // Paleta de colores 
    private static final Color COLOR_FONDO = new Color(0xF7, 0xE3, 0xA1);
    private static final Color COLOR_PANEL_CLARO = new Color(0xFF, 0xFC, 0xF5);
    private static final Color COLOR_PANEL_AZUL = new Color(0xC9, 0xDF, 0xE9);
    private static final Color COLOR_TEXTO_OSCURO = new Color(0x3D, 0x28, 0x17);
    private static final Color COLOR_COMBO_OSCURO = new Color(0x24, 0x24, 0x33);

    // Borde tipo "tarjeta": la misma linea azul de acento que ya se usa en
    // botones y encabezados, para que ambos paneles (parametros y algoritmos)
    // se vean como tarjetas consistentes entre si.
    private static final Border BORDE_TARJETA = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PANEL_AZUL, 2),
            new EmptyBorder(20, 20, 20, 20));

    private static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 30);
    private static final Font FUENTE_SUBTITULO = new Font("SansSerif", Font.BOLD, 16);
    private static final Font FUENTE_NORMAL = new Font("SansSerif", Font.PLAIN, 14);

    // Limites del tamaño del arreglo que pide el proyecto
    private static final int TAMANO_MINIMO = 10;
    private static final int TAMANO_MAXIMO = 10000;

    private JSlider sliderTamano;
    private JLabel labelTamanoValor;
    private JComboBox<EscenarioDatos> comboEscenario;

    private JCheckBox checkBubble, checkSelection, checkInsertion, checkMerge, checkQuick, checkHeap;
    private JButton botonEjecutar;

    public VentanaPrincipal() {
        super("Algoritmos de Ordenamiento - Proyecto 2");
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 620);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout(20, 20));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(25, 30, 25, 30));
    }

    private void construirInterfaz() {
        add(construirTitulo(), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirBotonEjecutar(), BorderLayout.SOUTH);
    }

    private JLabel construirTitulo() {
        JLabel titulo = new JLabel("ALGORITMOS DE ORDENAMIENTO", SwingConstants.CENTER);
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO_OSCURO);
        titulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        return titulo;
    }

    private JPanel construirPanelCentral() {
        // GridBagLayout en vez de GridLayout(1,2) para poder darle mas ancho
        // al panel de algoritmos (58%) que al de parametros (42%), ya que
        // tiene 6 opciones que necesitan mas espacio para respirar.
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.weightx = 0.42;
        gbc.insets = new Insets(0, 0, 0, 12);
        panel.add(construirPanelParametros(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.58;
        gbc.insets = new Insets(0, 12, 0, 0);
        panel.add(construirPanelAlgoritmos(), gbc);

        return panel;
    }

    private JPanel construirPanelParametros() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_PANEL_CLARO);
        panel.setBorder(BORDE_TARJETA);

        JLabel titulo = new JLabel("PARAMETROS");
        titulo.setFont(FUENTE_SUBTITULO);
        titulo.setForeground(COLOR_TEXTO_OSCURO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Fila "TAMAÑO DEL ARREGLO:  3,657" en un solo renglon (etiqueta a la
        // izquierda, valor a la derecha) usando BorderLayout. Antes el valor
        // era una JLabel aparte centrada con BoxLayout, lo que hacia que su
        // posicion "brincara" horizontalmente segun cuantos digitos tuviera
        // el numero (ej. "10" vs "10,000"); con WEST/EAST fijo el valor
        // siempre queda pegado al borde derecho, sin importar su ancho.
        JPanel filaTamano = new JPanel(new BorderLayout());
        filaTamano.setOpaque(false);
        filaTamano.setAlignmentX(Component.LEFT_ALIGNMENT);
        // OJO: BorderLayout reporta maximumLayoutSize sin limite, por lo que
        // SIEMPRE hay que fijarle un maximumSize explicito aqui o BoxLayout
        // lo estira verticalmente. La altura debe alcanzar para el contenido
        // (~21px) MAS el padding inferior (8px); el espacio de arriba se da
        // por separado con un vertical strut para no reducir el area util.
        filaTamano.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        filaTamano.setBorder(new EmptyBorder(0, 0, 8, 0));

        JLabel labelTamano = new JLabel("TAMAÑO DEL ARREGLO:");
        labelTamano.setFont(FUENTE_NORMAL);
        labelTamano.setForeground(COLOR_TEXTO_OSCURO);

        labelTamanoValor = new JLabel(String.valueOf(TAMANO_MINIMO));
        labelTamanoValor.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelTamanoValor.setForeground(COLOR_TEXTO_OSCURO);
        labelTamanoValor.setHorizontalAlignment(SwingConstants.RIGHT);

        filaTamano.add(labelTamano, BorderLayout.WEST);
        filaTamano.add(labelTamanoValor, BorderLayout.EAST);

        // Empieza en el valor mínimo (10) en vez de a la mitad del rango
        sliderTamano = new JSlider(TAMANO_MINIMO, TAMANO_MAXIMO, TAMANO_MINIMO);
        sliderTamano.setOpaque(false);
        sliderTamano.setAlignmentX(Component.LEFT_ALIGNMENT);
        sliderTamano.addChangeListener(e ->
                labelTamanoValor.setText(String.format("%,d", sliderTamano.getValue())));

        // Etiquetas solo en los extremos: 10 y 10,000.
        // No usamos setMajorTickSpacing aqui porque con un rango de
        // 10 a 10,000 generaria demasiadas marcas y se veria saturado;
        // por eso se arma una tabla de etiquetas a la medida.
        JLabel etiquetaMinimo = new JLabel(String.valueOf(TAMANO_MINIMO));
        etiquetaMinimo.setFont(FUENTE_NORMAL);
        etiquetaMinimo.setForeground(COLOR_TEXTO_OSCURO);

        JLabel etiquetaMaximo = new JLabel(String.format("%,d", TAMANO_MAXIMO));
        etiquetaMaximo.setFont(FUENTE_NORMAL);
        etiquetaMaximo.setForeground(COLOR_TEXTO_OSCURO);

        Hashtable<Integer, JLabel> tablaEtiquetas = new Hashtable<>();
        tablaEtiquetas.put(TAMANO_MINIMO, etiquetaMinimo);
        tablaEtiquetas.put(TAMANO_MAXIMO, etiquetaMaximo);

        sliderTamano.setLabelTable(tablaEtiquetas);
        sliderTamano.setPaintLabels(true);

        JLabel labelEscenario = new JLabel("ESCENARIO DE DATOS:");
        labelEscenario.setFont(FUENTE_NORMAL);
        labelEscenario.setForeground(COLOR_TEXTO_OSCURO);
        labelEscenario.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelEscenario.setBorder(new EmptyBorder(25, 0, 5, 0));

        comboEscenario = new JComboBox<>(EscenarioDatos.values());
        comboEscenario.setSelectedItem(EscenarioDatos.ALEATORIO);
        comboEscenario.setBackground(COLOR_COMBO_OSCURO);
        comboEscenario.setForeground(Color.WHITE);
        comboEscenario.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboEscenario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(25));
        panel.add(filaTamano);
        panel.add(sliderTamano);
        panel.add(labelEscenario);
        panel.add(comboEscenario);

        return panel;
    }

    private JPanel construirPanelAlgoritmos() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 15));
        // Mismo fondo y borde que el panel de PARAMETROS, para que las dos
        // tarjetas se vean como parte de un mismo conjunto (antes este panel
        // quedaba "hueco", dejando ver el fondo crema de la ventana detras
        // de los checkboxes en vez de una tarjeta propia).
        contenedor.setBackground(COLOR_PANEL_CLARO);
        contenedor.setBorder(BORDE_TARJETA);

        JLabel titulo = new JLabel("ALGORITMOS A EVALUAR:", SwingConstants.CENTER);
        titulo.setFont(FUENTE_SUBTITULO);
        titulo.setForeground(COLOR_TEXTO_OSCURO);
        titulo.setOpaque(true);
        titulo.setBackground(COLOR_PANEL_AZUL);
        titulo.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel panelChecks = new JPanel(new GridLayout(3, 2, 18, 20));
        panelChecks.setOpaque(false);

        checkBubble = crearCheckBox("Bubble Sort");
        checkInsertion = crearCheckBox("Insertion Sort");
        checkQuick = crearCheckBox("Quick Sort");
        checkSelection = crearCheckBox("Selection Sort");
        checkMerge = crearCheckBox("Merge Sort");
        checkHeap = crearCheckBox("Heap Sort");

        panelChecks.add(checkBubble);
        panelChecks.add(checkInsertion);
        panelChecks.add(checkQuick);
        panelChecks.add(checkSelection);
        panelChecks.add(checkMerge);
        panelChecks.add(checkHeap);

        contenedor.add(titulo, BorderLayout.NORTH);
        contenedor.add(panelChecks, BorderLayout.CENTER);
        return contenedor;
    }

    private JCheckBox crearCheckBox(String texto) {
        JCheckBox check = new JCheckBox(texto);
        check.setFont(new Font("SansSerif", Font.PLAIN, 16));
        check.setForeground(COLOR_TEXTO_OSCURO);
        check.setOpaque(false);
        check.setFocusPainted(false);
        return check;
    }

    private JButton construirBotonEjecutar() {
        botonEjecutar = new JButton("EJECUTAR COMPARACIÓN");
        botonEjecutar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botonEjecutar.setBackground(COLOR_PANEL_AZUL);
        botonEjecutar.setForeground(COLOR_TEXTO_OSCURO);
        botonEjecutar.setFocusPainted(false);
        botonEjecutar.setPreferredSize(new Dimension(0, 60));
        botonEjecutar.addActionListener(e -> ejecutarComparacion());
        return botonEjecutar;
    }

    /**
     * Valida la seleccion del usuario, genera los datos base y ejecuta
     * los algoritmos seleccionados en un SwingWorker (hilo de fondo)
     * para que la interfaz no se congele con arreglos grandes.
     * Al terminar, abre la ventana de resultados.
     */
    private void ejecutarComparacion() {
        List<Algoritmo> seleccionados = obtenerAlgoritmosSeleccionados();

        if (seleccionados.size() < 2) {
            JOptionPane.showMessageDialog(this,
                    "Debes seleccionar al menos 2 algoritmos para comparar.",
                    "Selección insuficiente",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int tamano = sliderTamano.getValue();
        EscenarioDatos escenario = (EscenarioDatos) comboEscenario.getSelectedItem();
        // Se genera UNA sola vez el arreglo base, cada algoritmo recibe una copia identica para que la comparacion sea justa.
        int[] datosBase = GeneradorDatos.generar(tamano, escenario);

        botonEjecutar.setEnabled(false);
        botonEjecutar.setText("EJECUTANDO...");

        SwingWorker<List<ResultadoOrdenamiento>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ResultadoOrdenamiento> doInBackground() {
                List<ResultadoOrdenamiento> resultados = new ArrayList<>();
                for (Algoritmo algoritmo : seleccionados) {
                    resultados.add(algoritmo.ejecutar(datosBase));
                }
                return resultados;
            }

            @Override
            protected void done() {
                botonEjecutar.setEnabled(true);
                botonEjecutar.setText("EJECUTAR COMPARACIÓN");
                try {
                    List<ResultadoOrdenamiento> resultados = get();
                    VentanaResultados ventana = new VentanaResultados(resultados, tamano, escenario);
                    ventana.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this,
                            "Ocurrió un error al ejecutar la comparación:\n" + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private List<Algoritmo> obtenerAlgoritmosSeleccionados() {
        List<Algoritmo> lista = new ArrayList<>();
        if (checkBubble.isSelected()) lista.add(new BubbleSort());
        if (checkSelection.isSelected()) lista.add(new SelectionSort());
        if (checkInsertion.isSelected()) lista.add(new InsertionSort());
        if (checkMerge.isSelected()) lista.add(new MergeSort());
        if (checkQuick.isSelected()) lista.add(new QuickSort());
        if (checkHeap.isSelected()) lista.add(new HeapSort());
        return lista;
    }
}
