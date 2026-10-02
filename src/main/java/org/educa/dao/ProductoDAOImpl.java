package org.educa.dao;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.educa.entity.ProductoEntity;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {
    @Override
    public List<ProductoEntity> readFile(String fileXml) {
        List<ProductoEntity> product;
        product = new ArrayList<>();
        try {
            JAXBContext productContext = JAXBContext.newInstance(Productos.class);
            Unmarshaller unmarshaller = productContext.createUnmarshaller();
            File file = new File(fileXml);
            Productos productos = (Productos) unmarshaller.unmarshal(file);

            if (productos != null && productos.getProducto() != null) {
                for (Producto producto : productos.getProducto()) {
                    ProductoEntity productoEntity = convertToFile(producto);
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


    private ProductoEntity convertToFile(Producto producto){
        ProductoEntity entity = new ProductoEntity();
        entity.setProducto(producto);

        BigDecimal precio = producto.getPrecio();
        BigDecimal descuento = producto.getDescuento();
        BigDecimal precioFinal = precio.subtract(precio.multiply(descuento.divide(BigDecimal.valueOf(100), RoundingMode.CEILING)));
        entity.setPrecioFinal(precioFinal);

        BigDecimal costeAlmacenaje = producto.getCostes().getCostesAlmacenaje();
        BigDecimal costeEnvio = producto.getCostes().getCostesEnvio();
        BigDecimal costeTotal = costeAlmacenaje.add(costeEnvio);
        entity.setCost(costeTotal);

        BigDecimal beneficio = precioFinal.subtract(costeTotal);
        entity.setProfit(beneficio);

        return entity;
    }
}
