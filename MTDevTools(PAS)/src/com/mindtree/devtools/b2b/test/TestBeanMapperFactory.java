package com.mindtree.devtools.b2b.test;

import com.mindtree.devtools.b2b.sample.BeanA;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;

public class TestBeanMapperFactory{
	public static void main( String[] args ){
		BeanMapperFactory.getMapperInstance( "BeanAToBeanCMapper", new BeanA() );
	}
}
