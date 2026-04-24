package com.sansalibros.api.service;

import com.sansalibros.api.model.Libro;
import com.sansalibros.api.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> obtenerLibros(String search) {
        if (search != null && !search.isEmpty()) {
            // Retorna coincidencias parciales en Título o Autor si existe el parámetro 'search'
            return libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(search, search);
        }
        // Si no hay parámetro, retorna todos
        return libroRepository.findAll();
    }

    public Libro crearLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public List<Libro> obtenerPorAutor(String autor) {
        return libroRepository.findByAutor(autor);
    }
}