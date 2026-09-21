package edu.proyecto.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import edu.proyecto.config.FileStorageConfig;
import edu.proyecto.entity.ArchivoEntity;
import edu.proyecto.repository.ArchivoRepository;
import edu.proyecto.service.ArchivoService;

import org.springframework.web.multipart.MultipartFile;

@Service
public class ArchivoServiceImpl implements ArchivoService {
    private Path fileStorageLocation = null;

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    public void FileStorageServiceImple(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio donde se almacenarán los archivos subidos.", ex);
        }
    }

    @Override
    public ArchivoEntity obtenerArchivo(String idArchivo) {
        return archivoRepository.findById(idArchivo).get();
    }

    public ArchivoEntity guardarArchivo(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();
        String extension = fileName.substring(fileName.lastIndexOf("."));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("El nombre del archivo contiene una secuencia de ruta inválida " + fileName);
            }

            String nombreArchivo = UUID.randomUUID().toString();
            String nombreArchivoConExtension = nombreArchivo + extension;
            
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(nombreArchivoConExtension);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            // GUARDAR ARCHIVO
            ArchivoEntity archivoEntity = new ArchivoEntity();

            // archivoEntity.setIdArchivo(UUID.randomUUID().toString());
            archivoEntity.setIdArchivo(nombreArchivo);
            archivoEntity.setNombre(fileName);
            archivoEntity.setTamanio(size);
            archivoEntity.setTipo(contentType);
            archivoEntity.setExtension(extension);
            archivoEntity.setRuta(nombreArchivoConExtension);
            archivoEntity.setActivo(true);
    
            archivoRepository.save(archivoEntity);

            return archivoEntity;
            
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + fileName + ". Por favor intente de nuevo!", ex);
        }
    }
    
    @Override
    public Resource descargarArchivo(String idArchivo) {
        
        ArchivoEntity archivo = archivoRepository.findById(idArchivo).get();

        if(archivo == null){
            throw new RuntimeException("El archivo no existe");
        }

        try {
            Path filePath = Paths.get(this.fileStorageLocation.toString(), archivo.getRuta()).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Archivo no encontrado " + archivo.getRuta());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Archivo no encontrado " + archivo.getRuta(), ex);
        }
    }

    
}
