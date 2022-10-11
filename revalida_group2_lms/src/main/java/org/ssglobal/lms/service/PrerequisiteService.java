package org.ssglobal.lms.service;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ssglobal.lms.model.Tables;
import org.ssglobal.lms.model.tables.pojos.Attendance;
import org.ssglobal.lms.model.tables.pojos.Prerequisite;

@Service
public class PrerequisiteService {
	@Autowired
	private DSLContext dslContext;
    public PrerequisiteService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insert(Prerequisite prerequisite) {
        try {

            dslContext.insertInto(
                    Tables.PREREQUISITE,
                    Tables.PREREQUISITE.COURSE_ID,
                    Tables.PREREQUISITE.PREREQUISITE_COURSE_ID
            		)
                    .values(
                    		prerequisite.getCourseId(),
                    		prerequisite.getPrerequisiteCourseId()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("prerequisite has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> edit(int prerequisite_id, Prerequisite prerequisite) {
    	try {
    		dslContext.update(Tables.PREREQUISITE)
            .set(Tables.PREREQUISITE.PREREQUISITE_COURSE_ID, prerequisite.getPrerequisiteCourseId())
            .where(Tables.PREREQUISITE.PREREQUISITE_ID.eq(prerequisite_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("prerequisite has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<JSONObject> delete(int prerequisite_id) {
    	try {
    		dslContext.delete(Tables.PREREQUISITE)
            .where(Tables.PREREQUISITE.PREREQUISITE_ID.eq(prerequisite_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("prerequisite has now been deleted"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<Prerequisite>> getAll() {
        return new ResponseEntity<List<Prerequisite>>(dslContext.selectFrom(Tables.PREREQUISITE).fetchInto(Prerequisite.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Prerequisite> getById(int prerequisite_id){
    	return new ResponseEntity<Prerequisite>(dslContext.selectFrom(Tables.PREREQUISITE).where(Tables.PREREQUISITE.PREREQUISITE_ID.eq(prerequisite_id)).fetchOneInto(Prerequisite.class), HttpStatus.OK);
    }

}
