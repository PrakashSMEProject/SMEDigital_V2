package com.rsaame.pas.web;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Configurations;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.util.AppConstants;

public class TomcatDataSourceInitContextListener implements ServletContextListener{

	private static final Logger logger = Logger.getLogger( TomcatDataSourceInitContextListener.class );
	DesEncrypter encrypter;
	@Override
	public void contextDestroyed( ServletContextEvent arg0 ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	@Override
	public void contextInitialized( ServletContextEvent arg0 ){
		try{
			/* Identify deployment on AppServer in order to use data source and connection pooling
			 * from the container itself */
			String appServerDeployment = (String) Utils.getSingleValueAppConfig( AppConstants.APP_SERVER_DEPLOYMENT, AppConstants.NON_APP_SERVER_DEPLOYMENT );
			if( !Utils.isEmpty( appServerDeployment ) && !appServerDeployment.equalsIgnoreCase( AppConstants.NON_APP_SERVER_DEPLOYMENT ) ){
				bindDataSourceFromAppServer();
			}
			else{
				bindDataSource();
			}
		}
		catch( NamingException e ){
			if( logger.isDebug() ) logger.debug( "Error in data source name" );
			e.printStackTrace();
		}
		catch( SQLException e ){
			if( logger.isDebug() ) logger.debug( "Error in connecting to database" );
			e.printStackTrace();
		}
	}

	private static final String APP_CONFIG_PROPERTIES = "config/appconfig.properties";

	private void bindDataSource() throws NamingException, SQLException{
		final String[] dss = Configurations.INSTANCE.getMultiValuedProperty( "TOMCAT_DATA_SOURCES", APP_CONFIG_PROPERTIES );
		encrypter = new DesEncrypter("Dbpasskey");
		if( !Utils.isEmpty( dss ) ){
			final Context ctx = new InitialContext();
			for( String ds : dss ){
				createDataSource( ctx, ds );
			}
		}
		else{
			if( logger.isInfo() ) logger.info( "No data source configured" );
		}

	}

	private void createDataSource( Context ctx, String dsName ) throws NamingException{
		final String dataSourceName = dsName.trim();
		if( Utils.isEmpty( dataSourceName ) ) return;

		//String url = getDSProperty( dataSourceName, "_URL" );
		//String user = getDSProperty( dataSourceName, "_USER" );
		//String pwd = getDSProperty( dataSourceName, "_PWD" );
		
		try{
			String[] dbDetails;
			dbDetails = getDetails(dataSourceName);
			DataSource ds = null;
			if(dbDetails != null && dbDetails.length == 3)
				//DataSource ds = createDataSourceInstance( dataSourceName, url, user, pwd );
				ds = createDataSourceInstance( dataSourceName, dbDetails[0], dbDetails[1], dbDetails[2] );
			if( !Utils.isEmpty( ds ) ){
				ctx.rebind( dataSourceName, ds );
			}
		}
		catch( IOException e ){
			// TODO Auto-generated catch block
			throw new SystemException( "", e, "Unable to read property" );
		}
		
	}

	private DataSource createDataSourceInstance( String dsName, String url, String user, String pwd ){
		String dataSourceClassName = Utils.getSingleValueAppConfig( Utils.concat( dsName, "_DSCLASS" ), "oracle.jdbc.pool.OracleDataSource" );

		DataSource ds = null;
		try{
			ds = (DataSource) Utils.newInstance( dataSourceClassName );

			if( "oracle.jdbc.pool.OracleDataSource".equals( dataSourceClassName ) ){
				OracleDataSource ods = (OracleDataSource) ds;
				ods.setDriverType( "oci" );
				ods.setNetworkProtocol( "tcp" );
				ods.setURL( url );
				ods.setUser( user );
				ods.setPassword( pwd );
			}
			else{
				String dsConfigClass = Utils.getSingleValueAppConfig( "DSCONFIGCLASS_" + dataSourceClassName );
				
				if( !Utils.isEmpty( dsConfigClass ) ){
					IDSConfig dsConfig = (IDSConfig) Utils.newInstance( dsConfigClass );
					dsConfig.configureDS( ds, url, user, pwd );
				}
			}
		}
		/* The expected exception is SQLException. However, since we are not instantiating the data source classes
		 * directly, the compiler will not allow catching SQLException. */
		catch( Exception e ){
			if( logger.isDebug() ) logger.debug( "Error in creating the datasource" );
			e.printStackTrace();
		}

		return ds;
	}

	/*private String getDSProperty( String dataSource, String suffix ){
		return Configurations.INSTANCE.getProperty( Utils.concat( dataSource, suffix ), APP_CONFIG_PROPERTIES );
	}*/
	
	/**
	 * If we are using some other database than Oracle, then this interface can be implemented to set the URL,
	 * user and password in the data source class's specific style.<br/><br/>
	 * 
	 * The main purpose of this interface is to avoid dependencies of JARs that we don't need on actual product-
	 * ion environment.
	 */
	public static interface IDSConfig{
		public void configureDS( DataSource ds, String url, String user, String password );
	}

	/* Added the method to fetch data from Env file */
	private String[] getDetails(String dataSourceName) throws IOException {
		String result[] = new String[3];
		String thisLine = "";
		String thisLine1 = "";
		String value[] = new String[5];
		
		try(BufferedReader dbReader = new BufferedReader(new FileReader(Utils.getSingleValueAppConfig( "PASEnvFileLocation" )));) {
			DesEncrypter encrypter = new DesEncrypter("Dbpasskey");// key
			while ((thisLine = dbReader.readLine()) != null) {
				thisLine1 = thisLine;
				if ((thisLine1.trim().indexOf("~``~") == -1)
						&& (thisLine1.trim().substring(0, thisLine1.indexOf("~`~")).contains(dataSourceName))) {
					StringTokenizer str = new StringTokenizer(thisLine1, "~`~");
					int i = 0;int j = 0;
					while (str.hasMoreTokens()) {
						value[i] = str.nextToken();
						if (i == 1)
						{
							result[j] = value[i];j++; 
						} else if (i > 1){
							result[j] = encrypter.decrypt(value[i].trim());// UserName and Password
							j++;
						}i++;
					}
					dbReader.close();
					return result;
				}
			}

			dbReader.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Rebinds context with datasource object via lookup when deployed on APP_SERVER
	 * @throws NamingException
	 */
	private void bindDataSourceFromAppServer() throws NamingException{
		final String[] dss = Configurations.INSTANCE.getMultiValuedProperty( "TOMCAT_DATA_SOURCES", APP_CONFIG_PROPERTIES );
		final Context ctx = new InitialContext();
		for( String ds : dss ){
			ctx.rebind( ds, ctx.lookup( ds ) );
		}
	}
}
