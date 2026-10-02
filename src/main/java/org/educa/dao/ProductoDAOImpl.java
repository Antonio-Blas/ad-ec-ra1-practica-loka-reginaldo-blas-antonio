package org.educa.dao;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.educa.entity.ProductoEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {
    @Override
    public List<ProductoEntity> readFile(String fileXml) {
        List<ProductoEntity> product = new ArrayList<>();
        try {
            JAXBContext productContext = JAXBContext.newInstance(Productos.class);
            Unmarshaller unmarshaller = productContext.createUnmarshaller();
            File file = new File(fileXml);
            Productos productos = (Productos) unmarshaller.unmarshal(file);

            if (productos != null && productos.getProducto() != null) {
                for (Producto producto : productos.getProducto()) {
                    ProductoEntity productoEntity = convertToEntity(producto);
                    product.add(productoEntity);
                }
            } else {
                System.out.println("No product found in XML file");
            }
            if (!file.exists()) {
                throw new RuntimeException("File not found: " + fileXml);
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
