package com.mindtree.devtools.b2b.core;

public class VelocityUtilsTest{

	/**
	 * @param args
	 */
	public static void main( String[] args ){
		// TODO Auto-generated method stub
		Object o = VelocityUtils.loadVelocityTemplate( "com/mindtree/devtools/b2b/config/BeanMapperTemplate.vm" );
		System.out.println( o );
	}

}
