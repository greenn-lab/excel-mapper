package greennlab.excel.mapper;


import greennlab.excel.mapper.types.ValueTypeHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
class ExcelMapperColumn {
  private static Map<Class<?>, ValueTypeHandler<?>> valueTypeHandlerMap = new HashMap<>();

  static {
    final ClassPathScanningCandidateComponentProvider scanner;
    scanner = new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AssignableTypeFilter(ValueTypeHandler.class));

    Arrays.stream(Package.getPackages())
        .filter(pkg -> !pkg.isSealed() &&
            !pkg.getName().matches("^(org.(springframework|slf4j|junit)|ch.qos).*"))
        .forEach(pkg -> {
          final Set<BeanDefinition> components = scanner.findCandidateComponents(pkg.getName());
          for (final BeanDefinition bean : components) {
            try {
              final Class<?> clazz = Class.forName(bean.getBeanClassName());
              final Method typedMethod = clazz.getDeclaredMethod("getValue", Cell.class);

              valueTypeHandlerMap.put(
                  typedMethod.getReturnType(),
                  (ValueTypeHandler<?>) clazz.getDeclaredConstructor().newInstance());
            }
            catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
              logger.error(e.getMessage(), e);
            }
          }
        });
  }

  @Getter(AccessLevel.PACKAGE)
  private final short columnIndex;
  private final Field field;
  private final ValueTypeHandler<?> valueTypeHandler;

  ExcelMapperColumn(short columnIndex, Field field) {
    this.columnIndex = columnIndex;
    this.field = field;
    this.field.setAccessible(true);
    this.valueTypeHandler = findFieldTypeMatchedValueTypeHandler(field);
  }

  private ValueTypeHandler<?> findFieldTypeMatchedValueTypeHandler(Field field) {
    Class<?> type = field.getType();
    if (type.isPrimitive()) {
      if (int.class == type) {
        type = Integer.class;
      }
      else if (long.class == type) {
        type = Long.class;
      }
      else if (double.class == type) {
        type = Double.class;
      }
      else if (short.class == type) {
        type = Short.class;
      }
      else if (float.class == type) {
        type = Float.class;
      }
      else if (byte.class == type) {
        type = Byte.class;
      }
      else if (char.class == type) {
        type = Character.class;
      }
    }

    final ValueTypeHandler<?> typeHandler = valueTypeHandlerMap.get(type);
    if (typeHandler == null) {
      throw new IllegalArgumentException(String.format("Not defined value type handler: %s", type.getName()));
    }

    return typeHandler;
  }

  void setValue(Cell cell, Object object) throws IllegalAccessException {
    field.set(object, valueTypeHandler.getValue(cell));
  }
}
