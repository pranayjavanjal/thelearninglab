package com.tll.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "userinfo")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "E-mail cannot be empty!")
    @Email(message = "Please provide a valid email!")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password cannot be empty!")
    @Length(min = 5, message = "Choose atleast five characters for password!")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Please provide a role!")
    @Column(name = "role")
    private String role;

    @NotEmpty(message = "Please provide first name!")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotEmpty(message = "Please provide last name!")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "active")
    private boolean active;
	/*
	 * @NotEmpty(message = "Please provide user department!")
	 * 
	 * @Column(name = "department") private String department;
	 */

    //@NotNull(message = "Please provide Joining date!")
    @Column(name = "dob")
    private String dob;
    
    //@NotNull(message = "Please provide Confirmation date!")
	/*
	 * @Column(name = "confirm_date") private Date confirmDate;
	 */
    @NotEmpty(message = "Contact Number cannot be empty!")
    @Column(name = "number")
    private String number;
    
    @Column(name = "number2")
    private String number2;
    
    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "zip_code")
    private String zipCode;

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfo(int id, String email, String password, String role, String firstName, String middleName,
			String lastName, boolean active, String dob, String number, String number2, String gender, String address,
			String city, String state, String zipCode) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.active = active;
		this.dob = dob;
		this.number = number;
		this.number2 = number2;
		this.gender = gender;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber2() {
		return number2;
	}

	public void setNumber2(String number2) {
		this.number2 = number2;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + ", firstName="
				+ firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", active=" + active + ", dob="
				+ dob + ", number=" + number + ", number2=" + number2 + ", gender=" + gender + ", address=" + address
				+ ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + "]";
	}
    
}
