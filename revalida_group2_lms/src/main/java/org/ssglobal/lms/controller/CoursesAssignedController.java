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
import org.ssglobal.lms.model.tables.pojos.Course;
import org.ssglobal.lms.model.tables.pojos.CoursesAssigned;
import org.ssglobal.lms.service.CourseService;
import org.ssglobal.lms.service.CoursesAssignedService;

@RestController
@RequestMapping("/api/courseassigned")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class CoursesAssignedController {
	@Autowired
	private final CoursesAssignedService service;

	public CoursesAssignedController(CoursesAssignedService service) {
        this.service = service;
    }
	
	@PostMapping("/insert")
    public ResponseEntity<JSONObject> add(@RequestBody CoursesAssigned courses_assigned) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insert(courses_assigned);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{courses_assigned_id}")
    public ResponseEntity<JSONObject> edit(@PathVariable("courses_assigned_id") int courses_assigned_id, @RequestBody CoursesAssigned courses_assigned) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
        	return service.setStatus(courses_assigned_id, courses_assigned);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        
        return service.getAll();
       
    }
    
    @GetMapping("/{courses_assigned_id}")
    public ResponseEntity<?> get (@PathVariable("courses_assigned_id") int courses_assigned_id) {
       
       return service.getByID(courses_assigned_id);
       
    }

}
