package com.sansalibros.api.controller;

import com.sansalibros.api.model.Libro;
import com.sansalibros.api.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibroController {

    @Autowired
    private LibroService libroService;

    // 1. /libros (Soporta query parameter 'search')
    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> verLibros(@RequestParam(required = false) String search) {
        List<Libro> libros = libroService.obtenerLibros(search);
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    // 2. /crearLibro
    @PostMapping("/crearLibro")
    public ResponseEntity<?> crearLibro(@RequestBody Libro libro) {
        // Validaciones obligatorias exigidas por el certamen
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty() ||
                libro.getAutor() == null || libro.getAutor().trim().isEmpty() ||
                libro.getCategoria() == null || libro.getCategoria().trim().isEmpty()) {
            return new ResponseEntity<>("Faltan campos obligatorios", HttpStatus.BAD_REQUEST);
        }
        if (libro.getIsbn() == null || libro.getIsbn().length() != 13) {
            return new ResponseEntity<>("El ISBN debe tener exactamente 13 caracteres", HttpStatus.BAD_REQUEST);
        }
        if (libro.getPaginas() <= 10) {
            return new ResponseEntity<>("El libro debe tener más de 10 páginas", HttpStatus.BAD_REQUEST);
        }

        Libro nuevoLibro = libroService.crearLibro(libro);
        return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED); // 201 Created
    }

    // 3. /libros/:autor
    @GetMapping("/libros/{autor}")
    public ResponseEntity<List<Libro>> verLibrosPorAutor(@PathVariable String autor) {
        List<Libro> libros = libroService.obtenerPorAutor(autor);
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }
}