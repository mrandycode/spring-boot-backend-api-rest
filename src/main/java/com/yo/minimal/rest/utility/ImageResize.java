package com.yo.minimal.rest.utility;

import com.yo.minimal.rest.constants.Constants;
import com.yo.minimal.rest.dto.ResponseJ;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageResize {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ResponseJ simpleResizeImage(String rootPath) {

        ResponseJ responseJ = new ResponseJ();
        int targetWidth = Constants.IMAGE_WIDTH_SMALL;
        int targetHeight = Constants.IMAGE_HEIGHT_SMALL;
        BufferedImage newImage;
        File inFile = new File(rootPath);
        String typeFile = getTypeFile(rootPath);

        try {

            newImage = ImageIO.read(inFile);
            BufferedImage imageToFile = Scalr.resize(newImage, targetWidth, targetHeight);
            ImageIO.write(imageToFile, typeFile, new File(rootPath));

            responseJ.setCod(String.valueOf(HttpStatus.OK.value()));
            responseJ.setMessage(Constants.MESSAGE_IMAGE_RESIZE_SUCCESS);

        } catch (Exception ex) {
            responseJ.setCod(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            responseJ.setCod(Constants.MESSAGE_IMAGE_RESIZE_ERROR.concat(rootPath));
            log.error(Constants.MESSAGE_IMAGE_RESIZE_ERROR.concat(rootPath + ex));
        }

        return responseJ;

    }

    private String getTypeFile(String rootPath) {
        return rootPath.substring(rootPath.length() - 3);
    }
}
