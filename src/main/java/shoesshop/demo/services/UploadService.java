package shoesshop.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class UploadService {
    @Value("${config.upload_folder}")
    String UPLOAD_FOLDER;
    public String upload(MultipartFile file) {
        String relativeFilePath =null;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();//2021
        int month = localDate.getMonthValue();//06
        String subFolder = month+"_"+year+"/";//06_2021/
        String fullUploadDir = UPLOAD_FOLDER+subFolder;
        File checkDir = new File(fullUploadDir);
        if (!checkDir.exists() || checkDir.isFile()) {
            //tạo mới folder
            checkDir.mkdir();
        }

        try {
            relativeFilePath = subFolder + Instant.now().getEpochSecond() +file.getOriginalFilename();
            Files.write(Paths.get(UPLOAD_FOLDER+relativeFilePath), file.getBytes());
        } catch (Exception e) {
            System.out.println("cannot upload file");
            e.printStackTrace();
            return null;
        }

        return relativeFilePath;
    }
}
