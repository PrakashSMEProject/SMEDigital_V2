package com.mindtree.devtools.b2b.test;

import com.mindtree.devtools.b2b.core.BeanMapperCodeGenUtils;
import com.mindtree.devtools.b2b.vo.FieldVO;

public class TestBeanMapperUtils{

	/**
	 * @param args
	 */
	public static void main( String[] args ){
		//System.out.println( BeanMapperCodeGenUtils.getAttributeNullCheckChain( "complex.inner.inner2.string" ) );
		FieldVO field = new FieldVO();
		field.setSrcAttribute( "policy.type.lob" );
		
		System.out.println( BeanMapperCodeGenUtils.getSrcAccessorMethodChain( field ) );
	}

}
