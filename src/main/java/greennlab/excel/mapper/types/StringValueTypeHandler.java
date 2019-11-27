package greennlab.excel.mapper.types;

import org.apache.poi.ss.usermodel.Cell;

public class StringValueTypeHandler implements ValueTypeHandler<String> {

  @Override
  public boolean matches(Class<?> type) {
    return String.class.isAssignableFrom(type);
  }

  @Override
  public String getValue(Cell cell) {
    return cell.getStringCellValue();
  }

}
