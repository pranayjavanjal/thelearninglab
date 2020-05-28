package com.tll.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tll.models.LeaveDetails;

@Repository
public class LeaveManageNativeSqlRepo {

    @PersistenceContext
    EntityManager entityManager;


    @SuppressWarnings("unchecked")
    public List<LeaveDetails> getAllLeavesOnStatus(StringBuffer whereQuery) {

	Query query = entityManager.createNativeQuery("select ld.* from leave_details ld "
			+ "inner join userinfo ui on ld.username=ui.email  where " + whereQuery,
		LeaveDetails.class);
	
	return query.getResultList();
    }
}
