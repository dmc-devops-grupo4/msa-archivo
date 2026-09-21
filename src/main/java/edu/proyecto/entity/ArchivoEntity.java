package edu.proyecto.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="archivo")
@Schema(name = "archivo", description = "Entity de Archivo")
public class ArchivoEntity {
    @Id
    @Column(name = "id_archivo")
    private String idArchivo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tamanio")
    private Long tamanio;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "extension")
    private String extension;

    @Column(name = "ruta")
    private String ruta;

    @Column(name = "activo")
    private boolean activo;
}
