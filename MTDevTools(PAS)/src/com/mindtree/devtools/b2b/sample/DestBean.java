/**
 * 
 */
package com.mindtree.devtools.b2b.sample;

import java.util.List;
import java.util.Set;

/**
 * @author smurthy
 *
 */
public class DestBean{

	private BeanC[] beanCArray;

	private List<BeanC> beanCList;

	private Set<BeanC> beanCSet;

	/**
	 * @return the beanCArray
	 */
	public BeanC[] getBeanCArray(){
		return beanCArray;
	}

	/**
	 * @return the beanCList
	 */
	public List<BeanC> getBeanCList(){
		return beanCList;
	}

	/**
	 * @return the beanCSet
	 */
	public Set<BeanC> getBeanCSet(){
		return beanCSet;
	}

	/**
	 * @param beanCArray the beanCArray to set
	 */
	public void setBeanCArray( BeanC[] beanCArray ){
		this.beanCArray = beanCArray;
	}

	/**
	 * @param beanCList the beanCList to set
	 */
	public void setBeanCList( List<BeanC> beanCList ){
		this.beanCList = beanCList;
	}

	/**
	 * @param beanCSet the beanCSet to set
	 */
	public void setBeanCSet( Set<BeanC> beanCSet ){
		this.beanCSet = beanCSet;
	}

}
