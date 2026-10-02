package org.educa.dao;

import generated.Producto;
import org.educa.entity.ProductoEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {
    @Override
    public List<ProductoEntity> readFile(String fileXml) {
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
