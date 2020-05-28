package com.tll.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.tll.models.LeaveDetails;
import com.tll.models.LeaveMaster;
import com.tll.models.LeaveStatus;
import com.tll.models.UserInfo;
import com.tll.services.LeaveManageService;
import com.tll.services.LeaveMasterService;
import com.tll.services.UserInfoService;

@Controller
public class LeaveManageController {

	@Autowired
	private LeaveManageService leaveManageService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private LeaveMasterService leaveMasterService;

	@RequestMapping(value = "/user/apply-leave", method = RequestMethod.GET)
	public ModelAndView applyLeave(ModelAndView mav) {

		List<UserInfo> userInfos = userInfoService.getAllUserInfo();
		mav.addObject("emailToList", userInfos);
		mav.addObject("leaveDetails", new LeaveDetails());
		mav.setViewName("applyLeave");
		return mav;
	}

	@RequestMapping(value = "/user/apply-leave", method = RequestMethod.POST)
	public ModelAndView submitApplyLeave(ModelAndView mav, @Valid LeaveDetails leaveDetails,
			BindingResult bindingResult) {

		UserInfo userInfo = userInfoService.getUserInfo();
		if (bindingResult.hasErrors()) {
			mav.setViewName("applyLeave");
		} else {
			leaveDetails.setUsername(userInfo.getEmail());
			leaveDetails.setEmployeeName(userInfo.getFirstName() + " " + userInfo.getLastName());
			leaveManageService.applyLeave(leaveDetails);
			mav.addObject("successMessage", "Your Leave Request is registered!");
			mav.setView(new RedirectView("/user/home"));
		}
		return mav;
	}

	@RequestMapping(value = "/user/get-all-leaves", method = RequestMethod.GET)
	public @ResponseBody String getAllLeaves(@RequestParam(value = "pending", defaultValue = "false") boolean pending,
			@RequestParam(value = "accepted", defaultValue = "false") boolean accepted,
			@RequestParam(value = "rejected", defaultValue = "false") boolean rejected,
			@RequestParam(value = "allleaves", defaultValue = "true") boolean allleaves) throws Exception {
		UserInfo userInfo = userInfoService.getUserInfo();

		Iterator<LeaveDetails> iterator = leaveManageService.getAllLeaves().iterator();
		if (pending || accepted || rejected || (allleaves && !userInfo.getRole().equals("HR")))
			iterator = leaveManageService.getAllLeavesOnStatus(pending, accepted, rejected, allleaves, userInfo)
					.iterator();
		JSONArray jsonArr = new JSONArray();
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();

		while (iterator.hasNext()) {
			LeaveDetails leaveDetails = iterator.next();
			if (userInfo.getRole().equals("HR") || userInfo.getRole().equals("ADMIN")) {
				if (!leaveDetails.isActive() && leaveDetails.isAcceptRejectFlag()) {
					calendar.setTime(leaveDetails.getToDate());
					calendar.add(Calendar.DATE, 1);
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("title", leaveDetails.getEmployeeName());
					jsonObj.put("start", dateFormat.format(leaveDetails.getFromDate()));
					jsonObj.put("end", dateFormat.format(date));
					jsonObj.put("color", "green");
					jsonArr.put(jsonObj);
				}

			} else {
				calendar.setTime(leaveDetails.getToDate());
				calendar.add(Calendar.DATE, 1);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("title", leaveDetails.getEmployeeName());
				jsonObj.put("start", dateFormat.format(leaveDetails.getFromDate()));
				jsonObj.put("end", dateFormat.format(date));
				if (leaveDetails.isActive())
					jsonObj.put("color", "#0878af");
				if (!leaveDetails.isActive() && leaveDetails.isAcceptRejectFlag())
					jsonObj.put("color", "green");
				if (!leaveDetails.isActive() && !leaveDetails.isAcceptRejectFlag())
					jsonObj.put("color", "red");
				jsonArr.put(jsonObj);
			}
		}

		return jsonArr.toString();
	}

	@RequestMapping(value = "/user/manage-leaves", method = RequestMethod.GET)
	public ModelAndView manageLeaves(ModelAndView mav, HttpServletRequest request) {
		UserInfo userInfo = userInfoService.getUserInfo();
		mav.addObject("leavesList", leaveManageService.getAllActiveLeaves(userInfo.getEmail()));
		mav.setViewName("manageLeaves");
		return mav;
	}

