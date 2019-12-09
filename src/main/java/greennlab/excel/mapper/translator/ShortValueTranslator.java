package greennlab.excel.mapper.translator;

import org.apache.poi.ss.usermodel.Cell;

public class ShortValueTranslator implements ValueTranslator<Short> {

  @Override
  public Short translate(Cell cell) {
    return Double.valueOf(cell.getNumericCellValue()).shortValue();
  }

}
