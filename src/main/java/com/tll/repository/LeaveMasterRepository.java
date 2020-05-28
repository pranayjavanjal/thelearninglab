package com.tll.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tll.models.LeaveMaster;

@Repository(value = "leaveMasterRepository")
public interface LeaveMasterRepository extends JpaRepository<LeaveMaster, Serializable> {

	
    @Query(nativeQuery = true, value = "select * from leave_master where emp_email=?")
    public List<LeaveMaster> getAssignLeaves(String email);
    
    @Transactional
    @Modifying
    @Query(value = "update leave_master set total_leaves=? where emp_email=? and leave_type=?", nativeQuery = true)
    public void updateLeaves(String totalLeaves,String email, String leaveType);
}
