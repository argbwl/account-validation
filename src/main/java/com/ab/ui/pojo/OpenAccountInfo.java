package com.ab.ui.pojo;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class OpenAccountInfo {
	
	@NotBlank(message = "firstName must not null")
	private String firstName;
	private String lastName;
	private String middleName;
	private Date dob;
	private String pob;
	@NotNull(message = "gender must not be null")
	private String gender;
	private String acctType;
	private String pincode;
	private String city;
	private String state;
	@NotNull(message = "contactNo must not null and max length is upto 10 digit")
	@Pattern(regexp = "^\\d{10}$")
	private String contactNo;
	private String address;
	@Email(message = "Inavalid emailId")
	private String emailId;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getPob() {
		return pob;
	}
	public void setPob(String pob) {
		this.pob = pob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
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
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	@Override
	public String toString() {
		return "OpenAccountInfo [firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName
				+ ", dob=" + dob + ", pob=" + pob + ", gender=" + gender + ", acctType=" + acctType + ", pincode="
				+ pincode + ", city=" + city + ", state=" + state + ", contactNo=" + contactNo + ", address=" + address
				+ ", emailId=" + emailId + "]";
	}

}
