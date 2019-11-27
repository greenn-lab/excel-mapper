# excel-mapper 
객체지향 설계를 학습하고 실습용으로 만들어 봤어요.

### 내용
- Mapper 를 정의하고 @SheetMapping 을 정의 해요.
- 정의된 Mapper 당 하나의 시트에 대응 되는 거에요.
  - name 속성은 엑셀 시트 이름을 넣고요
  - rowStarter 속성은 데이터 시작하는 행 번호를 정해요.
- Mapper 의 필드는 `@ColMapping` 으로 엑셀의 한 열(列)을 정의 해요.

`@ColMapping("B")` 엑셀의 두번째 열(列)을 정의 한거에요.

```java
@SheetMapping(name = "겨울왕국2", rowStarter = 4) public class
FrozenCharacter {

  @ColMapping("A")
  private String name;

  @ColMapping("B")
  private GenderType gender;

  // ...

}
```
