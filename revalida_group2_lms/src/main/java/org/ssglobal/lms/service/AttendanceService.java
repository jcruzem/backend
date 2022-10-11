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
import org.ssglobal.lms.model.tables.pojos.StudentLoad;

@Service
public class AttendanceService {
	@Autowired
	private DSLContext dslContext;
    public AttendanceService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insert(Attendance attendace) {
        try {

            dslContext.insertInto(
                    Tables.ATTENDANCE,
                    Tables.ATTENDANCE.ATTENDANCE_DATE,
                    Tables.ATTENDANCE.STATUS,
                    Tables.ATTENDANCE.ACCOUNT_ID,
                    Tables.ATTENDANCE.LECTURE_ID
            		)
                    .values(
                    		attendace.getAttendanceDate(),
                    		attendace.getStatus(),
                    		attendace.getAccountId(),
                    		attendace.getLectureId()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("attendance has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> edit(short attendance_id, Attendance attendace) {
    	try {
    		dslContext.update(Tables.ATTENDANCE)
            .set(Tables.ATTENDANCE.STATUS, attendace.getStatus())
            .where(Tables.ATTENDANCE.ATTENDANCE_ID.eq(attendance_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("attendance has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<JSONObject> delete(short attendance_id) {
    	try {
    		dslContext.delete(Tables.ATTENDANCE)
            .where(Tables.ATTENDANCE.ATTENDANCE_ID.eq(attendance_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("attendance has now been deleted"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<Attendance>> getAll() {
        return new ResponseEntity<List<Attendance>>(dslContext.selectFrom(Tables.ATTENDANCE).fetchInto(Attendance.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Attendance> getById(short attendance_id){
    	return new ResponseEntity<Attendance>(dslContext.selectFrom(Tables.ATTENDANCE).where(Tables.ATTENDANCE.ATTENDANCE_ID.eq(attendance_id)).fetchOneInto(Attendance.class), HttpStatus.OK);
    }

}
