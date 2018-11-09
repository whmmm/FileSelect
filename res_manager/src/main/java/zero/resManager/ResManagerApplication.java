package zero.resManager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zero.resManager.domain.entity.ResFile;

@SpringBootApplication
@MapperScan({"zero.resManager.domain.dao"})
public class ResManagerApplication {

    public static void main(String[] args) {
        ResFile file = new ResFile();
        file.getFileName();
        SpringApplication.run(ResManagerApplication.class, args);
    }
}
