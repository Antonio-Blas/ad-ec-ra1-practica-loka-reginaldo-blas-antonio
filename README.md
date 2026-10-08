ad-ec-ra1-practica-loka-reginaldo-blas-antonio

Proyecto desarrollado en Java para trabajar con información almacenada en un fichero XML. 
El proyecto incluye tres actividades relacionadas con la lectura, análisis y exportación de los datos.

1.Ejercicio 1

Este ejercicio consiste en leer el fichero xml y mostrarlo por la terminal.

2.Ejercicio 2

Analiza el fichero xml y lo resume, guardando ese resumen en un fichero txt

Fecha: junio2026

NumeroDeVehiculos: 5

BeneficioTotal: 1221.6516

Ruta del Fichero: src/main/resources/xml/inventario_junio2026.xml

Nombre del Fichero: inventario_junio2026

Tamaño del Fichero: 47 bytes

3.Ejercicio 3

Introduce los datos sacados del fichero xml en un excel, organizando los datos y proyectándolos para una mejor vista

Tecnologías utilizadas

-   Java
  
-   XML
  
-   Ficheros de texto
  
-   Excel

-   Maven

Estructura del proyecto

ad-ec-ra1-practica-loka-reginaldo-blas-antonio
    │   .gitignore
    │   pom.xml
    │   README.md
    │
    │
    ├───src
    │   └───main
    │       ├───java
    │       │   └───org
    │       │       └───educa
    │       │           ├───app
    │       │           │       Activity1.java
    │       │           │       Activity2.java
    │       │           │       Activity3.java
    │       │           │
    │       │           ├───dao
    │       │           │       ProductoDAO.java
    │       │           │       ProductoDAOImpl.java
    │       │           │
    │       │           ├───entity
    │       │           │       ProductoEntity.java
    │       │           │       SummaryEntity.java
    │       │           │
    │       │           └───service
    │       │                   ProductoService.java
    │       │
    │       └───resources
    │           ├───export
    │           │       export_junio2026.xlsx
    │           │       result_junio2026.txt
    │           │
    │           ├───xml
    │           │       inventario_junio2026.xml
    │           │
    │           └───xsd
    │                   inventario_junio2026.xsd
    │
    └───target
        ├───classes
        │   ├───export
        │   │       export_junio2026.xlsx
        │   │
        │   ├───generated
        │   │       Costes.class
        │   │       ObjectFactory.class
        │   │       Producto.class
        │   │       Productos.class
        │   │       Proveedor.class
        │   │
        │   │
        │   ├───org
        │   │   └───educa
        │   │       ├───app
        │   │       │       Activity1.class
        │   │       │       Activity2.class
        │   │       │       Activity3.class
        │   │       │
        │   │       ├───dao
        │   │       │       ProductoDAO.class
        │   │       │       ProductoDAOImpl.class
        │   │       │
        │   │       ├───entity
        │   │       │       ProductoEntity.class
        │   │       │       SummaryEntity.class
        │   │       │
        │   │       └───service
        │   │               ProductoService.class
        │   │
        │   ├───xml
        │   │       inventario_junio2026.xml
        │   │
        │   └───xsd
        │           inventario_junio2026.xsd
        │
        ├───generated-sources
            ├───annotations
            └───jaxb
                └───generated
                        Costes.java
                        ObjectFactory.java
                        Producto.java
                        Productos.java
                        Proveedor.java

Ejecución

- Para ejecutar el proyecto, clona el repositorio:

  - git clone https://github.com/Antonio-Blas/ad-ec-ra1-practica-loka-reginaldo-blas-antonio.git

- Accede a la carpeta del proyecto:

  - cd ad-ec-ra1-practica-loka-reginaldo-blas-antonio

- Compila y ejecuta el proyecto desde el IDE o mediante Maven.

Autores
- Antonio Blas
- Reginaldo Loka

