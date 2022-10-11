package org.ssglobal.lms.controller;

import org.jooq.DSLContext;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.lms.model.tables.pojos.Degree;
import org.ssglobal.lms.model.tables.pojos.Lecture;
import org.ssglobal.lms.model.tables.pojos.Semester;
import org.ssglobal.lms.service.DegreeService;
import org.ssglobal.lms.service.LectureService;

@RestController
@RequestMapping("/api/lecture")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class LectureController {
	@Autowired
	private final LectureService service;
    public LectureController(LectureService service) {
        this.service = service;
    }
    
    @PostMapping("/insert")
    public ResponseEntity<JSONObject> add(@RequestBody Lecture lecture) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insert(lecture);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{lecture_id}")
    public ResponseEntity<JSONObject> edit(@PathVariable("lecture_id") int lecture_id, @RequestBody Lecture lecture) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
        	return service.edit(lecture_id, lecture);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        
        return service.getAll();
       
    }
    
    @GetMapping("/{lecture_id}")
    public ResponseEntity<?> getById(@PathVariable("lecture_id") int lecture_id) {
       
       return service.getById(lecture_id);
       
    }
    

}
