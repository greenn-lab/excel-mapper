package greennlab;

import greennlab.excel.mapper.ExcelMapper;
import greennlab.excel.mapper.annotation.ExcelColumn;
import greennlab.excel.mapper.annotation.ExcelSheet;
import greennlab.excel.mapper.model.AnimationCharacter;
import greennlab.excel.mapper.model.FrozenCharacter;
import greennlab.excel.mapper.model.PororoCharacter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class ApplicationTests {

  @Autowired
  private ExcelMapper<PororoCharacter> pororoExcelMapper;

  @Autowired
  private ExcelMapper<FrozenCharacter> frozenCharacterExcelMapper;

  @Test
  public void shouldGetNormallyRegisteredSpringBean() {
    assert pororoExcelMapper != null;
    assert frozenCharacterExcelMapper != null;
  }

}
