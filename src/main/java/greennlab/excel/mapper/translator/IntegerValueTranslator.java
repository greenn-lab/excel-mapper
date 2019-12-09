package greennlab.excel.mapper.translator;

import org.apache.poi.ss.usermodel.Cell;

public class IntegerValueTranslator implements ValueTranslator<Integer> {

  @Override
  public Integer translate(Cell cell) {
    return Double.valueOf(cell.getNumericCellValue()).intValue();
  }

}
