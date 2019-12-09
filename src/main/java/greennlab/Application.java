package greennlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"greennlab.excel.mapper.config", "greennlab.excel.mapper.context", "greennlab.excel.mapper.annotation"})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

}
