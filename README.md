# excel-mapper 
객체지향 설계를 학습하고 실습용으로 만들어 봤어요.

### CRC
[https://drive.google.com/file/d/1_UxwQOfC1LntsEpKXjT7Agud4dBi3lnt/view](
https://drive.google.com/file/d/1_UxwQOfC1LntsEpKXjT7Agud4dBi3lnt/view)

### 기능 분해
- 엑셀 파일을 POJO 객체로 매핑해요.
  - 매핑 정보를 가진 Mapper 를 분석해서 캐시해두고,
  - Mapper 과 엑셀 파일을 읽어 와서,
  - Mapper 에 정의된 엑셀 시트와 행 단위로 데이터를 매핑해요.
