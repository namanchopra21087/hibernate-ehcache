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
public class EuropeUsers extends UserDetails{
	
	String country;

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
