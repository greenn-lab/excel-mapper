package greennlab.excel.mapper.model.helper;

import greennlab.excel.mapper.model.FrozenCharacter;
import greennlab.excel.mapper.types.ValueTypeHandler;
import org.apache.poi.ss.usermodel.Cell;

public class GenderTypeValueTypeHandler implements ValueTypeHandler<FrozenCharacter.GenderType> {

    @Override
    public boolean matches(Class<?> type) {
      return type == FrozenCharacter.GenderType.class;
    }

    @Override
    public FrozenCharacter.GenderType getValue(Cell cell) {
      final String value = cell.getStringCellValue();

      if ("여자".equals(value))
        return FrozenCharacter.GenderType.FEMALE;
      else if ("남자".equals(value))
        return FrozenCharacter.GenderType.MALE;
      else
        return FrozenCharacter.GenderType.UNISEX;
    }
  }
