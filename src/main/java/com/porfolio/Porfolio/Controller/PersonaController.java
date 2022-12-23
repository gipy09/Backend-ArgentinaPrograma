package com.porfolio.Porfolio.Controller;

import com.porfolio.Porfolio.Dto.dtoPersona;
import com.porfolio.Porfolio.Entity.Persona;
import com.porfolio.Porfolio.Security.Controller.Mensaje;
import com.porfolio.Porfolio.Service.ImpPersonaService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
@CrossOrigin(origins="https://bkporfoliotest-be649.web.app/")

public class PersonaController {

    @Autowired
    ImpPersonaService personaService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list(){
        List<Persona> list= personaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id){
        if(!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Persona persona = personaService.getOne(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }
    
    @PostMapping("/usuario/crear")
    public String createPersona (@RequestBody Persona persona){
        personaService.save(persona);
        return "Usuario creado";
    }
    
      
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody dtoPersona dtoperso){
        
        if(!personaService.existsById(id)){
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }
        
        if(personaService.existsByNombre(dtoperso.getNombre())&& personaService.getByNombre(dtoperso.getNombre()).get().getId() != id){
            return new ResponseEntity(new Mensaje("Experiencia ya existe"), HttpStatus.BAD_REQUEST);
        }
        
        if(StringUtils.isBlank(dtoperso.getNombre())){
            return new ResponseEntity(new Mensaje("Nombre obligatorio"), HttpStatus.BAD_REQUEST);
        }
        
        
        Persona persona = personaService.getOne(id).get();
        persona.setNombre(dtoperso.getNombre());
        persona.setDescripcion(dtoperso.getDescripcion());
        persona.setImg(dtoperso.getImg());
        persona.setTitulo(dtoperso.getTitulo());
        
        personaService.save((persona));
        return new ResponseEntity(new Mensaje("Experiencia actualizada con exito"), HttpStatus.OK);
    }
    
  
    

}
