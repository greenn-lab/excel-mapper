package greennlab.excel.mapper.model;

import greennlab.excel.mapper.annotation.ExcelColumn;
import greennlab.excel.mapper.annotation.ExcelSheet;
import greennlab.excel.mapper.translator.ValueTranslator;
import lombok.ToString;
import org.apache.poi.ss.usermodel.Cell;

@ExcelSheet(name = "겨울왕국2", startRow = 4)
@ToString
public class FrozenCharacter extends AnimationCharacter {

  @ExcelColumn("A")
  private String name;

  @ExcelColumn("B")
  private GenderType gender;

  @ExcelColumn("C")
  private short age;

  @ExcelColumn("D")
  private String ability;

  @ExcelColumn("E")
  private String remark;

  public enum GenderType {
    MALE, FEMALE, UNISEX;
  }

  public static class FrozenCharacterGenderTypeValueTranslator implements ValueTranslator<GenderType> {
    @Override
    public GenderType translate(Cell cell) {
      final String value = cell.getStringCellValue();

      if ("여자".equals(value))
        return GenderType.FEMALE;
      else if ("남자".equals(value))
        return GenderType.MALE;
      else
        return GenderType.UNISEX;
    }
  }

}
