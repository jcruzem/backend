package org.ssglobal.lms.service;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ssglobal.lms.model.Tables;
import org.ssglobal.lms.model.tables.pojos.Course;
import org.ssglobal.lms.model.tables.pojos.CoursesAssigned;

@Service
public class CoursesAssignedService {
	@Autowired
	private DSLContext dslContext;
    public CoursesAssignedService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insert(CoursesAssigned courses_assigned) {
        try {

            dslContext.insertInto(
                    Tables.COURSES_ASSIGNED,
                    Tables.COURSES_ASSIGNED.STATUS,
                    Tables.COURSES_ASSIGNED.COURSE_ID,
                    Tables.COURSES_ASSIGNED.ACCOUNT_ID
                    )
                    .values(
                    		courses_assigned.getStatus(),
                    		courses_assigned.getCourseId(),
                    		courses_assigned.getAccountId()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("courses_assigned has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> setStatus(int courses_assigned_id, CoursesAssigned courses_assigned) {
    	try {
    		   		
    		dslContext.update(Tables.COURSES_ASSIGNED)
            .set(Tables.COURSES_ASSIGNED.STATUS, courses_assigned.getStatus())
            .where(Tables.COURSES_ASSIGNED.COURSES_ASSIGNED_ID.eq(courses_assigned_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("courses_assigned has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<CoursesAssigned>> getAll() {
        return new ResponseEntity<List<CoursesAssigned>>(dslContext.selectFrom(Tables.COURSES_ASSIGNED).fetchInto(CoursesAssigned.class), HttpStatus.OK);
    }
    
    public ResponseEntity<CoursesAssigned> getByID(int courses_assigned_id){
    	return new ResponseEntity<CoursesAssigned>(dslContext.selectFrom(Tables.COURSES_ASSIGNED).where(Tables.COURSES_ASSIGNED.COURSES_ASSIGNED_ID.eq(courses_assigned_id)).fetchOneInto(CoursesAssigned.class), HttpStatus.OK);
    }
    
    

}
