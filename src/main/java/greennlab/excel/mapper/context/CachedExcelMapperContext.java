package greennlab.excel.mapper.context;

import greennlab.excel.mapper.ExcelMapper;

import java.util.HashMap;
import java.util.Map;

public class CachedExcelMapperContext implements ExcelMapperContext {

  private final CachedExcelMapper cache;

  public CachedExcelMapperContext() {
    cache = new CachedExcelMapper(findAllMappersInClasspath());
  }

  @Override
  public <T> ExcelMapper<T> findMapperBy(Class<T> clazz) {
    return cache.getMapper(clazz);
  }


  private Map<Class<?>, ExcelMapper<?>> findAllMappersInClasspath() {
    Map<Class<?>, ExcelMapper<?>> result = new HashMap<>();

    // TODO 클래스패스에서 @SheetMapping 들 모두 찾기

    return result;
  }
}
