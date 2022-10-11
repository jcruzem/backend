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
import org.ssglobal.lms.model.tables.pojos.Timeslot;
import org.ssglobal.lms.service.SemesterService;
import org.ssglobal.lms.service.TimeslotService;

@RestController
@RequestMapping("/api/timeslot")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class TimeslotController {
	@Autowired
	private final TimeslotService service;
	
	public TimeslotController(TimeslotService service) {
        this.service = service;
    }
	
	@PostMapping("/insert")
    public ResponseEntity<JSONObject> addTimeslot(@RequestBody Timeslot timeslot) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insertTimeslot(timeslot);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/edit/{timeslot_id}")
    public ResponseEntity<JSONObject> edit(@PathVariable("timeslot_id") int timeslot_id, @RequestBody Timeslot timeslot) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
        	return service.editTimeslot(timeslot_id, timeslot);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getTimeslots() {
        
        return service.getTimeslots();
       
    }
    
    @GetMapping("/{timeslot_id}")
    public ResponseEntity<?> getTimeslotById(@PathVariable("timeslot_id") int timeslot_id) {
       
       return service.getTimeslotByID(timeslot_id);
       
    }
	

}
