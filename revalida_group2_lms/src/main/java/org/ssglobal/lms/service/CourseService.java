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

@Service
public class CourseService {
	@Autowired
	private DSLContext dslContext;
    public CourseService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public ResponseEntity<JSONObject> insertCourse(Course course) {
        try {

            dslContext.insertInto(
                    Tables.COURSE,
                    Tables.COURSE.COURSE_CODE,
                    Tables.COURSE.COURSE_NAME,
                    Tables.COURSE.UNITS,
                    Tables.COURSE.DEGREE_ID,
                    Tables.COURSE.TIMESLOT_ID
                    )
                    .values(
                    		course.getCourseCode(),
                    		course.getCourseName(),
                    		course.getUnits(),
                    		course.getDegreeId(),
                    		course.getTimeslotId()
                    		)
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("course has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<JSONObject> editCourse(int course_id, Course course) {
    	try {
    		   		
    		dslContext.update(Tables.COURSE)
            .set(Tables.COURSE.COURSE_CODE, course.getCourseCode())
            .set(Tables.COURSE.COURSE_NAME, course.getCourseName())
            .set(Tables.COURSE.UNITS, course.getUnits())
            .set(Tables.COURSE.DEGREE_ID, course.getDegreeId())
            .set(Tables.COURSE.TIMESLOT_ID, course.getTimeslotId())
            .where(Tables.COURSE.COURSE_ID.eq(course_id)).execute();
    			
    		JSONObject message = new JSONObject();
    		message.put("message", new String("course has now been updated"));
    		return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    }
    
    public ResponseEntity<List<Course>> getCourses() {
        return new ResponseEntity<List<Course>>(dslContext.selectFrom(Tables.COURSE).fetchInto(Course.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Course> getCourseByID(int course_id){
    	return new ResponseEntity<Course>(dslContext.selectFrom(Tables.COURSE).where(Tables.COURSE.COURSE_ID.eq(course_id)).fetchOneInto(Course.class), HttpStatus.OK);
    }

}
