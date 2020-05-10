package com.yo.minimal.rest.models.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadFilePhoto {

    //Método para cargar la imagen.
    public Resource load (String file, String nameMethod) throws IOException;

    //Método para copiar la imagen.
    public String copy (MultipartFile file, String nameMethod) throws IOException;

    //Método para eliminar la imagen.
    public boolean delete (String fileName, String nameMethod);

    //método para borrar directorio y archivos cada vez que inicie la app.
    public void deleteAll();

    public void init () throws IOException;
}
