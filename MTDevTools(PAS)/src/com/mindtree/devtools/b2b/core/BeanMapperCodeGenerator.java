/**
 * 
 */
package com.mindtree.devtools.b2b.core;

import static org.junit.Assert.assertNotNull;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.mindtree.devtools.b2b.utils.BeanMapperConstants;
import com.mindtree.devtools.b2b.vo.BeanMapperContextVO;
import com.mindtree.devtools.b2b.vo.BeanMappingVO;
import com.mindtree.devtools.utils.ConfigProperties;
import com.mindtree.devtools.utils.Constants;
import com.mindtree.devtools.utils.DevToolsUtils;
import com.mindtree.devtools.xml.JAXBAdaptor;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;



/**
 * @author SMurthy
 * 
 */
public class BeanMapperCodeGenerator{
	private String xmlFileWithPath;

	private static final BeanMapperCodeGenerator instance = new BeanMapperCodeGenerator();

	private static final Logger logger = Logger.getLogger( BeanMapperCodeGenerator.class );
	
	private static final ResourceBundle config = ResourceBundle.getBundle( "com/mindtree/devtools/b2b/config/beanmapper" );
	
	public static BeanMapperCodeGenerator getInstance(){
		return instance;
	}

	private BeanMapperCodeGenerator(){
		
	}

	public BeanMapperCodeGenerator( String xmlFileWithPath ){
		this.xmlFileWithPath = xmlFileWithPath;
	}

	/**
	 * This method constructs the bean mapper context using JAXB adapter based on the configuration properties.
	 * 
	 * @param configProperties
	 * @return object
	 */
	private Object constructBeanMapperContext( ConfigProperties configProperties ){
		JAXBAdaptor jaxbAdapter = (JAXBAdaptor) JAXBAdaptor.newInstance( configProperties.getValue( Constants.JAXB_CONTEXT_PATH ) );
		Object object = jaxbAdapter.unmarshal( configProperties.getValue("XSD_FILE"), configProperties.getValue("XML_FILE") );
		return object;
	}

	/**
	 * This method updates the velocity context with the contents of the bean mapping.
	 * 
	 * @param context
	 * @param beanMappingVO
	 * @param beanMapperContextVO
	 * @return context
	 */
	private VelocityContext constructBeanMapperVelocityContext( VelocityContext context, BeanMappingVO beanMappingVO, BeanMapperContextVO beanMapperContextVO ){
		return VelocityUtils.constructBeanMapperVelocityContext( context, beanMappingVO, beanMapperContextVO );
	}

	/**
	 * This method maps the JAXB elements to BeanMapperContextVO.
	 * 
	 * @param jaxbElements
	 * @return BeanMapperContextVO
	 */
	private BeanMapperContextVO convertToBeanMapperContext( Object jaxbElements ){
		return new JaxbToBeanMapperConverter().convertToBeanMapperContext( jaxbElements );
	}

	/**
	 * This method generates the beanmapper classes using the Bean2BeanMapper
	 * Template.
	 * 
	 * @param beanMapperContextVO
	 */
	private void generateBeanMapperCode( BeanMapperContextVO beanMapperContextVO ){
		Properties beanMapperClassesProperties = new Properties(); 

		/* Load the velocity template */
		//String velocityTemplateFile = this.getClass().getClassLoader().getResource( config.getString( "VELOCITY_TEMPLATE_FILE" ) ).getFile();
		Template velocityTemplate = loadVelocityTemplate( config.getString( "VELOCITY_TEMPLATE_FILE" ) );

		/* Code to convert the beanMapperContextVO to context goes here */
		List<BeanMappingVO> beanMappingVOList = beanMapperContextVO.getBeanMappings();
		assertNotNull( beanMappingVOList );

		/* Iterate over the bean mappings and generate bean mapper classes */
		for( BeanMappingVO beanMappingVO : beanMappingVOList ){
			/* Get the velocity context */
			VelocityContext context = getVelocityContext();

			/* Construct the velocity context content for the bean mapper class */
			constructBeanMapperVelocityContext( context, beanMappingVO, beanMapperContextVO );

			/* Merge the content with the template */
			Writer writer = getTemplateContentWriter( context, velocityTemplate );

			/* Write the bean mapper class to file system. The name of the file would be using bean mapping Id, which is unique. */
			boolean fileCreated = writeToFile( beanMappingVO, writer.toString() );
			
			if( fileCreated ){
				addToProperties( beanMapperClassesProperties, beanMappingVO );
			}
		}

		createPropertiesFile( beanMapperClassesProperties );
	}

