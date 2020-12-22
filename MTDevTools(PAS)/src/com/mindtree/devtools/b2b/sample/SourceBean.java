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
public class SourceBean{

	private BeanA[] beanAArray;

	private List<BeanA> beanAList;

	private Set<BeanA> beanASet;

	/**
	 * @return the beanAArray
	 */
	public BeanA[] getBeanAArray(){
		return beanAArray;
	}

	/**
	 * @return the beanAList
	 */
	public List<BeanA> getBeanAList(){
		return beanAList;
	}

	/**
	 * @return the beanASet
	 */
	public Set<BeanA> getBeanASet(){
		return beanASet;
	}

	/**
	 * @param beanAArray the beanAArray to set
	 */
	public void setBeanAArray( BeanA[] beanAArray ){
		this.beanAArray = beanAArray;
	}

	/**
	 * @param beanAList the beanAList to set
	 */
	public void setBeanAList( List<BeanA> beanAList ){
		this.beanAList = beanAList;
	}

	/**
	 * @param beanASet the beanASet to set
	 */
	public void setBeanASet( Set<BeanA> beanASet ){
		this.beanASet = beanASet;
	}

}
