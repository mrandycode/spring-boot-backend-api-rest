package com.yo.minimal.rest.controllers;

import com.yo.minimal.rest.models.services.IUploadFilePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/image/")
public class ImageRestController {

    @Autowired
    private IUploadFilePhoto iUploadFilePhoto;
//
//    /***************************************
//     * @param
//     * @return Cargar Foto
//     ****************************************/
//    @PostMapping(value = "get/upload")
//    public ResponseEntity<?> viewPhoto(  @RequestParam (value = "filename", required = false) String filename
//                                              , @Valid  String type
//                                              , HttpServletRequest request) throws IOException {
//
//        Resource resource = iUploadFilePhoto.load(filename, type);
//
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//            System.out.println(contentType);
//        } catch (IOException ex) {
//            System.out.print("Could not determine file type.");
//        }
//
//        // Fallback to the default content type if type could not be determined
//        if (contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile() + "\"")
//                .body(resource);
//    }

    /***************************************
     * @param
     * @return Cargar Foto
     ****************************************/
    @GetMapping(value = "get/upload/{filename:.+}/{type}")
    public ResponseEntity<Resource> viewPhotoGet( @PathVariable String filename
            , @PathVariable String type
            , HttpServletRequest request) throws IOException {

        Resource resource = iUploadFilePhoto.load(filename, type);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            System.out.println(contentType);
        } catch (IOException ex) {
            System.out.print("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile() + "\"")
                .body(resource);
    }

}