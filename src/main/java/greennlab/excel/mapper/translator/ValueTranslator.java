package greennlab.excel.mapper.translator;

import org.apache.poi.ss.usermodel.Cell;

public interface ValueTranslator<T> {

  T translate(Cell cell);

}
