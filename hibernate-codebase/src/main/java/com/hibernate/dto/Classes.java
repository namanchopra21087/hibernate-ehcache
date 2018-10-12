/**
 * 
 */
package com.hibernate.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
public class Classes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="classSeq")
	@SequenceGenerator(name="classSeq",sequenceName="SEQ_CLASSES")
	@Column(name="CLASS_ID")
	Long classId;
	@Column(name="CLASS_NAME")
	String className;
	@ManyToMany(mappedBy="classes",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	Set<Student> students=new HashSet<Student>();
	/**
	 * @return the classId
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the students
	 */
	public Set<Student> getStudents() {
		return students;
	}
	/**
	 * @param students the students to set
	 */
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	

}
