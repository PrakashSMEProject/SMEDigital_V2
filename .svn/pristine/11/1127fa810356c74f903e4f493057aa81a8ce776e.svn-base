/**
 * 
 */
package com.mindtree.devtools.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.mindtree.devtools.utils.DevToolsUtils;
import com.mindtree.ruc.cmn.exception.SystemException;

/**
 * This class does unmarshalling using JAXB framework. 
 * @author yudhir
 *
 */
public class JAXBAdaptor implements JavaXMLAdaptor{

	private static JAXBContext jc = null;

	/**
	 * This method returns an instance of JAXBAdaptor based on the package name passed.
	 * @param packageName
	 * @return
	 */
	public static JAXBAdaptor newInstance( String packageName ){
		JAXBAdaptor jb = new JAXBAdaptor( packageName );
		return jb;
	}

	/**
	 * This method creates a new JAXBContext based on the package name passed.
	 * @param packageName
	 */
	public JAXBAdaptor( String packageName ){
		try{
			jc = JAXBContext.newInstance( packageName );
		}
		catch( JAXBException e ){
			throw new SystemException( e, "could not instantiate: ", e.getErrorCode() );
		}
	}

	/**
	 * returns validations bean by parsing xml file based on a schema
	 * validations bean contains the bean whose fields are to be validated against some validators
	 */
	@Override
	public Object unmarshal( String xsdPath, String xmlPath ){

		Unmarshaller u = null;
		Object element = null;
		URL xsdResUrl = null;
		URL xmlResUrl = null;
		try{
			if( !DevToolsUtils.isEmpty( xsdPath ) ){
				xsdResUrl = this.getClass().getClassLoader().getResource( xsdPath );
			}
			else{
				throw new SystemException( "cmn.xsdPathNull", null, "xsdPath is null" );
			}
			Schema schema = SchemaFactory.newInstance( "http://www.w3.org/2001/XMLSchema" ).newSchema( xsdResUrl );

			u = jc.createUnmarshaller();
			u.setSchema( schema );

			if( !DevToolsUtils.isEmpty( xmlPath ) ){
				xmlResUrl = this.getClass().getClassLoader().getResource( xmlPath );
			}
			else{
				throw new SystemException( "cmn.xmlPathNull", null, "xmlPath is null" );
			}
			element =  u.unmarshal( new FileInputStream( new File( xmlResUrl.getFile() ) ) );


		}
		catch( SAXException e ){
			throw new SystemException( "cmn.unmarshallError", e, "could not read the xml file" );
		}
		catch( JAXBException e ){
			throw new SystemException( "cmn.unmarshallError", e, "could not read xml file" );
		}
		catch( FileNotFoundException e ){
			throw new SystemException( "cmn.FileNotFound", e, "Specified File not found" );
		}
		return element;

	}

}
