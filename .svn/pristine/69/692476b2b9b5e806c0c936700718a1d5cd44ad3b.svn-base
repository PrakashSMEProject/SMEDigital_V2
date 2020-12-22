package com.mindtree.devtools.b2b.test;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import com.mindtree.devtools.b2b.jaxb.Mappings;


public class TestBeanMapperXMLLoading{

	/**
	 * @param args
	 */
	public static void main( String[] args ){
		try{
			JAXBContext c = JAXBContext.newInstance( Mappings.class );
			
			InputStream is = TestBeanMapperXMLLoading.class.getClassLoader().getSystemResourceAsStream( "uk/co/nfumutual/ccp/devtools/b2b/config/beanmapper.xml" );
			Object o = c.createUnmarshaller().unmarshal( new StreamSource( is ), Mappings.class );
			System.out.println();
		}
		catch( JAXBException e ){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
