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
import org.ssglobal.lms.model.tables.pojos.StudentLoad;
import org.ssglobal.lms.service.AttendanceService;
import org.ssglobal.lms.service.StudentLoadService;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class AttendanceController {
	@Autowired
	private final AttendanceService service;
    public AttendanceController(AttendanceService service) {
        this.service = service;
    }
    
    @PostMapping("/insert")
    public ResponseEntity<JSONObject> add(@RequestBody Attendance attendance) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")
        		|| authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("professor")) {
            return service.insert(attendance);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{attendance_id}")
    public ResponseEntity<JSONObject> editSemester(@PathVariable("attendance_id") short attendance_id, @RequestBody Attendance attendance) {
        
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")
         		|| authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("professor")) {
        	return service.edit(attendance_id, attendance);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/delete/{attendance_id}")
    public ResponseEntity<JSONObject> deleteSemesterr(@PathVariable("attendance_id") short attendance_id) {
        
   	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")
        		|| authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("professor")) {
       	return service.delete(attendance_id);
       }
    
       JSONObject record = new JSONObject();
       record.put("message", "ADMIN ONLY");
       return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
   }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        
        return service.getAll();
       
    }
    
    @GetMapping("/{attendance_id}")
    public ResponseEntity<?> getById(@PathVariable("attendance_id") short attendance_id) {
       
       return service.getById(attendance_id);
       
    }

}
