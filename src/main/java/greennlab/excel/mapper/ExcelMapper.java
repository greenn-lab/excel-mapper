package greennlab.excel.mapper;

import greennlab.excel.mapper.annotation.ColMapping;
import greennlab.excel.mapper.annotation.SheetMapping;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ExcelMapper<T> {

  private final Class<T> clazz;

  private String sheetName;
  private int startRow;
  private Set<ExcelMapperColumn> columns;

  public ExcelMapper(Class<T> clazz) {
    validAnnotatedSheetMapping(clazz);
    sheetDefinitionFrom(clazz);

    this.clazz = clazz;
  }

  public List<T> extract(InputStream spreadsheet) throws IOException {
    final Workbook workbook = new XSSFWorkbook(spreadsheet);
    final Sheet sheet = workbook.getSheet(sheetName);
    final int lastRowNum = sheet.getLastRowNum();

    final List<T> result = new ArrayList<>();

    for (int i = startRow - 1; i <= lastRowNum; i++) {
      final Row row = sheet.getRow(i);

      try {
        final T item = clazz.getDeclaredConstructor().newInstance();

        for (final ExcelMapperColumn column : columns) {
          final Cell cell = row.getCell(column.getColumnIndex());
          column.setValue(cell, item);
        }

        result.add(item);
      }
      catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new IllegalStateException(e);
      }
    }

    return result;
  }


  private void validAnnotatedSheetMapping(Class<T> clazz) {
    if (!clazz.isAnnotationPresent(SheetMapping.class)) {
      throw new IllegalArgumentException();
    }
  }


  private void sheetDefinitionFrom(Class<T> clazz) {
    final SheetMapping annotation = clazz.getAnnotation(SheetMapping.class);
    sheetName = annotation.name();
    startRow = annotation.rowStarter();
    columns = columnDefinitionFrom(clazz);
  }

  private Set<ExcelMapperColumn> columnDefinitionFrom(Class<T> clazz) {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(ColMapping.class))
        .map(field -> {
          final ColMapping mapping = field.getAnnotation(ColMapping.class);
          final short columnIndex = (short) CellReference.convertColStringToIndex(mapping.value());
          return new ExcelMapperColumn(columnIndex, field);
        })
        .collect(Collectors.toSet());
  }
}
