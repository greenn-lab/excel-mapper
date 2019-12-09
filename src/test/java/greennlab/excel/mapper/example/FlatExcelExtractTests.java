package greennlab.excel.mapper.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("엑셀파일에서_마구잡이로_출력")
class FlatExcelExtractTests {
  private static final String SHEET_NAME = "겨울왕국2";
  private static Workbook workbook;
  private static Sheet sheet;

  @BeforeAll
  static void init() throws IOException {
    logger.info("start!");
    workbook = new XSSFWorkbook(new ClassPathResource("frozen2.xlsx").getInputStream());
    sheet = workbook.getSheet(SHEET_NAME);
  }

  @AfterAll
  static void cleanUp() throws IOException {
    if (workbook != null) {
      workbook.close();
    }

    logger.info("bye~");
  }

  @Test
  @DisplayName("데이터를 꺼내봐요.")
  void shouldExtractFromFlatExcel() {
    final Row row = sheet.getRow(4);
    assertThat(row).isNotNull();

    for (final Cell cell : row) {
      final String cellName = new CellReference(cell).formatAsString();

      if (cell.getCellType() == CellType.STRING)
          logger.debug("string cell {}, value: {}", cellName, cell.getStringCellValue());
      else if (cell.getCellType() == CellType.NUMERIC)
          logger.debug("number cell {}, value: {}", cellName, cell.getNumericCellValue());
      else
          logger.debug("unknown cell {} ({})", cellName, cell.getCellType().toString());
    }
  }

  @Test
  @DisplayName("데이터의 범위를 찾아봐요.")
  void shouldGetExcelDataRange() {
    final int lastRowNum = sheet.getLastRowNum();
    assertThat(lastRowNum).isGreaterThan(4);

    final Row row = sheet.getRow(lastRowNum);
    final short lastCellNum = row.getLastCellNum();
    assertThat(lastCellNum).isGreaterThan((short) 3);

    logger.debug("last row {}, and column {}", lastRowNum, lastCellNum);
  }

}
