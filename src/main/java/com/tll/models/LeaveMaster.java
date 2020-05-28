package com.tll.models;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "leave_master")
public class LeaveMaster {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id")
    private int leaveId;
	
	@NotEmpty(message = "Please provide Leave Type!")
	@Column(name = "leave_type")
	private String leaveType;
	
	@Column(name = "total_leaves")
	private String totalLeaves;
	
	@NotEmpty(message = "E-mail cannot be empty!")
    @Email(message = "Please provide a valid email!")
    @Column(name = "emp_email")
    private String empEmail;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getTotalLeaves() {
		return totalLeaves;
	}

	public void setTotalLeaves(String totalLeaves) {
		this.totalLeaves = totalLeaves;
	}
	
	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	@Override
	public String toString() {
		return "LeaveMaster [leaveId=" + leaveId + ", leaveType=" + leaveType + ", totalLeaves=" + totalLeaves
				+ ", empEmail=" + empEmail + "]";
	}
}
