import streamlit as st
import pandas as pd
import matplotlib.pyplot as plt
import random

from algoritmos import (bubble_sort, selection_sort, insertion_sort, merge_sort, quick_sort, heap_sort)

st.set_page_config(
    layout="wide",
    page_title="Algoritmos de Ordenamiento",
    initial_sidebar_state="collapsed"
)

# Cargar estilos externos
with open("estilos.css", encoding="utf-8") as f:
    st.markdown(f"<style>{f.read()}</style>", unsafe_allow_html=True)

COLOR_BUTTERMILK = "#FFF1B5"
COLOR_PASTEL_BLUE = "#C1DBE8"
COLOR_BURGUNDY = "#43302E"


def generar_datos(tamano, escenario):
    if escenario == "Totalmente aleatorios":
        return [random.randint(1, 10000) for _ in range(tamano)]

    if escenario == "Ordenados":
        return list(range(1, tamano + 1))

    if escenario == "Ordenados en orden inverso":
        return list(range(tamano, 0, -1))

    if escenario == "Casi ordenados":
        arr = list(range(1, tamano + 1))

        for _ in range(int(tamano * 0.1)):
            idx1 = random.randint(0, tamano - 1)
            idx2 = random.randint(0, tamano - 1)
            arr[idx1], arr[idx2] = arr[idx2], arr[idx1]

        return arr


def agregar_resultado(nombre, funcion, arreglo, resultados):
    tiempo, comparaciones, intercambios = funcion(arreglo)

    resultados.append({
        "Algoritmo": nombre,
        "Tiempo (seg)": tiempo,
        "Comparaciones": comparaciones,
        "Intercambios": intercambios
    })


# INTERFAZ

st.title("ALGORITMOS DE ORDENAMIENTO")

col_izq, col_der = st.columns([1, 1.35], gap="large")

with col_izq:
    with st.container(border=True):
        st.subheader("PARAMETROS")
        st.caption("TAMAÑO DEL ARREGLO:")

    tamano = st.slider(
        "Tamaño del arreglo",
        min_value=10,
        max_value=1000,
        value=705,
        label_visibility="collapsed"
    )

    st.caption("ESCENARIO DE DATOS:")

    escenario = st.selectbox(
        "Escenario de datos",
        [
            "Totalmente aleatorios",
            "Ordenados",
            "Ordenados en orden inverso",
            "Casi ordenados"
        ],
        label_visibility="collapsed"
    )

with col_der:
    with st.container(border=True):
        st.subheader("ALGORITMOS A EVALUAR:")

    col_a, col_b, col_c = st.columns(3)

    with col_a:
        cb_bubble = st.checkbox("BUBBLE SORT")
        cb_selection = st.checkbox("SELECTION SORT")

    with col_b:
        cb_insertion = st.checkbox("INSERTION SORT")
        cb_merge = st.checkbox("MERGE SORT")

    with col_c:
        cb_quick = st.checkbox("QUICK SORT")
        cb_heap = st.checkbox("HEAP SORT")


st.write("")

col_boton_izq, col_boton_centro, col_boton_der = st.columns([1, 6, 1])

with col_boton_centro:
    ejecutar = st.button(
        "EJECUTAR COMPARACIÓN",
        use_container_width=True
    )


# EJECUCIÓN

if ejecutar:
    arreglo_base = generar_datos(tamano, escenario)
    filas_resultados = []

    if cb_bubble:
        agregar_resultado("Bubble Sort", bubble_sort, arreglo_base, filas_resultados)

    if cb_selection:
        agregar_resultado("Selection Sort", selection_sort, arreglo_base, filas_resultados)

    if cb_insertion:
        agregar_resultado("Insertion Sort", insertion_sort, arreglo_base, filas_resultados)

    if cb_merge:
        agregar_resultado("Merge Sort", merge_sort, arreglo_base, filas_resultados)

    if cb_quick:
        agregar_resultado("Quick Sort", quick_sort, arreglo_base, filas_resultados)

    if cb_heap:
        agregar_resultado("Heap Sort", heap_sort, arreglo_base, filas_resultados)

    if len(filas_resultados) < 2:
        st.error("Por favor, selecciona al menos 2 algoritmos para realizar la comparación.")
    else:
        df = pd.DataFrame(filas_resultados)
        mejor_algoritmo = df.loc[df["Tiempo (seg)"].idxmin(), "Algoritmo"]

        st.subheader("RESULTADOS DE LA COMPARACIÓN:")

        col_tabla, col_grafica = st.columns([1.1, 1], gap="large")

        with col_tabla:
            with st.container(border=True):
                st.markdown("### Tabla de resultados")
                st.dataframe(df, use_container_width=True, hide_index=True)

                st.success(f"El mejor algoritmo bajo este escenario fue: {mejor_algoritmo}")

        with col_grafica:
            with st.container(border=True):
                st.markdown("### Comparativa de rendimiento")

                fig, ax = plt.subplots(figsize=(7, 4))
                fig.patch.set_facecolor(COLOR_BUTTERMILK)
                ax.set_facecolor(COLOR_BUTTERMILK)

                colores_barras = [
                    COLOR_BURGUNDY if alg == mejor_algoritmo else COLOR_PASTEL_BLUE
                    for alg in df["Algoritmo"]
                ]

                barras = ax.bar(
                    df["Algoritmo"],
                    df["Tiempo (seg)"],
                    color=colores_barras,
                    edgecolor=COLOR_BURGUNDY,
                    linewidth=1.2
                )

                ax.set_ylabel(
                    "Tiempo en segundos",
                    fontsize=10,
                    color=COLOR_BURGUNDY,
                    weight="bold"
                )

                ax.tick_params(
                    axis="x",
                    labelsize=8,
                    colors=COLOR_BURGUNDY,
                    rotation=20
                )

                ax.tick_params(
                    axis="y",
                    labelsize=9,
                    colors=COLOR_BURGUNDY
                )

                ax.spines["top"].set_visible(False)
                ax.spines["right"].set_visible(False)
                ax.spines["left"].set_color(COLOR_BURGUNDY)
                ax.spines["bottom"].set_color(COLOR_BURGUNDY)

                ax.bar_label(
                    barras,
                    fmt="%.5f",
                    padding=3,
                    fontsize=8,
                    color=COLOR_BURGUNDY,
                    weight="bold"
                )

                plt.tight_layout()
                st.pyplot(fig)