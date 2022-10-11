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
import org.ssglobal.lms.model.tables.pojos.Degree;
import org.ssglobal.lms.model.tables.pojos.Semester;
import org.ssglobal.lms.service.DegreeService;
import org.ssglobal.lms.service.SemesterService;

@RestController
@RequestMapping("/api/degree")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class DegreeController {
	@Autowired
	private final DegreeService service;

    public DegreeController(DegreeService service) {
        this.service = service;
    }
    
    @PostMapping("/insert")
    public ResponseEntity<JSONObject> addDegree(@RequestBody Degree degree) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insertDegree(degree);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{degree_id}")
    public ResponseEntity<JSONObject> edit(@PathVariable("degree_id") int degree_id, @RequestBody Degree degree) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
        	return service.editDegree(degree_id, degree);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getDegrees() {
        
        return service.getDegrees();
       
    }
    
    @GetMapping("/{degree_id}")
    public ResponseEntity<?> getDegreeById(@PathVariable("degree_id") int degree_id) {
       
       return service.getDegreeByID(degree_id);
       
    }

}
