package org.ssglobal.lms.controller;

import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.ssglobal.lms.model.tables.pojos.Account;
import org.ssglobal.lms.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class AccountController {

	@Autowired
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }
    
//     This is only for emergency registrations. Leave it commented out during prod
//  @PostMapping("/emergency/register")
//  public ResponseEntity<JSONObject> add(@RequestBody Account account) {
//  	return service.insertAdminAccount(account);
//      
//  }
    
    @GetMapping("/currentuser")
    public ResponseEntity<?> getCurrent(){
    	return service.getCurrentUser();
    }

    @PostMapping("/admin/register")
    public ResponseEntity<JSONObject> addAdminAccount(@RequestBody Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insertAdminAccount(account);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PostMapping("/student/register")
    public ResponseEntity<JSONObject> addStudentAccount(@RequestBody Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insertStudentAccount(account);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PostMapping("/professor/register")
    public ResponseEntity<JSONObject> addProfessorAccount(@RequestBody Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insertProfessorAccount(account);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PostMapping("/parent/register")
    public ResponseEntity<JSONObject> addParentAccount(@RequestBody Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.insertParentAccount(account);
        }
     
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }

    
    // Can be used by all roles
    @PutMapping("/edit/{accountId}")
    public ResponseEntity<JSONObject> editAccountBio(@PathVariable("accountId") int accountId, @RequestBody Account account) {
        return service.updateAccountBio(accountId, account);
    }
    
    @PutMapping("/changePW/{username}/{accountId}")
    public ResponseEntity<JSONObject> editAccountPW(@PathVariable("username") String username, @PathVariable("accountId") int accountId, @RequestBody JSONObject data) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication.getName().equals(username)) {
    		return service.updateAccountPassword(accountId, data);
    	}
    	JSONObject record = new JSONObject();
        record.put("message", "You don't own this account");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }
    
    @PutMapping("/generatePWtemp/{accountId}")
    public ResponseEntity<JSONObject> generateTempPW(@PathVariable("accountId") int accountId) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.generateTempPassword(accountId);
        }
    
        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        
    }

    @PutMapping("/changeActiveStatus/{accountId}")
    public ResponseEntity<JSONObject> setActive(@PathVariable("accountId") int accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.setAccountActivity(accountId);
        }

        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);

    }
    
    @PutMapping("/changeChild/{accountId}")
    public ResponseEntity<JSONObject> setChild(@PathVariable("accountId") int accountId, @RequestBody Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.setChildAccount(accountId, account);
        }

        JSONObject record = new JSONObject();
        record.put("message", "ADMIN ONLY");
        return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.getAccounts();
        }
        else {
            JSONObject record = new JSONObject();
            record.put("error", "ADMIN ONLY");
            return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") int accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.getAccountByID(accountId);
        }
        else {
            JSONObject record = new JSONObject();
            record.put("error", "ADMIN ONLY");
            return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAdmins() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.getAdminAccounts();
        }
        else {
            JSONObject record = new JSONObject();
            record.put("error", "ADMIN ONLY");
            return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/student/all")
    public ResponseEntity<?> getStudents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.getStudentAccounts();
        }
        else {
            JSONObject record = new JSONObject();
            record.put("error", "ADMIN ONLY");
            return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/professor/all")
    public ResponseEntity<?> getProfessors() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.getProfessorAccounts();
        }
        else {
            JSONObject record = new JSONObject();
            record.put("error", "ADMIN ONLY");
            return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/parent/all")
    public ResponseEntity<?> getParents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase("admin")) {
            return service.getParentAccounts();
        }
        else {
            JSONObject record = new JSONObject();
            record.put("error", "ADMIN ONLY");
            return new ResponseEntity<JSONObject>(record, HttpStatus.BAD_REQUEST);
        }
    }


    
  
    
    
    











}