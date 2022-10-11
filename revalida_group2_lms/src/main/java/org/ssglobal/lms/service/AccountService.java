package org.ssglobal.lms.service;

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

import java.util.List;

@Service
public class AccountService {
	@Autowired
    private DSLContext dslContext;
    private PasswordEncoder passwordEncoder;
    public AccountService(DSLContext dslContext, PasswordEncoder passwordEncoder) {
        this.dslContext = dslContext;
        this.passwordEncoder = passwordEncoder;
    }
    
    public ResponseEntity<?> getCurrentUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	return new ResponseEntity<Account>(
    			dslContext.select(Tables.ACCOUNT.ACCOUNT_ID,
    					Tables.ACCOUNT.ROLE,
    					Tables.ACCOUNT.FIRST_NAME,
    					Tables.ACCOUNT.MIDDLE_NAME,
    					Tables.ACCOUNT.LAST_NAME,
    					Tables.ACCOUNT.GENDER,
    					Tables.ACCOUNT.BIRTHDATE,
    					Tables.ACCOUNT.USERNAME)
    			.from(Tables.ACCOUNT)
    			.where(Tables.ACCOUNT.USERNAME.eq(authentication.getName())).fetchOneInto(Account.class),
    			HttpStatus.OK);
    	
    }
    
    
    // Insert Admin
    public ResponseEntity<JSONObject> insertAdminAccount(Account account) {
        try {

            dslContext.insertInto(
                    Tables.ACCOUNT,
                    Tables.ACCOUNT.ROLE,
                    Tables.ACCOUNT.FIRST_NAME,
                    Tables.ACCOUNT.MIDDLE_NAME,
                    Tables.ACCOUNT.LAST_NAME,
                    Tables.ACCOUNT.GENDER,
                    Tables.ACCOUNT.BIRTHDATE,
                    Tables.ACCOUNT.ACTIVE,
                    Tables.ACCOUNT.USERNAME,
                    Tables.ACCOUNT.PASSWORD)
                    .values(
                            new String("admin"),
                            account.getFirstName(),
                            account.getMiddleName(),
                            account.getLastName(),
                            account.getGender(),
                            account.getBirthdate(),
                            true,
                            account.getUsername(),
                            passwordEncoder.encode(new String("temp").concat(String.valueOf(account.getBirthdate()))))
                    .execute();
           
            JSONObject message = new JSONObject();
            message.put("message", new String("account has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
 // Insert Student
    public ResponseEntity<JSONObject> insertStudentAccount(Account account) {
        try {

            dslContext.insertInto(
                    Tables.ACCOUNT,
                    Tables.ACCOUNT.ROLE,
                    Tables.ACCOUNT.FIRST_NAME,
                    Tables.ACCOUNT.MIDDLE_NAME,
                    Tables.ACCOUNT.LAST_NAME,
                    Tables.ACCOUNT.GENDER,
                    Tables.ACCOUNT.BIRTHDATE,
                    Tables.ACCOUNT.ACTIVE,
                    Tables.ACCOUNT.USERNAME,
                    Tables.ACCOUNT.PASSWORD,
                    Tables.ACCOUNT.DEGREE_ID)
                    .values(
                            new String("student"),
                            account.getFirstName(),
                            account.getMiddleName(),
                            account.getLastName(),
                            account.getGender(),
                            account.getBirthdate(),
                            true,
                            account.getUsername(),
                            passwordEncoder.encode(new String("temp").concat(String.valueOf(account.getBirthdate()))),
                            account.getDegreeId())
                    .execute();                
           
            JSONObject message = new JSONObject();
            message.put("message", new String("account has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
 // Insert Professor
    public ResponseEntity<JSONObject> insertProfessorAccount(Account account) {
        try {

            dslContext.insertInto(
                    Tables.ACCOUNT,
                    Tables.ACCOUNT.ROLE,
                    Tables.ACCOUNT.FIRST_NAME,
                    Tables.ACCOUNT.MIDDLE_NAME,
                    Tables.ACCOUNT.LAST_NAME,
                    Tables.ACCOUNT.GENDER,
                    Tables.ACCOUNT.BIRTHDATE,
                    Tables.ACCOUNT.ACTIVE,
                    Tables.ACCOUNT.USERNAME,
                    Tables.ACCOUNT.PASSWORD)
                    .values(
                            new String("professor"),
                            account.getFirstName(),
                            account.getMiddleName(),
                            account.getLastName(),
                            account.getGender(),
                            account.getBirthdate(),
                            true,
                            account.getUsername(),
                            passwordEncoder.encode(new String("temp").concat(String.valueOf(account.getBirthdate()))))
                    .execute();                
           
            JSONObject message = new JSONObject();
            message.put("message", new String("account has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    // Insert Parent
    public ResponseEntity<JSONObject> insertParentAccount(Account account) {
        try {

            dslContext.insertInto(
                    Tables.ACCOUNT,
                    Tables.ACCOUNT.ROLE,
                    Tables.ACCOUNT.FIRST_NAME,
                    Tables.ACCOUNT.MIDDLE_NAME,
                    Tables.ACCOUNT.LAST_NAME,
                    Tables.ACCOUNT.GENDER,
                    Tables.ACCOUNT.BIRTHDATE,
                    Tables.ACCOUNT.ACTIVE,
                    Tables.ACCOUNT.USERNAME,
                    Tables.ACCOUNT.PASSWORD,
                    Tables.ACCOUNT.CHILD_ID)
                    .values(
                            new String("parent"),
                            account.getFirstName(),
                            account.getMiddleName(),
                            account.getLastName(),
                            account.getGender(),
                            account.getBirthdate(),
                            true,
                            account.getUsername(),
                            passwordEncoder.encode(new String("temp").concat(String.valueOf(account.getBirthdate()))),
                            account.getChildId())
                    .execute();                
           
            JSONObject message = new JSONObject();
            message.put("message", new String("account has been created"));
 
            return new ResponseEntity<JSONObject>(message, HttpStatus.CREATED);

        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    // Update Bio (for student, admin, guardian, professor)
    public ResponseEntity<JSONObject> updateAccountBio(int accountId, Account account) {
        try {
           dslContext.update(Tables.ACCOUNT)
                    .set(Tables.ACCOUNT.FIRST_NAME, account.getFirstName())
                    .set(Tables.ACCOUNT.MIDDLE_NAME, account.getMiddleName())
                    .set(Tables.ACCOUNT.LAST_NAME, account.getLastName())
                    .set(Tables.ACCOUNT.GENDER, account.getGender())
                    .set(Tables.ACCOUNT.BIRTHDATE, account.getBirthdate())
                    .where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
            
            JSONObject message = new JSONObject();
            message.put("message", new String("profile bio has been updated"));
            
            return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);

        } catch(Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
    }

  
//     Update Password if not forgotten
    public ResponseEntity<JSONObject> updateAccountPassword(int accountId, JSONObject data) {
    	try {
    		if (passwordEncoder.matches(String.valueOf(data.get("oldPassword")), dslContext.select(Tables.ACCOUNT.PASSWORD)
    				.from(Tables.ACCOUNT)
    				.where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId))
    				.fetchOneInto(Account.class).getPassword())) {
    			dslContext.update(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.PASSWORD, passwordEncoder.encode(String.valueOf(data.get("newPassword"))))
                .where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
    			JSONObject message = new JSONObject();
    			message.put("message", new String("password has been changed"));
    			return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
    		} else {
    			JSONObject message = new JSONObject();
                message.put("message", "Incorrect Password");
                return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
    		}    

         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    	
    }
    
    public ResponseEntity<JSONObject> generateTempPassword(int accountId) {
    	try {
    		Account updatedAccount = dslContext.select(Tables.ACCOUNT.BIRTHDATE).from(Tables.ACCOUNT).where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId)).fetchOneInto(Account.class);
    		
    			dslContext.update(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.PASSWORD, passwordEncoder.encode(new String("temp").concat(String.valueOf(updatedAccount.getBirthdate()))))
                .where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
    			
    			JSONObject message = new JSONObject();
    			message.put("message", new String("temp password has been generated"));
    			return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
         } catch(Exception e) {
             JSONObject message = new JSONObject();
             message.put("message", e.getMessage());
             return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
         }
    	
    }

    public ResponseEntity<JSONObject> setAccountActivity(int accountId) {
        try {
            Account updatedAccount = dslContext.select(Tables.ACCOUNT.ACTIVE).from(Tables.ACCOUNT).where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId)).fetchOneInto(Account.class);
            dslContext.update(Tables.ACCOUNT)
                    .set(Tables.ACCOUNT.ACTIVE, !(updatedAccount.getActive()))
                    .where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
            JSONObject message = new JSONObject();
            message.put("message", new String("active status changed"));
            return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
        } catch(Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }

    }
    
    public ResponseEntity<JSONObject> setChildAccount(int accountId, Account account) {
        try {
            dslContext.update(Tables.ACCOUNT)
                    .set(Tables.ACCOUNT.CHILD_ID, account.getAccountId())
                    .where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
            JSONObject message = new JSONObject();
            message.put("message", new String("child has been changed"));
            return new ResponseEntity<JSONObject>(message, HttpStatus.ACCEPTED);
        } catch(Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", e.getMessage());
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }

    }
    
    public ResponseEntity<List<Account>> getAccounts() {
        return new ResponseEntity<List<Account>>(dslContext.selectFrom(Tables.ACCOUNT).fetchInto(Account.class), HttpStatus.OK);
    }
    
    public ResponseEntity<Account> getAccountByID(int accountId){
    	return new ResponseEntity<Account>(dslContext.selectFrom(Tables.ACCOUNT).where(Tables.ACCOUNT.ACCOUNT_ID.eq(accountId)).fetchOneInto(Account.class), HttpStatus.OK);
    }
    
    public ResponseEntity<List<Account>> getAdminAccounts() {
        return new ResponseEntity<List<Account>>(dslContext.selectFrom(Tables.ACCOUNT).where(Tables.ACCOUNT.ROLE.eq(new String("admin"))).fetchInto(Account.class), HttpStatus.OK);
    }
    
    public ResponseEntity<List<Account>> getStudentAccounts() {
        return new ResponseEntity<List<Account>>(dslContext.selectFrom(Tables.ACCOUNT).where(Tables.ACCOUNT.ROLE.eq(new String("student"))).fetchInto(Account.class), HttpStatus.OK);
    }
    
    public ResponseEntity<List<Account>> getProfessorAccounts() {
        return new ResponseEntity<List<Account>>(dslContext.selectFrom(Tables.ACCOUNT).where(Tables.ACCOUNT.ROLE.eq(new String("professor"))).fetchInto(Account.class), HttpStatus.OK);
    }
    
    public ResponseEntity<List<Account>> getParentAccounts() {
        return new ResponseEntity<List<Account>>(dslContext.selectFrom(Tables.ACCOUNT).where(Tables.ACCOUNT.ROLE.eq(new String("parent"))).fetchInto(Account.class), HttpStatus.OK);
    }
    



}