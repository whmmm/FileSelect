package org.zero.uploadapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zero.uploadapi.domain.entity.ResFile;

/**
 * @author Zero
 */
@SpringBootApplication
@MapperScan("org.zero.**.dao")
public class UploadApiApplication {

    public static void main(String[] args) {
        ResFile file = new ResFile();
        file.getFileName();
        SpringApplication.run(UploadApiApplication.class, args);
    }
}
