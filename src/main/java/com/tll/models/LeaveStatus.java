package com.tll.models;

public class LeaveStatus {

	private String leaveId;
	private String annualLeave;
	private String sickLeave;
	private String rhLeave;
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	public String getAnnualLeave() {
		return annualLeave;
	}
	public void setAnnualLeave(String annualLeave) {
		this.annualLeave = annualLeave;
	}
	public String getSickLeave() {
		return sickLeave;
	}
	public void setSickLeave(String sickLeave) {
		this.sickLeave = sickLeave;
	}
	public String getRhLeave() {
		return rhLeave;
	}
	public void setRhLeave(String rhLeave) {
		this.rhLeave = rhLeave;
	}
	@Override
	public String toString() {
		return "LeaveStatus [leaveId=" + leaveId + ", annualLeave=" + annualLeave + ", sickLeave=" + sickLeave
				+ ", rhLeave=" + rhLeave + "]";
	}

}
