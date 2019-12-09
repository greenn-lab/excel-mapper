package greennlab;

import greennlab.excel.mapper.annotation.ExcelColumn;
import greennlab.excel.mapper.model.FrozenCharacter;
import greennlab.excel.mapper.model.FrozenCharacter.FrozenCharacterGenderTypeValueTranslator;
import greennlab.excel.mapper.translator.ValueTranslator;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AboutClasspathTests {

  private static final String pakcageName = AboutClasspathTests.class.getPackage().getName();

  @Test
  public void shouldGetColMappingAnnotations() {
    final Class<FrozenCharacter> clazz = FrozenCharacter.class;

    findSuperclasses(clazz).stream().forEach(field -> {
      final String signature = field.getDeclaringClass().getSimpleName() + "." + field.getName();
      final String value = field.getAnnotation(ExcelColumn.class).value();

      System.out.println(signature + "," + value);

      assertThat(value).containsPattern("[A-Z]+");
    });
  }

  private Set<Field> findSuperclasses(Class<?> clazz) {
    final Set<Field> annotatedFields = new HashSet<>();

    do {
      annotatedFields.addAll(findExcelColumnAnnotationFieldsAt(clazz, annotatedFields));
    }
    while ((clazz = clazz.getSuperclass()) != Object.class);

    return annotatedFields;
  }

  private Collection<? extends Field> findExcelColumnAnnotationFieldsAt(Class<?> clazz, Set<Field> annotatedFields) {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
        .filter(field -> annotatedFields.stream()
            .allMatch(annotatedField -> {
              System.out.printf("%s --- %s\n", field.toString(), annotatedField.toString());
              return annotatedField.getName().equals(field.getName());
            })
        )
        .collect(Collectors.toSet());
  }

  @Test
  public void shouldFindNestedClassImplementedValueTranslator() {
    final ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(false);

    scanner.addIncludeFilter(new AssignableTypeFilter(ValueTranslator.class));

    final Stream<String> beanClasses = scanner.findCandidateComponents(pakcageName)
        .stream().map(BeanDefinition::getBeanClassName);

    assertThat(beanClasses)
       .contains(SampleTypeTranslator.class.getName());
  }

  private class Sample {}

  private static class SampleTypeTranslator implements ValueTranslator<Sample> {

    @Override
    public Sample translate(Cell cell) {
      return null;
    }

  }
}
