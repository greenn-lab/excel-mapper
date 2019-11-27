package greennlab.excel.mapper.context;

import greennlab.excel.mapper.ExcelMapper;
import greennlab.excel.mapper.annotation.SheetMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CachedExcelMapperContext implements ExcelMapperContext {
  private Map<Class<?>, ExcelMapper<?>> cachedMappers;

  public CachedExcelMapperContext() {
    cachedMappers = Collections.synchronizedMap(new HashMap<>());
  }

  @Override
  public <T> ExcelMapper<T> findMapperBy(Class<T> clazz) {
    if (cachedMappers.containsKey(clazz))
      return (ExcelMapper<T>) cachedMappers.get(clazz);

    if (!isSheetMapping(clazz))
      throw new IllegalArgumentException();

    final ExcelMapper<T> newMapper = new ExcelMapper<>(clazz);

    cachedMappers.put(clazz, newMapper);
    return newMapper;
  }

  private boolean isSheetMapping(Class<?> clazz) {
    return clazz.getAnnotationsByType(SheetMapping.class).length > 0;
  }
}
