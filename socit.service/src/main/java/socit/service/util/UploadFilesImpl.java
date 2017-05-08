package socit.service.util;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Log4j
public class UploadFilesImpl implements UploadFiles {

    private String appPath = "/home/liga/Downloads/Uploads/";

    @Override
    public String getFullSavePath(String saveDirectory) {
        log.debug("Request saveDirectory = "+saveDirectory);
        if (saveDirectory.startsWith("/")) {
            saveDirectory = saveDirectory.substring(1);
        }
        String[] pach = saveDirectory.split("/");
        String fullSavePath = appPath;
        for (String s : pach) {
            fullSavePath += "/" + s;
            File fileSaveDir = new File(fullSavePath);
            fileSaveDir.mkdir();
            log.debug("Creade directory "+fullSavePath);
        }
        log.debug("Return fullSavePath = "+fullSavePath);
        return fullSavePath;
    }
}
