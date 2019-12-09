package greennlab.excel.mapper;


import greennlab.excel.mapper.annotation.ExcelColumn;
import greennlab.excel.mapper.translator.ValueTranslator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import java.lang.reflect.Field;

@Slf4j
public class ExcelColumnMapper {

  private final Field field;
  private final ValueTranslator<?> valueTranslator;
  private final short columnIndex;

  public ExcelColumnMapper(final Field field, ValueTranslator<?> valueTranslator) {
    this.field = field;
    this.field.setAccessible(true);

    this.valueTranslator = valueTranslator;

    final ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
    this.columnIndex = (short) CellReference.convertColStringToIndex(annotation.value());
  }

  protected void setValue(final Row row, final Object object) throws IllegalAccessException {
    final Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
    field.set(object, valueTranslator.translate(cell));
  }

}
