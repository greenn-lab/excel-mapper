package greennlab.excel.mapper.model;

import greennlab.excel.mapper.annotation.ColMapping;
import greennlab.excel.mapper.annotation.SheetMapping;
import lombok.ToString;

@SheetMapping(name = "겨울왕국2", rowStarter = 4)
@ToString
public class FrozenCharacter {

  @ColMapping("A")
  private String name;

  @ColMapping("B")
  private GenderType gender;

  @ColMapping("C")
  private short age;

  @ColMapping("D")
  private String ability;

  @ColMapping("E")
  private String remark;

  public enum GenderType {
    MALE, FEMALE, UNISEX;
  }
}
