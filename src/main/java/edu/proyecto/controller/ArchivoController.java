package edu.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.proyecto.entity.ArchivoEntity;
import edu.proyecto.service.ArchivoService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api")
@Tag(name = "Archivo", description = "Api Subir-Bajar archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @GetMapping("/v1/archivos/{idArchivo}")
    public ResponseEntity<ArchivoEntity> obtenerArchivo(@PathVariable String idArchivo) {
        return ResponseEntity.status(HttpStatus.OK).body(archivoService.obtenerArchivo(idArchivo));
    }

    @GetMapping("/v1/archivos/{idArchivo}/descargar")
    public ResponseEntity<Resource> downloadFile(@PathVariable String idArchivo) {

        Resource resource = archivoService.descargarArchivo(idArchivo);
        String contentType = "application/pdf";
        
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @PostMapping("/v1/archivos")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Solo se permiten archivos PDF");
        }
        ArchivoEntity archivoEntity = archivoService.guardarArchivo(file);

        return ResponseEntity.status(HttpStatus.OK).body(archivoEntity);
    }


    @ControllerAdvice
    public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

        @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
        public String handleMaxSizeException(MaxUploadSizeExceededException exc) {
            return "Archivo demasiado grande. ¡Intenta subir un archivo más pequeño!";
        }
    }
}