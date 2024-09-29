package com.producto.gestion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
public class productoController {
    @Autowired
    private ProductoRepository ProductoRepository;

    // Obtener todos los productos
    @GetMapping
    public List<producto> obtenerTodosLosproductos() {
        return ProductoRepository.findAll();
    }

    // Agregar un nuevo producto
    @PostMapping
    public ResponseEntity<producto> agregarproducto(@RequestBody producto producto) {
        producto nuevoproducto = ProductoRepository.save(producto);
        return new ResponseEntity<>(nuevoproducto, HttpStatus.CREATED);
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<producto> obtenerproductoPorId(@PathVariable Long id) {
        return ProductoRepository.findById(id)
                .map(producto -> new ResponseEntity<>(producto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<producto> actualizarproducto(@PathVariable Long id, @RequestBody producto productoActualizado) {
        return ProductoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescripcion(productoActualizado.getDescripcion());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setDisponible(productoActualizado.isDisponible());
                    producto actualizado = ProductoRepository.save(producto);
                    return new ResponseEntity<>(actualizado, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarproducto(@PathVariable Long id) {
        if (ProductoRepository.existsById(id)) {
            ProductoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Buscar productos por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<producto>> buscarproductos(@RequestParam String nombre) {
        List<producto> productos = ProductoRepository.findByNombreContaining(nombre);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Obtener productos disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<producto>> obtenerproductosDisponibles() {
        List<producto> productosDisponibles = ProductoRepository.findByDisponible(true);
        return new ResponseEntity<>(productosDisponibles, HttpStatus.OK);
    }
    
}
