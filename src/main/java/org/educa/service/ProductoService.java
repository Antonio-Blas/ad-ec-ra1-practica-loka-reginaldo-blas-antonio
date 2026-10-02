package org.educa.service;

import jakarta.xml.bind.JAXBException;
import org.educa.dao.ProductoDAO;
import org.educa.dao.ProductoDAOImpl;
import org.educa.entity.ProductoEntity;

import java.io.IOException;
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

    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        //TODO: Implementar

    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //TODO: Implementar
    }
}
