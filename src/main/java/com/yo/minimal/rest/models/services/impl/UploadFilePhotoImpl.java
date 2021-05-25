package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.constants.Constants;
import com.yo.minimal.rest.models.services.interfaces.IUploadFilePhoto;
import com.yo.minimal.rest.utility.ImageResize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFilePhotoImpl implements IUploadFilePhoto {

    @Autowired
    private ImageResize imageResize;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Resource load(String fileName, String nameMethod) throws IOException {
        Path pathPhoto = getPath(fileName, nameMethod).normalize();
        log.info("pathPhoto: " + pathPhoto);
        Resource resource = new UrlResource(pathPhoto.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            if (nameMethod.equals(Constants.custoType)) {
                pathPhoto = getPath(Constants.noDispImage, Constants.custoType);
            }
            if (nameMethod.equals(Constants.itemType)) {
                pathPhoto = getPath(Constants.noDispImage, Constants.itemType);
            }
            resource = new UrlResource(pathPhoto.toUri());
        }

        return resource;
    }

    @Override
    public String copy(MultipartFile file, String nameMethod) throws IOException {

        //Nombre unico de archivo con la funcion UUID.
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        //Path relativo
        Path rootPath = getPath(uniqueFileName, nameMethod);

        log.info("Archivo: ".concat(rootPath.toString()));
        log.info("Ruta Absoluta: ".concat(rootPath.toString()));

        Files.copy(file.getInputStream(), rootPath);
        imageResize.simpleResizeImage(rootPath.toString());

        return uniqueFileName;
    }

    @Override
    public boolean delete(String fileName, String nameMethod) {
        Path rootPath = getPath(fileName, nameMethod);
        //Convertimos a archivo
        File archivo = rootPath.toFile();
        //Validamos que el archivo exista y este disponible
        if (archivo.exists() && archivo.canRead()) {
            return archivo.delete();
        }
        return false;
    }

    private Path getPath(String fileName, String nameMethod) {
        Path pathPhoto = Paths.get("null");
        if (nameMethod.equals(Constants.custoType)) {
            if (this.validateUrlImage(fileName, nameMethod)) {
                pathPhoto = Paths.get(Constants.PHOTO_CUSTO_NOT_DISP).toAbsolutePath();
            } else {
                pathPhoto = Paths.get(Constants.UPLOADS_FOLDER_CUST).resolve(fileName).toAbsolutePath();
            }

        } else if (nameMethod.equals(Constants.itemType)) {
            if (this.validateUrlImage(fileName, nameMethod)) {
                pathPhoto = Paths.get(Constants.PHOTO_ITEM_NOT_DISP).toAbsolutePath();
            } else {
                pathPhoto = Paths.get(Constants.UPLOADS_FOLDER_ITEM).resolve(fileName).toAbsolutePath();
            }

        }

        return pathPhoto;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(Constants.UPLOADS_FOLDER_CUST).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(Constants.UPLOADS_FOLDER_CUST));
        Files.createDirectory(Paths.get(Constants.UPLOADS_FOLDER_ITEM));
    }

    private boolean validateUrlImage(String fileName, String nameMethod) {

        boolean validate = false;

        if (fileName.equals(Constants.noDispImage) && nameMethod.equals(Constants.itemType)) {
            validate = true;
        }

        if (fileName.equals(Constants.noDispImage)
                && nameMethod.equals(Constants.custoType)) {
            validate = true;
        }

        return validate;

    }
}