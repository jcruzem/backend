package org.ssglobal.lms.service;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ssglobal.lms.model.Tables;
import org.ssglobal.lms.model.tables.pojos.Degree;
import org.ssglobal.lms.model.tables.pojos.Semester;

@Service
public class DegreeService {
	
	@Autowired
	private DSLContext dslContext;
    public DegreeService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insertDegree(Degree degree) {
        try {

            dslContext.insertInto(
                    Tables.DEGREE,
                    Tables.DEGREE.DEGREE_CODE,
                    Tables.DEGREE.DEGREE_NAME,
                    Tables.DEGREE.UNITS_REQUIRED)
                    .values(
                    		degree.getDegreeCode(),
                    		degree.getDegreeName(),
                    		degree.getUnitsRequired()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("degree has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> editDegree(int degree_id, Degree degree) {
    	try {
    		
    		
    		dslContext.update(Tables.DEGREE)
            .set(Tables.DEGREE.DEGREE_CODE, degree.getDegreeCode())
            .set(Tables.DEGREE.DEGREE_NAME, degree.getDegreeName())
            .set(Tables.DEGREE.UNITS_REQUIRED, degree.getUnitsRequired())
            .where(Tables.DEGREE.DEGREE_ID.eq(degree_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("degree has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<Degree>> getDegrees() {
        return new ResponseEntity<List<Degree>>(dslContext.selectFrom(Tables.DEGREE).fetchInto(Degree.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Degree> getDegreeByID(int degree_id){
    	return new ResponseEntity<Degree>(dslContext.selectFrom(Tables.DEGREE).where(Tables.DEGREE.DEGREE_ID.eq(degree_id)).fetchOneInto(Degree.class), HttpStatus.OK);
    }
	
	

}
