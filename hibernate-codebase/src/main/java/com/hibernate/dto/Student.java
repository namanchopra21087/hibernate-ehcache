/**
 * 
 */
package com.hibernate.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

/**
 * @author Naman
 *
 */
@Entity
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="studentSeq")
	@SequenceGenerator(name="studentSeq",sequenceName="SEQ_STUDENT")
	@Column(name="STUDENT_ID")
	Long studentId;
	@Column(name="STUDENT_NAME")
	String studentName;
	@ManyToMany(fetch=FetchType.LAZY)
	List<Classes> classes=new ArrayList<Classes>();
	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * @return the classes
	 */
	public List<Classes> getClasses() {
		return classes;
	}
	/**
	 * @param classes the classes to set
	 */
	public void setClasses(List<Classes> classes) {
		this.classes = classes;
	}
	
	

}
