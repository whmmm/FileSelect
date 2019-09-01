package pers.zero.upload.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pers.zero.upload.api.domain.entity.ResFile;

/**
 * @author Zero
 */
@SpringBootApplication
@MapperScan("pers.zero.upload.api.**.dao")
public class UploadApiApplication {

    public static void main(String[] args) {
        ResFile file = new ResFile();
        file.getFileName();
        SpringApplication.run(UploadApiApplication.class, args);
    }
}
