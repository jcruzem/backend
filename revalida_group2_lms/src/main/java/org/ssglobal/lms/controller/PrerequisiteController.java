package org.ssglobal.lms.controller;

import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.lms.model.tables.pojos.Grade;
import org.ssglobal.lms.model.tables.pojos.Prerequisite;
import org.ssglobal.lms.service.GradeService;
import org.ssglobal.lms.service.PrerequisiteService;

@RestController
@RequestMapping("/api/prerequisite")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class PrerequisiteController {
	@Autowired
	private final PrerequisiteService service;
    public PrerequisiteController(PrerequisiteService service) {
        this.service = service;
    }
    
    @PostMapping("/insert")
    public ResponseEntity<JSONObject> add(@RequestBody Prerequisite prerequisite) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insert(prerequisite);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{prerequisite_id}")
    public ResponseEntity<JSONObject> edit(@PathVariable("prerequisite_id") int prerequisite_id, @RequestBody Prerequisite prerequisite) {
        
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
        	return service.edit(prerequisite_id, prerequisite);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/delete/{prerequisite_id}")
    public ResponseEntity<JSONObject> delete(@PathVariable("prerequisite_id") int prerequisite_id) {
        
   	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
       	return service.delete(prerequisite_id);
       }
    
       JSONObject record = new JSONObject();
       record.put("message", "ADMIN ONLY");
       return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
   }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        
        return service.getAll();
       
    }
    
    @GetMapping("/{prerequisite_id}")
    public ResponseEntity<?> getById(@PathVariable("prerequisite_id") int prerequisite_id) {
       
       return service.getById(prerequisite_id);
       
    }

}
