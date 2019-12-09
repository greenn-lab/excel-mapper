package greennlab.excel.mapper.context;

import greennlab.excel.mapper.ExcelMapper;
import greennlab.excel.mapper.annotation.ExcelColumn;
import greennlab.excel.mapper.annotation.ExcelSheet;
import greennlab.excel.mapper.model.FrozenCharacter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CachedExcelMapperContextTest {

  @Test
  void shouldGetAttributesFromSheetMappingAnnotation() {
    final ExcelSheet annotation = FrozenCharacter.class.getAnnotation(ExcelSheet.class);

    assertThat(annotation.name()).isEqualTo("겨울왕국2");
    assertThat(annotation.startRow()).isEqualTo(4);
  }

  @Test
  void shouldGetFieldAnnotation() {
    assertThat(
        Arrays.stream(FrozenCharacter.class.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
            .map(i -> assertThat(i.getAnnotation(ExcelColumn.class).value()).matches("[A-Z]"))
            .count()
    ).isEqualTo(5);
  }

  @Test
  void findMapperBy() throws IOException {
    CachedExcelMapperContext ctx = new CachedExcelMapperContext();

    final ExcelMapper<FrozenCharacter> mapper = ctx.findMapperBy(FrozenCharacter.class);
    assertThat(mapper).isNotNull();

    final List<FrozenCharacter> characters = mapper.extract(new ClassPathResource("frozen2.xlsx").getInputStream());

    assertThat(characters).hasSize(5);

    characters.stream().forEach(System.out::println);
  }

}
