package com.sansalibros.api.repository;

import com.sansalibros.api.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends MongoRepository<Libro, String> {

    // Búsqueda exacta por autor
    List<Libro> findByAutor(String autor);

    // Búsqueda parcial (case-insensitive) en título o autor
    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor);
}