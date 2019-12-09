package greennlab.excel.mapper.context;

import greennlab.excel.mapper.ExcelMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedExcelMapper {

  private final Map<Class<?>, ExcelMapper<?>> excelMapperCache;

  public CachedExcelMapper(Map<Class<?>, ExcelMapper<?>> excelMappers) {
    this.excelMapperCache = new ConcurrentHashMap<>(excelMappers);
  }

  public <T> ExcelMapper<T> getMapper(Class<T> clazz) {
    if (!excelMapperCache.containsKey(clazz)) {
      throw new IllegalArgumentException(String.format("No mapper matches %s", clazz.getName()));
    }

    return (ExcelMapper<T>) excelMapperCache.get(clazz);
  }
}
