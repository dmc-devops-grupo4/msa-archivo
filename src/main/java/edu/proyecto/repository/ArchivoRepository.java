package edu.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.proyecto.entity.ArchivoEntity;

@Repository
public interface ArchivoRepository extends JpaRepository<ArchivoEntity, String> {
    
}