	@RequestMapping(value = "/user/manage-leaves/{action}/{id}", method = RequestMethod.GET)
	public ModelAndView acceptOrRejectLeaves(ModelAndView mav, @PathVariable("action") String action,
			@PathVariable("id") int id) {
		LeaveDetails leaveDetails = leaveManageService.getLeaveDetailsOnId(id);
		String username = leaveDetails.getUsername();
		String from = "notification@carprosystems.com";
		String password = "L3ave&*()2020";
		String to = leaveDetails.getUsername();
		String cc = leaveDetails.getEmailCC();
		String subject = "Leave Application";
		String body = "";
		UserInfo userInfo = userInfoService.getUserInfo();
		if (action.equals("accept")) {
			leaveDetails.setAcceptRejectFlag(true);
			leaveDetails.setActive(false);
			body = "Hello,\n\n Your Leaves have been approved!\n\nRegards,\n" + userInfo.getFirstName() + " "
					+ userInfo.getLastName();
		} else if (action.equals("reject")) {
			leaveDetails.setAcceptRejectFlag(false);
			leaveDetails.setActive(false);
			body = "Hello,\n\n Your leave request has been rejected!\n\nRegards,\n" + userInfo.getFirstName() + " "
					+ userInfo.getLastName();
		}
		EmailController email = new EmailController();
		try {
			email.sendMail("smtp.office365.com", "587", to, cc + ";hr@yopmail.com", "", username, from, password,
					subject, body, "", "Y");
		} catch (Exception e) {
			e.printStackTrace();
		}
		leaveManageService.updateLeaveDetails(leaveDetails);
		mav.addObject("successMessage", "Updated Successfully!");
		mav.setView(new RedirectView("/user/manage-leaves"));
		return mav;
	}

	@RequestMapping(value = "/user/my-leaves", method = RequestMethod.GET)
	public ModelAndView showMyLeaves(ModelAndView mav) {

		UserInfo userInfo = userInfoService.getUserInfo();
		List<LeaveDetails> leavesList = leaveManageService.getAllLeavesOfUser(userInfo.getEmail());
		mav.addObject("leavesList", leavesList);
		mav.setViewName("myLeaves");
		return mav;
	}

	@RequestMapping(value = "/user/leaves-status", method = RequestMethod.GET)
	public ModelAndView showLeavesStatus(ModelAndView mav) {
		List<UserInfo> userInfos = userInfoService.getAllUserInfo();
		mav.addObject("emailToList", userInfos);
		mav.setViewName("leaveStatus");
		return mav;
	}

