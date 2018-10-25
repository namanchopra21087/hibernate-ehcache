/**
 * 
 */
package com.hibernate.dto;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hibernate.constants.DtoConstants;

/**
 * @author Naman
 *
 */

@NamedQueries({@NamedQuery(name=DtoConstants.EMPLOYEE_DEP_ID_NQ,query="from Employee e where e.dp.departmentId<=:"+DtoConstants.DEPARTMENT_ID+" order by e.empName desc"),
	@NamedQuery(name=DtoConstants.EMPLOYEE_ID_NQ,query="from Employee e where e.employeeId<=:"+DtoConstants.EMP_ID+" order by e.empName desc")})
@NamedNativeQuery(name=DtoConstants.EMPLOYEE_ID_NNQ,query="select * from EMPLOYEE e where e.EMPLOYEE_ID<=:"+DtoConstants.EMP_ID+" and e.empName like '%C%'order by e.empName desc",resultClass=Employee.class)
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="EMPLOYEE")
public class Employee {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="employee_sequence")
	@SequenceGenerator(name="employee_sequence",sequenceName="SEQ_EMP",initialValue=1,allocationSize=1)
	@Column(name="EMPLOYEE_ID")
	Long employeeId;
	@Column(name="EMP_NAME")
	String empName;
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
		return empName;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.empName = name;
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
