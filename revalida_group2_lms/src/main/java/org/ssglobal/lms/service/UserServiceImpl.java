package org.ssglobal.lms.service;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.lms.model.tables.records.AccountRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserDetailsService {
	@Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountRecord user = userService.findByUsername(username);
        if(user == null){
           // log.error("User does not exist");
            throw new UsernameNotFoundException("User not found in the database");
        }else{
          //  log.info("User: {} exists", username);
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new User(user.getUsername(), user.getPassword(), List.of(authority));
    }
}
