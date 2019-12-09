package greennlab.excel.mapper.translator;

import greennlab.excel.mapper.ExcelColumnMapper;
import greennlab.excel.mapper.annotation.ExcelColumn;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ValueTranslatorMatcher {

  private final Class<?> type;
  private final Map<Class<?>, ValueTranslator<?>> valueTranslatorMap;

  public ValueTranslatorMatcher(final Class<?> type, final Map<Class<?>, ValueTranslator<?>> valueTranslatorMap) {
    this.type = type;
    this.valueTranslatorMap = valueTranslatorMap;
  }

  public Set<ExcelColumnMapper> getColumns() {
    return detectExcelColumn(type).stream()
        .map(field -> new ExcelColumnMapper(field, getMatchedValueTranslator(field.getType())))
        .collect(Collectors.toSet());
  }

  private ValueTranslator<?> getMatchedValueTranslator(Class<?> type) {
    if (!valueTranslatorMap.containsKey(type))
      throw new NoSuchElementException(String.format("Required translator for %s", type.getName()));

    return valueTranslatorMap.get(type);
  }

  private Set<Field> detectExcelColumn(Class<?> clazz) {
    final Set<Field> annotatedFields = new HashSet<>();

    do {
      annotatedFields.addAll(detectExcelColumnAnnotatedFieldsAt(clazz, annotatedFields));
    }
    while ((clazz = clazz.getSuperclass()) != Object.class);

    return annotatedFields;
  }

  private Set<Field> detectExcelColumnAnnotatedFieldsAt(final Class<?> clazz, final Set<Field> annotatedFields) {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
        .filter(field -> annotatedFields.stream()
            .allMatch(annotatedField -> annotatedField.getName().equals(field.getName()))
        )
        .collect(Collectors.toSet());
  }


  private static Class<?> resolvePrimitiveTypes(Class<?> type) {
    if (!type.isPrimitive()) {
      return type;
    }
    else if (int.class == type) {
      return Integer.class;
    }
    else if (long.class == type) {
      return Long.class;
    }
    else if (double.class == type) {
      return Double.class;
    }
    else if (short.class == type) {
      return Short.class;
    }
    else if (float.class == type) {
      return Float.class;
    }
    else if (byte.class == type) {
      return Byte.class;
    }
    else if (boolean.class == type) {
      return Boolean.class;
    }
    else if (char.class == type) {
      return Character.class;
    }
    else if (int[].class == type) {
      return Integer[].class;
    }
    else if (long[].class == type) {
      return Long[].class;
    }
    else if (double[].class == type) {
      return Double[].class;
    }
    else if (short[].class == type) {
      return Short[].class;
    }
    else if (float[].class == type) {
      return Float[].class;
    }
    else if (byte[].class == type) {
      return Byte[].class;
    }
    else if (boolean[].class == type) {
      return Boolean[].class;
    }
    else if (char[].class == type) {
      return Character[].class;
    }
    else {
      throw new IllegalArgumentException();
    }
  }

}
