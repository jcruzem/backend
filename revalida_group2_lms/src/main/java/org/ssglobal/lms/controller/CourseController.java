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
import org.ssglobal.lms.model.tables.pojos.Degree;
import org.ssglobal.lms.service.CourseService;
import org.ssglobal.lms.service.DegreeService;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class CourseController {
	@Autowired
	private final CourseService service;

	public CourseController(CourseService service) {
        this.service = service;
    }
	
	@PostMapping("/insert")
    public ResponseEntity<JSONObject> add(@RequestBody Course course) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insertCourse(course);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{course_id}")
    public ResponseEntity<JSONObject> editCourse(@PathVariable("course_id") int course_id, @RequestBody Course course) {
      
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
        	return service.editCourse(course_id, course);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        
        return service.getCourses();
       
    }
    
    @GetMapping("/{course_id}")
    public ResponseEntity<?> get (@PathVariable("course_id") int course_id) {
       
       return service.getCourseByID(course_id);
       
    }

}
