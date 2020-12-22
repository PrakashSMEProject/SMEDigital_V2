/**
 * 
 */
package com.mindtree.devtools.b2b.core;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ResourceBundle;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.mindtree.devtools.b2b.utils.BeanMapperConstants;
import com.mindtree.devtools.b2b.vo.BeanMapperContextVO;
import com.mindtree.devtools.b2b.vo.BeanMappingVO;
import com.mindtree.devtools.utils.DevToolsUtils;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;

/**
 * @author smurthy
 */
public class VelocityUtils{

	private static final Logger logger = Logger.getLogger( VelocityUtils.class );

	private static final ResourceBundle config = ResourceBundle.getBundle( "com/mindtree/devtools/b2b/config/beanmapper" );

	static{
		try{
			/* Initialize the velocity context */
			Velocity.init();
		}
		catch( Exception e ){
			logger.error( e, "An error occurred while initializing the velocity context." );
		}
	}

	/**
	 * This method adds the class name information to the velocity context.
	 * @param context
	 * @param beanMappingVO
	 */
	private static void addClassName( VelocityContext context, BeanMappingVO beanMappingVO ){
		context.put( BeanMapperConstants.VT_VARIABLE_BEAN_MAPPER_CLASS, beanMappingVO.getId() );
	}

	/**
	 * This method adds the getter and setter methods to the velocity template.
	 * @param context
	 * @param beanMappingVO
	 */
	private static void addMapperMethods( VelocityContext context, BeanMappingVO beanMappingVO ){
		context.put( BeanMapperConstants.VT_VARIABLE_BEAN_MAPPINGS, beanMappingVO );
	}

	/**
	 * This method adds the package information into the generated mapper class.<br/><br/>
	 * 
	 * Order of preference:
	 * (a) Configured class package in mappings.xml
	 * (b) Common package configured in beanmapper.properties
	 * (c) As set in the constant BeanMapperConstants.VT_PACKAGE_PATH
	 * 
	 * @param context
	 * @param beanMappingVO
	 */
	private static void addPackageInfo( VelocityContext context, BeanMappingVO beanMappingVO ){
		String configuredPackage = config.getString( BeanMapperConstants.CONFIG_GEN_CLASSES_PACKAGE );
		String packageName = ( beanMappingVO.getClazzPackage() != null ) ? beanMappingVO.getClazzPackage() : 
			( DevToolsUtils.isEmpty( configuredPackage ) ? BeanMapperConstants.VT_PACKAGE_PATH : configuredPackage );

		context.put( BeanMapperConstants.VT_VARIABLE_PACKAGE, packageName );
	}

	/**
	 * This method adds the source and destination class information to the velocity context.
	 * @param context
	 * @param beanMappingVO
	 */
	private static void addSrcAndDestBeanInformation( VelocityContext context, BeanMappingVO beanMappingVO ){
		context.put( BeanMapperConstants.VT_VARIABLE_CLAZZ_A, beanMappingVO.getClazzA() );
		context.put( BeanMapperConstants.VT_VARIABLE_CLAZZ_B, beanMappingVO.getClazzB() );
	}

	/**
	 * This method updates the velocity context with the contents of the bean mapping.
	 * @param context
	 * @param beanMappingVO
	 * @param beanMapperContextVO
	 * @return context
	 */
	public static VelocityContext constructBeanMapperVelocityContext( VelocityContext context, BeanMappingVO beanMappingVO, BeanMapperContextVO beanMapperContextVO ){

		/* Add the package information into the bean mapper class */
		addPackageInfo( context, beanMappingVO );

		/* This method sets the BeanMapper class name information into the context. */
		addClassName( context, beanMappingVO );

		/* This method sets the source and destination bean type information into the context. */
		addSrcAndDestBeanInformation( context, beanMappingVO );

		/* This method sets the list of setter-getter method combination into the context. */
		addMapperMethods( context, beanMappingVO );

		return context;
	}

	/**
	 * This method returns a writer object that contains the contents of the velocity template updated with the context
	 * information.
	 * @param context
	 * @param template
	 * @return writer
	 */
	public static Writer getTemplateContentWriter( VelocityContext context, Template template ){
		Writer writer = new StringWriter();
		try{
			template.merge( context, writer );
		}
		catch( ResourceNotFoundException e ){
			logger.error( e, "" );
			throw new SystemException( "", e, "" );
		}
		catch( ParseErrorException e ){
			logger.error( e, "" );
			throw new SystemException( "", e, "" );
		}
		catch( MethodInvocationException e ){
			logger.error( e, "" );
			throw new SystemException( "", e, "" );
		}
		catch( IOException e ){
			logger.error( e, "" );
			throw new SystemException( "", e, "" );
		}
		return writer;
	}

	/**
	 * This method returns a new velocity context.
	 * @return VelocityContext
	 */
	public static VelocityContext getVelocityContext(){
		VelocityContext context = new VelocityContext();
		return context;
	}

	/**
	 * This method loads the velocity template using the file location.
	 * @param templateFile
	 * @return template
	 */
	public static Template loadVelocityTemplate( String templateFile ){
		Template template;
		try{
			template = Velocity.getTemplate( templateFile );
		}
		catch( ResourceNotFoundException e ){
			logger.error( e, "" );
			throw new SystemException( "", e, "" );
		}
		catch( ParseErrorException e ){
			logger.error( e, "" );
			throw new SystemException( "", e, "" );
		}
		catch( Exception e ){
			logger.error( e, "" );
			throw new SystemException( "", e, "" );
		}
		return template;
	}
}
