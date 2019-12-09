package greennlab.excel.mapper;

import greennlab.excel.mapper.annotation.ExcelSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class ExcelMapper<T> {

  private final Class<T> type;
  private final Set<ExcelColumnMapper> columns;

  private final String sheetName;
  private final int startRow;


  public ExcelMapper(final Class<T> type, Set<ExcelColumnMapper> columns) {
    this.type = type;
    this.columns = columns;

    final ExcelSheet annotation = type.getAnnotation(ExcelSheet.class);
    sheetName = annotation.name();
    startRow = annotation.startRow() - 1;
  }

  public List<T> extract(final InputStream spreadsheet) throws IOException {
    return extractFromReadExcelSheet(spreadsheet, sheetName);
  }


  private List<T> extractFromReadExcelSheet(final InputStream spreadsheet, final String sheetName) throws IOException {
    final Workbook workbook = new XSSFWorkbook(spreadsheet);
    final Sheet sheet = workbook.getSheet(sheetName);

    try {
      final List<T> result = new ArrayList<>();

      for (final Row row : sheet) {
        if (row.getRowNum() < startRow) {
          continue;
        }

        final Constructor<T> constructor = type.getDeclaredConstructor();
        final T object = mappedRowAndNewObject(row, constructor.newInstance());
        result.add(object);
      }

      return result;
    }
    catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  private T mappedRowAndNewObject(final Row row, final T object) throws IllegalAccessException {
    for (final ExcelColumnMapper column : columns) {
      column.setValue(row, object);
    }

    return object;
  }

}
