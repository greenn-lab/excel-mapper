package greennlab.excel.mapper.context;

import greennlab.excel.mapper.ExcelMapper;

public interface ExcelMapperContext {

  <T> ExcelMapper<T> findMapperBy(Class<T> clazz);

}
