package interfaces;

import modelos.EscenarioDatos;
import modelos.ResultadoOrdenamiento;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Pantalla de resultados: muestra la tabla comparativa (tiempo, comparaciones e
 * intercambios de cada algoritmo elegido) y, al lado, una grafica de barras con
 * JFreeChart donde se resalta el algoritmo mas rapido (menor tiempo de ejecucion).
 *
 * IMPORTANTE: la paleta de colores es la MISMA que en VentanaPrincipal, para que
 * ambas pantallas se sientan parte de una sola aplicacion (fondo crema, paneles
 * color hueso, azul claro como acento/resaltado, texto cafe oscuro).
 */
public class VentanaResultados extends JFrame {

    // ---- Misma paleta que VentanaPrincipal ----
    private static final Color COLOR_FONDO = new Color(0xF7, 0xE3, 0xA1);        // crema (fondo general)
    private static final Color COLOR_PANEL_CLARO = new Color(0xFF, 0xFC, 0xF5);  // hueso (paneles/tarjetas)
    private static final Color COLOR_PANEL_AZUL = new Color(0xC9, 0xDF, 0xE9);   // azul claro (acento/resaltado)
    private static final Color COLOR_TEXTO_OSCURO = new Color(0x3D, 0x28, 0x17); // cafe oscuro (texto principal)

    // Colores derivados de la misma familia, solo para dar variedad sutil
    private static final Color COLOR_ENCABEZADO_TABLA = COLOR_TEXTO_OSCURO;       // encabezado tabla: cafe oscuro
    private static final Color COLOR_BARRA_NORMAL = new Color(0xD8, 0xB9, 0x8F);  // tostado suave (misma familia calida)
    private static final Color COLOR_BARRA_MEJOR = new Color(0x6F, 0xAF, 0xC7);   // azul un poco mas intenso que el acento

    private final List<ResultadoOrdenamiento> resultados;
    private final int tamanoArreglo;
    private final EscenarioDatos escenario;

    public VentanaResultados(List<ResultadoOrdenamiento> resultados, int tamanoArreglo, EscenarioDatos escenario) {
        super("Resultados de la Comparación");
        this.resultados = resultados;
        this.tamanoArreglo = tamanoArreglo;
        this.escenario = escenario;
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new GridLayout(1, 2, 20, 0));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
    }

    private void construirInterfaz() {
        add(construirPanelTabla());
        add(construirPanelGrafica());
    }

    /** Devuelve el algoritmo con menor tiempo de ejecucion entre los resultados. */
    private ResultadoOrdenamiento obtenerMejor() {
        ResultadoOrdenamiento mejor = resultados.get(0);
        for (ResultadoOrdenamiento r : resultados) {
            if (r.getTiempoMs() < mejor.getTiempoMs()) {
                mejor = r;
            }
        }
        return mejor;
    }

    private JPanel construirPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBackground(COLOR_PANEL_CLARO);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Tabla de Resultados");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(COLOR_TEXTO_OSCURO);

        String[] columnas = {"Algoritmo", "Tiempo (ms)", "Comparaciones", "Intercambios"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // la tabla es solo de lectura
            }
        };

        DecimalFormat formatoTiempo = new DecimalFormat("0.0000");
        ResultadoOrdenamiento mejor = obtenerMejor();

        for (ResultadoOrdenamiento r : resultados) {
            modelo.addRow(new Object[]{
                    r.getNombreAlgoritmo(),
                    formatoTiempo.format(r.getTiempoMs()),
                    r.getComparaciones(),
                    r.getIntercambios()
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(32);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setForeground(COLOR_TEXTO_OSCURO);
        tabla.setBackground(COLOR_PANEL_CLARO);
        tabla.setGridColor(COLOR_FONDO);
        tabla.setRowSelectionAllowed(false);
        tabla.getTableHeader().setBackground(COLOR_ENCABEZADO_TABLA);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Renderer personalizado: la fila del ganador se resalta con el mismo
        // azul claro que ya se usa como color de acento en VentanaPrincipal.
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object valor, boolean seleccionado,
                                                             boolean foco, int fila, int columna) {
                Component c = super.getTableCellRendererComponent(t, valor, seleccionado, foco, fila, columna);
                String nombreFila = String.valueOf(t.getValueAt(fila, 0));
                boolean esMejor = nombreFila.equals(mejor.getNombreAlgoritmo());
                c.setBackground(esMejor ? COLOR_PANEL_AZUL : COLOR_PANEL_CLARO);
                c.setForeground(COLOR_TEXTO_OSCURO);
                setHorizontalAlignment(columna == 0 ? SwingConstants.LEFT : SwingConstants.CENTER);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(COLOR_PANEL_CLARO);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_FONDO, 2));

        JLabel labelMejor = new JLabel("El mejor algoritmo bajo este escenario fue: "
                + mejor.getNombreAlgoritmo());
        labelMejor.setOpaque(true);
        labelMejor.setBackground(COLOR_PANEL_AZUL);
        labelMejor.setForeground(COLOR_TEXTO_OSCURO);
        labelMejor.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelMejor.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel labelContexto = new JLabel(String.format(
                "Tamaño del arreglo: %d   |   Escenario: %s", tamanoArreglo, escenario.getEtiqueta()));
        labelContexto.setForeground(COLOR_TEXTO_OSCURO);
        labelContexto.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel panelSur = new JPanel(new BorderLayout(5, 8));
        panelSur.setOpaque(false);
        panelSur.add(labelContexto, BorderLayout.NORTH);
        panelSur.add(labelMejor, BorderLayout.SOUTH);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel construirPanelGrafica() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_PANEL_CLARO);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        ResultadoOrdenamiento mejor = obtenerMejor();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ResultadoOrdenamiento r : resultados) {
            // Se grafica en segundos (tiempoMs / 1000) para que el eje sea legible
            dataset.addValue(r.getTiempoMs() / 1000.0, "Tiempo", r.getNombreAlgoritmo());
        }

        JFreeChart grafica = ChartFactory.createBarChart(
                "Comparativa de Rendimiento",
                null,
                "Tiempo en segundos",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        // Fondo de la grafica y del titulo, en la misma familia de colores del panel
        grafica.setBackgroundPaint(COLOR_PANEL_CLARO);
        grafica.getTitle().setPaint(COLOR_TEXTO_OSCURO);

        CategoryPlot plot = grafica.getCategoryPlot();
        plot.setBackgroundPaint(COLOR_PANEL_CLARO);
        plot.setRangeGridlinePaint(COLOR_FONDO);
        plot.getDomainAxis().setTickLabelPaint(COLOR_TEXTO_OSCURO);
        plot.getRangeAxis().setTickLabelPaint(COLOR_TEXTO_OSCURO);
        plot.getRangeAxis().setLabelPaint(COLOR_TEXTO_OSCURO);

        // Renderer personalizado: pinta con el azul de acento SOLO la barra del
        // algoritmo ganador; el resto usa un tostado calido de la misma paleta.
        BarRenderer renderer = new BarRenderer() {
            @Override
            public Paint getItemPaint(int fila, int columna) {
                String nombreCategoria = (String) dataset.getColumnKey(columna);
                return nombreCategoria.equals(mejor.getNombreAlgoritmo())
                        ? COLOR_BARRA_MEJOR
                        : COLOR_BARRA_NORMAL;
            }
        };
        renderer.setShadowVisible(false);
        renderer.setBarPainter(new StandardBarPainter());
        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(grafica);
        chartPanel.setBackground(COLOR_PANEL_CLARO);
        chartPanel.setPreferredSize(new Dimension(500, 500));

        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }
}