	private void createPropertiesFile( Properties beanMapperClassesProperties ){
		FileOutputStream fos = null;
		
		try{
			assertNotNull( beanMapperClassesProperties );
			File file = getFileToBeGenerated( BeanMapperConstants.PROPERTIES_FILE_NAME, BeanMapperConstants.PROPERTIES_FILE_EXTENSION );
			assertNotNull( file );
			fos = new FileOutputStream( file );
			assertNotNull( fos );
			
			beanMapperClassesProperties.store( fos, "This properties file has been generated. Please do not modify." );
		}
		catch( Exception e ){
			logger.error( e, "An error occurred while writing the contents to the file system." );
		}
		finally{
			if( !DevToolsUtils.isEmpty( fos ) ){
				try{
					fos.close();
				}
				catch( IOException e ){
					logger.error( e, "" );
					throw new SystemException( "", e, "" );
				}
			}
		}
		
	}

	private void addToProperties( Properties beanMapperClassesProperties, BeanMappingVO beanMappingVO ){
		/** The following is the preference for the package name configuration:<br>
		 * (a) If there is a package name against the mapping configuration in the XML, then that takes top priority. This
		 *     is a class specific configuration.<br>
		 * (b) Next, if (a) is not configured, then the package configured in beanmapper.properties is used. This is common
		 *     for all generated mapper classes except for the overridden ones as in (a).<br> 
		 * (c) If none of the above are configured, then the one against the constant BeanMapperConstants.VT_PACKAGE_PATH
		 *     is used. */
		String pkgConfigured = DevToolsUtils.getProperty( config, "GEN_CLASSES_PACKAGE" );
		String packageName = ( beanMappingVO.getClazzPackage() != null ) ? beanMappingVO.getClazzPackage() :
			DevToolsUtils.isEmpty( pkgConfigured ) ? BeanMapperConstants.VT_PACKAGE_PATH : pkgConfigured;
		
		beanMapperClassesProperties.put( "REF_" + beanMappingVO.getId(), packageName + "." + beanMappingVO.getId() );
		
		beanMapperClassesProperties.put( DevToolsUtils.concat( beanMappingVO.getClazzA(), "|", beanMappingVO.getClazzB() ), 
										 DevToolsUtils.concat( packageName, ".", beanMappingVO.getId() ) );
	}

	/**
	 * 
	 */
	public void generateMapperClass(){

		/* Construct the bean mapper context information */
		BeanMapperContextVO beanMapperContextVO = getBeanMapperContext();

		/* Generate the bean mapper classes based on the mapping configurations */
		generateBeanMapperCode( beanMapperContextVO );
	}

	/**
	 * This method constructs the configuration information required to map xml
	 * configuration to java classes.
	 * 
	 * @return configuration
	 */
	private ConfigProperties getBeanMapperConfiguration(){
		ConfigProperties configuration = new ConfigProperties();
		
		String xsdFile = config.getString( Constants.XSD_FILE );
		configuration.putValue( Constants.XSD_FILE, xsdFile );
		
		String jaxbElemPkg = config.getString( Constants.JAXB_CONTEXT_PATH );
		configuration.putValue( Constants.JAXB_CONTEXT_PATH, jaxbElemPkg );
		
		String xmlFile = config.getString( Constants.XML_FILE );
		configuration.putValue( Constants.XML_FILE, 
				( DevToolsUtils.isEmpty( this.xmlFileWithPath ) ? xmlFile : this.xmlFileWithPath ) );
		return configuration;
	}
	
