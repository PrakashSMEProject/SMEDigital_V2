/**
 * 
 */
package com.mindtree.devtools.b2b.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author SMurthy
 * 
 */
public class BeanMapperContextVO implements Serializable {

	private static final long serialVersionUID = 1L;

	List<BeanMappingVO> beanMappings;

	public List<BeanMappingVO> getBeanMappings() {
		return beanMappings;
	}

	public void setBeanMappings(List<BeanMappingVO> beanMappings) {
		this.beanMappings = beanMappings;
	}

}
