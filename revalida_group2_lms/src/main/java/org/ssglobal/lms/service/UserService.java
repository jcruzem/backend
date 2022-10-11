package org.ssglobal.lms.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.lms.model.Tables;
import org.ssglobal.lms.model.tables.records.AccountRecord;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    @Autowired
    private DSLContext dslContext;

    public AccountRecord findByUsername(String username){
        return dslContext.selectFrom(Tables.ACCOUNT).where(Tables.ACCOUNT.USERNAME.eq(username)).fetchOne();
    }


}
