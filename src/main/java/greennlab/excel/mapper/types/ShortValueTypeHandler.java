package greennlab.excel.mapper.types;

import org.apache.poi.ss.usermodel.Cell;

public class ShortValueTypeHandler implements ValueTypeHandler<Short> {

  @Override
  public boolean matches(Class<?> type) {
    return type == int.class || Short.class.isAssignableFrom(type);
  }

  @Override
  public Short getValue(Cell cell) {
    return Double.valueOf(cell.getNumericCellValue()).shortValue();
  }

}
