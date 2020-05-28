package com.tll.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.tll.models.UserInfo;
import com.tll.services.UserInfoService;

@Controller
public class AdminController {

	@Autowired
	UserInfoService userInfoService;

	@RequestMapping(value = "/user/change-password", method = RequestMethod.GET)
	public ModelAndView changePasswordForm(ModelAndView mav) {

		mav.setViewName("changePassword");
		return mav;
	}

	@RequestMapping(value = "/user/change-password", method = RequestMethod.POST)
	public ModelAndView changePasswordSubmit(@RequestParam("currentPassword") String current_password,
			@RequestParam("newPassword") String new_password) {
		ModelAndView mav = new ModelAndView();
		BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserInfo userInfo = userInfoService.findUserByEmail(username);
		String encodedPassword = userInfo.getPassword();
		if (!bCryptPassEncoder.matches(current_password, encodedPassword)) {
			mav.addObject("successMessage", "Current Password entered is wrong!!!");
			mav.setView(new RedirectView("../user/change-password"));
			return mav;
		}
		userInfo.setPassword(new_password);
		userInfoService.saveUser(userInfo);
		mav.addObject("successMessage", "Password changed Successfully!!!");
		mav.setView(new RedirectView("../user/home"));
		return mav;
	}

	@RequestMapping(value = "/user/manage-users", method = RequestMethod.GET)
	public ModelAndView showManageUsers(ModelAndView mav) {

		List<UserInfo> userList = userInfoService.getUsers();
		mav.addObject("users", userList);
		mav.setViewName("manageUsers");
		return mav;
	}

	@RequestMapping(value = "/user/manage-users/{action}/{id}", method = RequestMethod.GET)
	public ModelAndView manageUsers(ModelAndView mav, @PathVariable("action") String action,
			@PathVariable("id") int id) {

		UserInfo userInfo = null;
		if (action.equals("edit")) {
			userInfo = userInfoService.getUserById(id);
			mav.addObject("userInfo", userInfo);
			mav.setViewName("editUser");

		} else if (action.equals("delete")) {
			userInfoService.deleteUser(id);
			mav.addObject("successMessage", "User removed successfully!!");
			mav.setView(new RedirectView("/user/manage-users"));
		} else if (action.equals("block")) {
			userInfoService.blockUser(id);
			mav.addObject("successMessage", "User blocked successfully!!");
			mav.setView(new RedirectView("/user/manage-users"));
		} else if (action.equals("unblock")) {
			userInfoService.unBlockUser(id);
			mav.addObject("successMessage", "User is active now!!");
			mav.setView(new RedirectView("/user/manage-users"));
		}

		return mav;
	}

	@RequestMapping(value = "/user/editUserProfile", method = RequestMethod.GET)
	public ModelAndView editUser(ModelAndView mav) {

		UserInfo userInfo = userInfoService.getUserInfo();/*
		userInfo.setFirstName(user.getFirstName());
		userInfo.setLastName(user.getLastName());
		userInfo.setEmail(user.getEmail());
		userInfo.setRole(user.getRole());
		userInfo.setDepartment(user.getDepartment());*/
		mav.addObject("userInfo",userInfo);
		mav.setViewName("editUser");	  		  
		return mav;
	}
	
	@RequestMapping(value = "/user/manage-users/save-user-edit", method = RequestMethod.POST)
	public ModelAndView saveUserEdit(ModelAndView mav, @Valid UserInfo userInfo, BindingResult bindResult) {
		UserInfo user= userInfoService.getUserInfo();
		userInfo.setEmail(user.getEmail());
		userInfo.setPassword(user.getPassword());
		userInfo.setRole(user.getRole());
		userInfoService.updateUser(userInfo);
		UserInfo usrInfo= userInfoService.findUserByEmail(user.getEmail());
		mav.addObject("userInfo",usrInfo);
		if(userInfo.getRole().equals("EMPLOYEE")){
			mav.addObject("successMessage", "User upadated successfully! Awaiting for Admin approval!!");
		}else{
			mav.addObject("successMessage", "User upadated successfully!");
		}
		mav.setViewName("editUser");
		
		return mav;
	}

}
