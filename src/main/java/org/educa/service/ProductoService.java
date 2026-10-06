package org.educa.service;

import jakarta.xml.bind.JAXBException;
import org.educa.dao.ProductoDAO;
import org.educa.dao.ProductoDAOImpl;
import org.educa.entity.ProductoEntity;
import org.educa.entity.SummaryEntity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

public class ProductoService {
    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    /**
     * Esta funcion lee el fichero xml
     * @param fileXml nombre del fichero xml que se pasa en el main
     * @return devuelve el dao ejecutando la lectura
     * @throws JAXBException Excepcion para File
     */
    public List<ProductoEntity> readFile(String fileXml) throws JAXBException {
        return productoDAO.readFile(fileXml);
    }

    /**
     * Metodo para exportar un resumen del fichero Xml
     * @param path ruta para la creacion del fichero Txt
     * @param fileXml ruta para el fichero Xml
     * @throws IOException excepcion de salida de File
     */
    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        List<ProductoEntity> productoEntities = productoDAO.readFile(fileXml);
        int numProductos = productoEntities.size();
        BigDecimal beneficioTotal = calcularBeneficioTotal(productoEntities);

        File file = new File(fileXml);
        String nombreArchivo = file.getName();
        long tamanoArchivo = fileXml.length();
        String nombreSinExtension = nombreArchivo.replace(".xml", "");

        String mesAnio = extraerMesAnio(nombreSinExtension);

        String nombreFicheroTxt = "result_" + mesAnio + ".txt";
        String ruta = Paths.get(path, nombreFicheroTxt).toString();

        SummaryEntity summaryEntity = new SummaryEntity(
                mesAnio,
                numProductos,
                beneficioTotal,
                fileXml,
                nombreSinExtension,
                tamanoArchivo
        );

        productoDAO.exportSummary(ruta, summaryEntity);
    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //TODO: Implementar
    }

    private BigDecimal calcularBeneficioTotal(List<ProductoEntity> productoEntities) {
        BigDecimal beneficioTotal = BigDecimal.ZERO;
        for(ProductoEntity producto : productoEntities){
            beneficioTotal = beneficioTotal.add(producto.getProfit());
        }

        return beneficioTotal;
    }

    private String extraerMesAnio(String nombreSinExtension) {
        String[] partes = nombreSinExtension.split("_");
        return partes.length > 1 ? partes[1] : "desconocido";
    }
}
