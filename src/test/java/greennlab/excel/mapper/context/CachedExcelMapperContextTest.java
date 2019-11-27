package greennlab.excel.mapper.context;

import greennlab.excel.mapper.ExcelMapper;
import greennlab.excel.mapper.annotation.ColMapping;
import greennlab.excel.mapper.annotation.SheetMapping;
import greennlab.excel.mapper.model.FrozenCharacter;
import greennlab.excel.mapper.types.ValueTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CachedExcelMapperContextTest {

  @Test
  void shouldGetAttributesFromSheetMappingAnnotation() {
    final SheetMapping annotation = FrozenCharacter.class.getAnnotation(SheetMapping.class);

    assertThat(annotation.name()).isEqualTo("겨울왕국2");
    assertThat(annotation.rowStarter()).isEqualTo(4);
  }

  @Test
  void shouldGetFieldAnnotation() {
    assertThat(
        Arrays.stream(FrozenCharacter.class.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(ColMapping.class))
            .map(i -> assertThat(i.getAnnotation(ColMapping.class).value()).matches("[A-Z]"))
            .count()
    ).isEqualTo(5);
  }

  @Test
  void findMapperBy() throws IOException {
    CachedExcelMapperContext ctx = new CachedExcelMapperContext();

    final ExcelMapper<FrozenCharacter> mapper = ctx.findMapperBy(FrozenCharacter.class);
    assertThat(mapper).isNotNull();

    final List<FrozenCharacter> characters = mapper.extract(new ClassPathResource("sample.xlsx").getInputStream());

    assertThat(characters).hasSize(5);

    characters.stream().forEach(System.out::println);
  }

}
