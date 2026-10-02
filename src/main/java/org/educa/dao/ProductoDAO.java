package org.educa.dao;

import org.educa.entity.ProductoEntity;

import java.util.List;

public interface ProductoDAO {
    List<ProductoEntity> readFile(String fileXml);
}
