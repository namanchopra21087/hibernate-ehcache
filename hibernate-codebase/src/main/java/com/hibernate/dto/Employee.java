/**
 * 
 */
package com.hibernate.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Naman
 *
 */
@Entity
@Table(name="EMPLOYEE")
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="employee_sequence")
	@SequenceGenerator(name="employee_sequence",sequenceName="SEQ_EMP",initialValue=1,allocationSize=1)
	@Column(name="EMPLOYEE_ID")
	Long employeeId;
	@Column(name="NAME")
	String name;
	@Column(name="SALARY")
	BigDecimal salary;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEP_ID")
	Department dp;
	/**
	 * @return the id
	 */
	public Long getId() {
		return employeeId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the salary
	 */
	public BigDecimal getSalary() {
		return salary;
	}
	/**
	 * @param salary the salary to set
	 */
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	/**
	 * @return the dp
	 */
	public Department getDp() {
		return dp;
	}
	/**
	 * @param dp the dp to set
	 */
	public void setDp(Department dp) {
		this.dp = dp;
	}
     

}
