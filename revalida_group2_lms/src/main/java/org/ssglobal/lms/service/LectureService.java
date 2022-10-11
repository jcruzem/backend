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
import org.ssglobal.lms.model.tables.pojos.Degree;
import org.ssglobal.lms.model.tables.pojos.Lecture;

@Service
public class LectureService {
	@Autowired
	private DSLContext dslContext;
    public LectureService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insert(Lecture lecture) {
        try {

            dslContext.insertInto(
                    Tables.LECTURE,
                    Tables.LECTURE.SCHEDULE,
                    Tables.LECTURE.SECTION,
                    Tables.LECTURE.COURSE_ID,
                    Tables.LECTURE.ACCOUNT_ID,
                    Tables.LECTURE.SEMESTER_ID
            		)
                    .values(
                    		lecture.getSchedule(),
                    		lecture.getSection(),
                    		lecture.getCourseId(),
                    		lecture.getAccountId(), //Professor
                    		lecture.getSemesterId()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("lecture has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    
    public ResponseEntity<JSONObject> edit(int lecture_id, Lecture lecture) {
    	try {
    		dslContext.update(Tables.LECTURE)
            .set(Tables.LECTURE.SCHEDULE, lecture.getSchedule())
            .set(Tables.LECTURE.SECTION, lecture.getSection())
            .set(Tables.LECTURE.COURSE_ID, lecture.getCourseId())
            .set(Tables.LECTURE.ACCOUNT_ID, lecture.getAccountId())
            .set(Tables.LECTURE.SEMESTER_ID, lecture.getSemesterId())
            .where(Tables.LECTURE.LECTURE_ID.eq(lecture_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("lecture has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<Lecture>> getAll() {
        return new ResponseEntity<List<Lecture>>(dslContext.selectFrom(Tables.LECTURE).fetchInto(Lecture.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Lecture> getById(int lecture_id){
    	return new ResponseEntity<Lecture>(dslContext.selectFrom(Tables.LECTURE).where(Tables.LECTURE.LECTURE_ID.eq(lecture_id)).fetchOneInto(Lecture.class), HttpStatus.OK);
    }
    

}
