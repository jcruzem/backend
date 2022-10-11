package org.ssglobal.lms.controller;

import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.lms.model.tables.pojos.Lecture;
import org.ssglobal.lms.model.tables.pojos.StudentLoad;
import org.ssglobal.lms.service.LectureService;
import org.ssglobal.lms.service.StudentLoadService;

@RestController
@RequestMapping("/api/studentload")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class StudentLoadController {
	@Autowired
	private final StudentLoadService service;
    public StudentLoadController(StudentLoadService service) {
        this.service = service;
    }
    
    @PostMapping("/insert")
    public ResponseEntity<JSONObject> add(@RequestBody StudentLoad student_load) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insert(student_load);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{sload_id}")
    public ResponseEntity<JSONObject> edit(@PathVariable("sload_id") int sload_id, @RequestBody StudentLoad student_load) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
        	return service.edit(sload_id, student_load);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        
        return service.getAll();
       
    }
    
    @GetMapping("/{sload_id}")
    public ResponseEntity<?> getById(@PathVariable("sload_id") int sload_id) {
       
       return service.getById(sload_id);
       
    }

}