	@RequestMapping(value = "/user/getleaveStatus", method = RequestMethod.GET)
	public ModelAndView getLeavesStatus(ModelAndView mav,
			@RequestParam(value = "emailTo", defaultValue = "") String emailTo) {
		List<UserInfo> userInfos = userInfoService.getAllUserInfo();
		List<LeaveDetails> leavesList = leaveManageService.getAllLeavesOfUser(emailTo);
		List<LeaveMaster> totalLeaves = leaveMasterService.getAssignLeaves(emailTo);
		List<LeaveStatus> leaveStatus = new ArrayList<LeaveStatus>();
		HashMap<Integer, Integer> usedLeaves = new HashMap<Integer, Integer>();
		String userName="";
		if(leavesList!=null && leavesList.size()>0){
			userName=leavesList.get(0).getEmployeeName();
		}
		for (int i = 0; i < leavesList.size(); i++) {
			if (usedLeaves != null && usedLeaves.containsKey(leavesList.get(i).getLeaveType())) {
				if(leavesList.get(i).getLeaveType().equals("ANNUAL LEAVE")){
					usedLeaves.put(1,usedLeaves.get(leavesList.get(i).getLeaveType()) + leavesList.get(i).getDuration());
				}else if(leavesList.get(i).getLeaveType().equals("RESTRICTED")){
					usedLeaves.put(2,usedLeaves.get(leavesList.get(i).getLeaveType()) + leavesList.get(i).getDuration());
				}else if(leavesList.get(i).getLeaveType().equals("SICK LEAVE")){
					usedLeaves.put(3,usedLeaves.get(leavesList.get(i).getLeaveType()) + leavesList.get(i).getDuration());
				}
			}else {
				if(leavesList.get(i).getLeaveType().equals("ANNUAL LEAVE")){
					usedLeaves.put(1, leavesList.get(i).getDuration());
				}else if(leavesList.get(i).getLeaveType().equals("RESTRICTED")){
					usedLeaves.put(2, leavesList.get(i).getDuration());
				}else if(leavesList.get(i).getLeaveType().equals("SICK LEAVE")){
					usedLeaves.put(3, leavesList.get(i).getDuration());
				}
			}
		}
		int annualLeaveTotal=0;int rhLeaveTotal=0;int sickLeaveTotal=0;
		int annualLeaveBal=0;int rhLeaveBal=0;int sickLeaveBal=0;
		
		LeaveStatus totalLeaveStat= new LeaveStatus();
		for(int i=0; i<totalLeaves.size();i++){
			if(totalLeaves.get(i).getLeaveType().equals("ANNUAL LEAVE")){
				totalLeaveStat.setAnnualLeave(totalLeaves.get(i).getTotalLeaves());
				annualLeaveTotal=Integer.parseInt(totalLeaves.get(i).getTotalLeaves());
			}else if(totalLeaves.get(i).getLeaveType().equals("RESTRICTED")){
				totalLeaveStat.setRhLeave(totalLeaves.get(i).getTotalLeaves());
				rhLeaveTotal=Integer.parseInt(totalLeaves.get(i).getTotalLeaves());
			}else if(totalLeaves.get(i).getLeaveType().equals("SICK LEAVE")){
				totalLeaveStat.setSickLeave(totalLeaves.get(i).getTotalLeaves());
				sickLeaveTotal=Integer.parseInt(totalLeaves.get(i).getTotalLeaves());
			}
		}
		totalLeaveStat.setLeaveId("TOTAL LEAVES");
		leaveStatus.add(totalLeaveStat);
		LeaveStatus usedLeaveStat= new LeaveStatus();
		for (Entry<Integer, Integer> entry : usedLeaves.entrySet()) {
			if(entry.getKey()==1){
				usedLeaveStat.setAnnualLeave(String.valueOf(entry.getValue()));
				annualLeaveBal=entry.getValue();
			}else if(entry.getKey()==2){
				usedLeaveStat.setRhLeave(String.valueOf(entry.getValue()));
				rhLeaveBal=entry.getValue();
			}else if(entry.getKey()==3){
				usedLeaveStat.setSickLeave(String.valueOf(entry.getValue()));
				sickLeaveBal=entry.getValue();
			}
		}
		usedLeaveStat.setLeaveId("USED LEAVES");
		leaveStatus.add(usedLeaveStat);
		LeaveStatus balLeaveStat= new LeaveStatus();
		balLeaveStat.setAnnualLeave(String.valueOf(annualLeaveTotal-annualLeaveBal));
		balLeaveStat.setRhLeave(String.valueOf(rhLeaveTotal-rhLeaveBal));
		balLeaveStat.setSickLeave(String.valueOf(sickLeaveTotal-sickLeaveBal));
		balLeaveStat.setLeaveId("BALENCED LEAVES");
		leaveStatus.add(balLeaveStat);
		//userName
		mav.addObject("userName", userName);
		mav.addObject("leaveStatus", leaveStatus);
		mav.addObject("emailToList", userInfos);
		mav.setViewName("leaveStatus");
		return mav;
	}

	@RequestMapping(value = "/user/add-leaves", method = RequestMethod.GET)
	public ModelAndView assignLeaves(ModelAndView mav, HttpServletRequest request) {
		List<UserInfo> userList = userInfoService.getAllUserInfo();
		mav.addObject("userList", userList);
		mav.addObject("LeaveMaster", new LeaveMaster());
		mav.setViewName("assignLeaves");
		return mav;
	}

	@RequestMapping(value = "/user/add-leaves", method = RequestMethod.POST)
	public ModelAndView submitAssignLeaves(ModelAndView mav, @Valid LeaveMaster leaveMaster,
			BindingResult bindingResult) {
		List<LeaveMaster> empLeaveList = leaveMasterService.getAssignLeaves(leaveMaster.getEmpEmail());
		if (empLeaveList != null && empLeaveList.size() > 0) {
			for (int i = 0; i < empLeaveList.size(); i++) {
				if (empLeaveList.get(i).getLeaveType().equals(leaveMaster.getLeaveType())) {
					leaveMasterService.updateLeaves(leaveMaster.getTotalLeaves(), leaveMaster.getEmpEmail(),
							leaveMaster.getLeaveType());
					mav.addObject("successMessage", "Leave updated Successfully!");
				} else {
					leaveMasterService.saveAssignLeaves(leaveMaster);
					mav.addObject("successMessage", "Leave Allocated Successfully!");
				}
			}
		} else {
			leaveMasterService.saveAssignLeaves(leaveMaster);
			mav.addObject("successMessage", "Leave Allocated Successfully!");
		}

		List<UserInfo> userList = userInfoService.getAllUserInfo();
		mav.addObject("userList", userList);
		mav.addObject("LeaveMaster", new LeaveMaster());
		// mav.addObject("successMessage", "Leave Allocated Successfully!");
		mav.setViewName("assignLeaves");

		return mav;
	}
}
