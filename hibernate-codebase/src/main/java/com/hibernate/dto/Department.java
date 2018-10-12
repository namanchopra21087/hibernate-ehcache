/**
 * 
 */
package com.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Naman
 *
 */
@Entity
@Table(name="DEPARTMENT")
public class Department {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="department_sequence")
	@SequenceGenerator(name="department_sequence",sequenceName="SEQ_DEP",initialValue=1,allocationSize=100)
	@Column(name="DEPARTMENT_ID")
	private Long departmentId;
	@Column(name="DEPARTMENT_NAME")
	private String departmentName;
	@OneToMany(mappedBy="dp",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Collection<Employee> emp=new ArrayList<Employee>();
	/**
	 * @return the departmentId
	 */
	
	public Long getDepartmentId() {
		return departmentId;
	}
	/**
	 * @return the departmentName
	 */
	
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * @return the emp
	 */
	public Collection<Employee> getEmp() {
		return emp;
	}
	/**
	 * @param emp the emp to set
	 */
	public void setEmp(Collection<Employee> emp) {
		this.emp = emp;
	}
	
}
