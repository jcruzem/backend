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
import org.ssglobal.lms.model.tables.pojos.Attendance;
import org.ssglobal.lms.model.tables.pojos.Grade;
import org.ssglobal.lms.service.AttendanceService;
import org.ssglobal.lms.service.GradeService;

@RestController
@RequestMapping("/api/grade")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class GradeController {
	@Autowired
	private final GradeService service;
    public GradeController(GradeService service) {
        this.service = service;
    }
    
    @PostMapping("/insert")
    public ResponseEntity<JSONObject> add(@RequestBody Grade grade) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")
        		|| authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("professor")) {
            return service.insert(grade);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{grade_id}")
    public ResponseEntity<JSONObject> edit(@PathVariable("grade_id") int grade_id, @RequestBody Grade grade) {
        
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")
         		|| authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("professor")) {
        	return service.edit(grade_id, grade);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/delete/{grade_id}")
    public ResponseEntity<JSONObject> delete(@PathVariable("grade_id") int grade_id) {
        
   	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")
        		|| authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("professor")) {
       	return service.delete(grade_id);
       }
    
       JSONObject record = new JSONObject();
       record.put("message", "ADMIN ONLY");
       return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
   }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        
        return service.getAll();
       
    }
    
    @GetMapping("/{grade_id}")
    public ResponseEntity<?> getById(@PathVariable("grade_id") short grade_id) {
       
       return service.getById(grade_id);
       
    }

}
