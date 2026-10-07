package org.educa.service;

import generated.Producto;
import jakarta.xml.bind.JAXBException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.educa.dao.ProductoDAO;
import org.educa.dao.ProductoDAOImpl;
import org.educa.entity.ProductoEntity;
import org.educa.entity.SummaryEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

public class ProductoService {
    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    /**
     * Esta funcion lee el fichero xml
     *
     * @param fileXml nombre del fichero xml que se pasa en el main
     * @return devuelve el dao ejecutando la lectura
     * @throws JAXBException Excepcion para File
     */
    public List<ProductoEntity> readFile(String fileXml) throws JAXBException {
        return productoDAO.readFile(fileXml);
    }

    /**
     * Metodo para exportar un resumen del fichero Xml
     *
     * @param path    ruta para la creacion del fichero Txt
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

    /**
     * Este es un metodo que sirve para exportar a excel
     * @param path la ruta donde se guarda mi excel
     * @param fileXml fichero xml del cual obtengo los datos
     * @throws JAXBException  Excepcion de JAXB
     * @throws IOException    Excepcion de salida
     * @throws ParseException Excepcion de parse
     */

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        List<ProductoEntity> productoEntities = productoDAO.readFile(fileXml);

        File xmlFIle = new File(fileXml);
        String nameFile = xmlFIle.getName();
        String mesAnio = extraerMesAnio(nameFile);

        String outputFileName = "export_" + mesAnio.replace(".xml", "") + ".xlsx";
        String absolutePath = Paths.get(path, outputFileName).toString();

        crearArchivoExcel(productoEntities, absolutePath);

    }

    private void crearArchivoExcel(List<ProductoEntity> productoEntities, String absolutePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos");

            CellStyle headerStyle = crearEstiloCabecera(workbook);
            CellStyle estiloFilaPar = crearEstiloFilaPar(workbook);
            CellStyle estiloFilaImpar = crearEstiloFilaImpar(workbook);
            CellStyle estiloMoneda = crearEstiloMoneda(workbook);
            CellStyle estiloPorcentaje = crearEstiloPorcentaje(workbook);

            crearCabecera(sheet, headerStyle);

            int rowNum = 1;
            for (int i = 0; i < productoEntities.size(); i++) {
                ProductoEntity producto = productoEntities.get(i);
                Row row = sheet.createRow(rowNum++);

                CellStyle estiloFila = (i % 2 == 0) ? estiloFilaPar : estiloFilaImpar;

                llenarFilaProducto(row, producto, estiloFila, estiloMoneda, estiloPorcentaje);
            }

            autoajustarColumnas(sheet);

            try (FileOutputStream outputStream = new FileOutputStream(absolutePath)) {
                workbook.write(outputStream);
            }
        }
    }

    private void llenarFilaProducto(Row row, ProductoEntity productoEntities, CellStyle estiloFila, CellStyle estiloMoneda, CellStyle estiloPorcentaje) {
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(productoEntities.getProducto().getCodigo());
        cell0.setCellStyle(estiloFila);

        Cell cell1 = row.createCell(1);
        cell1.setCellValue(productoEntities.getProducto().getNumeroSerie());
        cell1.setCellStyle(estiloFila);

        Cell cell2 = row.createCell(2);
        cell2.setCellValue(productoEntities.getProducto().getPrecio().doubleValue());
        cell2.setCellStyle(estiloMoneda);
        cell2.setCellStyle(estiloFila);

        Cell cell3 = row.createCell(3);
        BigDecimal descuentoPorcentaje = productoEntities.getProducto().getDescuento().divide(BigDecimal.valueOf(100), RoundingMode.CEILING);
        cell3.setCellValue(descuentoPorcentaje.doubleValue());
        cell3.setCellStyle(estiloPorcentaje);
        cell3.setCellStyle(estiloFila);

        Cell cell4 = row.createCell(4);
        cell4.setCellValue(productoEntities.getPrecioFinal().doubleValue());
        cell4.setCellStyle(estiloMoneda);
        cell4.setCellStyle(estiloFila);

        Cell cell5 = row.createCell(5);
        cell5.setCellValue(productoEntities.getProducto().getCostes().getCostesEnvio().doubleValue());
        cell5.setCellStyle(estiloMoneda);
        cell5.setCellStyle(estiloFila);

        Cell cell6 = row.createCell(6);
        cell6.setCellValue(productoEntities.getProducto().getCostes().getCostesAlmacenaje().doubleValue());
        cell6.setCellStyle(estiloMoneda);
        cell6.setCellStyle(estiloFila);

        Cell cell7 = row.createCell(7);
        cell7.setCellValue(productoEntities.getProfit().doubleValue());
        cell7.setCellStyle(estiloMoneda);
        cell7.setCellStyle(estiloFila);
    }



    private void autoajustarColumnas(Sheet sheet) {
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }
    }


    private void crearCabecera(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);

        String[] headers = {
                "Codigo", "Numero de Serie", "Precio", "Descuento",
                "Precio Final", "Costes Envio", "Costes Almacenaje", "Beneficio"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private CellStyle crearEstiloPorcentaje(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("0.00%"));
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle crearEstiloMoneda(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00\" €\""));
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle crearEstiloFilaImpar(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle crearEstiloFilaPar(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.DOUBLE);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.DOUBLE);
        style.setBorderRight(BorderStyle.DOUBLE);
        return style;
    }

    private CellStyle crearEstiloCabecera(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.DOUBLE);
        style.setBorderLeft(BorderStyle.DOUBLE);
        style.setBorderRight(BorderStyle.DOUBLE);

        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }

    private BigDecimal calcularBeneficioTotal(List<ProductoEntity> productoEntities) {
        BigDecimal beneficioTotal = BigDecimal.ZERO;
        for(ProductoEntity producto : productoEntities){
            beneficioTotal = beneficioTotal.add(producto.getProfit());
        }

        return beneficioTotal;
    }



    private void crearDirectorioSiNoExiste(String path) throws IOException {
        Path directorio = Paths.get(path);
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio);
        }
    }




    private String extraerMesAnio(String nombreSinExtension) {
        String[] partes = nombreSinExtension.split("_");
        return partes.length > 1 ? partes[1] : "desconocido";
    }
}
