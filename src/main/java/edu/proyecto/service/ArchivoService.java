package edu.proyecto.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import edu.proyecto.entity.ArchivoEntity;

public interface ArchivoService {
    public ArchivoEntity obtenerArchivo(String idArchivo);
    public ArchivoEntity guardarArchivo(MultipartFile file);
    public Resource descargarArchivo(String idArchivo);

}
