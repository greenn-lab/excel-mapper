package greennlab.excel.mapper.model;

import greennlab.excel.mapper.annotation.ExcelColumn;
import greennlab.excel.mapper.annotation.ExcelSheet;
import lombok.ToString;

@ExcelSheet(name = "뽀로로", startRow = 2)
@ToString
public class PororoCharacter extends AnimationCharacter {

  @ExcelColumn("B")
  private String phase;

  @ExcelColumn("C")
  private String remark;

}
