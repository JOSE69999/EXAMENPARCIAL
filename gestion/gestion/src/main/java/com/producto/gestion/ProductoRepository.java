package com.producto.gestion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<producto, Long> {
    // Buscar productos por nombre
    List<producto> findByNombreContaining(String nombre);
    
    // Método alternativo para buscar productos por nombre usando @Query
    @Query("SELECT p FROM producto p WHERE p.nombre LIKE %:nombre%")
    List<producto> buscarPorNombre(@Param("nombre") String nombre);
    
    // Filtrar productos por disponibilidad
    List<producto> findByDisponible(boolean disponible);
    
    // Contar productos disponibles
    long countByDisponible(boolean disponible);
}
