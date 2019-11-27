package greennlab.excel.mapper.context;

import greennlab.excel.mapper.ExcelMapper;

public class GlobalClasspathExcelMapperContext implements ExcelMapperContext {

  public GlobalClasspathExcelMapperContext() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> ExcelMapper<T> findMapperBy(Class<T> clazz) {
    throw new UnsupportedOperationException();
  }
}
