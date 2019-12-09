package greennlab.excel.mapper.translator;

import org.apache.poi.ss.usermodel.Cell;

public class StringValueTranslator implements ValueTranslator<String> {

  @Override
  public String translate(Cell cell) {
    return cell.getStringCellValue();
  }

}