	/**
	 * This method constructs the bean mapper context using JAXB adapter.
	 * 
	 * @return BeanMapperContextVO
	 */
	private BeanMapperContextVO getBeanMapperContext(){

		/* Get bean mapper configuration */
		ConfigProperties configProperties = getBeanMapperConfiguration();
		assertNotNull( configProperties );

		/* Get the bean mapper context using JAXB adapter */
		Object object = constructBeanMapperContext( configProperties );
		assertNotNull( object );

		/* Map JAXB generated classes to Bean Mapper VO classes */
		BeanMapperContextVO beanMapperContextVO = convertToBeanMapperContext( object );
		assertNotNull( beanMapperContextVO );

		return beanMapperContextVO;
	}

	/**
	 * This method returns the mapper class file to be generated.
	 * 
	 * @param fileName
	 * @param fileExtension 
	 * @return fileToBeGenerated
	 */
	private File getFileToBeGenerated( String fileName, String fileExtension ){
		String genFilesDir = appendWithDirSep( config.getString( "OUTPUT_FOLDER" ) );
		StringBuffer sb = new StringBuffer();
		sb.append( DevToolsUtils.isEmpty( genFilesDir ) ? BeanMapperConstants.VT_BEAN_MAPPER_FILE_ROOT_DIRECTORY : genFilesDir );
		sb.append( fileName );
		sb.append( fileExtension );
		File fileToBeGenerated = new File( sb.toString() );
		return fileToBeGenerated;
	}

	/**
	 * Appends "/" to the path if not present already.
	 * @param path
	 * @return
	 */
	private String appendWithDirSep( String path ){
		if( !DevToolsUtils.isEmpty( path ) && !path.endsWith( "/" ) ) return path + "/";
		return path;
	}

	/**
	 * This method returns a writer object that contains the contents of the
	 * velocity template updated with the context information.
	 * 
	 * @param context
	 * @param template
	 * @return writer
	 */
	private Writer getTemplateContentWriter( VelocityContext context, Template template ){
		return VelocityUtils.getTemplateContentWriter( context, template );
	}

	/**
	 * This method returns a new velocity context.
	 * 
	 * @return
	 * @throws Exception
	 */
	private VelocityContext getVelocityContext(){
		return VelocityUtils.getVelocityContext();
	}

	/**
	 * This method loads the velocity template using the file location.
	 * 
	 * @param templateFile
	 * @return template
	 * @throws Exception
	 */
	private Template loadVelocityTemplate( String templateFile ){
		Template template = VelocityUtils.loadVelocityTemplate( templateFile );
		return template;
	}

	/**
	 * This method writes the contents of the string to a specified file.
	 * 
	 * @param fileName
	 * @param content
	 */
	private boolean writeToFile( BeanMappingVO beanMappingVO, String content ){
		boolean fileCreated = true;
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		try{
			assertNotNull( beanMappingVO.getId() );
			File file = getFileToBeGenerated( beanMappingVO.getId(), BeanMapperConstants.VT_JAVA_FILE_EXTENSION );
			assertNotNull( file );
			fos = new FileOutputStream( file );
			assertNotNull( fos );
			dos = new DataOutputStream( fos );
			assertNotNull( dos );
			dos.writeBytes( content );
		}
		catch( Exception e ){
			logger.error( e, "An error occurred while writing the contents to the file system." );
			fileCreated = false;
		}
		finally{
			if( !DevToolsUtils.isEmpty( dos ) ){
				try{
					dos.close();
				}
				catch( IOException e ){
					logger.error( e, "" );
					throw new SystemException( "", e, "" );
				}
			}
			if( !DevToolsUtils.isEmpty( fos ) ){
				try{
					fos.close();
				}
				catch( IOException e ){
					logger.error( e, "" );
					throw new SystemException( "", e, "" );
				}
			}
		}
		
		return fileCreated;
	}


}
