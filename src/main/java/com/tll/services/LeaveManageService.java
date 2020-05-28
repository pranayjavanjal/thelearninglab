package com.tll.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tll.controllers.EmailController;
import com.tll.models.LeaveDetails;
import com.tll.models.UserInfo;
import com.tll.repository.LeaveManageNativeSqlRepo;
import com.tll.repository.LeaveManageRepository;

@Service(value = "leaveManageService")
public class LeaveManageService {

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	private LeaveManageRepository leaveManageRepository;

	@Autowired
	private LeaveManageNativeSqlRepo leaveManageNativeRepo;

	@SuppressWarnings("deprecation")
	public void applyLeave(LeaveDetails leaveDetails) {

		int duration = leaveDetails.getToDate().getDate() - leaveDetails.getFromDate().getDate();
		leaveDetails.setDuration(duration + 1);
		leaveDetails.setActive(true);
		leaveManageRepository.save(leaveDetails);
		String username="notification@carprosystems.com";
		String from="notification@carprosystems.com";
		String password="L3ave&*()2020";
		String to=leaveDetails.getEmailTo();
		String cc=leaveDetails.getEmailCC();
		String subject="Leave Application";
		String body="";
		String fromDate=String.valueOf(leaveDetails.getFromDate()).substring(4, 10);
		String toDate=String.valueOf(leaveDetails.getToDate()).substring(4, 10);		
		if(duration>1){
			body = "Dear Sir/Madam,\n\nThis is to inform you that, I am not able to come to the office from "+fromDate+" to "+toDate+ "("+duration+" days).\n\nKindly accept the leave request.\n\nRegards,\n"+leaveDetails.getEmployeeName();
		}else{
			body = "Dear Sir/Madam,\n\nThis is to inform you that, I am not able to come to the office on "+fromDate+".\n\nKindly accept the leave request.\n\nRegards,\n"+leaveDetails.getEmployeeName();	
		}		
		EmailController email= new EmailController();
		try{
			email.sendMail("smtp.office365.com", "587", to, cc+";hr@yopmail.com", "", username, from,
			password, subject, body, "", "Y");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List<LeaveDetails> getAllLeaves() {

		return leaveManageRepository.findAll();
	}

	public LeaveDetails getLeaveDetailsOnId(int id) {

		return leaveManageRepository.findOne(id);
	}

	public void updateLeaveDetails(LeaveDetails leaveDetails) {

		leaveManageRepository.save(leaveDetails);

	}

	public List<LeaveDetails> getAllActiveLeaves(String email) {

		return leaveManageRepository.getAllActiveLeaves(email);
	}

	public List<LeaveDetails> getAllLeavesOfUser(String username) {

		return leaveManageRepository.getAllLeavesOfUser(username);

	}

	public List<LeaveDetails> getAllLeavesOnStatus(boolean pending, boolean accepted, boolean rejected,boolean allleaves,UserInfo userInfo) {

		StringBuffer whereQuery = new StringBuffer();
		whereQuery.append("ui.department='"+userInfo.getDepartment()+"' ");
		if (pending)
		    whereQuery.append("and ld.active='true' or ");
		if (accepted)
		    whereQuery.append("and (ld.active='false' and ld.accept_reject_flag='true') or ");
		if (rejected)
		    whereQuery.append("and (ld.active='false' and ld.accept_reject_flag='false') or ");
	    if(!allleaves)
		    whereQuery.append(" 1=0");
		
		return leaveManageNativeRepo.getAllLeavesOnStatus(whereQuery);
	}
}
