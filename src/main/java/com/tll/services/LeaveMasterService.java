package com.tll.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tll.models.LeaveMaster;
import com.tll.repository.LeaveMasterRepository;

@Service(value = "leaveMasterService")
public class LeaveMasterService {

	@Autowired
	LeaveMasterService leaveMasterService;
	
	@Autowired
	private LeaveMasterRepository leaveMasterRepository;
	
	public List<LeaveMaster> getAssignLeaves(String email) {

		return leaveMasterRepository.getAssignLeaves(email);
	}

	public void saveAssignLeaves(LeaveMaster leaveMaster) {

		leaveMasterRepository.save(leaveMaster);
	}
	
	public List<LeaveMaster> getAllAssign() {
		
		return leaveMasterRepository.findAll();
	}
	
	public void updateLeaves(String totalLeaves,String empEmail, String leaveType) {
		
		leaveMasterRepository.updateLeaves(totalLeaves,empEmail,leaveType);
	}
	
/*	public List<LeaveMaster> getUserTotalLeaves(String email) {

	return leaveMasterRepository.getAssignLeaves(email);
}*/
}
