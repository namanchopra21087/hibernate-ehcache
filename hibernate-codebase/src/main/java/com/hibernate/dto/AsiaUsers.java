/**
 * 
 */
package com.hibernate.dto;

import javax.persistence.Entity;

/**
 * @author Naman
 *
 */
@Entity
public class AsiaUsers extends UserDetails{
	
	String language;

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	

}
