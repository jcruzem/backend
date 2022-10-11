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
import org.ssglobal.lms.model.tables.pojos.Degree;
import org.ssglobal.lms.model.tables.pojos.Grade;

@Service
public class GradeService {
	@Autowired
	private DSLContext dslContext;
    public GradeService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insert(Grade grade) {
        try {

            dslContext.insertInto(
                    Tables.GRADE,
                    Tables.GRADE.GRADE_VALUE,
                    Tables.GRADE.DATEMODIFIED,
                    Tables.GRADE.STATUS,
                    Tables.GRADE.REMARKS,
                    Tables.GRADE.LECTURE_ID,
                    Tables.GRADE.ACCOUNT_ID
            		)
                    .values(
                    		grade.getGradeValue(),
                    		grade.getDatemodified(),
                    		grade.getStatus(),
                    		grade.getRemarks(),
                    		grade.getLectureId(),
                    		grade.getAccountId()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("grade has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> edit(int grade_id, Grade grade) {
    	try {
    		dslContext.update(Tables.GRADE)
            .set(Tables.GRADE.GRADE_VALUE, grade.getGradeValue())
            .set(Tables.GRADE.DATEMODIFIED, grade.getDatemodified())
            .set(Tables.GRADE.STATUS, grade.getStatus())
            .set(Tables.GRADE.REMARKS, grade.getRemarks())
            .where(Tables.GRADE.GRADE_ID.eq(grade_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("grade has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<JSONObject> delete(int grade_id) {
    	try {
    		dslContext.delete(Tables.GRADE)
            .where(Tables.GRADE.GRADE_ID.eq(grade_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("grade has now been deleted"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<Grade>> getAll() {
        return new ResponseEntity<List<Grade>>(dslContext.selectFrom(Tables.GRADE).fetchInto(Grade.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Grade> getById(int grade_id){
    	return new ResponseEntity<Grade>(dslContext.selectFrom(Tables.GRADE).where(Tables.GRADE.GRADE_ID.eq(grade_id)).fetchOneInto(Grade.class), HttpStatus.OK);
    }
	

}
