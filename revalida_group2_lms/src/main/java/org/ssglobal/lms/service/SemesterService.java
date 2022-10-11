package org.ssglobal.lms.service;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.lms.model.Tables;
import org.ssglobal.lms.model.tables.pojos.Account;
import org.ssglobal.lms.model.tables.pojos.Semester;

@Service
public class SemesterService {
	@Autowired
	private DSLContext dslContext;
    public SemesterService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<Semester> getCurrentSemesterr() {
    	return new ResponseEntity<Semester>(
    			dslContext.selectFrom(Tables.SEMESTER)
    			.where(Tables.SEMESTER.IS_CURRENT.eq(true)).fetchOneInto(Semester.class),
    			HttpStatus.OK);
    }
    
    public ResponseEntity<JSONObject> insertSemester(Semester semester) {
        try {

            dslContext.insertInto(
                    Tables.SEMESTER,
                    Tables.SEMESTER.START_DATE,
                    Tables.SEMESTER.IS_CURRENT,
                    Tables.SEMESTER.STARTING_YEAR,
                    Tables.SEMESTER.ENDING_YEAR,
                    Tables.SEMESTER.SEM_ORDER
            		)
                    .values(
                    		semester.getStartDate(),
                    		false,
                    		semester.getStartingYear(),
                    		semester.getEndingYear(),
                    		semester.getSemOrder()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("semester has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> editSemester(int semester_id, Semester semester) {
    	try {
    		
    		
    		dslContext.update(Tables.SEMESTER)
            .set(Tables.SEMESTER.START_DATE, semester.getStartDate())
            .set(Tables.SEMESTER.STARTING_YEAR, semester.getStartingYear())
            .set(Tables.SEMESTER.ENDING_YEAR, semester.getEndingYear())
            .set(Tables.SEMESTER.SEM_ORDER, semester.getSemOrder())
            .where(Tables.SEMESTER.SEMESTER_ID.eq(semester_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("sem has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    
    
    public ResponseEntity<JSONObject> makeCurrentSemester(int semester_id) {
    	try {
    		dslContext.update(Tables.SEMESTER)
            .set(Tables.SEMESTER.IS_CURRENT, false)
            .execute();
    		
    		dslContext.update(Tables.SEMESTER)
            .set(Tables.SEMESTER.IS_CURRENT, true)
            .where(Tables.SEMESTER.SEMESTER_ID.eq(semester_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("current sem has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    
    public ResponseEntity<List<Semester>> getSemesters() {
        return new ResponseEntity<List<Semester>>(dslContext.selectFrom(Tables.SEMESTER).fetchInto(Semester.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Semester> getSemesterByID(int semester_id){
    	return new ResponseEntity<Semester>(dslContext.selectFrom(Tables.SEMESTER).where(Tables.SEMESTER.SEMESTER_ID.eq(semester_id)).fetchOneInto(Semester.class), HttpStatus.OK);
    }
    
    
   
	

}
