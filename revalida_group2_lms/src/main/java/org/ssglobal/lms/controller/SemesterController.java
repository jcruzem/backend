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
import org.ssglobal.lms.model.tables.pojos.Account;
import org.ssglobal.lms.model.tables.pojos.Semester;
import org.ssglobal.lms.service.AccountService;
import org.ssglobal.lms.service.SemesterService;

@RestController
@RequestMapping("/api/semester")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class SemesterController {
	@Autowired
	private final SemesterService service;

    public SemesterController(SemesterService service) {
        this.service = service;
    }
    
    @GetMapping("/currentsem")
    public ResponseEntity<?> getCurrentSem(){
    	return service.getCurrentSemesterr();
    }
    
    @PostMapping("/insert")
    public ResponseEntity<JSONObject> addSemester(@RequestBody Semester semester) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insertSemester(semester);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{semester_id}")
    public ResponseEntity<JSONObject> editSemester(@PathVariable("semester_id") int semester_id, @RequestBody Semester semester) {
        
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
        	return service.editSemester(semester_id, semester);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/makecurrrent/{semester_id}")
    public ResponseEntity<JSONObject> makeCurrentSem(@PathVariable("semester_id") int semester_id) {
        return service.makeCurrentSemester(semester_id);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getSemesters() {
        
        return service.getSemesters();
       
    }
    
    @GetMapping("/{semester_id}")
    public ResponseEntity<?> getSemesterById(@PathVariable("semester_id") int semester_id) {
       
       return service.getSemesterByID(semester_id);
       
    }

}
