
package com.porfolio.Porfolio.Controller;

import com.porfolio.Porfolio.Dto.dtoProyecto;
import com.porfolio.Porfolio.Entity.Proyecto;
import com.porfolio.Porfolio.Security.Controller.Mensaje;
import com.porfolio.Porfolio.Service.SProyecto;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("proyecto")
@CrossOrigin(origins="https://bkporfoliotest-be649.web.app/")

public class CProyecto {
    
    
    @Autowired
    SProyecto sProyecto;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Proyecto>> list(){
        List<Proyecto> list= sProyecto.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<Proyecto> getById(@PathVariable("id") int id){
        if(!sProyecto.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Proyecto proyecto = sProyecto.getOne(id).get();
        return new ResponseEntity(proyecto, HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody dtoProyecto dtopro){
        if(StringUtils.isBlank(dtopro.getNombre())){
         return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(sProyecto.existsByNombre(dtopro.getNombre())){
            return new ResponseEntity (new Mensaje("Experiencia en existencia"),HttpStatus.BAD_REQUEST);
        }
        
        Proyecto proyecto = new Proyecto(dtopro.getNombre(), dtopro.getDescripcion(), dtopro.getImg());
        sProyecto.save(proyecto);
        
        return new ResponseEntity(new Mensaje ("Experiencia agregada con exito"), HttpStatus.OK);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody dtoProyecto dtopro){
        
        if(!sProyecto.existsById(id)){
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }
        
        if(sProyecto.existsByNombre(dtopro.getNombre())&& sProyecto.getByNombre(dtopro.getNombre()).get().getId() != id){
            return new ResponseEntity(new Mensaje("Experiencia ya existe"), HttpStatus.BAD_REQUEST);
        }
        
        if(StringUtils.isBlank(dtopro.getNombre())){
            return new ResponseEntity(new Mensaje("Nombre obligatorio"), HttpStatus.BAD_REQUEST);
        }
        
        
        Proyecto proyecto = sProyecto.getOne(id).get();
        proyecto.setNombre(dtopro.getNombre());
        proyecto.setDescripcion(dtopro.getDescripcion());
        
        sProyecto.save((proyecto));
        return new ResponseEntity(new Mensaje("Experiencia actualizada con exito"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!sProyecto.existsById(id)){
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }
        
        sProyecto.delete(id);
        
        return new ResponseEntity(new Mensaje("Experiencia eliminada"), HttpStatus.OK);
    }
    
    
}
