package com.example.api.controllers;

import com.example.api.persona.IPersonaDao;
import com.example.api.persona.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class PersonaController {
    @Autowired
    private IPersonaDao persodao;
    @GetMapping("/listar")
    public List<Persona> listar(){
        return persodao.findAll();
    }
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public void crear(@RequestBody Persona persona){
        persodao.save(persona);
    }
    @GetMapping("/buscar/{id}")
    public Persona buscarPersona(@PathVariable  Long id){
        return persodao.findById(id).orElse(null);
    }
    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Persona actualizar(@RequestBody Persona persona, @PathVariable Long id){
        return persodao.findById(id)
                .map(persona1 -> {
                    persona1.setNombre(persona.getNombre());
                    persona1.setCorreo(persona.getCorreo());
                    return persodao.save(persona1);
                })
                .orElseGet(()->{
                    persona.setId(id);
                    return persodao.save(persona);
                });
    }
    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long id){
        persodao.deleteById(id);
    }


}
