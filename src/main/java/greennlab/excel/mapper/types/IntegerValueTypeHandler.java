package greennlab.excel.mapper.types;

import org.apache.poi.ss.usermodel.Cell;

public class IntegerValueTypeHandler implements ValueTypeHandler<Integer> {

  @Override
  public boolean matches(Class<?> type) {
    return type == int.class || Integer.class.isAssignableFrom(type);
  }

  @Override
  public Integer getValue(Cell cell) {
    return Double.valueOf(cell.getNumericCellValue()).intValue();
  }

}
