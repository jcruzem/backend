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
import org.ssglobal.lms.model.tables.pojos.Timeslot;

@Service
public class TimeslotService {
	@Autowired
	private DSLContext dslContext;
    public TimeslotService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insertTimeslot(Timeslot timeslot) {
        try {

            dslContext.insertInto(
                    Tables.TIMESLOT,
                    Tables.TIMESLOT.TIMESLOT_CODE,
                    Tables.TIMESLOT.YEAR_NO,
                    Tables.TIMESLOT.SEM_NO)
                    .values(
                    		timeslot.getTimeslotCode(),
                    		timeslot.getYearNo(),
                    		timeslot.getSemNo()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("timeslot has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> editTimeslot(int timeslot_id, Timeslot timeslot) {
    	try {
    		
    		dslContext.update(Tables.TIMESLOT)
            .set(Tables.TIMESLOT.TIMESLOT_CODE, timeslot.getTimeslotCode())
            .set(Tables.TIMESLOT.YEAR_NO, timeslot.getYearNo())
            .set(Tables.TIMESLOT.SEM_NO, timeslot.getSemNo())
            .where(Tables.TIMESLOT.TIMESLOT_ID.eq(timeslot_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("timeslot has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<Timeslot>> getTimeslots() {
        return new ResponseEntity<List<Timeslot>>(dslContext.selectFrom(Tables.TIMESLOT).fetchInto(Timeslot.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Timeslot> getTimeslotByID(int timeslot_id){
    	return new ResponseEntity<Timeslot>(dslContext.selectFrom(Tables.TIMESLOT).where(Tables.TIMESLOT.TIMESLOT_ID.eq(timeslot_id)).fetchOneInto(Timeslot.class), HttpStatus.OK);
    }

}
