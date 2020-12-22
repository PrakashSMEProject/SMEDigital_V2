package com.mindtree.devtools.b2b;

import com.mindtree.devtools.b2b.core.BeanMapperCodeGenerator;

/**
 * @author SMurthy
 *
 */
public class B2BCodeGeneratorMain{

	/**
	 * @param args
	 */
	public static void main( String[] args ){
		System.out.println( "Code generation started..." );
		//BeanMapperCodeGenerator codeGenerator = BeanMapperCodeGenerator.getInstance();
		BeanMapperCodeGenerator codeGenerator = new BeanMapperCodeGenerator( "com/mindtree/devtools/b2b/config/samplemappings.xml" );
		codeGenerator.generateMapperClass();
		System.out.println( "Code generation ended successfully." );
	}

}
