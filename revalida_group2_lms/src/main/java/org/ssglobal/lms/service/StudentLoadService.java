package org.ssglobal.lms.service;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ssglobal.lms.model.Tables;
import org.ssglobal.lms.model.tables.pojos.Lecture;
import org.ssglobal.lms.model.tables.pojos.StudentLoad;

@Service
public class StudentLoadService {
	@Autowired
	private DSLContext dslContext;
    public StudentLoadService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insert(StudentLoad student_load) {
        try {

            dslContext.insertInto(
                    Tables.STUDENT_LOAD,
                    Tables.STUDENT_LOAD.ACCOUNT_ID,
                    Tables.STUDENT_LOAD.LECTURE_ID
            		)
                    .values(
                    		student_load.getAccountId(),
                    		student_load.getLectureId()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("student load has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> edit(int sload_id, StudentLoad student_load) {
    	try {
    		dslContext.update(Tables.STUDENT_LOAD)
            .set(Tables.STUDENT_LOAD.ACCOUNT_ID, student_load.getAccountId())
            .set(Tables.STUDENT_LOAD.LECTURE_ID, student_load.getLectureId())
            .where(Tables.STUDENT_LOAD.SLOAD_ID.eq(sload_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("student load has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<StudentLoad>> getAll() {
        return new ResponseEntity<List<StudentLoad>>(dslContext.selectFrom(Tables.STUDENT_LOAD).fetchInto(StudentLoad.class), HttpStatus.OK);
    }
    
    public ResponseEntity<StudentLoad> getById(int sload_id){
    	return new ResponseEntity<StudentLoad>(dslContext.selectFrom(Tables.STUDENT_LOAD).where(Tables.STUDENT_LOAD.SLOAD_ID.eq(sload_id)).fetchOneInto(StudentLoad.class), HttpStatus.OK);
    }

}
