package com.yo.minimal.rest.utility;

import com.yo.minimal.rest.models.services.interfaces.IUploadFilePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageUpload {

    @Autowired
    private IUploadFilePhoto iUploadFilePhoto;

    public String uploadFile(@Nullable MultipartFile photo, String nameMethod) {
        String uniqueFileName = "";

        if (photo != null) {
            try {
                uniqueFileName = iUploadFilePhoto.copy(photo, nameMethod);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            uniqueFileName = "nodisp.png";
        }
        return uniqueFileName;
    }

    public boolean deleteFile(String fileName, String nameMethod) {
        if (fileName.equals("nodisp.png")){
            return false;
        }
        return iUploadFilePhoto.delete(fileName, nameMethod);
    }

}
