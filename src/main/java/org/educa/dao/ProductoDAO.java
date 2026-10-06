package org.educa.dao;

import org.educa.entity.ProductoEntity;
import org.educa.entity.SummaryEntity;

import java.io.IOException;
import java.util.List;

public interface ProductoDAO {
    /**
     * Metodo que lee el fichero Xml pasado
     * @param fileXml ruta al Fichero Xml
     * @return devuelve una lista de valores leidos
     */
    List<ProductoEntity> readFile(String fileXml);

    /**
     * Metodo para exportar un resumen del fichero Xml
     * @param ruta ruta al fichero Xml
     * @param summaryEntity valores guardados para exportarlos
     * @throws IOException excepcion de salida de File
     */
    void exportSummary(String ruta, SummaryEntity summaryEntity) throws IOException;
}
