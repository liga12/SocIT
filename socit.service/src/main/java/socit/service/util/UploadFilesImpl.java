package socit.service.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadFilesImpl implements UploadFiles {

    private String appPath = "/home/liga/Downloads/Uploads/";

    @Override
    public String getFullSavePath(String saveDirectory) {
        if (saveDirectory.startsWith("/")) {
            saveDirectory = saveDirectory.substring(1);
        }
        String[] pach = saveDirectory.split("/");
        String fullSavePath = appPath;
        for (String s : pach) {
            fullSavePath += "/" + s;
            File fileSaveDir = new File(fullSavePath);
            fileSaveDir.mkdir();
        }
        return fullSavePath;
    }
}
