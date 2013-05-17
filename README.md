# Fundamentos de la Computación
## Proyecto Final
### Descripción
Aquí se encuentra la solución a los ejercicios del proyecto final de fundamentos 
de la computación; así como el ensayo con formato de artículo con una 
introducción, metodología, discusión y conclusión tratando el desarollo de la 
solución a los ejercicios.

**Resultados de Compilación y Pruebas**: [![Build Status](https://travis-ci.org/marcel-valdez/final_fundamentos.png?branch=master)](https://travis-ci.org/marcel-valdez/final_fundamentos)  

**Nota:** Para regenerar el archivo README.pdf, debe ejecutar el script: `$ ./make_readme.sh`  

**Requerimientos de software:**

- Java SE 1.7+
- Alguno de Internet Explorer, Chrome o Firefox (última versión)
- pandoc, procesador tex (opcional, para regenerar README.pdf)

### Integrantes

1. Priscila Angulo
2. Marcel valdez

## Contenido

### Problema 1. Heurística FFD

**Ruta del Código**: FirstFitDecreasing\\src\\firstfitdecreasing  
**Pruebas unitarias**: FirstFitDecreasing\\test\\firstfitdecreasing  
**Clase a ejecutar**: FirstFitDecreasing\\src\\firstfitdecreasing\\Program.java  

> **Input (instancias)**: Se incluyen dentro del código  
> **Ouput (resultados)**: En consola

#### Notas
- El programa resuelve de forma automática las 3 instancias dadas en el proyecto
- Para revisar la instancia 4 (creada por nosotros para replicar fenómeno de aumento de contenedores) ejecutar el test:

> **Archivo**: FirstFitDecreasing\\test\\firstfitdecreasing\\ProgramTest.java  
> **Método**: testFindSample()

______________________________________________________________

### Problema 2. Heurísticas DJD e Hiperheurística

**Ruta del Código**: HiperHeuristica\\src\\hiper  
**Pruebas unitarias**: final_fundamentos\\HiperHeuristica\\test  
**Clase a ejecutar**: HiperHeuristica\\src\\hiper\\Program.java

> **Input (instancias)**: HiperHeuristica\\input_data  
> **Ouput (resultados) en consola y en las carpetas**:  
  - HiperHeuristica\\results_H25  
  - HiperHeuristica\\results_H33  
  - HiperHeuristica\\results_HH  

#### Notas
- El código está organizado en 2 paquetes: hiper y parsing, el primero contiene la lógica de las heurísticas y la hiperheurística; el segundo la lógica para leer los archivos de las instancias. Las pruebas están organizadas de la misma manera.
- El programa resuelve de forma automática las instancias utilizando las variantes DJD 1/3, DJD 1/4 e hiperheurística.

______________________________________________________________

### Problema 2. Presentación visual de resultados de Heurísticas DJD e Hiperheurística

**Ruta del Código**: DrawContainers\\public_html  
**Archivo a ejecutar**: DrawContainers\\public_html\\index.html

> **Input (archivos en carpetas)**:  
  - HiperHeuristica\\results_H25  
  - HiperHeuristica\\results_H33  
  - HiperHeuristica\\results_HH  

> **Output**: Presentación visual de contenedores y sus piezas


#### Notas
- Esta app web fue elaborada con HTML5 y JavaScript, su funcionamiento fue probado en Internet Explorer 10 y Chrome 26.
- Para ejecutar esta app no se requiere tener instalado un servidor web, basta con abrir el archivo index.html en el navegador.
