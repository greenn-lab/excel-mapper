package greennlab.excel.mapper.types;

import org.apache.poi.ss.usermodel.Cell;

public interface ValueTypeHandler<T> {

  boolean matches(Class<?> type);

  T getValue(Cell cell);

}
